package eu.dedalus.cis.tools.ant.logger.remoteprofiler;

import java.util.Date;

public class LoggingEvent {
	private String username;
	private String hostname;
	private Date start;
	private Date end;
	private String project;
	private String task;
	private String target;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
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
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
	@Override
	public String toString() {
		return "LoggingEvent [username=" + username + ", hostname=" + hostname + ", start=" + start + ", end=" + end
				+ ", project=" + project + ", task=" + task + ", target=" + target + "]";
	}
}
