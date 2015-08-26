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
package org.osiam.shell.command.update;

import java.io.IOException;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.resources.scim.UpdateUser;
import org.osiam.resources.scim.User;
import org.osiam.shell.command.OsiamAccessCommand;
import org.osiam.shell.command.update.user.UpdateUserBuilder;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.builder.ShellBuilder;
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

	@Command(description = "Update the current logged-in user.", startsSubshell = true)
	public Object updateMe() throws IOException{
		String userName = connector.getCurrentUserBasic(accessToken).getUserName();
		return updateUser(userName);
	}

	@Command(description = "Update the given user.", startsSubshell = true)
	public Object updateUser(
			@Param(value = "userName", description = "The name of the user.")
			String userName) throws IOException{

		final UpdateUserBuilder builder;
		final User user;

		if(inRecordMode()){
			user = null;
			builder = new UpdateUserBuilder(null);
		}else{
			user = getUser(userName);
			if(user == null) return "There is no user with the name \"" + userName + "\"!";

			builder = new UpdateUserBuilder(user);
		}

		final Shell subShell = ShellBuilder.subshell("update-user[" + userName + "]", shell)
								.behavior()
									.disableExitCommand()
									.addHandler(builder)
								.build();

		output.out()
			.normal("In this subshell you can edit the user. Leave this sub shell via \"commit\" to persist the changes.")
		.println();

		subShell.commandLoop();

		if(inRecordMode()){
			return null;
		}else{
			final UpdateUser update = builder.build();
			if(update == null) return null;

			return connector.updateUser(user.getId(), update, accessToken);
		}
	}
}
