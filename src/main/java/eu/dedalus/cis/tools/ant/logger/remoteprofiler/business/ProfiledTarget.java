package eu.dedalus.cis.tools.ant.logger.remoteprofiler.business;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;

public class ProfiledTarget {
	private Target target;
	private Date start;
	private Date end;
	private Map<Task,ProfiledTask> taskMap = new HashMap<>();
	private List<ProfiledTask> profiledTasks = new Vector<>();
	
	public ProfiledTarget(Target target, Date start) {
		super();
		this.target = target;
		this.start = start;
	}
	
	public Target getTarget() {
		return target;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
	public ProfiledTask getProfiledTask(Task task) {
		return taskMap.get(task);
	}
	
	public List<ProfiledTask> getProfiledTasks() {
		return profiledTasks;
	}
	
	public void addProfiledTask(ProfiledTask task) {
		profiledTasks.add(task);
		taskMap.put(task.getTask(), task);
	}
	
	public boolean containsTask(Task task) {
		return taskMap.containsKey(task); 
	}
	
	@Override
	public String toString() {
		return "ProfiledTarget [target=" + target.getName() + ", start=" + start + ", end=" + end + "]";
	}
	
	
}
