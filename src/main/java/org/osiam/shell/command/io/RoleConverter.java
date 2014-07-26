package org.osiam.shell.command.io;

import org.osiam.resources.scim.Role;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.raysha.lib.jsimpleshell.io.OutputConverter;

/**
 * {@link OutputConverter} for the {@link Role}
 * 
 * @author rainu
 */
public class RoleConverter implements OutputConverter {

	private ObjectMapper objectMapper = new ObjectMapper();	
	
	@Override
	public Object convertOutput(Object toBeFormatted) {
		if(!(toBeFormatted instanceof Role)) return null;
		
		final Role role = (Role)toBeFormatted;
		
		try {
			return toString(role);
		} catch (JsonProcessingException e) {
			return e;
		}
	}

	private synchronized String toString(final Role role) throws JsonProcessingException {
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(role);
	}

}
