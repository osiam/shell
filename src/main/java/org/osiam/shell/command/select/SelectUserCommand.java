package org.osiam.shell.command.select;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.resources.scim.User;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

/**
 * This class contains all commands with that show(select) users.
 * 
 * @author rainu
 */
public class SelectUserCommand {
	private AccessToken accessToken;
	private final OsiamConnector connector;

	public SelectUserCommand(AccessToken at, OsiamConnector connector) {
		this.accessToken = at;
		this.connector = connector;
	}
	
	@Command(description="Show the user with the given user id.")
	public User showUser(
			@Param(name = "userId", description = "The id of the user that should be shown.")
			String userId){
		
		return connector.getUser(userId, accessToken);
	}
	
	@Command(description="Show the current logged in user (you).")
	public User showMe(){
		return connector.getCurrentUser(accessToken);
	}
}
