package org.osiam.shell.command.delete;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.resources.scim.User;
import org.osiam.shell.command.OsiamAccessCommand;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

/**
 * This class contains commands which can remove users.
 * 
 * @author rainu
 */
public class DeleteUserCommand extends OsiamAccessCommand {

	public DeleteUserCommand(AccessToken at, OsiamConnector connector) {
		super(at, connector);
	}

	@Command(description = "Delete the given user.")
	public String deleteUser(
			@Param(value = "userName", description = "The name of the user.")
			String userName) {
		final User user = getUser(userName);
		if(user == null) return "There is no user with the username \"" + userName + "\"!";
		
		connector.deleteUser(user.getId(), accessToken);
		return null;
	}
}
