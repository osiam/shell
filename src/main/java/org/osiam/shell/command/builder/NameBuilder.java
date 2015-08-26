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
package org.osiam.shell.command.builder;

import org.osiam.resources.scim.Name;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

/**
 * This class contains commands which can create {@link Name}s.
 * 
 * @author rainu
 */
public class NameBuilder extends AbstractBuilderCommand<Name> {
	private Name.Builder builder = new Name.Builder();

	@Command(description = "Shows the name state. This state is not persisted yet!")
	public Name showState() {
		return _build();
	}
	
	@Command(description = "Set the familiy name.")
	public void setFamilyName(
			@Param(value = "familyName", description = "The family name.")
			String familyName) {
		
		builder.setFamilyName(familyName);
	}

	@Command(description = "Set the formatted name.")
	public void setFormatted(
			@Param(value = "formatted", description = "The formatted name.")
			String formatted) {
		
		builder.setFormatted(formatted);
	}

	@Command(description = "Set the given name.")
	public void setGivenName(
			@Param(value = "givenName", description = "The given name.")
			String givenName) {
		
		builder.setGivenName(givenName);
	}

	@Command(description = "Set the honorific prefix.")
	public void setHonorificPrefix(
			@Param(value = "honorificPrefix", description = "The honorific prefix.")
			String honorificPrefix) {
		
		builder.setHonorificPrefix(honorificPrefix);
	}

	@Command(description = "Set the honorific suffix.")
	public void setHonorificSuffix(
			@Param(value = "honorificSuffix", description = "The honorific suffix.")
			String honorificSuffix) {
		
		builder.setHonorificSuffix(honorificSuffix);
	}

	@Command(description = "Set the middle name.")
	public void setMiddleName(
			@Param(value = "middleName", description = "The middle name.")
			String middleName) {
		
		builder.setMiddleName(middleName);
	}

	@Override
	protected Name _build() {
		return builder.build();
	}
}
