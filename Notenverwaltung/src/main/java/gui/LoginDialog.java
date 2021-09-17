package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Nutzer;
import model.NutzerContainer;

/**
 * 
 * Dialog zum Anmelden eines Nutzers
 */
public class LoginDialog {
	private Stage dialog;
	private Notenverwaltung parent;
	private NutzerContainer nutzerListe;
	private TextField txtFieldName;
	private PasswordField txtFldPasswort;
	private Label lblErrorMessage;
	private boolean neuerNutzer = false;
	
	/**
	 * Nutzereingabe wird überprüft
	 * Falls neuer Nutzer -> mit "weiter" noch einmal bestätigen
	 * Falls Nutzer vorhanden und Eingabe korrekt, das Vaterfenster updaten
	 * und Dialog schließen
	 * Sonst: Passwort falsch, mit Fehlerausgabe
	 */
	private void submit(){
		Nutzer nutzer = new Nutzer(txtFieldName.getText(), txtFldPasswort.getText());
		if(nutzerListe.isNewNutzer(nutzer) && !neuerNutzer){
			lblErrorMessage.setTextFill(Color.DARKSLATEBLUE);
			lblErrorMessage.setText("Neuer Nutzer. Mit 'Weiter' bestätigen.");
			neuerNutzer = true;
		} else if (nutzerListe.checkNutzer(nutzer)) {
			parent.updateLabelNutzer();
			parent.setUpListView();
			parent.setUpChart();
			dialog.close();
		} else {
			txtFldPasswort.setText("");
			lblErrorMessage.setTextFill(Color.CRIMSON);
			lblErrorMessage.setText("Falsches Passwort");
		}
	}

	/**
	 * Aufbau des Dialog-Fensters
	 * @param parent
	 */
	public LoginDialog(Notenverwaltung parent) {
		System.out.println("Log In oder Neuen Nutzer erstellen");
		this.parent = parent;
		nutzerListe = NutzerContainer.instance();
		dialog = new Stage();

		//Aufgabe a) + b)
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		// Set modality
		dialog.initOwner(parent.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL);
		txtFieldName = new TextField();
		txtFldPasswort = new PasswordField();
		HBox usernameBox = new HBox();
		usernameBox.setAlignment(Pos.CENTER);
		txtFieldName.requestFocus();
		HBox passwordBox = new HBox();
		passwordBox.setAlignment(Pos.CENTER);
		txtFieldName.setOnKeyPressed((this::onKeyPressed));
		txtFldPasswort.setOnKeyPressed((this::onKeyPressed));
		usernameBox.getChildren().add(new Label("User:"));
		usernameBox.getChildren().add(txtFieldName);
		passwordBox.getChildren().add(new Label("Password:"));
		passwordBox.getChildren().add(txtFldPasswort);
		gridPane.add(usernameBox, 0, 0);
		gridPane.add(passwordBox, 0, 1);

		lblErrorMessage = new Label();
		Button nextButton = new Button("Weiter");
		nextButton.setOnMouseClicked(mouseEvent -> {
			submit();
		});
		BorderPane buttonsPane = new BorderPane();
		buttonsPane.setRight(nextButton);
		gridPane.add(buttonsPane, 0, 3);


		gridPane.add(lblErrorMessage, 0, 2);

		Scene scene = new Scene(gridPane, 350, 200);

		//Aufgabe c) + d)
		dialog.setOnCloseRequest(windowEvent -> {
			try {
				parent.getStage().close();
			} catch (Exception e) {
				System.exit(0);
			}
		});
		dialog.setScene(scene);
		dialog.setResizable(false);
		dialog.showAndWait();
	}

	private void onKeyPressed(KeyEvent keyEvent){
		if(keyEvent.getCode().equals(KeyCode.ENTER)){
			submit();
		}
	}
}
