package org.osiam.shell.command.select;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.client.query.Query;
import org.osiam.resources.scim.SCIMSearchResult;
import org.osiam.resources.scim.User;

/**
 * This class is responsible for searching {@link User}s.
 * 
 * @author rainu
 */
public class UserSearchResultCommand extends SearchResultCommand<User> {

	public UserSearchResultCommand(AccessToken at, OsiamConnector connector, Query query) {
		super(at, connector, query);
	}

	@Override
	protected SCIMSearchResult<User> search(Query query) {
		return connector.searchUsers(query, accessToken);
	}
}
