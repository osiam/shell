package org.osiam.shell.command.io;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.raysha.lib.jsimpleshell.io.InputConverter;
import de.raysha.lib.jsimpleshell.io.OutputConverter;

/**
 * {@link OutputConverter} for the {@link Date}
 * 
 * @author rainu
 */
public class DateConverter implements InputConverter {
	
	@Override
	public Object convertInput(String original, Class toClass) throws Exception {
		if(toClass == Date.class){
			try{
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(original);
			}catch (ParseException e){
				try{
					return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(original);
				}catch (ParseException e1){
					return new SimpleDateFormat("yyyy-MM-dd").parse(original);
				}
			}
		}
		return null;
	}

}
