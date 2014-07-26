package org.osiam.shell.command.io;

import org.osiam.resources.scim.X509Certificate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.raysha.lib.jsimpleshell.io.OutputConverter;

/**
 * {@link OutputConverter} for the {@link X509Certificate}
 * 
 * @author rainu
 */
public class X509CertificateConverter implements OutputConverter {

	private ObjectMapper objectMapper = new ObjectMapper();	
	
	@Override
	public Object convertOutput(Object toBeFormatted) {
		if(!(toBeFormatted instanceof X509Certificate)) return null;
		
		final X509Certificate certificate = (X509Certificate)toBeFormatted;
		
		try {
			return toString(certificate);
		} catch (JsonProcessingException e) {
			return e;
		}
	}

	private synchronized String toString(final X509Certificate certificate) throws JsonProcessingException {
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(certificate);
	}

}
