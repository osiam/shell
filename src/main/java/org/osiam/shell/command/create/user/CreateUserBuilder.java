package org.osiam.shell.command.create.user;

import java.io.IOException;
import java.util.List;

import org.osiam.resources.scim.Address;
import org.osiam.resources.scim.Email;
import org.osiam.resources.scim.Entitlement;
import org.osiam.resources.scim.Im;
import org.osiam.resources.scim.PhoneNumber;
import org.osiam.resources.scim.Photo;
import org.osiam.resources.scim.Role;
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

public class CreateUserBuilder extends AbstractBuilderCommand<User> 
	implements OutputDependent, InputDependent, ShellDependent {
	
	User.Builder builder;
	private OutputBuilder output;
	private InputBuilder input;
	private Shell shell;
	BuilderShellFactory builderShellFactory = new BuilderShellFactory();
	ObjectMapper objectMapper = new ObjectMapper();	
	
	public CreateUserBuilder(String userName) {
		this.builder = new User.Builder(userName);
	}
	
	public CreateUserBuilder(User user, String newUserName) {
		this.builder = new User.Builder(newUserName, user);
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
		
		shell.addMainHandler(new CreateUserDeleteCommands(this), "");
		shell.addMainHandler(new CreateUserAddCommands(this), "");
	}
	
	@Command(description = "Shows the user state. This state is not persisted yet!")
	public User showState() {
		return _build();
	}

	@Command(description = "Deactivate the user.")
	public void deactivate() {
		builder.setActive(false);
	}

	@Command(description = "(Re-)Activate the user.")
	public void activate() {
		builder.setActive(true);
	}

	@Command(description = "Set the display name of the user.")
	public void setDisplayName(
			@Param(value = "displayName", description = "The display name for the user.")
			String displayName) {
		
		builder.setDisplayName(displayName);
	}

	@Command(description = "Set the of externalId the user.")
	public void setExternalId(
			@Param(value = "externalId", description = "The externalId for the user.")
			String externalId) {

		builder.setExternalId(externalId);
	}

	@Command(description = "Set the locale of the user.")
	public void setLocale(
			@Param(value = "locale", description = "The locale for the user.")
			String locale) {
		
		builder.setLocale(locale);
	}

	@Command(description = "Set the nickname of the user.")
	public void setNickName(
			@Param(value = "nickName", description = "The nickname for the user.")
			String nickName) {
		
		builder.setNickName(nickName);
	}

	@Command(description = "Set the password of the user.")
	public void setPassword(
			@Param(value = "password", description = "The password for the user.")
			String password) {
		
		builder.setPassword(password);
	}

	@Command(description = "Set the password of the user.")
	public void setPassword() throws IOException {
		final String password = input.invisibleIn()
				.withPromt("Enter the password: ")
			.readLine();
		final String repeat = input.invisibleIn()
				.withPromt("Repeat the password: ")
			.readLine();
		
		if(!password.equals(repeat)){
			output.out()
				.normal("Your inputs are not equals!")
			.println();
			return;
		}
		
		setPassword(password);
	}

	@Command(description = "Set the preferred language of the user.")
	public void setPreferredLanguage(
			@Param(value = "language", description = "The preferred language for the user.")
			String preferredLanguage) {

		builder.setPreferredLanguage(preferredLanguage);
	}

	@Command(description = "Set the profile URL of the user.")
	public void setProfileUrl(
			@Param(value = "url", description = "The profile URL for the user.")
			String profileUrl) {
		
		builder.setProfileUrl(profileUrl);
	}

	@Command(description = "Set the timezone of the user.")
	public void setTimezone(
			@Param(value = "timezone", description = "The timezone for the user.")
			String timezone) {
		
		builder.setTimezone(timezone);
	}

	@Command(description = "Set the title of the user.")
	public void setTitle(
			@Param(value = "title", description = "The title for the user.")
			String title) {
		
		builder.setTitle(title);
	}

	@Command(description = "Set the type of the user.")
	public void setUserType(
			@Param(value = "type", description = "The type for the user.")
			String userType) {
		
		builder.setUserType(userType);
	}

	@Command(description = "Show all addresses (non persisted) for the user.")
	public List<Address> showAllAddresses(){
		return _build().getAddresses();
	}
	
	@Command(description = "Show all emails (non persisted) for the user.")
	public List<Email> showAllEmails(){
		return _build().getEmails();
	}
	
	@Command(description = "Show all entitlements (non persisted) for the user.")
	public List<Entitlement> showAllEntitlements(){
		return _build().getEntitlements();
	}
	
	@Command(description = "Show all ims (non persisted) for the user.")
	public List<Im> showAllIms(){
		return _build().getIms();
	}
	
	@Command(description = "Show all phone numbers (non persisted) for the user.")
	public List<PhoneNumber> showAllPhoneNumbers(){
		return _build().getPhoneNumbers();
	}
	
	@Command(description = "Show all photos (non persisted) for the user.")
	public List<Photo> showAllPhotos(){
		return _build().getPhotos();
	}
	
	@Command(description = "Show all roles (non persisted) for the user.")
	public List<Role> showAllRoles(){
		return _build().getRoles();
	}
	
	@Command(name = "show-all-certificate", description = "Show all certificates (non persisted) for the user.")
	public List<X509Certificate> showAllX509Certificates(){
		return _build().getX509Certificates();
	}
	
	@Override
	protected User _build() {
		return builder.build();
	}
}