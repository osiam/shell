package org.osiam.shell.command.io;

import org.osiam.resources.scim.Address;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.raysha.lib.jsimpleshell.io.OutputConverter;

/**
 * {@link OutputConverter} for the {@link Address}
 * 
 * @author rainu
 */
public class AddressConverter implements OutputConverter {

	private ObjectMapper objectMapper = new ObjectMapper();	
	
	@Override
	public Object convertOutput(Object toBeFormatted) {
		if(!(toBeFormatted instanceof Address)) return null;
		
		final Address address = (Address)toBeFormatted;
		
		try {
			return toString(address);
		} catch (JsonProcessingException e) {
			return e;
		}
	}

	private synchronized String toString(final Address address) throws JsonProcessingException {
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(address);
	}

}
