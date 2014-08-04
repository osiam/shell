package org.osiam.shell.command.delete;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.resources.scim.Group;
import org.osiam.shell.command.OsiamAccessCommand;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

/**
 * This class contains commands which can remove groups.
 * 
 * @author rainu
 */
public class DeleteGroupCommand extends OsiamAccessCommand {

	public DeleteGroupCommand(AccessToken at, OsiamConnector connector) {
		super(at, connector);
	}

	@Command(description = "Delete the given group.")
	public String deleteGroup(
			@Param(value = "groupName", description = "The name of the group.")
			String groupName) {
		final Group group = getGroup(groupName);
		if(group == null) return "There is no group with the name \"" + groupName + "\"!";
		
		connector.deleteGroup(group.getId(), accessToken);
		return null;
	}
}
