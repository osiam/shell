package org.osiam.shell;

import java.io.IOException;

import org.osiam.shell.command.ConnectionCommand;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.ShellBuilder;

public class Starter {

	public static void main(String[] args) throws IOException {
		final Shell shell = ShellBuilder.shell("osiam-shell")
								.addHandler(new ConnectionCommand())
							.build();
		
		shell.commandLoop();
	}

}
