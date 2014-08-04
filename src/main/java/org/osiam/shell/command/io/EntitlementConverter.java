package org.osiam.shell.command.io;

import org.osiam.resources.scim.Entitlement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.raysha.lib.jsimpleshell.handler.OutputConverter;

/**
 * {@link OutputConverter} for the {@link Entitlement}
 * 
 * @author rainu
 */
public class EntitlementConverter implements OutputConverter {

	private ObjectMapper objectMapper = new ObjectMapper();	
	
	@Override
	public Object convertOutput(Object toBeFormatted) {
		if(!(toBeFormatted instanceof Entitlement)) return null;
		
		final Entitlement entitlement = (Entitlement)toBeFormatted;
		
		try {
			return toString(entitlement);
		} catch (JsonProcessingException e) {
			return e;
		}
	}

	private synchronized String toString(final Entitlement entitlement) throws JsonProcessingException {
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(entitlement);
	}

}
