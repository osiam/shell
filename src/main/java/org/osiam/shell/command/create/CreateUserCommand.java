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

		if(!inRecordMode()){
			final User user = getUser(userName);
			if(user != null) return "An user with the name \"" + userName + "\" already exists!";
		}

		final CreateUserBuilder builder = new CreateUserBuilder(userName);

		final Shell subShell = ShellBuilder.subshell("create-user[" + userName + "]", shell)
								.behavior()
									.disableExitCommand()
									.addHandler(builder)
								.build();

		output.out()
			.normal("In this subshell you can create a new user. Leave this sub shell via \"commit\" to persist the changes.")
		.println();

		subShell.commandLoop();

		if(inRecordMode()){
			return null;
		}else{
			final User create = builder.build();
			if(create == null) return null;

			return connector.createUser(create, accessToken);
		}
	}

	@Command(description = "Copy an existing user.", startsSubshell = true)
	public Object copyUser(
			@Param(value = "userName", description = "The name of the copied user.")
			String userName,
			@Param(value = "newUserName", description = "The name of the new user.")
			String newUserName) throws IOException{

		final CreateUserBuilder builder;

		if(inRecordMode()){
			builder = new CreateUserBuilder(null, userName);
		}else{
			final User user = getUser(userName);
			if(user == null) return "An user with the name \"" + userName + "\" does not exists!";

			if(getUser(newUserName) != null) return "An user with the name \"" + newUserName + "\" already exists!";

			builder = new CreateUserBuilder(user, newUserName);
		}

		final Shell subShell = ShellBuilder.subshell("create-user[" + newUserName + "]", shell)
								.behavior()
									.disableExitCommand()
									.addHandler(builder)
								.build();

		output.out()
			.normal("In this subshell you can create a new user. Leave this sub shell via \"commit\" to persist the changes.")
		.println();

		subShell.commandLoop();

		if(inRecordMode()){
			return null;
		}else{
			final User create = builder.build();
			if(create == null) return null;

			return connector.createUser(create, accessToken);
		}
	}
}
