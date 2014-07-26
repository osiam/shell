package org.osiam.shell.command.io;

import org.osiam.resources.scim.PhoneNumber.Type;

import de.raysha.lib.jsimpleshell.io.InputConverter;

/**
 * {@link InputConverter} for the {@link Type}
 * 
 * @author rainu
 */
public class PhoneNumberTypeConverter implements InputConverter {

	@Override
	public Object convertInput(String original, Class toClass) throws Exception {
		if(original != null && toClass == Type.class){
			switch(original.toUpperCase()){
			case "WORK": return Type.WORK;
	        case "HOME": return Type.HOME;
	        case "MOBILE": return Type.MOBILE;
	        case "FAX": return Type.FAX;
	        case "PAGER": return Type.PAGER;
	        case "OTHER": return Type.OTHER;
			}
			
			return new Type(original);
		}
		
		return null;
	}
}
