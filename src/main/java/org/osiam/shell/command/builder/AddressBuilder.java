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

import org.osiam.resources.scim.Address;
import org.osiam.resources.scim.Address.Type;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.handler.ShellDependent;

/**
 * This class contains commands which can create {@link Address}es.
 * 
 * @author rainu
 */
public class AddressBuilder extends AbstractBuilderCommand<Address> implements ShellDependent {
	private Address.Builder builder;
	private Address current;

	public AddressBuilder(Address current) {
		this.current = current;
		this.builder = new Address.Builder(current);
	}
	
	@Override
	public void cliSetShell(Shell theShell) {
		if(current != null){
			theShell.addMainHandler(new ShowAddress(), "");
		}
	}
	
	public class ShowAddress {
		@Command(description = "Shows the current (persited) address that will be replaced.")
		public Address showAddress(){
			return current;
		}
	}
	
	@Command(description = "Shows the address state. This state is not persisted yet!")
	public Address showState() {
		return _build();
	}
	
	@Command(description = "Sets the country name.")
	public void setCountry(
			@Param(value = "country", description = "The country.")
			String country) {
		
		builder.setCountry(country);
	}

	@Command(description = "Sets the full mailing address.")
	public void setFormatted(
			@Param(value = "formatted", description = "The formatted address.")
			String formatted) {
		
		builder.setFormatted(formatted);
	}

	@Command(description = "Sets the city or locality.")
	public void setLocality(
			@Param(value = "locality", description = "The locality.")
			String locality) {
		
		builder.setLocality(locality);
	}

	@Command(description = "Sets the postal code.")
	public void setPostalCode(
			@Param(value = "postalCode", description = "The postal code.")
			String postalCode) {
		
		builder.setPostalCode(postalCode);
	}

	@Command(description = "Is this address primary?")
	public void setPrimary(
			@Param(value = "primary", description = "True if this address is primary. Otherwise false.")
			Boolean primary) {
		
		builder.setPrimary(primary);
	}

	@Command(description = "Sets the state or region.")
	public void setRegion(
			@Param(value = "region", description = "The region.")
			String region) {
		
		builder.setRegion(region);
	}

	@Command(description = "Sets the full street address.")
	public void setStreetAddress(
			@Param(value = "address", description = "The street address.")
			String streetAddress) {
		
		builder.setStreetAddress(streetAddress);
	}

	@Command(description = "Sets the label indicating the attribute's function.")
	public void setType(
			@Param(value = "type", description = "The type of the attribute.")
			Type type) {
		
		builder.setType(type);
	}

	@Override
	protected Address _build() {
		return builder.build();
	}
}
