package de.koetter.robin.eingabe;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import de.koetter.robin.FileHandler;
import de.koetter.robin.Parser;
import de.koetter.robin.credits.CreditsView;
import de.koetter.robin.model.DataBean;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller for the main view.
 * 
 * @author Robin Kötter
 */
public class MainVC {

	/** Model */
	private final DataBean dataBean;

	/** View */
	private final MainView view;
	/** Input textarea for markdown */
	private final TextArea markedInputTextarea;
	/** parsed html output */
	private final TextArea parsedOutputTextarea;

	/**
	 * Controller for the main view.
	 * 
	 * @param dataBean
	 *            databean that contains all data.
	 */
	public MainVC(final DataBean dataBean) {
		this.dataBean = dataBean;
		this.view = new MainView();

		parsedOutputTextarea = view.getMarkdownInputTextarea();
		markedInputTextarea = view.getMarkedOutputTextarea();

		view.getOpenFile().setOnAction(event -> {
			openFile();
		});

		view.getSaveFileAs().setOnAction(event -> {
			saveFileAs();
		});

		// changelistener
		parsedOutputTextarea.textProperty().addListener((observable, oldValue, newValue) -> {
			handleInput(newValue);
		});

		view.getMenuExportWebpage().setOnAction(event -> {
			exportWebpage();
		});

		view.getMenuCredits().setOnAction(event -> {
			showCredits();
		});

		view.getMenuItemHelp().setOnAction(event -> {
			showHelp();
		});

	}

	/** Shows the help */
	private void showHelp() {
		final Stage dialog = new Stage();
		final WebView browser = new WebView();
		final WebEngine webEngine = browser.getEngine();
		final BorderPane browserContainer = new BorderPane();
		browserContainer.setCenter(browser);

		final String template = getFile("templates/example.html");
		final String helpContentText = getFile("help/help.tpl.html");
		final String help = template.replace("$body", helpContentText);
		webEngine.loadContent(help, "text/html");

		dialog.setScene(new Scene(browserContainer));
		dialog.initOwner(dataBean.getPrimaryStage());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}

	/** Displays a credits modal */
	private void showCredits() {
		final Stage dialog = new Stage();
		final CreditsView creditsView = new CreditsView();
		dialog.setScene(new Scene(creditsView));

		dialog.initOwner(dataBean.getPrimaryStage());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}

	/** Export input as a full webpage */
	private void exportWebpage() {
		final String template = getFile("templates/example.html");
		final String replace = template.replace("$body", markedInputTextarea.getText());
		try {
			final FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("HTML File", "*.html"));
			fileChooser.setTitle("Save .md File");
			final File file = fileChooser.showSaveDialog(dataBean.getPrimaryStage());
			final FileHandler fileHandler = new FileHandler();
			fileHandler.writeToFile(file.getAbsolutePath(), replace);
		} catch (final Exception e) {
			final Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Look, an Error Dialog");
			alert.setContentText("Ooops, there was an error!\n" + e);
			alert.showAndWait();
		}
	}

	/**
	 * Two way data-binding handler
	 * 
	 * @param newValue
	 *            the user input
	 */
	private void handleInput(final String newValue) {
		final WebEngine webEngine = view.getWebEngine();
		final Parser transformer = new Parser();
		final String transformInput = transformer.parseInput(newValue, dataBean.getElementsHandler().getList());
		webEngine.loadContent(transformInput, "text/html");
		markedInputTextarea.setText(transformInput);
	}

	/** open .md file */
	private void openFile() {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Markup file", "*.md"));
		fileChooser.setTitle("Save .md File");
		final File showOpenDialog = fileChooser.showOpenDialog(dataBean.getPrimaryStage());

		final FileHandler fileHandler = new FileHandler();
		final String loadFile = fileHandler.loadFile(showOpenDialog.getAbsolutePath());
		parsedOutputTextarea.setText(loadFile);
	}

	/** Save .md file as */
	private void saveFileAs() {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Markup file", "*.md"));
		fileChooser.setTitle("Save .md File");
		final File file = fileChooser.showSaveDialog(dataBean.getPrimaryStage());
		final FileHandler fileHandler = new FileHandler();
		if (fileHandler != null && file != null) {
			fileHandler.writeToFile(file.getAbsolutePath(), parsedOutputTextarea.getText());
		}

	}

	/** Show the view */
	public final void show() {
		view.show(dataBean.getPrimaryStage());
	}

	/**
	 * gets content of a file
	 * 
	 * @param fullPathFileName
	 *            full filename with path
	 * @return returns content of a file
	 */
	private String getFile(final String fullPathFileName) {
		final StringBuilder result = new StringBuilder("");
		final File file = new File(fullPathFileName);

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				final String line = scanner.nextLine();
				result.append(line).append("\n");
			}
			scanner.close();
		} catch (final IOException e) {
			final Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Look, an Error Dialog");
			alert.setContentText("Ooops, there was an error!\n" + e);
			alert.showAndWait();
		}
		return result.toString();
	}

}