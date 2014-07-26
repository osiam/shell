package org.osiam.shell.command;

import java.io.IOException;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.client.oauth.Scope;
import org.osiam.shell.command.create.CreateGroupCommand;
import org.osiam.shell.command.delete.DeleteGroupCommand;
import org.osiam.shell.command.delete.DeleteUserCommand;
import org.osiam.shell.command.select.SelectGroupCommand;
import org.osiam.shell.command.select.SelectUserCommand;
import org.osiam.shell.command.update.UpdateGroupCommand;
import org.osiam.shell.command.update.UpdateUserCommand;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.ShellBuilder;
import de.raysha.lib.jsimpleshell.ShellDependent;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.io.InputBuilder;
import de.raysha.lib.jsimpleshell.io.InputDependent;

/**
 * After the connection to a osiam server, the user must login.
 * 
 * @author rainu
 */
public class LoginCommand implements ShellDependent, InputDependent {
	private static final String PARAM_DESCRIPTION_USERNAME = "The username.";

	private static final String PARAM_NAME_USERNAME = "username";

	private final OsiamConnector connector;
	
	private Shell shell;
	private InputBuilder input;
	
	public LoginCommand(OsiamConnector connector) {
		this.connector = connector;
	}
	
	@Override
	public void cliSetShell(Shell theShell) {
		this.shell = theShell;		
	}
	
	@Override
	public void cliSetInput(InputBuilder input) {
		this.input = input;
	}
	
	@Command(description="Login as a user. The password will be requested separately.")
	public void login(
			@Param(name = PARAM_NAME_USERNAME, description = PARAM_DESCRIPTION_USERNAME)
			String userName) throws IOException{
		
		final String password = input.invisibleIn()
									.withPromt("Enter your password: ")
								.readLine();
		
		login(userName, password);
	}
	
	@Command(description="Login as a user.")
	public void login(
			@Param(name = PARAM_NAME_USERNAME, description = PARAM_DESCRIPTION_USERNAME)
			String userName,
			@Param(name = "password", description = "The password for the user.")
			String password) throws IOException{
		
		final AccessToken at = connector.retrieveAccessToken(userName, password, Scope.ALL);
		if(at == null){
			throw new NullPointerException("The retrieved access token is null!");
		}
		
		final Shell subshell = ShellBuilder.subshell(userName, shell)
									.addHandler(new LogoutCommand(at, connector))
									.addAuxHandler(new MiscCommand(at, connector))
									.addHandler(new SelectUserCommand(at, connector))
									.addHandler(new SelectGroupCommand(at, connector))
									.addHandler(new CreateGroupCommand(at, connector))
									.addHandler(new UpdateGroupCommand(at, connector))
									.addHandler(new UpdateUserCommand(at, connector))
									.addHandler(new DeleteGroupCommand(at, connector))
									.addHandler(new DeleteUserCommand(at, connector))
								.build();
		
		subshell.commandLoop();
	}
	
}
