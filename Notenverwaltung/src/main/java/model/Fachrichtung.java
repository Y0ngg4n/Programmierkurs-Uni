package model;

import java.io.Serializable;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *  Liste aller Fachrichtungen
 */
public class Fachrichtung implements Serializable, Iterable<String>{
	private static final long serialVersionUID = 1L;
	private static Fachrichtung unique = null;
	private ObservableList<String> fachrichtungen = 
		    FXCollections.observableArrayList(
		        "Informatik",
		        "Mathematik"
		    );
	
	private Fachrichtung(){}
	
	public static Fachrichtung instance(){
		if(unique == null)
			unique = new Fachrichtung();
		return unique;
	}
	
	public ObservableList<String> getFachrichtungen() {
		return fachrichtungen;
	}

	/**
	 * Hinzufügen einer neuen Modulgruppe
	 * @param e: neue Modulgruppe, Invariante: mindestens 2 Zeichen, mit Buchstaben beginnend
	 * @throws CreateNewException wird geworfen falls Invariante verletzt oder Modulgruppe bereits vorhanden
	 */
	public void add(String e) throws CreateNewException {
		if(e.length() < 2)
			throw new CreateNewException("Neue Modulgruppe muss aus mind. 2 Zeichen bestehen");
		if(!e.matches("^[a-zA-Z].*$"))
			throw new CreateNewException("Neue Modulgruppe muss mit Buchstaben anfangen");
		if(fachrichtungen.contains(e))
			throw new CreateNewException("Modulgruppe bereits vorhanden");
		fachrichtungen.add(e);
	}

	@Override
	public Iterator<String> iterator() {
		return fachrichtungen.iterator();
	}
}
