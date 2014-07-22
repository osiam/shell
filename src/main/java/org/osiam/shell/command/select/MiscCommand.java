package org.osiam.shell.command.select;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;

import de.raysha.lib.jsimpleshell.annotation.Command;

public class MiscCommand {
	private AccessToken accesstoken;
	private final OsiamConnector connector;

	public MiscCommand(AccessToken at, OsiamConnector connector) {
		this.accesstoken = at;
		this.connector = connector;
	}
	
	@Command
	public AccessToken showAccessToken(){
		return accesstoken;
	}
}
