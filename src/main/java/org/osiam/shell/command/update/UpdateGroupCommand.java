package org.osiam.shell.command.update;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.client.query.Query;
import org.osiam.client.query.QueryBuilder;
import org.osiam.resources.scim.Group;
import org.osiam.resources.scim.SCIMSearchResult;
import org.osiam.resources.scim.UpdateGroup;
import org.osiam.resources.scim.User;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

/**
 * This class contains commands that can update groups.
 * 
 * @author rainu
 */
public class UpdateGroupCommand {
	private AccessToken accessToken;
	private final OsiamConnector connector;

	public UpdateGroupCommand(AccessToken at, OsiamConnector connector) {
		this.accessToken = at;
		this.connector = connector;
	}
	
	@Command(description = "Add the given user to the given group.")
	public Object addMemberToGroup(
			@Param(name = "userName", description = "The name of the user.")
			String userName,
			@Param(name = "groupName", description = "The name of the group.")
			String groupName){
		
		final Group group = getGroup(groupName);
		if(group == null) return "There is no group with the name \"" + groupName + "\"!";
		
		final User user = getUser(userName);
		if(user == null) return "There is no user with the name \"" + userName + "\"!";
		
		final UpdateGroup update = new UpdateGroup.Builder().addMember(user.getId()).build();
		
		return connector.updateGroup(group.getId(), update, accessToken);
	}

	protected Group getGroup(String groupName) {
		final Query query = new QueryBuilder().filter("displayName eq \"" + groupName + "\"").build();
		final SCIMSearchResult<Group> result = connector.searchGroups(query, accessToken);

		if (result.getTotalResults() > 0) {
			return result.getResources().get(0);
		}

		return null;
	}
	
	private User getUser(String userName) {
		final Query query = new QueryBuilder().filter("userName eq \"" + userName + "\"").build();
		final SCIMSearchResult<User> result = connector.searchUsers(query, accessToken);

		if (result.getTotalResults() > 0) {
			return result.getResources().get(0);
		}

		return null;
	}
}
