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

import java.io.IOException;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.client.oauth.Scope;
import org.osiam.shell.command.create.CreateGroupCommand;
import org.osiam.shell.command.create.CreateUserCommand;
import org.osiam.shell.command.delete.DeleteGroupCommand;
import org.osiam.shell.command.delete.DeleteUserCommand;
import org.osiam.shell.command.select.SelectGroupCommand;
import org.osiam.shell.command.select.SelectUserCommand;
import org.osiam.shell.command.update.UpdateGroupCommand;
import org.osiam.shell.command.update.UpdateUserCommand;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Inject;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.builder.ShellBuilder;
import de.raysha.lib.jsimpleshell.io.InputBuilder;

/**
 * After the connection to a osiam server, the user must login.
 *
 * @author rainu
 */
public class LoginCommand extends AbstractOsiamCommand {
	private static final String PARAM_DESCRIPTION_USERNAME = "The username.";

	private static final String PARAM_NAME_USERNAME = "username";

	private final OsiamConnector connector;

	@Inject
	private InputBuilder input;

	public LoginCommand(OsiamConnector connector) {
		this.connector = connector;
	}

	@Command(description="Login as a user. The password will be requested separately.", startsSubshell = true)
	public void login(
			@Param(value = PARAM_NAME_USERNAME, description = PARAM_DESCRIPTION_USERNAME)
			String userName) throws IOException{

		if(inRecordMode()){
			login(userName, null);
			return;
		}

		final String password = input.invisibleIn()
									.withPromt("Enter your password: ")
								.readLine();

		login(userName, password);
	}

	@Command(description="Login as a user.", startsSubshell = true)
	public void login(
			@Param(value = PARAM_NAME_USERNAME, description = PARAM_DESCRIPTION_USERNAME)
			String userName,
			@Param(value = "password", description = "The password for the user.")
			String password) throws IOException{

		AccessToken at = null;

		if(!inRecordMode()){
			at = connector.retrieveAccessToken(userName, password, Scope.ADMIN);
			if(at == null){
				throw new NullPointerException("The retrieved access token is null!");
			}
		}

		final Shell subshell = ShellBuilder.subshell("@" + userName, shell)
								.behavior()
									.disableExitCommand()
									.addHandler(new LogoutCommand(at, connector))
									.addAuxHandler(new MiscCommand(at, connector))
									.addHandler(new SelectUserCommand(at, connector))
									.addHandler(new SelectGroupCommand(at, connector))
									.addHandler(new CreateGroupCommand(at, connector))
									.addHandler(new CreateUserCommand(at, connector))
									.addHandler(new UpdateGroupCommand(at, connector))
									.addHandler(new UpdateUserCommand(at, connector))
									.addHandler(new DeleteGroupCommand(at, connector))
									.addHandler(new DeleteUserCommand(at, connector))
								.build();

		subshell.commandLoop();
	}

}
