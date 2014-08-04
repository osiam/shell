package org.osiam.shell.command.io;

import org.osiam.resources.scim.Extension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.raysha.lib.jsimpleshell.handler.OutputConverter;

/**
 * {@link OutputConverter} for the {@link Extension}
 * 
 * @author rainu
 */
public class ExtensionConverter implements OutputConverter {

	private ObjectMapper objectMapper = new ObjectMapper();	
	
	@Override
	public Object convertOutput(Object toBeFormatted) {
		if(!(toBeFormatted instanceof Extension)) return null;
		
		final Extension extension = (Extension)toBeFormatted;
		
		try {
			return toString(extension);
		} catch (JsonProcessingException e) {
			return e;
		}
	}

	private synchronized String toString(final Extension extension) throws JsonProcessingException {
		return 	"URN: " + extension.getUrn() + "\n\n" + 
				objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(extension);
	}

}
