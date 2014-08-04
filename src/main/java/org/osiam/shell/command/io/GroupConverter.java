package org.osiam.shell.command.io;

import org.osiam.resources.scim.Group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.raysha.lib.jsimpleshell.handler.OutputConverter;

/**
 * {@link OutputConverter} for the {@link Group}
 * 
 * @author rainu
 */
public class GroupConverter implements OutputConverter {
	private ObjectMapper objectMapper = new ObjectMapper();	
	
	@Override
	public Object convertOutput(Object toBeFormatted) {
		if(!(toBeFormatted instanceof Group)) return null;
		
		final Group group = (Group)toBeFormatted;
		
		try {
			return toString(group);
		} catch (JsonProcessingException e) {
			return e;
		}
	}

	private synchronized String toString(final Group group) throws JsonProcessingException {
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(group);
	}

	
}
