package org.osiam.shell.command.builder;

import org.osiam.resources.scim.Address;
import org.osiam.resources.scim.Address.Type;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

/**
 * This class contains commands which can create {@link Address}es.
 * 
 * @author rainu
 */
public class AddressBuilder extends AbstractBuilderCommand<Address> {
	private Address.Builder builder = new Address.Builder();

	@Command(description = "Shows the address state. This state is not persisted yet!")
	public Address showState() {
		return _build();
	}
	
	@Command(description = "Sets the country name.")
	public void setCountry(
			@Param(name = "country", description = "The country.")
			String country) {
		
		builder.setCountry(country);
	}

	@Command(description = "Sets the full mailing address.")
	public void setFormatted(
			@Param(name = "formatted", description = "The formatted address.")
			String formatted) {
		
		builder.setFormatted(formatted);
	}

	@Command(description = "Sets the city or locality.")
	public void setLocality(
			@Param(name = "locality", description = "The locality.")
			String locality) {
		
		builder.setLocality(locality);
	}

	@Command(description = "Sets the postal code.")
	public void setPostalCode(
			@Param(name = "postalCode", description = "The postal code.")
			String postalCode) {
		
		builder.setPostalCode(postalCode);
	}

	@Command(description = "Is this address primary?")
	public void setPrimary(
			@Param(name = "primary", description = "True if this address is primary. Otherwise false.")
			Boolean primary) {
		
		builder.setPrimary(primary);
	}

	@Command(description = "Sets the state or region.")
	public void setRegion(
			@Param(name = "region", description = "The region.")
			String region) {
		
		builder.setRegion(region);
	}

	@Command(description = "Sets the full street address.")
	public void setStreetAddress(
			@Param(name = "address", description = "The street address.")
			String streetAddress) {
		
		builder.setStreetAddress(streetAddress);
	}

	@Command(description = "Sets the label indicating the attribute's function.")
	public void setType(
			@Param(name = "type", description = "The type of the attribute.")
			Type type) {
		
		builder.setType(type);
	}

	@Override
	protected Address _build() {
		return builder.build();
	}
}
