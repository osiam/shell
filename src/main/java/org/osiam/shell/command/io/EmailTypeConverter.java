package org.osiam.shell.command.io;

import org.osiam.resources.scim.Email.Type;

import de.raysha.lib.jsimpleshell.handler.InputConverter;

/**
 * {@link InputConverter} for the {@link Type}
 * 
 * @author rainu
 */
public class EmailTypeConverter implements InputConverter {

	@Override
	public Object convertInput(String original, Class toClass) throws Exception {
		if(original != null && toClass == Type.class){
			switch(original.toUpperCase()){
			case "WORK": return Type.WORK;
			case "HOME": return Type.HOME;
			case "OTHER": return Type.OTHER;
			}
			
			return new Type(original);
		}
		
		return null;
	}
}
