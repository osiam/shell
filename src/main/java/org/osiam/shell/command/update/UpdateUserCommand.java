package org.osiam.shell.command.update;

import java.io.IOException;

import org.osiam.client.OsiamConnector;
import org.osiam.client.oauth.AccessToken;
import org.osiam.resources.scim.Address;
import org.osiam.resources.scim.UpdateUser;
import org.osiam.resources.scim.User;
import org.osiam.shell.command.AbstractBuilderCommand;
import org.osiam.shell.command.OsiamAccessCommand;
import org.osiam.shell.command.update.user.AddressBuilder;

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
		
		public UpdateUserBuilder(User user) {
			this.user = user;
		}
		
		@Override
		public void cliSetOutput(OutputBuilder output) {
			this.output = output;
		}
		
		@Override
		public void cliSetInput(InputBuilder input) {
			this.input = input;
		}
		
		@Override
		public void cliSetShell(Shell theShell) {
			this.shell = theShell;
		}
		
		@Command(description = "Shows the current (persited) user that will be updated.")
		public User showUser(){
			return user;
		}
		
		@Command(description = "Shows the user state. This state is not persisted yet!")
		public User showState() {
			return build().getScimConformUpdateUser();
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
		public void deleteAddresses() {
			builder.deleteAddresses();
		}

		@Command(description = "Deletes all emails.")
		public void deleteEmails() {
			builder.deleteEmails();
		}

		@Command(description = "Deletes all entitlements.")
		public void deleteEntitlements() {
			builder.deleteEntitlements();
		}

		@Command(description = "Deletes all ims.")
		public void deleteIms() {
			builder.deleteIms();
		}

		@Command(description = "Deletes all phonenumbers.")
		public void deletePhoneNumbers() {
			builder.deletePhoneNumbers();
		}

		@Command(description = "Deletes all photos.")
		public void deletePhotos() {
			builder.deletePhotos();
		}

		@Command(description = "Deletes all roles.")
		public void deleteRoles() {
			builder.deleteRoles();
		}

		@Command(name = "delete-X509Certificates", description = "Deletes all X509Certificates.")
		public void deleteX509Certificates() {
			builder.deleteX509Certificates();
		}
		
		@Command(description = "Add an address for this user.")
		public void addAddress() throws IOException {
			final AddressBuilder addressBuilder = new AddressBuilder();
			
			final Shell subShell = ShellBuilder.subshell("create-address", shell)
					.addHandler(addressBuilder)
				.build();

			output.out()
				.normal("In this subshell you can create an address. Leave this sub shell via \"commit\" to persist the changes.")
			.println();
			
			subShell.commandLoop();
			
			final Address address = addressBuilder.build();
			if(address != null){
				builder.addAddress(address);
			}
		}
		
		@Override
		protected UpdateUser _build() {
			return builder.build();
		}
	}
}
