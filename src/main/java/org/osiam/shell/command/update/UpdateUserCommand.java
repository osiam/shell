package org.osiam.shell.command.update;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
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
import org.osiam.shell.command.OsiamAccessCommand;
import org.osiam.shell.command.update.user.BuilderShellFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.ShellBuilder;
import de.raysha.lib.jsimpleshell.ShellDependent;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.io.InputBuilder;
import de.raysha.lib.jsimpleshell.io.InputDependent;
import de.raysha.lib.jsimpleshell.io.OutputBuilder;
import de.raysha.lib.jsimpleshell.io.OutputDependent;

/**
 * This class contains commands which can update users.
 * 
 * @author rainu
 */
public class UpdateUserCommand extends OsiamAccessCommand implements ShellDependent, OutputDependent {
	private Shell shell;
	private OutputBuilder output;
	
	public UpdateUserCommand(AccessToken at, OsiamConnector connector) {
		super(at, connector);
	}
	
	@Override
	public void cliSetShell(Shell theShell) {
		this.shell = theShell;
	}
	
	@Override
	public void cliSetOutput(OutputBuilder output) {
		this.output = output;
	}
	
	@Command(description = "Update the given user.")
	public Object updateUser(
			@Param(name = "userName", description = "The name of the user.")
			String userName) throws IOException{
		
		final User user = getUser(userName);
		if(user == null) return "There is no user with the name \"" + userName + "\"!";
		
		final UpdateUserBuilder builder = new UpdateUserBuilder(user);
		
		final Shell subShell = ShellBuilder.subshell("user-group", shell)
									.addHandler(builder)
								.build();
		
		output.out()
			.normal("In this subshell you can edit the user. Leave this sub shell via \"commit\" to persist the changes.")
		.println();
		
		subShell.commandLoop();
		
		final UpdateUser update = builder.build();
		if(update == null) return null;
		
		return connector.updateUser(user.getId(), update, accessToken);
	}
	

	public class UpdateUserBuilder extends AbstractBuilderCommand<UpdateUser> 
		implements OutputDependent, InputDependent, ShellDependent {
		
		private final User user;
		private UpdateUser.Builder builder = new UpdateUser.Builder();
		private OutputBuilder output;
		private InputBuilder input;
		private Shell shell;
		private BuilderShellFactory builderShellFactory = new BuilderShellFactory();
		private ObjectMapper objectMapper = new ObjectMapper();	
		
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
				@Param(name = "displayName", description = "The new display name for the user.")
				String displayName) {
			
			builder.updateDisplayName(displayName);
		}

		@Command(description = "Set the of externalId the user.")
		public void updateExternalId(
				@Param(name = "externalId", description = "The new externalId for the user.")
				String externalId) {
			
			builder.updateExternalId(externalId);
		}

		@Command(description = "Set the locale of the user.")
		public void updateLocale(
				@Param(name = "locale", description = "The new locale for the user.")
				String locale) {
			
			builder.updateLocale(locale);
		}

		@Command(description = "Set the nickname of the user.")
		public void updateNickName(
				@Param(name = "nickName", description = "The new nickname for the user.")
				String nickName) {
			
			builder.updateNickName(nickName);
		}

		@Command(description = "Set the password of the user.")
		public void updatePassword(
				@Param(name = "password", description = "The new password for the user.")
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
				@Param(name = "language", description = "The new preferred language for the user.")
				String preferredLanguage) {
			
			builder.updatePreferredLanguage(preferredLanguage);
		}

		@Command(description = "Set the profile URL of the user.")
		public void updateProfileUrl(
				@Param(name = "url", description = "The new profile URL for the user.")
				String profileUrl) {
			
			builder.updateProfileUrl(profileUrl);
		}

		@Command(description = "Set the timezone of the user.")
		public void updateTimezone(
				@Param(name = "timezone", description = "The new timezone for the user.")
				String timezone) {
			
			builder.updateTimezone(timezone);
		}

		@Command(description = "Set the title of the user.")
		public void updateTitle(
				@Param(name = "title", description = "The new title for the user.")
				String title) {
			
			builder.updateTitle(title);
		}

		@Command(description = "Rename the user.")
		public void rename(
				@Param(name = "name", description = "The new name for the user.")
				String userName) {
			
			builder.updateUserName(userName);
		}

		@Command(description = "Set the type of the user.")
		public void updateUserType(
				@Param(name = "type", description = "The new type for the user.")
				String userType) {
			
			builder.updateUserType(userType);
		}

		@Command(description = "Deletes all existing addresses.")
		public void deleteAllAddresses() {
			builder.deleteAddresses();
		}

		@Command(description = "Deletes all emails.")
		public void deleteAllEmails() {
			builder.deleteEmails();
		}

		@Command(description = "Deletes all entitlements.")
		public void deleteAllEntitlements() {
			builder.deleteEntitlements();
		}

		@Command(description = "Deletes all ims.")
		public void deleteAllIms() {
			builder.deleteIms();
		}

		@Command(description = "Deletes all phonenumbers.")
		public void deleteAllPhoneNumbers() {
			builder.deletePhoneNumbers();
		}

		@Command(description = "Deletes all photos.")
		public void deleteAllPhotos() {
			builder.deletePhotos();
		}

		@Command(description = "Deletes all roles.")
		public void deleteAllRoles() {
			builder.deleteRoles();
		}

		@Command(name = "delete-X509Certificates", description = "Deletes all X509Certificates.")
		public void deleteAllX509Certificates() {
			builder.deleteX509Certificates();
		}
		
		@Command(description = "Add an address for this user.")
		public void addAddress() throws IOException {
			final Address address = builderShellFactory.enterAddressShell();
			if(address != null){
				builder.addAddress(address);
			}
		}
		
		@Command(description = "Add an email for this user.")
		public void addEmail() throws IOException {
			final Email email = builderShellFactory.enterEmailShell();
			if(email != null){
				builder.addEmail(email);
			}
		}
		
		@Command(description = "Add an entitlement for this user.")
		public void addEntitlement() throws IOException {
			final Entitlement entitlement = builderShellFactory.enterEntitlementShell();
			if(entitlement != null){
				builder.addEntitlement(entitlement);
			}
		}
		
		@Command(description = "Add an im for this user.")
		public void addIm() throws IOException {
			final Im im = builderShellFactory.enterImShell();
			if(im != null){
				builder.addIm(im);
			}
		}
		
		@Command(description = "Add a phone number for this user.")
		public void addPhoneNumber() throws IOException {
			final PhoneNumber phoneNumber = builderShellFactory.enterPhoneNumberShell();
			if(phoneNumber != null){
				builder.addPhoneNumber(phoneNumber);
			}
		}
		
		@Command(description = "Add a photo for this user.")
		public void addPhoto() throws IOException {
			final Photo photo = builderShellFactory.enterPhotoShell();
			if(photo != null){
				builder.addPhoto(photo);
			}
		}
		
		@Command(description = "Add a role for this user.")
		public void addRole() throws IOException {
			final Role role = builderShellFactory.enterRoleShell();
			if(role != null){
				builder.addRole(role);
			}
		}
		
		@Command(name = "add-certificate", description = "Add a (X509)certificate for this user.")
		public void addX509Certificate() throws IOException {
			final X509Certificate x509Certificate = builderShellFactory.enterX509CertificateShell();
			if(x509Certificate != null){
				builder.addX509Certificate(x509Certificate);
			}
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
		
		@Command(description = "Delete an address from the user.")
		public void deleteAddress(
				@Param(name = "searchKey", description = "Which key should be used to identify the address.")
				String key,
				@Param(name = "expr", description = "If the value matches this regular expression, the address will deleted.")
				String valueExp){
			
			Set<Address> addresses = showAllAddresses();
			for(Address current : addresses){
				if(match(current, key, valueExp)){
					builder.deleteAddress(current);
				}
			}
		}
		
		@Command(description = "Delete an email from the user.")
		public void deleteEmail(
				@Param(name = "searchKey", description = "Which key should be used to identify the email.")
				String key,
				@Param(name = "expr", description = "If the value matches this regular expression, the email will deleted.")
				String valueExp){
			
			Set<Email> emails = showAllEmails();
			for(Email current : emails){
				if(match(current, key, valueExp)){
					builder.deleteEmail(current);
				}
			}
		}
		
		@Command(description = "Delete an entitlement from the user.")
		public void deleteEntitlement(
				@Param(name = "searchKey", description = "Which key should be used to identify the entitlement.")
				String key,
				@Param(name = "expr", description = "If the value matches this regular expression, the entitlement will deleted.")
				String valueExp){
			
			Set<Entitlement> entitlements = showAllEntitlements();
			for(Entitlement current : entitlements){
				if(match(current, key, valueExp)){
					builder.deleteEntitlement(current);
				}
			}
		}
		
		@Command(description = "Delete an im from the user.")
		public void deleteIm(
				@Param(name = "searchKey", description = "Which key should be used to identify the im.")
				String key,
				@Param(name = "expr", description = "If the value matches this regular expression, the im will deleted.")
				String valueExp){
			
			Set<Im> im = showAllIms();
			for(Im current : im){
				if(match(current, key, valueExp)){
					builder.deleteIm(current);
				}
			}
		}
		
		@Command(description = "Delete a phone number from the user.")
		public void deletePhoneNumber(
				@Param(name = "searchKey", description = "Which key should be used to identify the phone number.")
				String key,
				@Param(name = "expr", description = "If the value matches this regular expression, the phone number will deleted.")
				String valueExp){
			
			Set<PhoneNumber> phoneNumbers = showAllPhoneNumbers();
			for(PhoneNumber current : phoneNumbers){
				if(match(current, key, valueExp)){
					builder.deletePhoneNumber(current);
				}
			}
		}
		
		
		@Command(description = "Delete a photo from the user.")
		public void deletePhoto(
				@Param(name = "searchKey", description = "Which key should be used to identify the photo.")
				String key,
				@Param(name = "expr", description = "If the value matches this regular expression, the photo will deleted.")
				String valueExp){
			
			Set<Photo> photos = showAllPhotos();
			for(Photo current : photos){
				if(match(current, key, valueExp)){
					builder.deletePhoto(current);
				}
			}
		}
		
		@Command(description = "Delete a role from the user.")
		public void deleteRole(
				@Param(name = "searchKey", description = "Which key should be used to identify the role.")
				String key,
				@Param(name = "expr", description = "If the value matches this regular expression, the role will deleted.")
				String valueExp){
			
			Set<Role> roles = showAllRoles();
			for(Role current : roles){
				if(match(current, key, valueExp)){
					builder.deleteRole(current);
				}
			}
		}
		
		@Command(description = "Delete a certificate from the user.")
		public void deleteCertificate(
				@Param(name = "searchKey", description = "Which key should be used to identify the certificate.")
				String key,
				@Param(name = "expr", description = "If the value matches this regular expression, the certificate will deleted.")
				String valueExp){
			
			Set<X509Certificate> certificates = showAllX509Certificates();
			for(X509Certificate current : certificates){
				if(match(current, key, valueExp)){
					builder.deleteX509Certificate(current);
				}
			}
		}
		
		private boolean match(Object object, String key, String valueExp) {
			Map<String, Object> props = objectMapper.convertValue(object, Map.class);
			
			String sValue = "null";
			
			if(props.containsKey(key)){
				sValue = String.valueOf(props.get(key));
			}
			
			return sValue.matches(valueExp);
		}
		
		@Override
		protected UpdateUser _build() {
			return builder.build();
		}
	}
}
