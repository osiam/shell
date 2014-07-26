package org.osiam.shell.command.update.user;

import org.osiam.resources.scim.Email;
import org.osiam.resources.scim.Email.Type;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

/**
 * This class contains commands which can create {@link Email}s.
 * 
 * @author rainu
 */
public class EmailBuilder extends AbstractBuilderCommand<Email> {
	private Email.Builder builder = new Email.Builder();

	@Command(description = "Shows the email state. This state is not persisted yet!")
	public Email showState() {
		return _build();
	}

	@Command(description = "Set the display name of the email.")
	public void setDisplay(
			@Param(name = "display", description = "The display name.")
			String display) {
		
		builder.setDisplay(display);
	}

	@Command(description = "Sets the operation.")
	public void setOperation(
			@Param(name = "operation", description = "The operation.")
			String operation) {
		
		builder.setOperation(operation);
	}

	@Command(description = "Is this address primary?")
	public void setPrimary(
			@Param(name = "primary", description = "True if this address is primary. Otherwise false.")
			Boolean primary) {
		
		builder.setPrimary(primary);
	}

	@Command(description = "Sets the label indicating the attribute's function.")
	public void setType(
			@Param(name = "type", description = "The type of the attribute.")
			Type type) {
		
		builder.setType(type);
	}

	@Command(description = "Set the value of the email.")
	public void setValue(
			@Param(name = "value", description = "The value (a valid email-address).")
			String value) {
		
		builder.setValue(value);
	}

	@Override
	protected Email _build() {
		return builder.build();
	}
}
