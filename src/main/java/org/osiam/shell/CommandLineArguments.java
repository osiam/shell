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
package org.osiam.shell;

import com.beust.jcommander.Parameter;

public class CommandLineArguments {

	@Parameter(names = "--help", description = "Show Help")
	Boolean help = Boolean.FALSE;

	@Parameter(names = "--endpoint")
	String endpoint = "";

	@Parameter(names = "--redirectUri")
	String redirectUri = "";

	@Parameter(names = "--clientId")
	String clientId = "";

	@Parameter(names = "--clientSecret")
	String clientSecret = "";

	@Parameter(names = "--username")
	String username = "";

	@Parameter(names = "--password")
	String password = "";

	public boolean hasConnectionInformation() {
		return !endpoint.isEmpty()
				&& !redirectUri.isEmpty()
				&& !clientId.isEmpty()
				&& !clientSecret.isEmpty();
	}

	public boolean hasLoginInformation() {
		return !username.isEmpty() && !password.isEmpty();
	}
}
