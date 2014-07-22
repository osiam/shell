package org.osiam.shell.command.io;

import java.text.SimpleDateFormat;

import org.osiam.client.oauth.AccessToken;

import de.raysha.lib.jsimpleshell.io.OutputConverter;

/**
 * {@link OutputConverter} for the {@link AccessToken}
 * 
 * @author rainu
 */
public class AccessTokenConverter implements OutputConverter {

	@Override
	public Object convertOutput(Object toBeFormatted) {
		if(toBeFormatted instanceof AccessToken){
			final AccessToken at = ((AccessToken)toBeFormatted);
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("Token: ");
			sb.append(at.getToken());
			sb.append("\nType: ");
			sb.append(at.getType());
			sb.append("\nExpires: ");
			sb.append(sdf.format(at.getExpiresAt()));
			
			return sb;
		}
		
		return null;
	}

}
