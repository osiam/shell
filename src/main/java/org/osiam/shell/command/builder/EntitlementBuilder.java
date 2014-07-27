package org.osiam.shell.command.builder;

import org.osiam.resources.scim.Entitlement;
import org.osiam.resources.scim.Entitlement.Type;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.ShellDependent;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

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
		this.builder = new Entitlement.Builder(current);
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
			@Param(name = "display", description = "The display name.")
			String display) {
		
		builder.setDisplay(display);
	}

	@Command(description = "Is this entitlement primary?")
	public void setPrimary(
			@Param(name = "primary", description = "True if this entitlement is primary. Otherwise false.")
			Boolean primary) {
		
		builder.setPrimary(primary);
	}

	@Command(description = "Sets the label indicating the attribute's function.")
	public void setType(
			@Param(name = "type", description = "The type of the attribute.")
			Type type) {
		
		builder.setType(type);
	}

	@Command(description = "Set the value of the entitlement.")
	public void setValue(
			@Param(name = "value", description = "The value.")
			String value) {
		
		builder.setValue(value);
	}

	@Override
	protected Entitlement _build() {
		return builder.build();
	}
}
