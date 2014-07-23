package org.osiam.shell;

import java.io.File;
import java.io.IOException;

import org.osiam.shell.command.ConnectionCommand;
import org.osiam.shell.command.io.AccessTokenConverter;
import org.osiam.shell.command.io.GroupConverter;
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
							.build();
		
		shell.commandLoop();
	}

}
