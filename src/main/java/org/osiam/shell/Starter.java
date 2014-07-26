package org.osiam.shell;

import java.io.File;
import java.io.IOException;

import org.osiam.shell.command.ConnectionCommand;
import org.osiam.shell.command.io.AccessTokenConverter;
import org.osiam.shell.command.io.AddressConverter;
import org.osiam.shell.command.io.AddressTypeConverter;
import org.osiam.shell.command.io.EmailConverter;
import org.osiam.shell.command.io.EmailTypeConverter;
import org.osiam.shell.command.io.EntitlementConverter;
import org.osiam.shell.command.io.EntitlementTypeConverter;
import org.osiam.shell.command.io.GroupConverter;
import org.osiam.shell.command.io.ImConverter;
import org.osiam.shell.command.io.ImTypeConverter;
import org.osiam.shell.command.io.PhoneNumberConverter;
import org.osiam.shell.command.io.PhoneNumberTypeConverter;
import org.osiam.shell.command.io.UserConverter;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.ShellBuilder;

public class Starter {

	public static void main(String[] args) throws IOException {
		final Shell shell = ShellBuilder.shell("osiam-shell")
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
								.addAuxHandler(new ImTypeConverter())
								.addAuxHandler(new ImConverter())
								.addAuxHandler(new PhoneNumberTypeConverter())
								.addAuxHandler(new PhoneNumberConverter())
							.build();
		
		shell.commandLoop();
	}

}
