package eu.dedalus.cis.tools.ant.logger.remoteprofiler.dto;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import eu.dedalus.cis.tools.ant.logger.remoteprofiler.business.ProfiledBuild;
import eu.dedalus.cis.tools.ant.logger.remoteprofiler.business.ProfiledTarget;

public class ProfiledBuildDTO {
	private String username;
	private String hostname;
	private Date start;
	private Date end;
	private String projectName;
	private List<ProfiledTargetDTO> targets=new Vector<ProfiledTargetDTO>();
	
	public ProfiledBuildDTO() {};
	
	public ProfiledBuildDTO(ProfiledBuild build) {
		username = build.getUsername();
		hostname = build.getHostname();
		start = (Date) build.getStart().clone();
		end = (Date) build.getEnd().clone();
		projectName = build.getProject().getName();
		for(ProfiledTarget target : build.getProfiledTargets()) {
			targets.add(new ProfiledTargetDTO(target));
		}
	}

	public String getUsername() {
		return username;
	}

	public String getHostname() {
		return hostname;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	public String getProjectName() {
		return projectName;
	}

	public List<ProfiledTargetDTO> getTargets() {
		return targets;
	}
}
