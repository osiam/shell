package org.osiam.shell.command.builder;

import org.osiam.resources.scim.Role;
import org.osiam.resources.scim.Role.Type;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.handler.ShellDependent;

/**
 * This class contains commands which can create {@link Role}s.
 * 
 * @author rainu
 */
public class RoleBuilder extends AbstractBuilderCommand<Role> implements ShellDependent {
	private Role.Builder builder;
	private Role current;

	public RoleBuilder(Role current) {
		this.current = current;
		this.builder = new Role.Builder(current);
	}
	
	@Override
	public void cliSetShell(Shell theShell) {
		if(current != null){
			theShell.addMainHandler(new ShowRole(), "");
		}
	}
	
	public class ShowRole {
		@Command(description = "Shows the current (persited) role that will be replaced.")
		public Role showRole(){
			return current;
		}
	}
	
	@Command(description = "Shows the role state. This state is not persisted yet!")
	public Role showState() {
		return _build();
	}

	@Command(description = "Set the display name of the role.")
	public void setDisplay(
			@Param(value = "display", description = "The display name.")
			String display) {
		
		builder.setDisplay(display);
	}

	@Command(description = "Is this role primary?")
	public void setPrimary(
			@Param(value = "primary", description = "True if this role is primary. Otherwise false.")
			Boolean primary) {
		
		builder.setPrimary(primary);
	}

	@Command(description = "Sets the label indicating the attribute's function.")
	public void setType(
			@Param(value = "type", description = "The type of the attribute.")
			Type type) {
		
		builder.setType(type);
	}

	@Command(description = "Set the value of the role.")
	public void setValue(
			@Param(value = "value", description = "The value.")
			String value) {
		
		builder.setValue(value);
	}

	@Override
	protected Role _build() {
		return builder.build();
	}
}
