package de.koetter.robin.credits;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Zeigt Daten über den Autor dieses Programms.
 * 
 * @author Robin
 */
public class CreditsView extends GridPane {

	/** 10 */
	private static final int ABSTAND = 10;

	/** 25 */
	private static final int INSETS = 25;

	/** 20 */
	private static final int FONT_SIZE = 20;

	/** Erstellt eine View mit Informationen über den Autor. */
	public CreditsView() {
		this.setAlignment(Pos.CENTER);
		this.setHgap(ABSTAND);
		this.setVgap(ABSTAND);
		this.setPadding(new Insets(INSETS, INSETS, INSETS, INSETS));

		final Text scenetitle = new Text("MaDParser");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, FONT_SIZE));
		this.add(scenetitle, 0, 0, 2, 1);

		final Label userName = new Label("Robin Kötter:");
		this.add(userName, 0, 1);

		final Label labelMail = new Label("Mail: robinkotter@yahoo.de");
		this.add(labelMail, 0, 2);

	}

}