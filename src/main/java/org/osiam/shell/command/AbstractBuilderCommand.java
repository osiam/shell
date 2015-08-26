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
