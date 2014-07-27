package org.osiam.shell.command.update.user;

import java.io.IOException;

import org.osiam.resources.scim.Address;
import org.osiam.resources.scim.Email;
import org.osiam.resources.scim.Entitlement;
import org.osiam.resources.scim.Im;
import org.osiam.resources.scim.PhoneNumber;
import org.osiam.resources.scim.Photo;
import org.osiam.resources.scim.Role;
import org.osiam.resources.scim.X509Certificate;

import de.raysha.lib.jsimpleshell.annotation.Command;

/**
 * This class is only a part of {@link UpdateUserBuilder}! This class contains
 * commands to add entities.
 * 
 * @author rainu
 */
class UpdateUserAddCommands {
	private UpdateUserBuilder builderCommand;
	
	UpdateUserAddCommands(UpdateUserBuilder builder) {
		this.builderCommand = builder;
	}
	
	@Command(description = "Add an address for this user.")
	public void addAddress() throws IOException {
		final Address address = builderCommand.builderShellFactory.enterAddressShell();
		if(address != null){
			builderCommand.builder.addAddress(address);
		}
	}
	
	@Command(description = "Add an email for this user.")
	public void addEmail() throws IOException {
		final Email email = builderCommand.builderShellFactory.enterEmailShell();
		if(email != null){
			builderCommand.builder.addEmail(email);
		}
	}
	
	@Command(description = "Add an entitlement for this user.")
	public void addEntitlement() throws IOException {
		final Entitlement entitlement = builderCommand.builderShellFactory.enterEntitlementShell();
		if(entitlement != null){
			builderCommand.builder.addEntitlement(entitlement);
		}
	}
	
	@Command(description = "Add an im for this user.")
	public void addIm() throws IOException {
		final Im im = builderCommand.builderShellFactory.enterImShell();
		if(im != null){
			builderCommand.builder.addIm(im);
		}
	}
	
	@Command(description = "Add a phone number for this user.")
	public void addPhoneNumber() throws IOException {
		final PhoneNumber phoneNumber = builderCommand.builderShellFactory.enterPhoneNumberShell();
		if(phoneNumber != null){
			builderCommand.builder.addPhoneNumber(phoneNumber);
		}
	}
	
	@Command(description = "Add a photo for this user.")
	public void addPhoto() throws IOException {
		final Photo photo = builderCommand.builderShellFactory.enterPhotoShell();
		if(photo != null){
			builderCommand.builder.addPhoto(photo);
		}
	}
	
	@Command(description = "Add a role for this user.")
	public void addRole() throws IOException {
		final Role role = builderCommand.builderShellFactory.enterRoleShell();
		if(role != null){
			builderCommand.builder.addRole(role);
		}
	}
	
	@Command(name = "add-certificate", description = "Add a (X509)certificate for this user.")
	public void addX509Certificate() throws IOException {
		final X509Certificate x509Certificate = builderCommand.builderShellFactory.enterX509CertificateShell();
		if(x509Certificate != null){
			builderCommand.builder.addX509Certificate(x509Certificate);
		}
	}
}
