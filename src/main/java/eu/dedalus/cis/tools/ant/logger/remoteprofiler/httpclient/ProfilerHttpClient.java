package eu.dedalus.cis.tools.ant.logger.remoteprofiler.httpclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import eu.dedalus.cis.tools.ant.logger.remoteprofiler.business.ProfiledBuild;

public class ProfilerHttpClient {
	private String hostname;
	private int port = 0;
	private ProfiledBuild profiledBuild;
	
	public ProfilerHttpClient(ProfiledBuild profiledBuild, String hostname, int port) {
		super();
		this.hostname = hostname;
		this.port = port;
		this.profiledBuild=profiledBuild;
	}

	public ProfilerHttpClient(ProfiledBuild profiledBuild) {
		String methodName = "[eu.dedalus.cis.tools.ant.logger.remoteprofiler.httpclient.ProfilerHttpClient.ProfilerHttpClient()]";
		this.profiledBuild=profiledBuild;
		profiledBuild.getProject().log(methodName + " Initializing transmission to profiling server...");

		hostname = System.getProperty("RemoteProfilerHost");
		try {
			port = Integer.parseInt(System.getProperty("RemoteProfilerPort"));
		} catch (NumberFormatException n) {
			profiledBuild.getProject().log(
					methodName + " invalid port: RemoteProfilerPort=" + System.getProperty("RemoteProfilerPort"));
		}
	}

	public void send() {
		String methodName="[eu.dedalus.cis.tools.ant.logger.remoteprofiler.httpclient.ProfilerHttpClient.send(ProfiledBuild)] ";
		
		if (hostname == null || port == 0) {
			profiledBuild.getProject().log(methodName + "hostname and/or port not set");
			return;
		}

		if (!pingHost(hostname, port, 1000)) {
			profiledBuild.getProject().log("cannot ping " + hostname + " on port " + port);
			return;
		}
		
		profiledBuild.getProject().log(methodName + " Sending profiling data...");
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost("http://" + hostname + ":" + port + "/antremoteprofilerserver/RemoteProfilingServlet");
		StringEntity stringEntity = null;
		try {
			stringEntity = new StringEntity(profiledBuild.toJSON());
			request.addHeader("content-type", "application/x-www-form-urlencoded");
			request.setEntity(stringEntity);
			httpClient.execute(request);
		} catch (IOException e) {
			profiledBuild.getProject().log(
					"[eu.dedalus.cis.tools.ant.logger.remoteprofiler.httpclient.ProfilerHttpClient.send(ProfiledBuild)] Cannot process send request");
			return;
		}

		profiledBuild.getProject().log("Profiling data sent.");
	}

	public static boolean pingHost(String host, int port, int timeout) {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(host, port), timeout);
			return true;
		} catch (IOException e) {
			return false; // Either timeout or unreachable or failed DNS lookup.
		}
	}
}
