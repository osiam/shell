package org.osiam.shell.command;

import de.raysha.lib.jsimpleshell.CommandRecorder;
import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.annotation.Inject;

public abstract class AbstractOsiamCommand {

	@Inject
	protected Shell shell;

	protected final boolean inRecordMode(){
		return CommandRecorder.isShellInRecordMode(shell);
	}
}
