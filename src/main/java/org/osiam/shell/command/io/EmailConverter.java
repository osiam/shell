package org.osiam.shell.command.io;

import org.osiam.resources.scim.Email;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.raysha.lib.jsimpleshell.handler.OutputConverter;

/**
 * {@link OutputConverter} for the {@link Email}
 * 
 * @author rainu
 */
public class EmailConverter implements OutputConverter {

	private ObjectMapper objectMapper = new ObjectMapper();	
	
	@Override
	public Object convertOutput(Object toBeFormatted) {
		if(!(toBeFormatted instanceof Email)) return null;
		
		final Email email = (Email)toBeFormatted;
		
		try {
			return toString(email);
		} catch (JsonProcessingException e) {
			return e;
		}
	}

	private synchronized String toString(final Email email) throws JsonProcessingException {
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(email);
	}

}
