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
package org.osiam.shell;

import java.io.File;

import com.beust.jcommander.JCommander;
import de.raysha.lib.jsimpleshell.io.OutputBuilder;
import org.osiam.shell.command.ConnectionCommand;
import org.osiam.shell.command.io.AccessTokenConverter;
import org.osiam.shell.command.io.AddressConverter;
import org.osiam.shell.command.io.AddressTypeConverter;
import org.osiam.shell.command.io.DateConverter;
import org.osiam.shell.command.io.EmailConverter;
import org.osiam.shell.command.io.EmailTypeConverter;
import org.osiam.shell.command.io.EntitlementConverter;
import org.osiam.shell.command.io.EntitlementTypeConverter;
import org.osiam.shell.command.io.ExtensionConverter;
import org.osiam.shell.command.io.GroupConverter;
import org.osiam.shell.command.io.ImConverter;
import org.osiam.shell.command.io.ImTypeConverter;
import org.osiam.shell.command.io.NameConverter;
import org.osiam.shell.command.io.PhoneNumberConverter;
import org.osiam.shell.command.io.PhoneNumberTypeConverter;
import org.osiam.shell.command.io.PhotoConverter;
import org.osiam.shell.command.io.PhotoTypeConverter;
import org.osiam.shell.command.io.RoleConverter;
import org.osiam.shell.command.io.RoleTypeConverter;
import org.osiam.shell.command.io.URIConverter;
import org.osiam.shell.command.io.UserConverter;
import org.osiam.shell.command.io.X509CertificateConverter;
import org.osiam.shell.command.io.X509CertificateTypeConverter;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.builder.ShellBuilder;

public class Starter {

	public static void main(String[] args) throws Exception {
		CommandLineArguments arguments = new CommandLineArguments();
		JCommander jCommander = new JCommander(arguments, args);

		final Shell shell = ShellBuilder.shell("osiam-shell")
							.behavior()
								.setHistoryFile(new File(System.getProperty("user.home"), ".osiamshell_history"))
								.addHandler(new ConnectionCommand())
								.addAuxHandler(new AccessTokenConverter())
								.addAuxHandler(new UserConverter())
								.addAuxHandler(new GroupConverter())
								.addAuxHandler(new AddressTypeConverter())
								.addAuxHandler(new AddressConverter())
								.addAuxHandler(new EmailTypeConverter())
								.addAuxHandler(new EmailConverter())
								.addAuxHandler(new EntitlementTypeConverter())
								.addAuxHandler(new EntitlementConverter())
								.addAuxHandler(new ExtensionConverter())
								.addAuxHandler(new ImTypeConverter())
								.addAuxHandler(new ImConverter())
								.addAuxHandler(new NameConverter())
								.addAuxHandler(new PhoneNumberTypeConverter())
								.addAuxHandler(new PhoneNumberConverter())
								.addAuxHandler(new PhotoTypeConverter())
								.addAuxHandler(new PhotoConverter())
								.addAuxHandler(new RoleTypeConverter())
								.addAuxHandler(new RoleConverter())
								.addAuxHandler(new X509CertificateTypeConverter())
								.addAuxHandler(new X509CertificateConverter())
								.addAuxHandler(new DateConverter())
								.addAuxHandler(new URIConverter())
							.build();

		final StringBuilder helpText = new StringBuilder();
		helpText.append("OSIAM Shell - http://osiam.org\n");
		helpText.append("Copyright (c) 2015 OSIAM - Distributed under the terms of the MIT License (MIT)\n\n");
		final OutputBuilder.OutputBuilder_ outputBuilder = shell.getOutputBuilder().out();

		if (arguments.help) {
			jCommander.usage(helpText);
			outputBuilder.normal(helpText).println();
			System.exit(0);
		}

		helpText.append("For help, type: ?help or invoke the OSIAM Shell with the --help flag.\n");
		outputBuilder.normal(helpText).println();

		if (arguments.hasConnectionInformation()) {
			shell.getPipeline().append(String.format("connect %s %s %s %s",
					arguments.endpoint, arguments.redirectUri,
					arguments.clientId, arguments.clientSecret));

			if (arguments.hasLoginInformation()) {
				shell.getPipeline().append(String.format("login %s %s", arguments.username, arguments.password));
			}
		}

		shell.commandLoop();
	}
}
