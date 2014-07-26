package org.osiam.shell.command.update.user;

import org.osiam.resources.scim.X509Certificate;
import org.osiam.resources.scim.X509Certificate.Type;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

/**
 * This class contains commands which can create {@link X509Certificate}s.
 * 
 * @author rainu
 */
public class X509CertificateBuilder extends AbstractBuilderCommand<X509Certificate> {
	private X509Certificate.Builder builder = new X509Certificate.Builder();

	@Command(description = "Shows the certificate state. This state is not persisted yet!")
	public X509Certificate showState() {
		return _build();
	}

	@Command(description = "Set the display name of the certificate.")
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

	@Command(description = "Is this certificate primary?")
	public void setPrimary(
			@Param(name = "primary", description = "True if this certificate is primary. Otherwise false.")
			Boolean primary) {
		
		builder.setPrimary(primary);
	}

	@Command(description = "Sets the label indicating the attribute's function.")
	public void setType(
			@Param(name = "type", description = "The type of the attribute.")
			Type type) {
		
		builder.setType(type);
	}

	@Command(description = "Set the value of the certificate.")
	public void setValue(
			@Param(name = "value", description = "The value.")
			String value) {
		
		builder.setValue(value);
	}

	@Override
	protected X509Certificate _build() {
		return builder.build();
	}
}
