package eu.dedalus.cis.tools.ant.logger.remoteprofiler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.launch.Launcher;

import eu.dedalus.cis.tools.ant.logger.remoteprofiler.business.ProfiledBuild;
import eu.dedalus.cis.tools.ant.logger.remoteprofiler.business.ProfiledTarget;
import eu.dedalus.cis.tools.ant.logger.remoteprofiler.business.ProfiledTask;
import eu.dedalus.cis.tools.ant.logger.remoteprofiler.httpclient.ProfilerHttpClient;

public class RemoteProfilerLogger extends DefaultLogger {
	private ProfiledBuild profiledBuild;
	
	@Override
	public void buildStarted(BuildEvent event) {
		super.buildStarted(event);
		String username = System.getProperty("user.name");
		String hostname="";
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			profiledBuild.getProject().log("[eu.dedalus.cis.tools.ant.logger.remoteprofiler.RemoteProfilerLogger] Cannot retrieve hostname");
		}
		profiledBuild = new ProfiledBuild(username,hostname,new Date(),event.getProject());
	}
	
	@Override
	public void buildFinished(BuildEvent event) {
		super.buildFinished(event);
		profiledBuild.setEnd(new Date());
		
		ProfilerHttpClient client = new ProfilerHttpClient(profiledBuild);
		client.send();
	}
	
	private boolean isTargetEvent(BuildEvent event) {
		if (event.getTarget()==null) {
			profiledBuild.getProject().log("[eu.dedalus.cis.tools.ant.logger.remoteprofiler.RemoteProfilerLogger] targetStarted but no target");
			return false;
		}
		
		return true;
	}
	
	@Override
	public void targetStarted(BuildEvent event) {
		super.targetStarted(event);
		
		if (!isTargetEvent(event)) {
			return;
		}
		
		//at build start, project name is empty
		if(profiledBuild.getProject()==null) {
			profiledBuild.setProject(event.getProject());
		}
		
		ProfiledTarget profiledTarget = new ProfiledTarget(event.getTarget(), new Date());
		
		profiledBuild.addProfiledTarget(profiledTarget);
	}
	
	@Override
	public void targetFinished(BuildEvent event) {
		super.targetFinished(event);
		
		if (!isTargetStarted(event.getTarget()))
			return;
		
		profiledBuild.getProfiledTarget(event.getTarget()).setEnd(new Date());
	}
	
	private boolean isTaskEvent(BuildEvent event) {
		if(event.getTask()==null) {
			profiledBuild.getProject().log("[eu.dedalus.cis.tools.ant.logger.remoteprofiler.RemoteProfilerLogger] taskStarted but null task!");
			return false;
		}
		
		return true;
	}
	
	private boolean isTaskWithTarget(BuildEvent event ) {
		if(!isTaskEvent(event))
			return false;
		
		Target target = event.getTask().getOwningTarget();
		if(target==null) {
			profiledBuild.getProject().log("[eu.dedalus.cis.tools.ant.logger.remoteprofiler.RemoteProfilerLogger] taskStarted but task has empty target!");
			return false;
		}
		
		return true;
	}
	
	@Override
	public void taskStarted(BuildEvent event) {
		super.taskStarted(event);
		
		if(!isTaskWithTarget(event))
			return;
		
		Target target = event.getTask().getOwningTarget();
		if (!isTargetStarted(target)) {
			return;
		}
		
		ProfiledTask profiledTask = new ProfiledTask(event.getTask(),new Date());
		profiledBuild.getProfiledTarget(target).addProfiledTask(profiledTask);
	}
	
	private boolean isTargetStarted(Target target) {
		if (!profiledBuild.containsTarget(target)) {
			profiledBuild.getProject().log("[eu.dedalus.cis.tools.ant.logger.remoteprofiler.RemoteProfilerLogger] targetFinished not registered at start");
			return false;
		}
		
		return true;
	}

	@Override
	public void taskFinished(BuildEvent event) {
		super.taskFinished(event);
		
		if(!isTaskWithTarget(event))
			return;
		
		Task task = event.getTask(); 
		Target target = task.getOwningTarget();
		if(!isTargetStarted(target))
			return;
		
		ProfiledTarget profiledTarget = profiledBuild.getProfiledTarget(target); 
		if(!profiledTarget.containsTask(task)) {
			profiledBuild.getProject().log("[eu.dedalus.cis.tools.ant.logger.remoteprofiler.RemoteProfilerLogger] taskFinished not registered at start");
			return;
		}
		
		profiledTarget.getProfiledTask(task).setEnd(new Date());
	}
	
	/**
	 * Outputs to console the whole profiledBuild
	 * Useful for testing
	 */
	@SuppressWarnings("unused")
	private void print() {
		System.out.println("**** PROFILING DATA *****");
		System.out.println(profiledBuild);
		for(ProfiledTarget target : profiledBuild.getProfiledTargets()) {
			System.out.println("\t"+target);
			for(ProfiledTask task : target.getProfiledTasks()) {
				System.out.println("\t\t"+task);
			}
		}
	}
	
	public static void main(String[] args) {
		List<String> antArgs = new ArrayList<String>();
		antArgs.add("-logger");
		antArgs.add("eu.dedalus.cis.tools.ant.logger.remoteprofiler.RemoteProfilerLogger");
		antArgs.add("-f");
		antArgs.add("src\\test\\java\\testbuild.xml");
		
		Launcher.main(antArgs.toArray(new String[0]));
	}
}
