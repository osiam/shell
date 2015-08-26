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
import java.util.HashSet;
import java.util.Set;

import org.osiam.resources.scim.Address;
import org.osiam.resources.scim.Email;
import org.osiam.resources.scim.Entitlement;
import org.osiam.resources.scim.Im;
import org.osiam.resources.scim.PhoneNumber;
import org.osiam.resources.scim.Photo;
import org.osiam.resources.scim.Role;
import org.osiam.resources.scim.UpdateUser;
import org.osiam.resources.scim.User;
import org.osiam.resources.scim.X509Certificate;
import org.osiam.shell.command.AbstractBuilderCommand;
import org.osiam.shell.command.builder.BuilderShellFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.handler.InputDependent;
import de.raysha.lib.jsimpleshell.handler.OutputDependent;
import de.raysha.lib.jsimpleshell.handler.ShellDependent;
import de.raysha.lib.jsimpleshell.io.InputBuilder;
import de.raysha.lib.jsimpleshell.io.OutputBuilder;

public class UpdateUserBuilder extends AbstractBuilderCommand<UpdateUser> 
	implements OutputDependent, InputDependent, ShellDependent {
	
	private final User user;
	UpdateUser.Builder builder = new UpdateUser.Builder();
	private OutputBuilder output;
	private InputBuilder input;
	private Shell shell;
	BuilderShellFactory builderShellFactory = new BuilderShellFactory();
	ObjectMapper objectMapper = new ObjectMapper();	
	
	public UpdateUserBuilder(User user) {
		this.user = user;
	}
	
	@Override
	public void cliSetOutput(OutputBuilder output) {
		this.output = output;
		this.builderShellFactory.setOutput(output);
	}
	
	@Override
	public void cliSetInput(InputBuilder input) {
		this.input = input;
	}
	
	@Override
	public void cliSetShell(Shell theShell) {
		this.shell = theShell;
		this.builderShellFactory.setShell(theShell);
		
		shell.addMainHandler(new UpdateUserDeleteCommands(this), "");
		shell.addMainHandler(new UpdateUserAddCommands(this), "");
		shell.addMainHandler(new UpdateUserUpdateCommands(this), "");
	}
	
	@Command(description = "Shows the current (persited) user that will be updated.")
	public User showUser(){
		return user;
	}
	
	@Command(description = "Shows the user state. This state is not persisted yet!")
	public User showState() {
		return _build().getScimConformUpdateUser();
	}

	@Command(description = "Deactivate the user.")
	public void deactivate() {
		builder.updateActive(false);
	}

	@Command(description = "(Re-)Activate the user.")
	public void activate() {
		builder.updateActive(true);
	}

	@Command(description = "Set the display name of the user.")
	public void updateDisplayName(
			@Param(value = "displayName", description = "The new display name for the user.")
			String displayName) {
		
		builder.updateDisplayName(displayName);
	}

	@Command(description = "Set the of externalId the user.")
	public void updateExternalId(
			@Param(value = "externalId", description = "The new externalId for the user.")
			String externalId) {

		builder.updateExternalId(externalId);
	}

	@Command(description = "Set the locale of the user.")
	public void updateLocale(
			@Param(value = "locale", description = "The new locale for the user.")
			String locale) {
		
		builder.updateLocale(locale);
	}

	@Command(description = "Set the nickname of the user.")
	public void updateNickName(
			@Param(value = "nickName", description = "The new nickname for the user.")
			String nickName) {
		
		builder.updateNickName(nickName);
	}

	@Command(description = "Set the password of the user.")
	public void updatePassword(
			@Param(value = "password", description = "The new password for the user.")
			String password) {
		
		builder.updatePassword(password);
	}

	@Command(description = "Set the password of the user.")
	public void updatePassword() throws IOException {
		final String password = input.invisibleIn()
				.withPromt("Enter the new password: ")
			.readLine();
		final String repeat = input.invisibleIn()
				.withPromt("Repeat the new password: ")
			.readLine();
		
		if(!password.equals(repeat)){
			output.out()
				.normal("Your inputs are not equals!")
			.println();
			return;
		}
		
		updatePassword(password);
	}

	@Command(description = "Set the preferred language of the user.")
	public void updatePreferredLanguage(
			@Param(value = "language", description = "The new preferred language for the user.")
			String preferredLanguage) {
		
		builder.updatePreferredLanguage(preferredLanguage);
	}

	@Command(description = "Set the profile URL of the user.")
	public void updateProfileUrl(
			@Param(value = "url", description = "The new profile URL for the user.")
			String profileUrl) {
		
		builder.updateProfileUrl(profileUrl);
	}

	@Command(description = "Set the timezone of the user.")
	public void updateTimezone(
			@Param(value = "timezone", description = "The new timezone for the user.")
			String timezone) {

		builder.updateTimezone(timezone);
	}

	@Command(description = "Set the title of the user.")
	public void updateTitle(
			@Param(value = "title", description = "The new title for the user.")
			String title) {
		
		builder.updateTitle(title);
	}

	@Command(description = "Rename the user.")
	public void rename(
			@Param(value = "name", description = "The new name for the user.")
			String userName) {
		
		builder.updateUserName(userName);
	}

	@Command(description = "Set the type of the user.")
	public void updateUserType(
			@Param(value = "type", description = "The new type for the user.")
			String userType) {
		
		builder.updateUserType(userType);
	}

	@Command(description = "Show all addresses (persited and non persisted) for the user.")
	public Set<Address> showAllAddresses(){
		Set<Address> all = new HashSet<>(user.getAddresses());
		all.addAll(_build().getScimConformUpdateUser().getAddresses());
		
		return all;
	}
	
	@Command(description = "Show all emails (persited and non persisted) for the user.")
	public Set<Email> showAllEmails(){
		Set<Email> all = new HashSet<>(user.getEmails());
		all.addAll(_build().getScimConformUpdateUser().getEmails());
		
		return all;
	}
	
	@Command(description = "Show all entitlements (persited and non persisted) for the user.")
	public Set<Entitlement> showAllEntitlements(){
		Set<Entitlement> all = new HashSet<>(user.getEntitlements());
		all.addAll(_build().getScimConformUpdateUser().getEntitlements());
		
		return all;
	}
	
	@Command(description = "Show all ims (persited and non persisted) for the user.")
	public Set<Im> showAllIms(){
		Set<Im> all = new HashSet<>(user.getIms());
		all.addAll(_build().getScimConformUpdateUser().getIms());
		
		return all;
	}
	
	@Command(description = "Show all phone numbers (persited and non persisted) for the user.")
	public Set<PhoneNumber> showAllPhoneNumbers(){
		Set<PhoneNumber> all = new HashSet<>(user.getPhoneNumbers());
		all.addAll(_build().getScimConformUpdateUser().getPhoneNumbers());
		
		return all;
	}
	
	@Command(description = "Show all photos (persited and non persisted) for the user.")
	public Set<Photo> showAllPhotos(){
		Set<Photo> all = new HashSet<>(user.getPhotos());
		all.addAll(_build().getScimConformUpdateUser().getPhotos());
		
		return all;
	}
	
	@Command(description = "Show all roles (persited and non persisted) for the user.")
	public Set<Role> showAllRoles(){
		Set<Role> all = new HashSet<>(user.getRoles());
		all.addAll(_build().getScimConformUpdateUser().getRoles());
		
		return all;
	}
	
	@Command(name = "show-all-certificate", description = "Show all certificates (persited and non persisted) for the user.")
	public Set<X509Certificate> showAllX509Certificates(){
		Set<X509Certificate> all = new HashSet<>(user.getX509Certificates());
		all.addAll(_build().getScimConformUpdateUser().getX509Certificates());
		
		return all;
	}
	
	@Override
	protected UpdateUser _build() {
		return builder.build();
	}
}