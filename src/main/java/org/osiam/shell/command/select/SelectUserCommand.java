package org.osiam.shell.command.select;

import java.io.IOException;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.client.query.QueryBuilder;
import org.osiam.resources.scim.User;
import org.osiam.shell.command.OsiamAccessCommand;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.ShellBuilder;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.exception.CLIException;
import de.raysha.lib.jsimpleshell.handler.ShellDependent;

/**
 * This class contains all commands with that show(select) users.
 * 
 * @author rainu
 */
public class SelectUserCommand extends OsiamAccessCommand implements ShellDependent {
	private Shell shell;
	
	public SelectUserCommand(AccessToken at, OsiamConnector connector) {
		super(at, connector);
	}
	
	@Override
	public void cliSetShell(Shell theShell) {
		this.shell = theShell;
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
	
	@Command(description = "Search for existing users by a given filter.")
	public void searchUsers(
			@Param(name = "filter", description = "The filter string.")
			String filter) throws CLIException, IOException{

		searchUsers(filter, 1L, 1, "userName", true);
	}
	
	@Command(description = "Search for existing users by a given Query.")
	public void searchUsers(
			@Param(name = "filter", description = "The filter string.")
			String filter,
			@Param(name="index", description = "The index (1-based) of the first resource.")
			Long startIndex,
			@Param(name = "limit", description = "The number of returned users per page.")
			Integer limit,
			@Param(name = "orderBy", description = "Sort the users by the given attribute.")
			String orderBy,
			@Param(name = "asc", description = "Should the users sorted ascending?")
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
		
		StringBuilder prompt = new StringBuilder("searchUser(\"");
		prompt.append(filter);
		prompt.append("\" ");
		prompt.append(ascending ? "v|" : "^|");
		prompt.append(orderBy);
		prompt.append(ascending ? "|v" : "|^");
		prompt.append(")");
		
		final Shell subShell = ShellBuilder.subshell(prompt.toString(), shell)
									.addHandler(new UserSearchResultCommand(accessToken, connector, builder.build()))
								.build();
		
		subShell.processLine(UserSearchResultCommand.COMMAND_NAME_NEXT);
		subShell.commandLoop();
	}
}
