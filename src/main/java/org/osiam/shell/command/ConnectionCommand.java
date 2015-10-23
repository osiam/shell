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
 * At beginning the user must "connect" to OSIAM.
 *
 * @author rainu
 */
public class ConnectionCommand extends AbstractOsiamCommand {

	private static final String COMMAND_DESCRIPTION_WITH_SECRET = "Open a connection to a osiam server.";
	private static final String COMMAND_DESCRIPTION_WITHOUT_SECRET = "Open a connection to a osiam server. The client secret will be requested separately.";

	private static final String PARAM_NAME_OSIAM_ENDPOINT = "osiamEndpoint";
	private static final String PARAM_NAME_REDIRECT_URI = "redirectUri";
	private static final String PARAM_NAME_CLIENT_SECRET = "clientSecret";
	private static final String PARAM_NAME_CLIENT_ID = "clientId";

	private static final String PARAM_DESCRIPTION_OSIAM_ENDPOINT = "The endpoint of OSIAM";
	private static final String PARAM_DESCRIPTION_CLIENT_SECRET = "The clientSecret for this shell.";
	private static final String PARAM_DESCRIPTION_CLIENT_ID = "The clientId for this shell.";
	private static final String PARAM_DESCRIPTION_REDIRECT_URI = "The redirectUri for this client.";

	@Inject
	private InputBuilder input;

	@Command(description=COMMAND_DESCRIPTION_WITHOUT_SECRET, startsSubshell = true)
	public void connect(
			@Param(value = PARAM_NAME_OSIAM_ENDPOINT, description = PARAM_DESCRIPTION_OSIAM_ENDPOINT)
			String osiamEndpoint,
			@Param(value = PARAM_NAME_REDIRECT_URI, description = PARAM_DESCRIPTION_REDIRECT_URI)
			String redirectUri,
			@Param(value = PARAM_NAME_CLIENT_ID, description = PARAM_DESCRIPTION_CLIENT_ID)
			String clientId) throws IOException{

		if(inRecordMode()){
			connect(osiamEndpoint, redirectUri, clientId, null);
			return;
		}

		final String clientSecret = readClientSecret();
		connect(osiamEndpoint, redirectUri, clientId, clientSecret);
	}

	@Command(description=COMMAND_DESCRIPTION_WITH_SECRET, startsSubshell = true)
	public void connect(
			@Param(value = PARAM_NAME_OSIAM_ENDPOINT, description = PARAM_DESCRIPTION_OSIAM_ENDPOINT)
			String osiamEndpoint,
			@Param(value = PARAM_NAME_REDIRECT_URI, description = PARAM_DESCRIPTION_REDIRECT_URI)
			String redirectUri,
			@Param(value = PARAM_NAME_CLIENT_ID, description = PARAM_DESCRIPTION_CLIENT_ID)
			String clientId,
			@Param(value = PARAM_NAME_CLIENT_SECRET, description = PARAM_DESCRIPTION_CLIENT_SECRET)
			String clientSecret) throws IOException{

		if(inRecordMode()){
			openSubShell(null);
			return;
		}

		final OsiamConnector connector = new OsiamConnector.Builder()
											.setEndpoint(osiamEndpoint)
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
								.build();

		subshell.commandLoop();
	}

	private String getHostName(OsiamConnector connector) {
		return connector.getAuthorizationUri(Scope.ADMIN).getHost();
	}
}
