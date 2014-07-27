package org.osiam.shell.command.io;

import org.osiam.resources.scim.Name;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.raysha.lib.jsimpleshell.io.OutputConverter;

/**
 * {@link OutputConverter} for the {@link Name}
 * 
 * @author rainu
 */
public class NameConverter implements OutputConverter {

	private ObjectMapper objectMapper = new ObjectMapper();	
	
	@Override
	public Object convertOutput(Object toBeFormatted) {
		if(!(toBeFormatted instanceof Name)) return null;
		
		final Name name = (Name)toBeFormatted;
		
		try {
			return toString(name);
		} catch (JsonProcessingException e) {
			return e;
		}
	}

	private synchronized String toString(final Name name) throws JsonProcessingException {
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(name);
	}

}
