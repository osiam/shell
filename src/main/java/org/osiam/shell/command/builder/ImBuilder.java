package org.osiam.shell.command.builder;

import org.osiam.resources.scim.Im;
import org.osiam.resources.scim.Im.Type;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.handler.ShellDependent;

/**
 * This class contains commands which can create {@link Im}s.
 * 
 * @author rainu
 */
public class ImBuilder extends AbstractBuilderCommand<Im> implements ShellDependent {
	private Im.Builder builder;
	private Im current;

	public ImBuilder(Im current) {
		this.current = current;
		this.builder = new Im.Builder(current);
	}
	
	@Override
	public void cliSetShell(Shell theShell) {
		if(current != null){
			theShell.addMainHandler(new ShowIm(), "");
		}
	}
	
	public class ShowIm {
		@Command(description = "Shows the current (persited) im that will be replaced.")
		public Im showIm(){
			return current;
		}
	}
	
	@Command(description = "Shows the im state. This state is not persisted yet!")
	public Im showState() {
		return _build();
	}

	@Command(description = "Set the display name of the im.")
	public void setDisplay(
			@Param(value = "display", description = "The display name.")
			String display) {
		
		builder.setDisplay(display);
	}

	@Command(description = "Is this im primary?")
	public void setPrimary(
			@Param(value = "primary", description = "True if this im is primary. Otherwise false.")
			Boolean primary) {
		
		builder.setPrimary(primary);
	}

	@Command(description = "Sets the label indicating the attribute's function.")
	public void setType(
			@Param(value = "type", description = "The type of the attribute.")
			Type type) {
		
		builder.setType(type);
	}

	@Command(description = "Set the value of the im.")
	public void setValue(
			@Param(value = "value", description = "The value.")
			String value) {
		
		builder.setValue(value);
	}

	@Override
	protected Im _build() {
		return builder.build();
	}
}
