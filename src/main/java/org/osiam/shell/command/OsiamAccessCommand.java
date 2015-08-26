/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 OSIAM, rainu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
