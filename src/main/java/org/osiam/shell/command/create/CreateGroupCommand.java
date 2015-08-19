package org.osiam.shell.command.create;

import java.io.IOException;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.resources.scim.Group;
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
 * This class contains commands which can create groups.
 *
 * @author rainu
 */
public class CreateGroupCommand extends OsiamAccessCommand implements ShellDependent, OutputDependent {
	private Shell shell;
	private OutputBuilder output;

	public CreateGroupCommand(AccessToken at, OsiamConnector connector) {
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

	@Command(description="Create a new group.", startsSubshell = true)
	public Group createGroup(
			@Param(value = "name", description = "The display name of the new group.")
			String displayName) throws IOException{

		final GroupBuilder builder = new GroupBuilder(displayName);

		final Shell subShell = ShellBuilder.subshell("createGroup[" + displayName + "]", shell)
								.behavior()
									.disableExitCommand()
									.addHandler(builder)
								.build();

		output.out()
			.normal("In this subshell you can build your group. Leave this sub shell to persist the group.")
		.println();

		subShell.commandLoop();

		if(inRecordMode()){
			return null;
		}else{
			final Group toCreate = builder.build();

			if(toCreate == null) return null;
			return connector.createGroup(toCreate, accessToken);
		}
	}

	@Command(description="Copy a existing group.", startsSubshell = true)
	public Object copyGroup(
			@Param(value = "name", description = "The display name of the persited group.")
			String displayName,
			@Param(value = "newName", description = "The display name of the new group.")
			String newDisplayName) throws IOException{

		final GroupBuilder builder;

		if(inRecordMode()){
			builder = new GroupBuilder(null, displayName);
		}else{
			final Group group = getGroup(displayName);
			if(group == null) return "A group with the name \"" + displayName + "\" does not exists!";

			if(getGroup(newDisplayName) != null) return "A group with the name \"" + newDisplayName + "\" already exists!";

			builder = new GroupBuilder(group, newDisplayName);
		}

		final Shell subShell = ShellBuilder.subshell("createGroup[" + newDisplayName +"]", shell)
								.behavior()
									.disableExitCommand()
									.addHandler(builder)
								.build();

		output.out()
			.normal("In this subshell you can build your group. Leave this sub shell to persist the group.")
		.println();

		subShell.commandLoop();

		if(inRecordMode()){
			return null;
		}else{
			final Group toCreate = builder.build();

			if(toCreate == null) return null;
			return connector.createGroup(toCreate, accessToken);
		}
	}

	public class GroupBuilder extends AbstractBuilderCommand<Group> {
		private final Group.Builder groupBuilder;

		public GroupBuilder(String displayName) {
			groupBuilder = new Group.Builder(displayName);
		}

		public GroupBuilder(Group group, String displayName) {
			groupBuilder = new Group.Builder(displayName, group);
		}

		@Command(description = "Shows the group state. This state is not persisted yet!")
		public Group showState() {
			return _build();
		}

		@Command
		public void setExternalId(
				@Param(value = "externalId", description = "The external id for the new group.")
				String externalId){

			groupBuilder.setExternalId(externalId);
		}

		protected Group _build() {
			return groupBuilder.build();
		}

	}
}
