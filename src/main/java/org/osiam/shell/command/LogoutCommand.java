package org.osiam.shell.command;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;

import de.raysha.lib.jsimpleshell.annotation.Command;

/**
 * After the user is logged in, the user can also log out.
 * 
 * @author rainu
 */
public class LogoutCommand {
	private AccessToken accessToken;
	private final OsiamConnector connector;
	
	public LogoutCommand(AccessToken at, OsiamConnector connector) {
		this.accessToken = at;
		this.connector = connector;
	}
	
	@Command(description="Logout the current user.")
	public void logout() {
		connector.revokeAccessToken(accessToken);
	}
}
