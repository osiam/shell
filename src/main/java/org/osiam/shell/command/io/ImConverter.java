package org.osiam.shell.command.io;

import org.osiam.resources.scim.Im;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.raysha.lib.jsimpleshell.handler.OutputConverter;

/**
 * {@link OutputConverter} for the {@link Im}
 * 
 * @author rainu
 */
public class ImConverter implements OutputConverter {

	private ObjectMapper objectMapper = new ObjectMapper();	
	
	@Override
	public Object convertOutput(Object toBeFormatted) {
		if(!(toBeFormatted instanceof Im)) return null;
		
		final Im im = (Im)toBeFormatted;
		
		try {
			return toString(im);
		} catch (JsonProcessingException e) {
			return e;
		}
	}

	private synchronized String toString(final Im im) throws JsonProcessingException {
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(im);
	}

}
