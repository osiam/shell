package org.osiam.shell.command;

import java.io.IOException;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.Scope;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Inject;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.builder.ShellBuilder;
import de.raysha.lib.jsimpleshell.io.InputBuilder;

/**
 * At begining the user must "connect" to a osiam server.
 *
 * @author rainu
 */
public class ConnectionCommand extends AbstractOsiamCommand {
	private static final String COMMAND_DESCRIPTION_WITH_SECRET = "Open a connection to a osiam server.";
	private static final String COMMAND_DESCRIPTION_WITHOUT_SECRET = "Open a connection to a osiam server. The client secret will be requested separately.";

	private static final String PARAM_NAME_ENDPOINT = "endpoint";
	private static final String PARAM_NAME_RESOURCE_ENDPOINT = "resEndpoint";
	private static final String PARAM_NAME_AUTHENTICATION_ENDPOINT = "authEndpoint";
	private static final String PARAM_NAME_REDIRECT_URI = "redirectUri";
	private static final String PARAM_NAME_CLIENT_SECRET = "clientSecret";
	private static final String PARAM_NAME_CLIENT_ID = "clientId";

	private static final String PARAM_DESCRIPTION_RESOURCE_ENDPOINT = "The endpoint of the osiam resource server.";
	private static final String PARAM_DESCRIPTION_AUTHENTICATION_ENDPOINT = "The endpoint of the osiam authentication server.";
	private static final String PARAM_DESCRIPTION_CLIENT_SECRET = "The clientSecret for this shell.";
	private static final String PARAM_DESCRIPTION_CLIENT_ID = "The clientId for this shell.";
	private static final String PARAM_DESCRIPTION_ENDPOINT = "The endpoint of the osiam server.";
	private static final String PARAM_DESCRIPTION_REDIRECT_URI = "The redirectUri for this client.";

	@Inject
	private InputBuilder input;

	@Command(description=COMMAND_DESCRIPTION_WITHOUT_SECRET, startsSubshell = true)
	public void connect(
			@Param(value=PARAM_NAME_ENDPOINT, description=PARAM_DESCRIPTION_ENDPOINT)
			String endpoint,
			@Param(value=PARAM_NAME_REDIRECT_URI, description=PARAM_DESCRIPTION_REDIRECT_URI)
			String redirectUri,
			@Param(value=PARAM_NAME_CLIENT_ID, description=PARAM_DESCRIPTION_CLIENT_ID)
			String clientId) throws IOException{

		if(inRecordMode()){
			connect(endpoint, redirectUri, clientId, null);
			return;
		}

		final String clientSecret = readClientSecret();
		connect(endpoint, redirectUri, clientId, clientSecret);
	}

	@Command(description=COMMAND_DESCRIPTION_WITH_SECRET, startsSubshell = true)
	public void connect(
			@Param(value=PARAM_NAME_ENDPOINT, description=PARAM_DESCRIPTION_ENDPOINT)
			String endpoint,
			@Param(value=PARAM_NAME_REDIRECT_URI, description=PARAM_DESCRIPTION_REDIRECT_URI)
			String redirectUri,
			@Param(value=PARAM_NAME_CLIENT_ID, description=PARAM_DESCRIPTION_CLIENT_ID)
			String clientId,
			@Param(value=PARAM_NAME_CLIENT_SECRET, description=PARAM_DESCRIPTION_CLIENT_SECRET)
			String clientSecret) throws IOException{

		if(inRecordMode()){
			openSubShell(null);
			return;
		}

		final OsiamConnector connector = new OsiamConnector.Builder()
											.setEndpoint(endpoint)
											.setClientRedirectUri(redirectUri)
											.setClientId(clientId)
											.setClientSecret(clientSecret)
										.build();

		openSubShell(connector);
	}

	@Command(description=COMMAND_DESCRIPTION_WITHOUT_SECRET, startsSubshell = true)
	public void connectAdvanced(
			@Param(value=PARAM_NAME_AUTHENTICATION_ENDPOINT, description=PARAM_DESCRIPTION_AUTHENTICATION_ENDPOINT)
			String authEndpoint,
			@Param(value=PARAM_NAME_RESOURCE_ENDPOINT, description=PARAM_DESCRIPTION_RESOURCE_ENDPOINT)
			String resourceEndpoint,
			@Param(value=PARAM_NAME_REDIRECT_URI, description=PARAM_DESCRIPTION_REDIRECT_URI)
			String redirectUri,
			@Param(value=PARAM_NAME_CLIENT_ID, description=PARAM_DESCRIPTION_CLIENT_ID)
			String clientId) throws IOException{

		if(inRecordMode()){
			connectAdvanced(authEndpoint, resourceEndpoint, redirectUri, clientId, null);
			return;
		}

		final String clientSecret = readClientSecret();
		connectAdvanced(authEndpoint, resourceEndpoint, redirectUri, clientId, clientSecret);
	}

	@Command(description=COMMAND_DESCRIPTION_WITH_SECRET, startsSubshell = true)
	public void connectAdvanced(
			@Param(value=PARAM_NAME_AUTHENTICATION_ENDPOINT, description=PARAM_DESCRIPTION_AUTHENTICATION_ENDPOINT)
			String authEndpoint,
			@Param(value=PARAM_NAME_RESOURCE_ENDPOINT, description=PARAM_DESCRIPTION_RESOURCE_ENDPOINT)
			String resourceEndpoint,
			@Param(value=PARAM_NAME_REDIRECT_URI, description=PARAM_DESCRIPTION_REDIRECT_URI)
			String redirectUri,
			@Param(value=PARAM_NAME_CLIENT_ID, description=PARAM_DESCRIPTION_CLIENT_ID)
			String clientId,
			@Param(value=PARAM_NAME_CLIENT_SECRET, description=PARAM_DESCRIPTION_CLIENT_SECRET)
			String clientSecret) throws IOException{

		if(inRecordMode()){
			openSubShell(null);
			return;
		}

		final OsiamConnector connector = new OsiamConnector.Builder()
											.setAuthServerEndpoint(authEndpoint)
											.setResourceServerEndpoint(resourceEndpoint)
											.setClientId(clientId)
											.setClientSecret(clientSecret)
											.setClientRedirectUri(redirectUri)
										.build();

		openSubShell(connector);
	}

	private String readClientSecret() throws IOException {
		final String clientSecret = input.invisibleIn()
										.withPromt("Enter the clientSecret: ")
									.readLine();
		return clientSecret;
	}

	private void openSubShell(OsiamConnector connector) throws IOException {
		final Shell subshell = ShellBuilder.subshell(getHostName(connector), shell)
								.behavior()
									.addHandler(new LoginCommand(connector))
								.back().build();

		subshell.commandLoop();
	}

	private String getHostName(OsiamConnector connector) {
		return connector.getAuthorizationUri(Scope.ALL).getHost();
	}
}
