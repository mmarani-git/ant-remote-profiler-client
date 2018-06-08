package eu.dedalus.cis.tools.ant.logger.remoteprofiler.dto;

import java.util.Date;

import eu.dedalus.cis.tools.ant.logger.remoteprofiler.business.ProfiledTask;

public class ProfiledTaskDTO {
	private String taskName;
	private Date start;
	private Date end;
	
	public ProfiledTaskDTO(ProfiledTask task) {
		this.taskName=task.getTask().getTaskName();
		this.start=task.getStart();
		this.end = task.getEnd();
	}

	public String getTaskName() {
		return taskName;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}	
}
