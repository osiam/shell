package org.osiam.shell.command.builder;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.osiam.resources.scim.Extension;
import org.osiam.shell.command.AbstractBuilderCommand;

import de.raysha.lib.jsimpleshell.annotation.Command;
import de.raysha.lib.jsimpleshell.annotation.Param;

/**
 * This class contains commands which can create {@link Extension}s.
 * 
 * @author rainu
 */
public class ExtensionBuilder extends AbstractBuilderCommand<Extension> {
	private Extension.Builder builder;

	public ExtensionBuilder(String urn) {
		this.builder = new Extension.Builder(urn);
	}
	
	@Command(description = "Shows the extension state. This state is not persisted yet!")
	public Extension showState() {
		return _build();
	}

	@Command(description = "Remove a field from the extension.")
	public void removeField(
			@Param(name = "field", description = "The name of the extension field.")
			String fieldName){
		
		builder.removeField(fieldName);
	}

	@Command(description = "Set the string value of a extension field.")
	public void setStringField(
			@Param(name = "field", description = "The name of the extension field.")
			String fieldName,
			@Param(name = "value", description = "The new field value.")
			String value){
		
		builder.setField(fieldName, value);
	}
	
	@Command(description = "Set the boolean value of a extension field.")
	public void setBooleanField(
			@Param(name = "field", description = "The name of the extension field.")
			String fieldName,
			@Param(name = "value", description = "The new field value.")
			Boolean value){
		
		builder.setField(fieldName, value);
	}
	
	@Command(description = "Set the decimal value of a extension field.")
	public void setDecimalField(
			@Param(name = "field", description = "The name of the extension field.")
			String fieldName,
			@Param(name = "value", description = "The new field value.")
			BigDecimal value){
		
		builder.setField(fieldName, value);
	}
	
	@Command(description = "Set the integer value of a extension field.")
	public void setIntegerField(
			@Param(name = "field", description = "The name of the extension field.")
			String fieldName,
			@Param(name = "value", description = "The new field value.")
			BigInteger value){
		
		builder.setField(fieldName, value);
	}
	
	@Command(description = "Set the date value of a extension field.")
	public void setDateField(
			@Param(name = "field", description = "The name of the extension field.")
			String fieldName,
			@Param(name = "value", description = "The new field value.")
			Date value){
		
		builder.setField(fieldName, value);
	}
	
	@Command(description = "Set the uri value of a extension field.")
	public void setURIField(
			@Param(name = "field", description = "The name of the extension field.")
			String fieldName,
			@Param(name = "value", description = "The new field value.")
			URI value){
		
		builder.setField(fieldName, value);
	}
	
	@Command(description = "Set the file value of a extension field.")
	public void setFileField(
			@Param(name = "field", description = "The name of the extension field.")
			String fieldName,
			@Param(name = "path", description = "The path to the file.")
			String filePath) throws IOException {
		
		Path path = new File(filePath).toPath();
		byte[] byteContent = Files.readAllBytes(path);
		
		builder.setField(fieldName, ByteBuffer.wrap(byteContent));
	}
	
	@Override
	protected Extension _build() {
		return builder.build();
	}
}
