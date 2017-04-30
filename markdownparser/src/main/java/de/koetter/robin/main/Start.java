package de.koetter.robin.main;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import de.koetter.robin.eingabe.MainVC;
import de.koetter.robin.mapper.ElementsHandler;
import de.koetter.robin.model.DataBean;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Start of the MaDParser
 * 
 * @author Robin
 */
public class Start extends Application {

	/**
	 * Main
	 * 
	 * @param args
	 *            overgiven arguments
	 */
	public static void main(final String[] args) {
		launch(args);
	}

	/**
	 * Load .xml with regexes
	 * 
	 * @return elements of xml
	 */
	private static ElementsHandler load() {
		final Serializer serializer = new Persister();
		final File source = new File("patterns.xml");

		ElementsHandler example = null;
		try {
			example = serializer.read(ElementsHandler.class, source);
		} catch (final Exception e) {
			final Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Look, an Error Dialog");
			alert.setContentText("Ooops, there was an error!\n" + e);
			alert.showAndWait();
		}
		return example;
	}

	/**
	 * Start of javaFX application
	 */
	@Override
	public final void start(final Stage primaryStage) {
		// save();
		// final Elements elements = new Elements(new ArrayList<>());
		final ElementsHandler elements = load();

		final DataBean dataBean = new DataBean(primaryStage, elements);
		final MainVC eingabeVC = new MainVC(dataBean);
		eingabeVC.show();
	}
}