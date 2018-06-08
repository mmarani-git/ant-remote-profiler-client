package eu.dedalus.cis.tools.ant.logger.remoteprofiler.business;

import java.util.Date;

import org.apache.tools.ant.Task;

public class ProfiledTask {
	private Task task;
	private Date start;
	private Date end;

	
	public ProfiledTask(Task task, Date start) {
		super();
		this.task = task;
		this.start = start;
	}

	public Task getTask() {
		return task;
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

	@Override
	public String toString() {
		return "ProfiledTask [name=" + task.getTaskName() + ", type=" + task.getTaskType() + ", start=" + start
				+ ", end=" + end + "]";
	}
}
