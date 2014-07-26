package org.osiam.shell.command.update.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.osiam.resources.data.ImageDataURI;
import org.osiam.resources.scim.Photo;
import org.osiam.resources.scim.Photo.Type;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

/**
 * This class contains commands which can create {@link Photo}s.
 * 
 * @author rainu
 */
public class PhotoBuilder extends AbstractBuilderCommand<Photo> {
	private Photo.Builder builder = new Photo.Builder();

	@Command(description = "Shows the photo state. This state is not persisted yet!")
	public Photo showState() {
		return _build();
	}

	@Command(description = "Set the display name of the photo.")
	public void setDisplay(
			@Param(name = "display", description = "The display name.")
			String display) {
		
		builder.setDisplay(display);
	}

	@Command(description = "Sets the operation.")
	public void setOperation(
			@Param(name = "operation", description = "The operation.")
			String operation) {
		
		builder.setOperation(operation);
	}

	@Command(description = "Is this photo primary?")
	public void setPrimary(
			@Param(name = "primary", description = "True if this photo is primary. Otherwise false.")
			Boolean primary) {
		
		builder.setPrimary(primary);
	}

	@Command(description = "Sets the label indicating the attribute's function.")
	public void setType(
			@Param(name = "type", description = "The type of the attribute.")
			Type type) {
		
		builder.setType(type);
	}

	@Command(description = "Set the uri value of the photo.")
	public void setUriValue(
			@Param(name = "uri", description = "A URI of the form data:image/[image extension][;base64],<data>")
			String uri) {
		
		builder.setValue(URI.create(uri));
	}
	
	@Command(description = "Set the value of the photo.")
	public void setValue(
			@Param(name = "path", description = "The path of the localy stored photo file.")
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
