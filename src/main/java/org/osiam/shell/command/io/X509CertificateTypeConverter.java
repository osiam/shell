package org.osiam.shell.command.io;

import org.osiam.resources.scim.X509Certificate.Type;

import de.raysha.lib.jsimpleshell.handler.InputConverter;

/**
 * {@link InputConverter} for the {@link Type}
 * 
 * @author rainu
 */
public class X509CertificateTypeConverter implements InputConverter {

	@Override
	public Object convertInput(String original, Class toClass) throws Exception {
		if(original != null && toClass == Type.class){
			return new Type(original);
		}
		
		return null;
	}
}
