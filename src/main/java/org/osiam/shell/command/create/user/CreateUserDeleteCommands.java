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
package org.osiam.shell.command.create.user;

import java.util.List;
import java.util.Map;

import org.osiam.resources.scim.Address;
import org.osiam.resources.scim.Email;
import org.osiam.resources.scim.Entitlement;
import org.osiam.resources.scim.Im;
import org.osiam.resources.scim.PhoneNumber;
import org.osiam.resources.scim.Photo;
import org.osiam.resources.scim.Role;
import org.osiam.resources.scim.X509Certificate;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

/**
 * This class is only a part of {@link CreateUserBuilder}! This class contains
 * commands to delete entities.
 * 
 * @author rainu
 */
public class CreateUserDeleteCommands {
	private CreateUserBuilder builderCommand;
	
	CreateUserDeleteCommands(CreateUserBuilder builder) {
		this.builderCommand = builder;
	}
	
	@Command(description = "Deletes all existing addresses.")
	public void deleteAllAddresses() {
		builderCommand.builder.removeAddresses();
	}

	@Command(description = "Deletes all emails.")
	public void deleteAllEmails() {
		builderCommand.builder.removeEmails();
	}

	@Command(description = "Deletes all entitlements.")
	public void deleteAllEntitlements() {
		builderCommand.builder.removeEntitlements();
	}
	
	@Command(description = "Deletes all ims.")
	public void deleteAllIms() {
		builderCommand.builder.removeIms();
	}

	@Command(description = "Deletes all phonenumbers.")
	public void deleteAllPhoneNumbers() {
		builderCommand.builder.removePhoneNumbers();
	}

	@Command(description = "Deletes all photos.")
	public void deleteAllPhotos() {
		builderCommand.builder.removePhotos();
	}

	@Command(description = "Deletes all roles.")
	public void deleteAllRoles() {
		builderCommand.builder.removeRoles();
	}

	@Command(name = "delete-X509Certificates", description = "Deletes all X509Certificates.")
	public void deleteAllX509Certificates() {
		builderCommand.builder.removeX509Certificates();
	}
	
	@Command(description = "Delete an address from the user.")
	public void deleteAddress(
			@Param(value = "searchKey", description = "Which key should be used to identify the address.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the address will deleted.")
			String valueExp){
		
		List<Address> addresses = builderCommand.showAllAddresses();
		for(Address current : addresses){
			if(match(current, key, valueExp)){
				builderCommand.builder.removeAddress(current);
			}
		}
	}
	
	@Command(description = "Delete an email from the user.")
	public void deleteEmail(
			@Param(value = "searchKey", description = "Which key should be used to identify the email.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the email will deleted.")
			String valueExp){
		
		List<Email> emails = builderCommand.showAllEmails();
		for(Email current : emails){
			if(match(current, key, valueExp)){
				builderCommand.builder.removeEmail(current);
			}
		}
	}
	
	@Command(description = "Delete an entitlement from the user.")
	public void deleteEntitlement(
			@Param(value = "searchKey", description = "Which key should be used to identify the entitlement.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the entitlement will deleted.")
			String valueExp){
		
		List<Entitlement> entitlements = builderCommand.showAllEntitlements();
		for(Entitlement current : entitlements){
			if(match(current, key, valueExp)){
				builderCommand.builder.removeEntitlement(current);
			}
		}
	}
	
	@Command(description = "Deletes the given extension.")
	public void deleteExtension(
			@Param(value = "urn", description = "The URN of the extension.")
			String urn) {
		
		builderCommand.builder.removeExtension(urn);
	}
	
	@Command(description = "Delete an im from the user.")
	public void deleteIm(
			@Param(value = "searchKey", description = "Which key should be used to identify the im.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the im will deleted.")
			String valueExp){
		
		List<Im> im = builderCommand.showAllIms();
		for(Im current : im){
			if(match(current, key, valueExp)){
				builderCommand.builder.removeIm(current);
			}
		}
	}
	
	@Command(description = "Delete a phone number from the user.")
	public void deletePhoneNumber(
			@Param(value = "searchKey", description = "Which key should be used to identify the phone number.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the phone number will deleted.")
			String valueExp){
		
		List<PhoneNumber> phoneNumbers = builderCommand.showAllPhoneNumbers();
		for(PhoneNumber current : phoneNumbers){
			if(match(current, key, valueExp)){
				builderCommand.builder.removePhoneNumber(current);
			}
		}
	}
	
	
	@Command(description = "Delete a photo from the user.")
	public void deletePhoto(
			@Param(value = "searchKey", description = "Which key should be used to identify the photo.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the photo will deleted.")
			String valueExp){
		
		List<Photo> photos = builderCommand.showAllPhotos();
		for(Photo current : photos){
			if(match(current, key, valueExp)){
				builderCommand.builder.removePhoto(current);
			}
		}
	}
	
	@Command(description = "Delete a role from the user.")
	public void deleteRole(
			@Param(value = "searchKey", description = "Which key should be used to identify the role.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the role will deleted.")
			String valueExp){
		
		List<Role> roles = builderCommand.showAllRoles();
		for(Role current : roles){
			if(match(current, key, valueExp)){
				builderCommand.builder.removeRole(current);
			}
		}
	}
	
	@Command(description = "Delete a certificate from the user.")
	public void deleteCertificate(
			@Param(value = "searchKey", description = "Which key should be used to identify the certificate.")
			String key,
			@Param(value = "expr", description = "If the value matches this regular expression, the certificate will deleted.")
			String valueExp){
		
		List<X509Certificate> certificates = builderCommand.showAllX509Certificates();
		for(X509Certificate current : certificates){
			if(match(current, key, valueExp)){
				builderCommand.builder.removeX509Certificate(current);
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
