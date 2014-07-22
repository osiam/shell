package org.osiam.shell.command;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.exception.ExitException;

/**
 * This abstract class contains only a command to cancel the current build process.
 * 
 * @author rainu
 */
public abstract class AbstractBuilderCommand {
	private boolean isCanceled = false;
	
	@Command(description = "Cancel the current process.")
	public void cancel() throws ExitException{
		this.isCanceled = true;
		throw new ExitException("Process was canceled!");
	}
	
	@Command(description = "Confirms the current process.")
	public void confirm() throws ExitException{
		throw new ExitException();
	}
	
	public boolean isCanceled() {
		return isCanceled;
	}

}
