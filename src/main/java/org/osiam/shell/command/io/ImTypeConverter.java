package org.osiam.shell.command.io;

import org.osiam.resources.scim.Im.Type;

import de.raysha.lib.jsimpleshell.io.InputConverter;

/**
 * {@link InputConverter} for the {@link Type}
 * 
 * @author rainu
 */
public class ImTypeConverter implements InputConverter {

	@Override
	public Object convertInput(String original, Class toClass) throws Exception {
		if(original != null && toClass == Type.class){
			switch(original.toUpperCase()){
	        case "AIM": return Type.AIM;
	        case "GTALK": return Type.GTALK;
	        case "ICQ": return Type.ICQ;
	        case "XMPP": return Type.XMPP;
	        case "MSN": return Type.MSN;
	        case "SKYPE": return Type.SKYPE;
	        case "QQ": return Type.QQ;
	        case "YAHOO": return Type.YAHOO;
			}
			
			return new Type(original);
		}
		
		return null;
	}
}
