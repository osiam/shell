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
import de.raysha.lib.jsimpleshell.ShellBuilder;
import de.raysha.lib.jsimpleshell.ShellDependent;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.io.OutputBuilder;
import de.raysha.lib.jsimpleshell.io.OutputDependent;

/**
 * This class contains commands that can update groups.
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
	
	@Command(description = "Update the given group.")
	public Object updateGroup(
			@Param(name = "groupName", description = "The name of the group.")
			String groupName) throws IOException{
		
		final Group group = getGroup(groupName);
		if(group == null) return "There is no group with the name \"" + groupName + "\"!";
		
		final UpdateGroupBuilder builder = new UpdateGroupBuilder(group);
		
		final Shell subShell = ShellBuilder.subshell("update-group", shell)
									.addHandler(builder)
								.build();
		
		output.out()
			.normal("In this subshell you can edit the group. Leave this sub shell via \"commit\" to persist the changes.")
		.println();
		
		subShell.commandLoop();
		
		final UpdateGroup update = builder.build();
		if(update == null) return null;
		
		return connector.updateGroup(group.getId(), update, accessToken);
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
			return build().getScimConformUpdateGroup();
		}
		
		@Command(description = "Add the given user to the given group.")
		public String addUser(
				@Param(name = "userName", description = "The name of the user.")
				String userName){
			
			final User user = getUser(userName);
			if(user == null) return "There is no user with the name \"" + userName + "\"!";
			
			builder.addMember(user.getId());
			
			return null;
		}
		
		@Command(description = "Remove the given user from the given group.")
		public String removeUser(
				@Param(name = "userName", description = "The name of the user.")
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
		
		@Command(description = "Remove the given user from the given group.")
		public void rename(
				@Param(name = "name", description = "The new group display name.")
				String newName){
			
			builder.updateDisplayName(newName);
		}

		@Command(description = "Set the externalId for the group.")
		public void setExternalId(
				@Param(name = "externalId", description = "The new externalId for the group.")
				String externalId){
			
			builder.updateExternalId(externalId);
		}
		
		@Override
		protected UpdateGroup _build() {
			return builder.build();
		}
	}
}
