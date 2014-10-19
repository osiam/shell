package org.osiam.shell.command.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.osiam.resources.data.ImageDataURI;
import org.osiam.resources.scim.Photo;
import org.osiam.resources.scim.Photo.Type;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.Shell;
import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;
import de.raysha.lib.jsimpleshell.handler.ShellDependent;

/**
 * This class contains commands which can create {@link Photo}s.
 *
 * @author rainu
 */
public class PhotoBuilder extends AbstractBuilderCommand<Photo> implements ShellDependent {
	private Photo.Builder builder;
	private Photo current;

	public PhotoBuilder(Photo current) {
		this.current = current;
		this.builder = current == null ? new Photo.Builder() : new Photo.Builder(current);
	}

	@Override
	public void cliSetShell(Shell theShell) {
		if(current != null){
			theShell.addMainHandler(new ShowPhoto(), "");
		}
	}

	public class ShowPhoto {
		@Command(description = "Shows the current (persited) photo that will be replaced.")
		public Photo showPhoto(){
			return current;
		}
	}

	@Command(description = "Shows the photo state. This state is not persisted yet!")
	public Photo showState() {
		return _build();
	}

	@Command(description = "Set the display name of the photo.")
	public void setDisplay(
			@Param(value = "display", description = "The display name.")
			String display) {

		builder.setDisplay(display);
	}

	@Command(description = "Is this photo primary?")
	public void setPrimary(
			@Param(value = "primary", description = "True if this photo is primary. Otherwise false.")
			Boolean primary) {

		builder.setPrimary(primary);
	}

	@Command(description = "Sets the label indicating the attribute's function.")
	public void setType(
			@Param(value = "type", description = "The type of the attribute.")
			Type type) {

		builder.setType(type);
	}

	@Command(description = "Set the uri value of the photo.")
	public void setUriValue(
			@Param(value = "uri", description = "A URI of the form data:image/[image extension][;base64],<data>")
			String uri) {

		builder.setValue(URI.create(uri));
	}

	@Command(description = "Set the value of the photo.")
	public void setValue(
			@Param(value = "path", description = "The path of the localy stored photo file.")
			String path) throws FileNotFoundException, IOException {

		FileInputStream fileInputStream = new FileInputStream(new File(path));
		try {
			builder.setValue(new ImageDataURI(fileInputStream));
		} finally {
			try { fileInputStream.close(); } catch (Exception e) { }
		}
	}

	@Override
	protected Photo _build() {
		return builder.build();
	}
}
