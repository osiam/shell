package org.osiam.shell.command.io;

import org.osiam.resources.scim.Photo.Type;

import de.raysha.lib.jsimpleshell.io.InputConverter;

/**
 * {@link InputConverter} for the {@link Type}
 * 
 * @author rainu
 */
public class PhotoTypeConverter implements InputConverter {

	@Override
	public Object convertInput(String original, Class toClass) throws Exception {
		if(original != null && toClass == Type.class){
			switch(original.toUpperCase()){
			case "PHOTO": return Type.PHOTO;
	        case "THUMBNAIL": return Type.THUMBNAIL;
			}
			
			return new Type(original);
		}
		
		return null;
	}
}
