/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 OSIAM, rainu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.osiam.shell.command.io;

import java.text.SimpleDateFormat;

import org.osiam.client.oauth.AccessToken;

import de.raysha.lib.jsimpleshell.handler.InputConverter;
import de.raysha.lib.jsimpleshell.handler.OutputConverter;

/**
 * {@link OutputConverter} for the {@link AccessToken}
 * 
 * @author rainu
 */
public class AccessTokenConverter implements OutputConverter, InputConverter {

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

	@Override
	public Object convertInput(String original, Class toClass) throws Exception {
		if(toClass == AccessToken.class){
			return new AccessToken.Builder(original).build();
		}
		
		return null;
	}
}
