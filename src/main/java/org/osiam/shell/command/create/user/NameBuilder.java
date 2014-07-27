package org.osiam.shell.command.create.user;

import org.osiam.resources.scim.Name;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

/**
 * This class contains commands which can create {@link Name}s.
 * 
 * @author rainu
 */
public class NameBuilder extends AbstractBuilderCommand<Name> {
	private Name.Builder builder = new Name.Builder();

	@Command(description = "Shows the name state. This state is not persisted yet!")
	public Name showState() {
		return _build();
	}
	
	@Command(description = "Set the familiy name.")
	public void setFamilyName(
			@Param(name = "familyName", description = "The family name.")
			String familyName) {
		
		builder.setFamilyName(familyName);
	}

	@Command(description = "Set the formatted name.")
	public void setFormatted(
			@Param(name = "formatted", description = "The formatted name.")
			String formatted) {
		
		builder.setFormatted(formatted);
	}

	@Command(description = "Set the given name.")
	public void setGivenName(
			@Param(name = "givenName", description = "The given name.")
			String givenName) {
		
		builder.setGivenName(givenName);
	}

	@Command(description = "Set the honorific prefix.")
	public void setHonorificPrefix(
			@Param(name = "honorificPrefix", description = "The honorific prefix.")
			String honorificPrefix) {
		
		builder.setHonorificPrefix(honorificPrefix);
	}

	@Command(description = "Set the honorific suffix.")
	public void setHonorificSuffix(
			@Param(name = "honorificSuffix", description = "The honorific suffix.")
			String honorificSuffix) {
		
		builder.setHonorificSuffix(honorificSuffix);
	}

	@Command(description = "Set the middle name.")
	public void setMiddleName(
			@Param(name = "middleName", description = "The middle name.")
			String middleName) {
		
		builder.setMiddleName(middleName);
	}

	@Override
	protected Name _build() {
		return builder.build();
	}
}
