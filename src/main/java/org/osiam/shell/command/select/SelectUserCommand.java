package org.osiam.shell.command.select;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;

public class SelectUserCommand {
	private AccessToken at;
	private final OsiamConnector connector;

	public SelectUserCommand(AccessToken at, OsiamConnector connector) {
		this.connector = connector;
	}
	
	
}
