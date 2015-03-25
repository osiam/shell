package org.osiam.shell.command;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.exception.ExitException;

/**
 * After the user is logged in, the user can also log out.
 *
 * @author rainu
 */
public class LogoutCommand extends OsiamAccessCommand {

	public LogoutCommand(AccessToken at, OsiamConnector connector) {
		super(at, connector);
	}

	@Command(description="Logout the current user.")
	public void logout() throws ExitException {
		try{
			connector.revokeAccessToken(accessToken);
		}catch(Exception e){
			//do nothing
		}

		//this will cause to exit the current subshell
		throw new ExitException();
	}
}
