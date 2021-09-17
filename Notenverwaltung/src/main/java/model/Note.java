package model;

import java.io.Serializable;
import java.time.LocalDate;
/**
 *  
 */
public class Note implements Serializable {
	/**
	 * Eine Note besteht aus
	 *  Namen : String
	 *  Fachrichtung : String
	 *  Credits : int
	 *  Datum : LocalDate
	 *  Ergebnis : float [kann 0.0 sein wenn unbenotet]
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private float ergebnis;
	private String fachrichtung;
	private int credits;
	private LocalDate datum;
	
	/**
	 * Erstellen einer unbenoteten Note
	 * @param name: Bezeichnung der Note
	 * @param gruppe: Zugehörige Modulgruppe
	 * @param credits: Anzahl der Credits 
	 * @param datum: Datum der Note
	 */
	private Note(String name, String gruppe, int credits, LocalDate datum){
		this(name, gruppe, credits, datum, (float)0.0);
	}
	
	/**
	 * Erstellen einer Note mit Benotung
	 * @param name: Bezeichnung der Note
	 * @param gruppe: Zugehörige Modulgruppe
	 * @param credits: Anzahl der Credits
	 * @param datum: Datum der Note
	 * @param ergebnis: Ergebnis der Note
	 */
	private Note(String name, String gruppe, int credits, LocalDate datum, float ergebnis){
		setName(name);
		setFachrichtung(gruppe);
		setCredits(credits);
		setDatum(datum);
		setErgebnis(ergebnis);
	}
	
	/**
	 * Finden der am nächsten liegenden Note, für einen double value
	 * @param val: übergebene Note
	 * @return val gerundet auf die nächste Note
	 */
	public static double jumpValue(double val){
		int tmp = (int)(val * 100);
		if(tmp % 100 >= 85)
			tmp += 100;
		val = tmp / 100;
		if(tmp % 100 >= 15 && tmp % 100 < 50)
			val += 0.3;
		if(tmp % 100 >= 50 && tmp % 100 < 85)
			val += 0.7;
		return val;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public float getErgebnis() {
		return ergebnis;
	}
	/**
	 * Setzen der Noten
	 * @param ergebnis: Invariante >= 0 && <= 5
	 * @return Gibt false zurück falls Invariante verletzt, sonst true
	 */
	public boolean setErgebnis(float ergebnis) {
		if(ergebnis < 0 || ergebnis > 5.0)
			return false;
		this.ergebnis = ergebnis;
		return true;
	}
	public String getFachrichtung() {
		return fachrichtung;
	}
	public void setFachrichtung(String fachrichtung) {
		this.fachrichtung = fachrichtung;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	public LocalDate getDatum() {
		return datum;
	}
	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}
	
	/**
	 * Note mit anderer Note vergleichen
	 * @param n: Note zum Vergleichen
	 * @return Noten sind gleich falls Name, Modulgruppe, Ergebnis, Credits und Datum gleich sind
	 */
	public boolean equals(Note n){
		if(n.getName().equals(getName()) && n.getFachrichtung().equals(getFachrichtung()) && n.getErgebnis() == getErgebnis() && n.getCredits() == getCredits() && n.getDatum().equals(getDatum()))
			return true;
		return false;
	}
	
	public void printNote(){
		System.out.println("Note: " + getName() + ", " + getFachrichtung() + " am " + getDatum() + " mit " + getCredits() + " Credits und Note " + getErgebnis());
	}
	
	@Override
	public String toString(){
		return getName() + ", " + getFachrichtung() + " am " + getDatum() + " mit " + getCredits() + " Credits und Note " + getErgebnis();
	}
	
	/**
	 * Überprüfung der Eingabe
	 * @param fach Invariante: != null, length > 2, muss mit Buchstaben beginnen
	 * @param modul Invariante: != null
	 * @param credits
	 * @param datum Invariante: != null
	 * @param ergebnis
	 * @return Neue Note falls Eingabe korrekt
	 * @throws CreateNewException falls Invarianten verletzt wurden
	 */
	public static Note createNote(String fach, String modul, int credits, LocalDate datum, float ergebnis) throws CreateNewException {
		if (fach == null || modul == null || datum == null) {
			throw new CreateNewException("Nicht alle Felder ausgefüllt, um neue Note zu erstellen.");
		} else {
			if (fach.length() < 2) {
				throw new CreateNewException("Notenname muss aus mindestens zwei Zeichen bestehen");
			}
			if (!fach.matches("^[a-zA-Z].*$")) {
				throw new CreateNewException("Notename muss mit einem Buchstaben beginnen.");
			}
		}
		return new Note(fach, modul, credits, datum, ergebnis);
	}
}
