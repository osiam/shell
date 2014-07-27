package org.osiam.shell.command.create;

import java.io.IOException;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.resources.scim.UpdateUser;
import org.osiam.resources.scim.User;
import org.osiam.shell.command.OsiamAccessCommand;
import org.osiam.shell.command.create.user.CreateUserBuilder;
import org.osiam.shell.command.update.user.UpdateUserBuilder;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.ShellBuilder;
import de.raysha.lib.jsimpleshell.ShellDependent;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.io.OutputBuilder;
import de.raysha.lib.jsimpleshell.io.OutputDependent;

/**
 * This class contains commands which can create users.
 * 
 * @author rainu
 */
public class CreateUserCommand extends OsiamAccessCommand implements ShellDependent, OutputDependent {
	private Shell shell;
	private OutputBuilder output;
	
	public CreateUserCommand(AccessToken at, OsiamConnector connector) {
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
	
	@Command(description = "Create a new user.")
	public Object createUser(
			@Param(name = "userName", description = "The name of the user.")
			String userName) throws IOException{
		
		final User user = getUser(userName);
		if(user != null) return "A user with the name \"" + userName + "\" already exists!";
		
		final CreateUserBuilder builder = new CreateUserBuilder(userName);
		
		final Shell subShell = ShellBuilder.subshell("create-user[" + userName + "]", shell)
									.disableExitCommand()
									.addHandler(builder)
								.build();
		
		output.out()
			.normal("In this subshell you can create a new user. Leave this sub shell via \"commit\" to persist the changes.")
		.println();
		
		subShell.commandLoop();
		
		final User create = builder.build();
		if(create == null) return null;
		
		return connector.createUser(create, accessToken);
	}
}
