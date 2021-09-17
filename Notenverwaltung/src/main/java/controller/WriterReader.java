package controller;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.CreateNewException;
import model.Fachrichtung;
import model.Nutzer;
import model.NutzerContainer;

/**
 * 
 * Klasse zum dauerhaften Speichern der Nutzer, Noten und Modulgruppen
 */
public class WriterReader {
	private FileOutputStream out;
	private ObjectOutputStream oos;
	private FileInputStream in;
	private ObjectInputStream ois;
	private File file, fileModule;
	private NutzerContainer nutzerListe;
	private Fachrichtung module;

	/**
	 * Erzeugt einen neuen WriterReader zum schreiben und lesen in / aus einer Datei
	 */
	public WriterReader() {
		file = new File("noten.txt");
		fileModule = new File("modulguppen.txt");
		nutzerListe = NutzerContainer.instance();
		module = Fachrichtung.instance();
	}
	
	/**
	 * Schließt die geöffneten OutputStreams
	 */
	private void closeOutputStreams() {
		try {
			if (oos != null)
				oos.close();
			if (out != null)
				out.close();
		} catch (IOException e) {
			System.err.println("Stream konnte nicht geschlossen werden.");
		}
	}

	/**
	 * Schließt die geöffneten InputStreams
	 */
	private void closeInputStreams() {
		try {
			if (ois != null)
				ois.close();
			if (in != null)
				in.close();
		} catch (IOException e) {
			System.err.println("Stream konnte nicht geschlossen werden.");
		}
	}

	/**
	 * Schreibt sämtliche Nutzer und ihre Noten in die Datei noten.txt
	 */
	public void writeNutzer() {
		try {
			nutzerListe = NutzerContainer.instance();
			if (!file.exists())
				file.createNewFile();
			out = new FileOutputStream(file);
			oos = new ObjectOutputStream(out);
			for (Nutzer n : nutzerListe)
				oos.writeObject(n);
		} catch (FileNotFoundException e) {
			System.err.println("Fehler bei Writer erstellen.");
		} catch (IOException e) {
			System.err.println("Fehler beim schreiben der Datei.");
		} finally {
			closeOutputStreams();
		}
	}

	/**
	 * Liest alle Nutzer aus der Datei noten.txt mit ihren jeweiligen Noten
	 * @return true falls lesen erfolgreich, sonst false
	 */
	public boolean readNutzer() {
		try {
			if (!file.exists())
				file.createNewFile();
			in = new FileInputStream(file);
			ois = new ObjectInputStream(in);
			Nutzer n;
			while (in.available() > 0 && (n = (Nutzer) ois.readObject()) != null) {
				nutzerListe.addNutzer(n);
			}
			closeInputStreams();
			return true;
		} catch (EOFException e) {
			System.out.println("Text-Dateien werden erzeugt.");
			closeInputStreams();
			return true;
		} catch (IOException e) {
			System.err.println(
					"Fehler in der Text-Datei 'noten.txt'. \nDatei löschen, falls Fehler weiterhin auftritt - Achtung: Dabei gehen alle Daten verloren.\n");
			closeInputStreams();
			return false;
		} catch (ClassNotFoundException e) {
			System.err.println("Class Not Found - WirterReader(), readNutzer()");
			closeInputStreams();
			return false;
		}
	}

	/**
	 * Schreibt alle Modulgruppen in die Datei modulgruppen.txt
	 */
	public void writeModule() {
		try {
			module = Fachrichtung.instance();
			if (!fileModule.exists())
				fileModule.createNewFile();
			out = new FileOutputStream(fileModule);
			oos = new ObjectOutputStream(out);
			for (String s : module)
				oos.writeObject(s);
		} catch (FileNotFoundException e) {
			System.err.println("Fehler bei Writer erstellen.");
		} catch (IOException e) {
			System.err.println("Fehler beim schreiben der Datei.");
		} finally {
			closeOutputStreams();
		}
	}

	/**
	 * Liest alle Modulgruppen aus der Datei modulgruppen.txt
	 * @return true falls lesen erfolgreich, sonst false
	 */
	public boolean readModule() {
		try {
			if (!fileModule.exists())
				fileModule.createNewFile();
			in = new FileInputStream(fileModule);
			ois = new ObjectInputStream(in);
			String s;
			while (in.available() > 0 && (s = (String) ois.readObject()) != null) {
				try {
					module.add(s);
				} catch (CreateNewException e) {
				}
			}
			closeInputStreams();
			return true;
		} catch (EOFException e) {
			closeInputStreams();
			return true;
		} catch (IOException e) {
			System.err.println(
					"Fehler in der Text-Datei 'modulgruppen.txt'. \nDatei löschen, falls Fehler weiterhin auftritt - Achtung: Dabei gehen alle Daten verloren.\n");
			closeInputStreams();
			return false;
		} catch (ClassNotFoundException e) {
			System.err.println("Class Not Found - WirterReader(), readModule()");
			closeInputStreams();
			return false;
		}
	}
}
