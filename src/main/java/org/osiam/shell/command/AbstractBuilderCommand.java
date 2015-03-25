package org.osiam.shell.command;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.exception.ExitException;

/**
 * This abstract class contains only a command to cancel the current build process.
 *
 * @author rainu
 */
public abstract class AbstractBuilderCommand<ToBuild> extends AbstractOsiamCommand {
	private boolean isCanceled = true;

	@Command(description = "Cancel the current process.")
	public void cancel() throws ExitException{
		throw new ExitException("Process was canceled!");
	}

	@Command(description = "Commit the changes from the current process.")
	public void commit() throws ExitException{
		this.isCanceled = false;
		throw new ExitException();
	}

	public boolean isCanceled() {
		return isCanceled;
	}

	/**
	 * Build the instance. If the process was canceled by the user, null will returned.
	 *
	 * @return The instance of null if the user canceled the process.
	 */
	public ToBuild build(){
		if(isCanceled()) return null;

		return _build();
	}

	protected abstract ToBuild _build();
}
