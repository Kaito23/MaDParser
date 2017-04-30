package de.koetter.robin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * A file hanndling class
 * 
 * @author Robin
 */
public class FileHandler {

	/**
	 * Write String to a file
	 * 
	 * @param filename
	 *            name of the file
	 * @param content
	 *            the content of the file
	 */
	public final void writeToFile(final String filename, final String content) {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
			bw.write(content);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load a file
	 * 
	 * @param path
	 *            path to the file
	 * @return returns the file content
	 */
	public final String loadFile(final String path) {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(path)));
		} catch (final IOException e) {
			content = null;
			final Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Look, an Error Dialog");
			alert.setContentText("Ooops, there was an error!\n" + e);
			alert.showAndWait();
		}
		return content;
	}
}
