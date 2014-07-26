package org.osiam.shell.command.io;

import org.osiam.resources.scim.Photo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.raysha.lib.jsimpleshell.io.OutputConverter;

/**
 * {@link OutputConverter} for the {@link Photo}
 * 
 * @author rainu
 */
public class PhotoConverter implements OutputConverter {

	private ObjectMapper objectMapper = new ObjectMapper();	
	
	@Override
	public Object convertOutput(Object toBeFormatted) {
		if(!(toBeFormatted instanceof Photo)) return null;
		
		final Photo photo = (Photo)toBeFormatted;
		
		try {
			return toString(photo);
		} catch (JsonProcessingException e) {
			return e;
		}
	}

	private synchronized String toString(final Photo photo) throws JsonProcessingException {
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(photo);
	}

}
