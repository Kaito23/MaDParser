package de.koetter.robin.model;

import de.koetter.robin.mapper.ElementsHandler;
import javafx.stage.Stage;
import lombok.Getter;

/**
 * DataBean - Handler for important data
 * 
 * @author Robin
 */
public class DataBean {
	/** The primary stage */
	@Getter
	private Stage primaryStage = null;
	/** xml elements handler */
	@Getter
	private final ElementsHandler elementsHandler;

	/**
	 * Data-Handler
	 * 
	 * @param primaryStage
	 *            the primary staeg
	 * @param elementsHandler
	 *            the handler of xml markdown regex elements
	 */
	public DataBean(final Stage primaryStage, final ElementsHandler elementsHandler) {
		this.primaryStage = primaryStage;
		this.elementsHandler = elementsHandler;
	}

}