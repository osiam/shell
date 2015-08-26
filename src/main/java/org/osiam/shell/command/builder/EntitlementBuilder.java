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
package org.osiam.shell.command.builder;

import org.osiam.resources.scim.Entitlement;
import org.osiam.resources.scim.Entitlement.Type;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.handler.ShellDependent;

/**
 * This class contains commands which can create {@link Entitlement}s.
 *
 * @author rainu
 */
public class EntitlementBuilder extends AbstractBuilderCommand<Entitlement> implements ShellDependent {
	private Entitlement.Builder builder;
	private Entitlement current;

	public EntitlementBuilder(Entitlement current) {
		this.current = current;
		this.builder = current == null ? new Entitlement.Builder() : new Entitlement.Builder(current);
	}

	@Override
	public void cliSetShell(Shell theShell) {
		if(current != null){
			theShell.addMainHandler(new ShowEntitlement(), "");
		}
	}

	public class ShowEntitlement {
		@Command(description = "Shows the current (persited) entitlement that will be replaced.")
		public Entitlement showEntitlement(){
			return current;
		}
	}

	@Command(description = "Shows the entitlement state. This state is not persisted yet!")
	public Entitlement showState() {
		return _build();
	}

	@Command(description = "Set the display name of the entitlement.")
	public void setDisplay(
			@Param(value = "display", description = "The display name.")
			String display) {

		builder.setDisplay(display);
	}

	@Command(description = "Is this entitlement primary?")
	public void setPrimary(
			@Param(value = "primary", description = "True if this entitlement is primary. Otherwise false.")
			Boolean primary) {

		builder.setPrimary(primary);
	}

	@Command(description = "Sets the label indicating the attribute's function.")
	public void setType(
			@Param(value = "type", description = "The type of the attribute.")
			Type type) {

		builder.setType(type);
	}

	@Command(description = "Set the value of the entitlement.")
	public void setValue(
			@Param(value = "value", description = "The value.")
			String value) {

		builder.setValue(value);
	}

	@Override
	protected Entitlement _build() {
		return builder.build();
	}
}
