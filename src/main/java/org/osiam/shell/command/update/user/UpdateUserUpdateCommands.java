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
package org.osiam.shell.command.update.user;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.osiam.resources.scim.Address;
import org.osiam.resources.scim.Email;
import org.osiam.resources.scim.Entitlement;
import org.osiam.resources.scim.Extension;
import org.osiam.resources.scim.Im;
import org.osiam.resources.scim.Name;
import org.osiam.resources.scim.PhoneNumber;
import org.osiam.resources.scim.Photo;
import org.osiam.resources.scim.Role;
import org.osiam.resources.scim.X509Certificate;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

/**
 * This class is only a part of {@link UpdateUserBuilder}! This class contains
 * commands to update entities.
 *
 * @author rainu
 */
public class UpdateUserUpdateCommands {
	private UpdateUserBuilder builderCommand;

	UpdateUserUpdateCommands(UpdateUserBuilder builder) {
		this.builderCommand = builder;
	}

	@Command(description = "Update an address from the user.", startsSubshell = true)
	public void updateAddress(
			@Param(value = "searchKey", description = "Which key should be used to identify the address.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the address will updated.")
			String valueExp) throws IOException{

		Set<Address> addresses = builderCommand.showAllAddresses();
		for(Address current : addresses){
			if(match(current, key, valueExp)){
				final Address address = builderCommand.builderShellFactory.enterAddressShell(current);
				if(address != null){
					builderCommand.builder.updateAddress(current, address);
				}
			}
		}
	}

	@Command(description = "Update an email from the user.", startsSubshell = true)
	public void updateEmail(
			@Param(value = "searchKey", description = "Which key should be used to identify the email.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the email will updated.")
			String valueExp) throws IOException{

		Set<Email> emails = builderCommand.showAllEmails();
		for(Email current : emails){
			if(match(current, key, valueExp)){
				final Email email = builderCommand.builderShellFactory.enterEmailShell(current);
				if(email != null){
					builderCommand.builder.updateEmail(current, email);
				}
			}
		}
	}

	@Command(description = "Update an entitlement from the user.", startsSubshell = true)
	public void updateEntitlement(
			@Param(value = "searchKey", description = "Which key should be used to identify the entitlement.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the entitlement will updated.")
			String valueExp) throws IOException{

		Set<Entitlement> entitlements = builderCommand.showAllEntitlements();
		for(Entitlement current : entitlements){
			if(match(current, key, valueExp)){
				final Entitlement entitlement = builderCommand.builderShellFactory.enterEntitlementShell(current);
				if(entitlement != null){
					builderCommand.builder.updateEntitlement(current, entitlement);
				}
			}
		}
	}

	@Command(description = "Update the extension.", startsSubshell = true)
	public void updateExtension(
			@Param(value = "urn", description = "The URN of the extension.")
			String urn) throws IOException{

		final Extension extension = builderCommand.builderShellFactory.enterExtensionShell(urn);
		if(extension != null){
			builderCommand.builder.updateExtension(extension);
		}
	}

	@Command(description = "Update an im from the user.", startsSubshell = true)
	public void updateIm(
			@Param(value = "searchKey", description = "Which key should be used to identify the im.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the im will updated.")
			String valueExp) throws IOException{

		Set<Im> im = builderCommand.showAllIms();
		for(Im current : im){
			if(match(current, key, valueExp)){
				final Im newIm = builderCommand.builderShellFactory.enterImShell(current);
				if(im != null){
					builderCommand.builder.updateIm(current, newIm);
				}
			}
		}
	}

	@Command(description = "Update the (advanced) user name.", startsSubshell = true)
	public void updateName() throws IOException{
		final Name name = builderCommand.builderShellFactory.enterNameShell();
		if(name != null){
			builderCommand.builder.updateName(name);
		}
	}

	@Command(description = "Update a phone number from the user.", startsSubshell = true)
	public void updatePhoneNumber(
			@Param(value = "searchKey", description = "Which key should be used to identify the phone number.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the phone number will updated.")
			String valueExp) throws IOException{

		Set<PhoneNumber> phoneNumbers = builderCommand.showAllPhoneNumbers();
		for(PhoneNumber current : phoneNumbers){
			if(match(current, key, valueExp)){
				final PhoneNumber phoneNumber = builderCommand.builderShellFactory.enterPhoneNumberShell(current);
				if(phoneNumber != null){
					builderCommand.builder.updatePhoneNumber(current, phoneNumber);
				}
			}
		}
	}


	@Command(description = "Update a photo from the user.", startsSubshell = true)
	public void updatePhoto(
			@Param(value = "searchKey", description = "Which key should be used to identify the photo.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the photo will updated.")
			String valueExp) throws IOException{

		Set<Photo> photos = builderCommand.showAllPhotos();
		for(Photo current : photos){
			if(match(current, key, valueExp)){
				final Photo photo = builderCommand.builderShellFactory.enterPhotoShell(current);
				if(photo != null){
					builderCommand.builder.updatePhoto(current, photo);
				}
			}
		}
	}

	@Command(description = "Update a role from the user.", startsSubshell = true)
	public void updateRole(
			@Param(value = "searchKey", description = "Which key should be used to identify the role.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the role will updated.")
			String valueExp) throws IOException{

		Set<Role> roles = builderCommand.showAllRoles();
		for(Role current : roles){
			if(match(current, key, valueExp)){
				final Role role = builderCommand.builderShellFactory.enterRoleShell(current);
				if(role != null){
					builderCommand.builder.updateRole(current, role);
				}
			}
		}
	}

	@Command(description = "Update a certificate from the user.", startsSubshell = true)
	public void updateCertificate(
			@Param(value = "searchKey", description = "Which key should be used to identify the certificate.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the certificate will updated.")
			String valueExp) throws IOException{

		Set<X509Certificate> certificates = builderCommand.showAllX509Certificates();
		for(X509Certificate current : certificates){
			if(match(current, key, valueExp)){
				final X509Certificate certificate = builderCommand.builderShellFactory.enterX509CertificateShell(current);
				if(certificate != null){
					builderCommand.builder.updateX509Certificate(current, certificate);
				}
			}
		}
	}

	private boolean match(Object object, String key, String valueExp) {
		Map<String, Object> props = builderCommand.objectMapper.convertValue(object, Map.class);

		String sValue = "null";

		if(props.containsKey(key)){
			sValue = String.valueOf(props.get(key));
		}

		return sValue.matches(valueExp);
	}
}
