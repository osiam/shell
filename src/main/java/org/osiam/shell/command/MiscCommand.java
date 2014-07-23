package org.osiam.shell.command;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;

import de.raysha.lib.jsimpleshell.annotation.Command;

/**
 * This command contains some miscellaneous commands.
 * 
 * @author rainu
 */
public class MiscCommand extends OsiamAccessCommand {
	
	public MiscCommand(AccessToken at, OsiamConnector connector) {
		super(at, connector);
	}
	
	@Command(description="Show information about your current access token.")
	public AccessToken showAccessToken(){
		return accessToken;
	}
}
