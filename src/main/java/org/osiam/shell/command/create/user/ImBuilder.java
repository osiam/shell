package org.osiam.shell.command.create.user;

import org.osiam.resources.scim.Im;
import org.osiam.resources.scim.Im.Type;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

/**
 * This class contains commands which can create {@link Im}s.
 * 
 * @author rainu
 */
public class ImBuilder extends AbstractBuilderCommand<Im> {
	private Im.Builder builder = new Im.Builder();

	@Command(description = "Shows the im state. This state is not persisted yet!")
	public Im showState() {
		return _build();
	}

	@Command(description = "Set the display name of the im.")
	public void setDisplay(
			@Param(name = "display", description = "The display name.")
			String display) {
		
		builder.setDisplay(display);
	}

	@Command(description = "Is this im primary?")
	public void setPrimary(
			@Param(name = "primary", description = "True if this im is primary. Otherwise false.")
			Boolean primary) {
		
		builder.setPrimary(primary);
	}

	@Command(description = "Sets the label indicating the attribute's function.")
	public void setType(
			@Param(name = "type", description = "The type of the attribute.")
			Type type) {
		
		builder.setType(type);
	}

	@Command(description = "Set the value of the im.")
	public void setValue(
			@Param(name = "value", description = "The value.")
			String value) {
		
		builder.setValue(value);
	}

	@Override
	protected Im _build() {
		return builder.build();
	}
}
