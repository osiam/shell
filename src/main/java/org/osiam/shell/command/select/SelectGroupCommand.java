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
package org.osiam.shell.command.select;

import java.io.IOException;
import java.util.List;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.client.query.QueryBuilder;
import org.osiam.resources.scim.Group;
import org.osiam.shell.command.OsiamAccessCommand;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.builder.ShellBuilder;
import de.raysha.lib.jsimpleshell.exception.CLIException;
import de.raysha.lib.jsimpleshell.handler.ShellDependent;

/**
 * This class contains all commands with that show(select) groups.
 *
 * @author rainu
 */
public class SelectGroupCommand extends OsiamAccessCommand implements ShellDependent {

	private Shell shell;

	public SelectGroupCommand(AccessToken at, OsiamConnector connector) {
		super(at, connector);
	}

	@Override
	public void cliSetShell(Shell theShell) {
		this.shell = theShell;
	}

	@Command(description = "Show the given group.")
	public Object showGroup(
			@Param(value = "groupName", description = "The name of the group.")
			String groupName){

		Group group = getGroup(groupName);

		if(group == null) return "There is no group with the name \"" + groupName + "\"!";
		return group;
	}

	@Command(description = "Show all groups.")
	public List<Group> showAllGroups(){
		return connector.getAllGroups(accessToken);
	}

	@Command(description = "Search for existing groups by a given filter.", startsSubshell = true)
	public void searchGroups(
			@Param(value = "filter", description = "The filter string.")
			String filter) throws CLIException, IOException{

		searchGroups(filter, 1L, 5, "displayName", true);
	}

	@Command(description = "Search for existing groups by a given Query.", startsSubshell = true)
	public void searchGroups(
			@Param(value = "filter", description = "The filter string.")
			String filter,
			@Param(value="index", description = "The index (1-based) of the first resource.")
			Long startIndex,
			@Param(value = "limit", description = "The number of returned groups per page.")
			Integer limit,
			@Param(value = "orderBy", description = "Sort the groups by the given attribute.")
			String orderBy,
			@Param(value = "asc", description = "Should the groups sorted ascending?")
			Boolean ascending) throws CLIException, IOException{

		final QueryBuilder builder = new QueryBuilder()
								.filter(filter)
								.startIndex(startIndex)
								.count(limit);

		if(orderBy != null){
			if(ascending){
				builder.ascending(orderBy);
			}else{
				builder.descending(orderBy);
			}
		}

		StringBuilder prompt = new StringBuilder("searchGroups(\"");
		prompt.append(filter);
		prompt.append("\" ");
		prompt.append(ascending ? "v|" : "^|");
		prompt.append(orderBy);
		prompt.append(ascending ? "|v" : "|^");
		prompt.append(")");

		final Shell subShell = ShellBuilder.subshell(prompt.toString(), shell)
								.behavior()
									.addHandler(new GroupSearchResultCommand(accessToken, connector, builder.build()))
								.build();

		subShell.processLine(GroupSearchResultCommand.COMMAND_NAME_NEXT);
		subShell.commandLoop();
	}
}
