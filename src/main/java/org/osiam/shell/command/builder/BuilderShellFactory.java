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
import de.raysha.lib.jsimpleshell.builder.ShellBuilder;
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
			.behavior()
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
		return enterAddressShell(null);
	}

	/**
	 * Enter a new subshell for creating an {@link Address}.
	 *
	 * @param current The current persisted {@link Address}.
	 * @return The {@link Address}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public Address enterAddressShell(Address current) throws IOException {
		final AddressBuilder addressBuilder = new AddressBuilder(current);

		final Shell subShell = ShellBuilder.subshell((current == null ? "create" : "replace") + "-address", shell)
			.behavior()
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
		return enterEmailShell(null);
	}

	/**
	 * Enter a new subshell for creating an {@link Email}.
	 *
	 * @param current The current persisted {@link Email}.
	 * @return The {@link Email}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public Email enterEmailShell(Email current) throws IOException {
		final EmailBuilder emailBuilder = new EmailBuilder(current);

		final Shell subShell = ShellBuilder.subshell((current == null ? "create" : "replace") + "-email", shell)
			.behavior()
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
		return enterEntitlementShell(null);
	}

	/**
	 * Enter a new subshell for creating an {@link Entitlement}.
	 *
	 * @param current The current persisted {@link Entitlement}.
	 * @return The {@link Entitlement}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public Entitlement enterEntitlementShell(Entitlement current) throws IOException {
		final EntitlementBuilder entitlementBuilder = new EntitlementBuilder(current);

		final Shell subShell = ShellBuilder.subshell((current == null ? "create" : "replace") + "-entitlement", shell)
			.behavior()
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
			.behavior()
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
		return enterImShell(null);
	}

	/**
	 * Enter a new subshell for creating an {@link Im}.
	 *
	 * @param current The current persisted {@link Im}.
	 * @return The {@link Im}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public Im enterImShell(Im current) throws IOException {
		final ImBuilder imBuilder = new ImBuilder(current);

		final Shell subShell = ShellBuilder.subshell((current == null ? "create" : "replace") + "-im", shell)
			.behavior()
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
		return enterPhoneNumberShell(null);
	}

	/**
	 * Enter a new subshell for creating a {@link PhoneNumber}.
	 *
	 * @param current The current persisted {@link PhoneNumber}.
	 * @return The {@link PhoneNumber}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public PhoneNumber enterPhoneNumberShell(PhoneNumber current) throws IOException {
		final PhoneNumberBuilder phoneNumberBuilder = new PhoneNumberBuilder(current);

		final Shell subShell = ShellBuilder.subshell((current == null ? "create" : "replace") + "-phone-number", shell)
			.behavior()
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
		return enterPhotoShell(null);
	}

	/**
	 * Enter a new subshell for creating a {@link Photo}.
	 *
	 * @param current The current persisted {@link Photo}.
	 * @return The {@link Photo}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public Photo enterPhotoShell(Photo current) throws IOException {
		final PhotoBuilder photoBuilder = new PhotoBuilder(current);

		final Shell subShell = ShellBuilder.subshell((current == null ? "create" : "replace") + "-photo", shell)
			.behavior()
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
		return enterRoleShell(null);
	}

	/**
	 * Enter a new subshell for creating a {@link Role}.
	 *
	 * @param current The current persisted {@link Role}.
	 * @return The {@link Role}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public Role enterRoleShell(Role current) throws IOException {
		final RoleBuilder roleBuilder = new RoleBuilder(current);

		final Shell subShell = ShellBuilder.subshell((current == null ? "create" : "replace") + "-role", shell)
			.behavior()
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
		return enterX509CertificateShell(null);
	}

	/**
	 * Enter a new subshell for creating a {@link X509Certificate}.
	 *
	 * @param current The current persisted {@link X509Certificate}.
	 * @return The {@link X509Certificate}. Or null if the user interrupt the process.
	 * @throws IOException
	 */
	public X509Certificate enterX509CertificateShell(X509Certificate current) throws IOException {
		final X509CertificateBuilder x509CertificateBuilder = new X509CertificateBuilder(current);

		final Shell subShell = ShellBuilder.subshell((current == null ? "create" : "replace") + "-certificate", shell)
			.behavior()
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
