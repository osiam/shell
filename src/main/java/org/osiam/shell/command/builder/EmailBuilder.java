package org.osiam.shell.command.builder;

import org.osiam.resources.scim.Email;
import org.osiam.resources.scim.Email.Type;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.handler.ShellDependent;

/**
 * This class contains commands which can create {@link Email}s.
 * 
 * @author rainu
 */
public class EmailBuilder extends AbstractBuilderCommand<Email> implements ShellDependent {
	private Email.Builder builder;
	private Email current;

	public EmailBuilder(Email current) {
		this.current = current;
		this.builder = new Email.Builder(current);
	}
	
	@Override
	public void cliSetShell(Shell theShell) {
		if(current != null){
			theShell.addMainHandler(new ShowEmail(), "");
		}
	}
	
	public class ShowEmail {
		@Command(description = "Shows the current (persited) email that will be replaced.")
		public Email showEmail(){
			return current;
		}
	}
	
	@Command(description = "Shows the email state. This state is not persisted yet!")
	public Email showState() {
		return _build();
	}

	@Command(description = "Set the display name of the email.")
	public void setDisplay(
			@Param(value = "display", description = "The display name.")
			String display) {
		
		builder.setDisplay(display);
	}

	@Command(description = "Is this email address primary?")
	public void setPrimary(
			@Param(value = "primary", description = "True if this email address is primary. Otherwise false.")
			Boolean primary) {
		
		builder.setPrimary(primary);
	}

	@Command(description = "Sets the label indicating the attribute's function.")
	public void setType(
			@Param(value = "type", description = "The type of the attribute.")
			Type type) {
		
		builder.setType(type);
	}

	@Command(description = "Set the value of the email.")
	public void setValue(
			@Param(value = "value", description = "The value (a valid email-address).")
			String value) {
		
		builder.setValue(value);
	}

	@Override
	protected Email _build() {
		return builder.build();
	}
}
