package eu.dedalus.cis.tools.ant.logger.remoteprofiler.dto;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import eu.dedalus.cis.tools.ant.logger.remoteprofiler.business.ProfiledTarget;
import eu.dedalus.cis.tools.ant.logger.remoteprofiler.business.ProfiledTask;

public class ProfiledTargetDTO {
	private String targetName;
	private Date start;
	private Date end;
	private List<ProfiledTaskDTO> tasks = new Vector<ProfiledTaskDTO>();

	public ProfiledTargetDTO() {};

	public ProfiledTargetDTO(ProfiledTarget target) {
		targetName = target.getTarget().getName();
		start = (Date) target.getStart().clone();
		end = (Date) target.getEnd().clone();
		for (ProfiledTask task : target.getProfiledTasks()) {
			tasks.add(new ProfiledTaskDTO(task));
		}
	}

	public String getTargetName() {
		return targetName;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	public List<ProfiledTaskDTO> getTasks() {
		return tasks;
	}
}
