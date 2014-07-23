package org.osiam.shell.command.select;

import java.util.List;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.resources.scim.Group;
import org.osiam.shell.command.OsiamAccessCommand;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

/**
 * This class contains all commands with that show(select) groups.
 * 
 * @author rainu
 */
public class SelectGroupCommand extends OsiamAccessCommand {

	public SelectGroupCommand(AccessToken at, OsiamConnector connector) {
		super(at, connector);
	}

	@Command(description = "Show the given group.")
	public Object showGroup(
			@Param(name = "groupName", description = "The name of the group.")
			String groupName){
		
		Group group = getGroup(groupName);
		
		if(group == null) return "There is no group with the name \"" + groupName + "\"!";
		return group;
	}
	
	@Command(description = "Show all groups.")
	public List<Group> showAllGroups(){
		return connector.getAllGroups(accessToken);
	}
}
