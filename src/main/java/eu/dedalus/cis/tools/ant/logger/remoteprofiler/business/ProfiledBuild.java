package eu.dedalus.cis.tools.ant.logger.remoteprofiler.business;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.dedalus.cis.tools.ant.logger.remoteprofiler.dto.ProfiledBuildDTO;

public class ProfiledBuild {
	private String username;
	private String hostname;
	private Date start;
	private Date end;
	private Project project;
	private Map<Target, ProfiledTarget> targetsMap = new HashMap<>();
	private List<ProfiledTarget> profiledTargets = new Vector<>();
	
	public ProfiledBuild(String username, String hostname, Date start, Project project) {
		super();
		this.username = username;
		this.hostname = hostname;
		this.start = start;
		this.project = project;
	}
	
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
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ProfiledTarget getProfiledTarget(Target target) {
		return targetsMap.get(target);
	}
	
	public List<ProfiledTarget> getProfiledTargets() {
		return profiledTargets;
	}

	public void addProfiledTarget(ProfiledTarget target) {
		profiledTargets.add(target);
		targetsMap.put(target.getTarget(), target);
	}
	
	public boolean containsTarget(Target target) {
		return targetsMap.containsKey(target);
	}

	public String toJSON() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		return mapper.writeValueAsString(new ProfiledBuildDTO(this));
	}
	
	@Override
	public String toString() {
		return "ProfiledBuild [username=" + username + ", hostname=" + hostname + ", start=" + start + ", end=" + end
				+ ", project=" + project + "]";
	}
}
