package gui;

import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDate;

import controller.Counter;
import controller.WriterReader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.CreateNewException;
import model.Fachrichtung;
import model.Note;
import model.Nutzer;
import model.NutzerContainer;

public class Notenverwaltung extends Application {
    private BarChart<String, Number> bc;
    private Stage stage;
    private BorderPane border;
    private GridPane gridLeft, gridRight, gridBottom;
    private int width = 1300;
    private int height = 940;
    private static NutzerContainer nutzerListe;
    private Label lblWillkommen, lblMessage;
    private Spinner<Integer> spnCredits;
    private Slider sldNote;
    private TextField txtFldFach;
    private ComboBox<String> comboBoxGruppe;
    private CheckBox cbBenotet;
    private DatePicker dpDatum;
    private static WriterReader inOut;
    private ListView<String> listView;
    private ObservableList<String> notenSchritt = FXCollections.
            observableArrayList("1.0", "1.3", "1.7", "2.0", "2.3",
                    "2.7", "3.0", "3.3", "3.7", "4.0", "4.3", "4.7", "5.0");
    private ObservableList<String> noten = FXCollections.observableArrayList();

    public Stage getStage() {
        return stage;
    }

    protected void updateLabelNutzer() {
        nutzerListe = NutzerContainer.instance();
        lblWillkommen.setText("Willkommen " + nutzerListe.getAktivNutzer().
                getName());
    }

    private void setUpNeueNote() {
        gridLeft.add(new Separator(), 0, 3, 10, 1);

        Label lblNeueNote = new Label("Neue Note: ");
        lblNeueNote.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
        gridLeft.add(lblNeueNote, 0, 2);

        /* Fach und Modulgruppe */
        HBox hb1 = new HBox();
        Label lblFach = new Label("Fach ");
        txtFldFach = new TextField();
        Label lblRichtung = new Label("\tModulgruppe ");
        comboBoxGruppe = new ComboBox<String>(Fachrichtung.instance().
                getFachrichtungen());
        hb1.getChildren().addAll(lblFach, txtFldFach, lblRichtung,
                comboBoxGruppe);
        gridLeft.add(hb1, 0, 4, 6, 1);

        /* Benotet-Checkbox und Noten-Slider */
        HBox hb2 = new HBox();
        Label lblBenotet = new Label("\tBenotet ");
        cbBenotet = new CheckBox();
        Label lblNote = new Label("\tNote ");
        sldNote = new Slider(1.0, 5.0, 0.1);
        sldNote.setDisable(true);
        sldNote.setShowTickLabels(true);
        sldNote.setShowTickMarks(true);
        sldNote.setSnapToTicks(true);
        sldNote.setMajorTickUnit(1.0);

        /* Ausgewählte Note + Slider-Ereignisbehandlung */
        Label lblBestanden = new Label(" 1.0");
        lblBestanden.setDisable(true);
        sldNote.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                new_val = Note.jumpValue(new_val.doubleValue());
                sldNote.setValue(new_val.doubleValue());
                DecimalFormat f = new DecimalFormat("0.0");
                if (new_val.doubleValue() > 4.05)
                    lblBestanden.setTextFill(Color.CRIMSON);
                else
                    lblBestanden.setTextFill(Color.DARKGREEN);
                lblBestanden.setText(" " + f.format(new_val));
            }
        });

        /* Checkbox-Ereignisbehandlung */
        cbBenotet.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                                Boolean old_val, Boolean new_val) {
                sldNote.setDisable(!new_val);
                lblBestanden.setDisable(!new_val);
            }
        });

        /* Credits */
        Label lblCredits = new Label("Credits ");
        spnCredits = new Spinner<Integer>();
        spnCredits.setMaxWidth(100);
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1);
        spnCredits.setValueFactory(valueFactory);
        hb2.getChildren().addAll(lblCredits, spnCredits, lblBenotet, cbBenotet,
                lblNote, sldNote, lblBestanden);
        gridLeft.add(hb2, 0, 5, 6, 1);

        /* Datum */
        HBox hb3 = new HBox();
        Label lblDatum = new Label("Datum ");
        dpDatum = new DatePicker();
        Button btnAdd = new Button("Hinzufügen");
        hb3.getChildren().addAll(lblDatum, dpDatum);
        gridLeft.add(hb3, 0, 6, 6, 1);
        gridLeft.add(btnAdd, 9, 6);


        btnAdd.setOnMouseClicked(mouseEvent -> {
            try {
                Note note = Note.createNote(this.txtFldFach.getText(), this.comboBoxGruppe.getValue(),
                        this.spnCredits.getValue(), this.dpDatum.getValue(), (float) this.sldNote.getValue());
                this.listView.getItems().add(note.toString());
                this.noten.add(note.toString());
                this.txtFldFach.clear();
                this.comboBoxGruppe.getSelectionModel().clearSelection();
                this.spnCredits.getEditor().clear();
                this.dpDatum.getEditor().clear();
                this.sldNote.setValue(this.sldNote.getMin());
                this.cbBenotet.setSelected(false);
                this.updateChart();
            } catch (CreateNewException e) {
                this.lblMessage.setText(e.getMessage());
            }
        });
    }

    private void setUpNeueModulGruppe() {
        gridLeft.add(new Separator(), 0, 9, 10, 1);

        Label lblNeueGruppe = new Label("Neue Modulgruppe: ");
        lblNeueGruppe.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
        gridLeft.add(lblNeueGruppe, 0, 8);

        HBox hb = new HBox();
        Label lblModulBezeichnung = new Label("Bezeichnung ");
        TextField txtfldModul = new TextField();
        hb.getChildren().addAll(lblModulBezeichnung, txtfldModul);
        gridLeft.add(hb, 0, 10, 6, 1);

        Button btnAddModul = new Button("Hinzufügen");
        gridLeft.add(btnAddModul, 9, 10);

        btnAddModul.setOnAction(e -> {
            try {
                Fachrichtung.instance().add(txtfldModul.getText());
                txtfldModul.setText("");
                lblMessage.setText("");
            } catch (CreateNewException exception) {
                System.out.println("Fehler beim Erstellen einer neuen Modulgruppe: "
                        + exception.getMessage());
                lblMessage.setTextFill(Color.CRIMSON);
                lblMessage.setText(exception.getMessage());
            }
        });
    }

    protected void setUpListView() {
        listView = new ListView<>();
        listView.setPrefWidth(500);
        listView.setPrefHeight(305);
        gridRight.add(listView, 0, 4, 10, 10);
        emptyListView();
        updateListView();

        Button btnDelete = new Button("Löschen");
        Button btnModify = new Button("Bearbeiten");
        gridRight.add(btnModify, 3, 14, 2, 1);
        gridRight.add(btnDelete, 0, 14, 2, 1);

        btnDelete.setOnMouseClicked(mouseEvent -> {
            this.deleteNote(listView.getSelectionModel().getSelectedItem());
        });

        /* TODO Ereignisbehandlung btnModify */
        btnModify.setOnMouseClicked(mouseEvent -> {
            Note note = nutzerListe.getAktivNutzer().toNote(listView.getSelectionModel().getSelectedItem());
            this.txtFldFach.setText(note.getName());
            this.comboBoxGruppe.getSelectionModel().select(note.getFachrichtung());
            this.spnCredits.getEditor().setText(String.valueOf(note.getCredits()));
            this.dpDatum.getEditor().setText(note.getDatum().toString());
            this.sldNote.setValue(note.getErgebnis());
            this.deleteNote(listView.getSelectionModel().getSelectedItem());
        });

    }

    private Note deleteNote(String s) {
        if (s == null)
            return null;
        Note note = nutzerListe.getAktivNutzer().toNote(s);
        nutzerListe.getAktivNutzer().deleteNote(note);
        emptyListView();
        updateListView();
        updateChart();
        return note;
    }

    /**
     * ListView updaten
     */
    protected void updateListView() {
        Nutzer n = nutzerListe.getAktivNutzer();
        if (n != null) {
            for (Note note : n)
                noten.add(note.toString());
        }
        listView.setItems(noten);
    }

    /**
     * ListView leeren
     */
    protected void emptyListView() {
        noten.clear();
        listView.setItems(noten);
    }

    /**
     * Chart implementieren, der die Credits-Verteilung anzeigt
     * für die jeweiligen Note
     */
    protected void setUpChart() {
        // TODO BarChart initialisieren
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(true);
        xAxis.setCategories(notenSchritt);
        bc = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Credits");
        bc.setAnimated(false);
        /* Berechnung des Durchschnitts */
        Counter c = new Counter();
        double durchschnitt = Math.round(c.getDurchschnittsNote() * 100);
        durchschnitt /= 100;
        int credits = c.getGesamtAnzahlCredits();

        // TODO XYChart anlegen
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.setName("Durchschnitt: " + durchschnitt + " Credits: " + credits);

        int[] count = new Counter().count();
        for (int i = 0; i < count.length; i++) {
            String s = String.valueOf(((float) (i + 10)) / 10);
            // TODO XYChart füllen
            series.getData().add(new XYChart.Data<>(s, count[i]));
        }
        // TODO BarChart und grid füllen
//        bc.getData().clear();
        bc.getData().add(series);
        gridBottom.add(bc, 0, 0);
    }

    /**
     * updateChart() aufrufen sobald neue Note oder neue Fachrichtung
     * hinzugefügt wurde.
     */
    protected void updateChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        Counter c = new Counter();
        double durchschnitt = Math.round(c.getDurchschnittsNote() * 100);
        durchschnitt /= 100;
        int credits = c.getGesamtAnzahlCredits();
        series.setName("Durchschnitt: " + durchschnitt + " Credits: " + credits);

        int[] count = new Counter().count();
        for (int i = 0; i < count.length; i++) {
            String s = String.valueOf(((float) (i + 10)) / 10);
            series.getData().add(new XYChart.Data<>(s, count[i]));
        }
        bc.getData().clear();
        bc.getData().add(series);
    }

    /**
     * Fenster erzeugen
     */
    @Override
    public void start(Stage s) {
        System.out.println("Notenverwaltung starten...");
        stage = s;
        stage.setTitle("Notenverwaltung");
        stage.getIcons().add(new Image("./Una.png"));

        // Hauptlayout bereits gegeben
        border = new BorderPane();

        gridLeft = new GridPane();
        gridLeft.setAlignment(Pos.TOP_LEFT);
        gridLeft.setHgap(5.0);
        gridLeft.setVgap(20.0);
        gridLeft.setPadding(new Insets(25, 25, 25, 25));

        lblWillkommen = new Label("Willkommen\t\t");
        lblWillkommen.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridLeft.add(lblWillkommen, 0, 0);

        gridRight = new GridPane();
        gridRight.setAlignment(Pos.TOP_LEFT);
        gridRight.setHgap(5.0);
        gridRight.setVgap(20.0);
        gridRight.setPadding(new Insets(25, 25, 25, 100));

        gridBottom = new GridPane();
        lblMessage = new Label();
        gridBottom.setAlignment(Pos.BOTTOM_CENTER);
        gridBottom.setPadding(new Insets(15, 10, 10, 10));
        border.setBottom(gridBottom);

        gridLeft.add(lblMessage, 0, 1);

        border.setLeft(gridLeft);
        border.setRight(gridRight);
        setUpNeueNote();
        setUpListView();
        setUpNeueModulGruppe();
        Scene scene = new Scene(border, width, height);

        /* TODO Aufgabe 7 - Stylesheet laden */

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        LoginDialog loginDialog = new LoginDialog(this);

        stage.setOnCloseRequest(windowEvent -> {
            System.out.println("Speichere ab");
            inOut.writeNutzer();
            inOut.writeModule();
        });
    }

    /**
     * Application starten und Nutzerdaten aus Datei lesen, falls vorhanden
     */
    public static void main(String[] args) {
        inOut = new WriterReader();
        if (inOut.readNutzer() && inOut.readModule()) {
            nutzerListe = NutzerContainer.instance();
            Application.launch(args);
        } else {
            Platform.exit();
            System.out.println("Notenverwaltung konnte nicht gestartet werden. Programm beenden...");
        }
    }

    public static NutzerContainer getNutzerListe() {
        return nutzerListe;
    }
}
