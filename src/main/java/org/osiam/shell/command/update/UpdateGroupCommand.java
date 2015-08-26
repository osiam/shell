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
import org.osiam.resources.scim.Group;
import org.osiam.resources.scim.UpdateGroup;
import org.osiam.resources.scim.User;
import org.osiam.shell.command.AbstractBuilderCommand;
import org.osiam.shell.command.OsiamAccessCommand;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.builder.ShellBuilder;
import de.raysha.lib.jsimpleshell.handler.OutputDependent;
import de.raysha.lib.jsimpleshell.handler.ShellDependent;
import de.raysha.lib.jsimpleshell.io.OutputBuilder;

/**
 * This class contains commands which can update groups.
 *
 * @author rainu
 */
public class UpdateGroupCommand extends OsiamAccessCommand implements ShellDependent, OutputDependent {
	private Shell shell;
	private OutputBuilder output;

	public UpdateGroupCommand(AccessToken at, OsiamConnector connector) {
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

	@Command(description = "Update the given group.", startsSubshell = true)
	public Object updateGroup(
			@Param(value = "groupName", description = "The name of the group.")
			String groupName) throws IOException{

		final UpdateGroupBuilder builder;
		final Group group;

		if(inRecordMode()){
			group = null;
			builder = new UpdateGroupBuilder(null);
		}else{
			group = getGroup(groupName);
			if(group == null) return "There is no group with the name \"" + groupName + "\"!";

			builder = new UpdateGroupBuilder(group);
		}

		final Shell subShell = ShellBuilder.subshell("update-group", shell)
								.behavior()
									.disableExitCommand()
									.addHandler(builder)
								.build();

		output.out()
			.normal("In this subshell you can edit the group. Leave this sub shell via \"commit\" to persist the changes.")
		.println();

		subShell.commandLoop();

		if(inRecordMode()){
			return null;
		}else{
			final UpdateGroup update = builder.build();
			if(update == null) return null;

			return connector.updateGroup(group.getId(), update, accessToken);
		}
	}


	public class UpdateGroupBuilder extends AbstractBuilderCommand<UpdateGroup> {
		private final Group group;
		private UpdateGroup.Builder builder = new UpdateGroup.Builder();

		public UpdateGroupBuilder(Group group) {
			this.group = group;
		}

		@Command(description = "Shows the current (persited) group that will be updated.")
		public Group showGroup(){
			return group;
		}

		@Command(description = "Shows the group state. This state is not persisted yet!")
		public Group showState(){
			return _build().getScimConformUpdateGroup();
		}

		@Command(description = "Add the given user to the given group.")
		public String addUser(
				@Param(value = "userName", description = "The name of the user.")
				String userName){

			final User user = getUser(userName);
			if(user == null) return "There is no user with the name \"" + userName + "\"!";

			builder.addMember(user.getId());

			return null;
		}

		@Command(description = "Remove the given user from the given group.")
		public String removeUser(
				@Param(value = "userName", description = "The name of the user.")
				String userName){

			final User user = getUser(userName);
			if(user == null) return "There is no user with the name \"" + userName + "\"!";

			builder.deleteMember(user.getId());

			return null;
		}

		@Command(description = "Remove all members from the given group.")
		public void removeAllMembers(){
			builder.deleteMembers();
		}

		@Command(description = "Rename the group.")
		public void rename(
				@Param(value = "name", description = "The new group display name.")
				String newName){

			builder.updateDisplayName(newName);
		}

		@Command(description = "Set the externalId for the group.")
		public void setExternalId(
				@Param(value = "externalId", description = "The new externalId for the group.")
				String externalId){

			builder.updateExternalId(externalId);
		}

		@Override
		protected UpdateGroup _build() {
			return builder.build();
		}
	}
}
