package org.osiam.shell.command.io;

import java.net.URI;

import de.raysha.lib.jsimpleshell.handler.InputConverter;
import de.raysha.lib.jsimpleshell.handler.OutputConverter;

/**
 * {@link OutputConverter} for the {@link URI}
 * 
 * @author rainu
 */
public class URIConverter implements InputConverter {
	@Override
	public Object convertInput(String original, Class toClass) throws Exception {
		if(toClass == URI.class){
			return URI.create(original);
		}
		return null;
	}

}
