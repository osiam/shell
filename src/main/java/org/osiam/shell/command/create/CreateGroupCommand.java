package org.osiam.shell.command.create;

import java.io.IOException;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.resources.scim.Group;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.ShellBuilder;
import de.raysha.lib.jsimpleshell.ShellDependent;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.io.OutputBuilder;
import de.raysha.lib.jsimpleshell.io.OutputDependent;

/**
 * This class contains commands that can creates groups.
 * 
 * @author rainu
 */
public class CreateGroupCommand implements ShellDependent, OutputDependent {
	private AccessToken accessToken;
	private final OsiamConnector connector;

	private Shell shell;
	private OutputBuilder output;
	
	public CreateGroupCommand(AccessToken at, OsiamConnector connector) {
		this.accessToken = at;
		this.connector = connector;
	}
	
	@Override
	public void cliSetShell(Shell theShell) {
		this.shell = theShell;
	}
	
	@Override
	public void cliSetOutput(OutputBuilder output) {
		this.output = output;
	}
	
	@Command
	public Group createGroup(
			@Param(name = "name", description = "The display name of the new group.")
			String displayName) throws IOException{
		
		final GroupBuilder builder = new GroupBuilder(displayName);
		
		final Shell subShell = ShellBuilder.subshell("createGroup", shell)
									.addHandler(builder)
								.build();
		
		output.out()
			.normal("In this subshell you can build your group. Leave this sub shell to persist the group.")
		.println();
		
		subShell.commandLoop();
		final Group toCreate = builder.build();
		
		if(toCreate == null) return null;
		return connector.createGroup(toCreate, accessToken);
	}
	
	public class GroupBuilder extends AbstractBuilderCommand<Group> {
		private final Group.Builder groupBuilder;
		
		public GroupBuilder(String displayName) {
			groupBuilder = new Group.Builder(displayName);
		}
		
		@Command
		public void setExternalId(
				@Param(name = "externalId", description = "The external id for the new group.")
				String externalId){
			
			groupBuilder.setExternalId(externalId);
		}
		
		protected Group _build() {
			return groupBuilder.build();
		}
		
	}
}
