package de.koetter.robin.eingabe;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lombok.Getter;

/**
 * Main view
 * 
 * @author Robin Kötter
 */
public class MainView {

	/** Default window heihgt */
	private static final int WINDOW_HEIGHT = 600;
	/** Default window width */
	private static final int WINDOW_WIDTH = 800;

	/** The scene */
	private final Scene scene;

	/** input textarea for the markdown */
	@Getter
	private final TextArea markdownInputTextarea;

	/** Output textarea for the parsed html */
	@Getter
	private final TextArea markedOutputTextarea;

	/** Web-Engine for displaying the parsed input */
	@Getter
	private final WebEngine webEngine;

	/** Menu item for creating a new file */
	private final MenuItem newFile = new MenuItem("New File");
	/** Menuitem for opening a .md file */
	@Getter
	private final MenuItem openFile = new MenuItem("Open File");
	/** Menuitem for saving a .md file */
	@Getter
	private final MenuItem saveFile = new MenuItem("Save File");

	/** Save markdown page as */
	@Getter
	private final MenuItem saveFileAs = new MenuItem("Save as");

	/** Menu point for exporting a complete webpage */
	@Getter
	private final MenuItem menuExportWebpage = new MenuItem("Export Webpage");

	/** Opens help for markdown */
	@Getter
	private final MenuItem menuItemHelp = new MenuItem("Markdown Help");
	/** Opens the credits view */
	@Getter
	private final MenuItem menuCredits = new MenuItem("Credits");

	/** View for input */
	public MainView() {
		final TabPane tabPane = new TabPane();

		final SplitPane grid = new SplitPane();
		markdownInputTextarea = new TextArea();
		grid.getItems().add(markdownInputTextarea);

		markedOutputTextarea = new TextArea();
		markedOutputTextarea.setEditable(false);

		final WebView browser = new WebView();
		webEngine = browser.getEngine();
		grid.getItems().add(browser);

		final Tab tabInput = new Tab("Input");
		tabInput.setContent(grid);

		final Tab tabOutput = new Tab("Output");
		tabOutput.setContent(markedOutputTextarea);

		tabPane.getTabs().addAll(tabInput, tabOutput);
		for (final Tab tab : tabPane.getTabs()) {
			tab.setClosable(false);
		}

		final MenuBar menuBar = new MenuBar();
		final Menu menuFile = new Menu("File");

		menuFile.getItems().addAll(newFile, openFile, saveFile, saveFileAs, menuExportWebpage);

		final Menu menuHelp = new Menu("Help");
		menuHelp.getItems().addAll(menuItemHelp, menuCredits);

		menuBar.getMenus().addAll(menuFile, menuHelp);

		final VBox container = new VBox();
		container.getChildren().addAll(menuBar, tabPane);
		scene = new Scene(container, WINDOW_WIDTH, WINDOW_HEIGHT);

	}

	/**
	 * Show the input view
	 * 
	 * @param stage
	 *            the primary stage
	 */
	public final void show(final Stage stage) {
		stage.setTitle("Markdown Parser");
		stage.setScene(scene);
		stage.show();
	}

}