package org.osiam.shell.command.update;

import java.io.IOException;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.resources.scim.UpdateUser;
import org.osiam.resources.scim.User;
import org.osiam.shell.command.OsiamAccessCommand;
import org.osiam.shell.command.update.user.UpdateUserBuilder;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.ShellBuilder;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.handler.OutputDependent;
import de.raysha.lib.jsimpleshell.handler.ShellDependent;
import de.raysha.lib.jsimpleshell.io.OutputBuilder;

/**
 * This class contains commands which can update users.
 * 
 * @author rainu
 */
public class UpdateUserCommand extends OsiamAccessCommand implements ShellDependent, OutputDependent {
	private Shell shell;
	private OutputBuilder output;
	
	public UpdateUserCommand(AccessToken at, OsiamConnector connector) {
		super(at, connector);
	}
	
	@Override
	public void cliSetShell(Shell theShell) {
		this.shell = theShell;
	}
	
	@Override
	public void cliSetOutput(OutputBuilder output) {
		this.output = output;
	}
	
	@Command(description = "Update the current logged-in user.")
	public Object updateMe() throws IOException{
		String userName = connector.getCurrentUserBasic(accessToken).getUserName();
		return updateUser(userName);
	}
	
	@Command(description = "Update the given user.")
	public Object updateUser(
			@Param(value = "userName", description = "The name of the user.")
			String userName) throws IOException{
		
		final User user = getUser(userName);
		if(user == null) return "There is no user with the name \"" + userName + "\"!";
		
		final UpdateUserBuilder builder = new UpdateUserBuilder(user);
		
		final Shell subShell = ShellBuilder.subshell("update-user[" + userName + "]", shell)
									.disableExitCommand()
									.addHandler(builder)
								.build();
		
		output.out()
			.normal("In this subshell you can edit the user. Leave this sub shell via \"commit\" to persist the changes.")
		.println();
		
		subShell.commandLoop();
		
		final UpdateUser update = builder.build();
		if(update == null) return null;
		
		return connector.updateUser(user.getId(), update, accessToken);
	}
}
