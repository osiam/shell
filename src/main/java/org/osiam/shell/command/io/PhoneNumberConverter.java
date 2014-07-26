package org.osiam.shell.command.io;

import org.osiam.resources.scim.PhoneNumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.raysha.lib.jsimpleshell.io.OutputConverter;

/**
 * {@link OutputConverter} for the {@link PhoneNumber}
 * 
 * @author rainu
 */
public class PhoneNumberConverter implements OutputConverter {

	private ObjectMapper objectMapper = new ObjectMapper();	
	
	@Override
	public Object convertOutput(Object toBeFormatted) {
		if(!(toBeFormatted instanceof PhoneNumber)) return null;
		
		final PhoneNumber phoneNumber = (PhoneNumber)toBeFormatted;
		
		try {
			return toString(phoneNumber);
		} catch (JsonProcessingException e) {
			return e;
		}
	}

	private synchronized String toString(final PhoneNumber phoneNumber) throws JsonProcessingException {
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(phoneNumber);
	}

}
