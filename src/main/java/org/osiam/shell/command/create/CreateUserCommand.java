package org.osiam.shell.command.create;

import java.io.IOException;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.resources.scim.User;
import org.osiam.shell.command.OsiamAccessCommand;
import org.osiam.shell.command.create.user.CreateUserBuilder;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.builder.ShellBuilder;
import de.raysha.lib.jsimpleshell.handler.OutputDependent;
import de.raysha.lib.jsimpleshell.handler.ShellDependent;
import de.raysha.lib.jsimpleshell.io.OutputBuilder;

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

	@Command(description = "Create a new user.", startsSubshell = true)
	public Object createUser(
			@Param(value = "userName", description = "The name of the user.")
			String userName) throws IOException{

		final User user = getUser(userName);
		if(user != null) return "An user with the name \"" + userName + "\" already exists!";

		final CreateUserBuilder builder = new CreateUserBuilder(userName);

		final Shell subShell = ShellBuilder.subshell("create-user[" + userName + "]", shell)
								.behavior()
									.disableExitCommand()
									.addHandler(builder)
								.back().build();

		output.out()
			.normal("In this subshell you can create a new user. Leave this sub shell via \"commit\" to persist the changes.")
		.println();

		subShell.commandLoop();

		final User create = builder.build();
		if(create == null) return null;

		return connector.createUser(create, accessToken);
	}

	@Command(description = "Copy an existing user.", startsSubshell = true)
	public Object copyUser(
			@Param(value = "userName", description = "The name of the copied user.")
			String userName,
			@Param(value = "newUserName", description = "The name of the new user.")
			String newUserName) throws IOException{

		final User user = getUser(userName);
		if(user == null) return "An user with the name \"" + userName + "\" does not exists!";

		if(getUser(newUserName) != null) return "An user with the name \"" + newUserName + "\" already exists!";

		final CreateUserBuilder builder = new CreateUserBuilder(user, newUserName);

		final Shell subShell = ShellBuilder.subshell("create-user[" + newUserName + "]", shell)
								.behavior()
									.disableExitCommand()
									.addHandler(builder)
								.back().build();

		output.out()
			.normal("In this subshell you can create a new user. Leave this sub shell via \"commit\" to persist the changes.")
		.println();

		subShell.commandLoop();

		final User create = builder.build();
		if(create == null) return null;

		return connector.createUser(create, accessToken);
	}
}
