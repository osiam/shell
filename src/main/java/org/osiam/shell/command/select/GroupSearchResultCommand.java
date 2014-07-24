package org.osiam.shell.command.select;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.client.query.Query;
import org.osiam.resources.scim.Group;
import org.osiam.resources.scim.SCIMSearchResult;

/**
 * This class is responsible for searching {@link Group}s.
 * 
 * @author rainu
 */
public class GroupSearchResultCommand extends SearchResultCommand<Group> {

	public GroupSearchResultCommand(AccessToken at, OsiamConnector connector, Query query) {
		super(at, connector, query);
	}

	@Override
	protected SCIMSearchResult<Group> search(Query query) {
		return connector.searchGroups(query, accessToken);
	}
}
