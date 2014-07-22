package org.osiam.shell.command.io;

import org.osiam.resources.scim.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.raysha.lib.jsimpleshell.io.OutputConverter;

/**
 * {@link OutputConverter} for the {@link User}
 * 
 * @author rainu
 */
public class UserConverter implements OutputConverter {
	private ObjectMapper objectMapper = new ObjectMapper();	
	
	@Override
	public Object convertOutput(Object toBeFormatted) {
		if(!(toBeFormatted instanceof User)) return null;
		
		final User user = (User)toBeFormatted;
		
		try {
			return toString(user);
		} catch (JsonProcessingException e) {
			return e;
		}
	}

	private synchronized String toString(final User user) throws JsonProcessingException {
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
	}

	
}
