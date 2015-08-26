package org.osiam.shell;

import com.beust.jcommander.Parameter;

public class CommandLineArguments {

	@Parameter(names = "--help", description = "Show Help")
	Boolean help = Boolean.FALSE;

	@Parameter(names = "--endpoint")
	String endpoint = "";

	@Parameter(names = "--redirectUri")
	String redirectUri = "";

	@Parameter(names = "--clientId")
	String clientId = "";

	@Parameter(names = "--clientSecret")
	String clientSecret = "";

	@Parameter(names = "--username")
	String username = "";

	@Parameter(names = "--password")
	String password = "";

	public boolean hasConnectionInformation() {
		return !endpoint.isEmpty()
				&& !redirectUri.isEmpty()
				&& !clientId.isEmpty()
				&& !clientSecret.isEmpty();
	}

	public boolean hasLoginInformation() {
		return !username.isEmpty() && !password.isEmpty();
	}
}
