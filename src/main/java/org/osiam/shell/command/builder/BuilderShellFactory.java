package org.osiam.shell.command.builder;

import java.io.IOException;

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

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.ShellBuilder;
import de.raysha.lib.jsimpleshell.io.OutputBuilder;

/**
 * This class contains some methods which enters in a new subshell for creating entities.
 * 
 * @author rainu
 */
public class BuilderShellFactory {
	private OutputBuilder output;
	private Shell shell;

	public void setOutput(OutputBuilder output) {
		this.output = output;
	}
	
	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	/**
	 * Enter a new subshell for creating an {@link Name}.
	 * 
	 * @return The {@link Name}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public Name enterNameShell() throws IOException {
		final NameBuilder nameBuilder = new NameBuilder();
		
		final Shell subShell = ShellBuilder.subshell("create-name", shell)
				.disableExitCommand()
				.addHandler(nameBuilder)
			.build();

		output.out()
			.normal("In this subshell you can create an name. Leave this sub shell via \"commit\" to persist the changes.")
		.println();
		
		subShell.commandLoop();
		
		return nameBuilder.build();
	}

	/**
	 * Enter a new subshell for creating an {@link Address}.
	 * 
	 * @return The {@link Address}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public Address enterAddressShell() throws IOException {
		final AddressBuilder addressBuilder = new AddressBuilder();
		
		final Shell subShell = ShellBuilder.subshell("create-address", shell)
				.disableExitCommand()
				.addHandler(addressBuilder)
			.build();

		output.out()
			.normal("In this subshell you can create an address. Leave this sub shell via \"commit\" to persist the changes.")
		.println();
		
		subShell.commandLoop();
		
		return addressBuilder.build();
	}
	
	/**
	 * Enter a new subshell for creating an {@link Email}.
	 * 
	 * @return The {@link Email}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public Email enterEmailShell() throws IOException {
		final EmailBuilder emailBuilder = new EmailBuilder();
		
		final Shell subShell = ShellBuilder.subshell("create-email", shell)
				.disableExitCommand()
				.addHandler(emailBuilder)
			.build();

		output.out()
			.normal("In this subshell you can create an email. Leave this sub shell via \"commit\" to persist the changes.")
		.println();
		
		subShell.commandLoop();
		
		return emailBuilder.build();
	}
	
	/**
	 * Enter a new subshell for creating an {@link Entitlement}.
	 * 
	 * @return The {@link Entitlement}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public Entitlement enterEntitlementShell() throws IOException {
		final EntitlementBuilder entitlementBuilder = new EntitlementBuilder();
		
		final Shell subShell = ShellBuilder.subshell("create-entitlement", shell)
				.disableExitCommand()
				.addHandler(entitlementBuilder)
			.build();

		output.out()
			.normal("In this subshell you can create an entitlement. Leave this sub shell via \"commit\" to persist the changes.")
		.println();
		
		subShell.commandLoop();
		
		return entitlementBuilder.build();
	}
	
	public Extension enterExtensionShell(String urn) throws IOException {
		final ExtensionBuilder extensionBuilder = new ExtensionBuilder(urn);
		
		final Shell subShell = ShellBuilder.subshell("update-extension", shell)
				.disableExitCommand()
				.addHandler(extensionBuilder)
			.build();

		output.out()
			.normal("In this subshell you can create an extension. Leave this sub shell via \"commit\" to persist the changes.")
		.println();
		
		subShell.commandLoop();
		
		return extensionBuilder.build();
	}
	
	/**
	 * Enter a new subshell for creating an {@link Im}.
	 * 
	 * @return The {@link Im}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public Im enterImShell() throws IOException {
		final ImBuilder imBuilder = new ImBuilder();
		
		final Shell subShell = ShellBuilder.subshell("create-im", shell)
				.disableExitCommand()
				.addHandler(imBuilder)
			.build();

		output.out()
			.normal("In this subshell you can create an im. Leave this sub shell via \"commit\" to persist the changes.")
		.println();
		
		subShell.commandLoop();
		
		return imBuilder.build();
	}
	
	/**
	 * Enter a new subshell for creating a {@link PhoneNumber}.
	 * 
	 * @return The {@link PhoneNumber}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public PhoneNumber enterPhoneNumberShell() throws IOException {
		final PhoneNumberBuilder phoneNumberBuilder = new PhoneNumberBuilder();
		
		final Shell subShell = ShellBuilder.subshell("create-phone-number", shell)
				.disableExitCommand()
				.addHandler(phoneNumberBuilder)
			.build();

		output.out()
			.normal("In this subshell you can create a phone-number. Leave this sub shell via \"commit\" to persist the changes.")
		.println();
		
		subShell.commandLoop();
		
		return phoneNumberBuilder.build();
	}
	
	/**
	 * Enter a new subshell for creating a {@link Photo}.
	 * 
	 * @return The {@link Photo}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public Photo enterPhotoShell() throws IOException {
		final PhotoBuilder photoBuilder = new PhotoBuilder();
		
		final Shell subShell = ShellBuilder.subshell("create-photo", shell)
				.disableExitCommand()
				.addHandler(photoBuilder)
			.build();

		output.out()
			.normal("In this subshell you can create a photo. Leave this sub shell via \"commit\" to persist the changes.")
		.println();
		
		subShell.commandLoop();
		
		return photoBuilder.build();
	}
	
	/**
	 * Enter a new subshell for creating a {@link Role}.
	 * 
	 * @return The {@link Role}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public Role enterRoleShell() throws IOException {
		final RoleBuilder roleBuilder = new RoleBuilder();
		
		final Shell subShell = ShellBuilder.subshell("create-role", shell)
				.disableExitCommand()
				.addHandler(roleBuilder)
			.build();

		output.out()
			.normal("In this subshell you can create a role. Leave this sub shell via \"commit\" to persist the changes.")
		.println();
		
		subShell.commandLoop();
		
		return roleBuilder.build();
	}

	/**
	 * Enter a new subshell for creating a {@link X509Certificate}.
	 * 
	 * @return The {@link X509Certificate}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public X509Certificate enterX509CertificateShell() throws IOException {
		final X509CertificateBuilder x509CertificateBuilder = new X509CertificateBuilder();
		
		final Shell subShell = ShellBuilder.subshell("create-certificate", shell)
				.disableExitCommand()
				.addHandler(x509CertificateBuilder)
			.build();

		output.out()
			.normal("In this subshell you can create a certificate. Leave this sub shell via \"commit\" to persist the changes.")
		.println();
		
		subShell.commandLoop();
		
		return x509CertificateBuilder.build();
	}

}
