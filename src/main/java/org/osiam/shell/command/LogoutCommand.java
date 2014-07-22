package org.osiam.shell.command;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.ShellManageable;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.exception.ExitException;

/**
 * After the user is logged in, the user can also log out.
 * 
 * @author rainu
 */
public class LogoutCommand implements ShellManageable {
	private AccessToken accessToken;
	private final OsiamConnector connector;
	
	public LogoutCommand(AccessToken at, OsiamConnector connector) {
		this.accessToken = at;
		this.connector = connector;
	}
	
	@Override
	public void cliEnterLoop(Shell shell) {
		//DO NOTHING
	}
	
	@Override
	public void cliLeaveLoop(Shell shell) {
		//user enter exit or logout
		connector.revokeAccessToken(accessToken);
	}
	
	@Command(description="Logout the current user.")
	public void logout() throws ExitException {
		//this will cause to exit the current subshell
		throw new ExitException();
	}
}
