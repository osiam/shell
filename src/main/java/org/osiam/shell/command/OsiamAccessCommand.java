package org.osiam.shell.command;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.client.query.Query;
import org.osiam.client.query.QueryBuilder;
import org.osiam.resources.scim.Group;
import org.osiam.resources.scim.SCIMSearchResult;
import org.osiam.resources.scim.User;

/**
 * This class contains some command methods that can be used by Command-classes
 * that have already access to osiam (a connection AND a access token)
 *
 * @author rainu
 */
public abstract class OsiamAccessCommand extends AbstractOsiamCommand {
	protected AccessToken accessToken;
	protected final OsiamConnector connector;

	public OsiamAccessCommand(AccessToken at, OsiamConnector connector) {
		this.accessToken = at;
		this.connector = connector;
	}

	protected Group getGroup(String groupName) {
		final Query query = new QueryBuilder().filter("displayName eq \"" + groupName + "\"").build();
		final SCIMSearchResult<Group> result = connector.searchGroups(query, accessToken);

		if (result.getTotalResults() > 0) {
			return result.getResources().get(0);
		}

		return null;
	}

	protected User getUser(String userName) {
		final Query query = new QueryBuilder().filter("userName eq \"" + userName + "\"").build();
		final SCIMSearchResult<User> result = connector.searchUsers(query, accessToken);

		if (result.getTotalResults() > 0) {
			return result.getResources().get(0);
		}

		return null;
	}
}
