package controller;

import model.Note;
import model.Nutzer;
import model.NutzerContainer;
/**
 * 
 * Counter bietet Funktionen um Berechnungen auf den gesammelten Noten durchzuführen
 */
public class Counter {
	private Nutzer nutzer;
	private int[] counter;
	private static int unterschiedlicheNoten = 41;

	/**
	 * Ein neuer Counter arbeitet auf dem aktiven Nutzer 
	 */
	public Counter(){
		nutzer = NutzerContainer.instance().getAktivNutzer();
		counter = new int[unterschiedlicheNoten];
		for(int i = 0; i < unterschiedlicheNoten; i++)
			counter[i] = 0;
	}
	
	/**
	 * Zählt die Anzahl der gesammelten Credits für jede Note
	 * @return Gibt die Anzahl der Credits zurück
	 */
	public int[] count(){
		for(Note note : nutzer.getNoten()){
			int ergebnis = (int) (note.getErgebnis() * 10 - 10);
			if(ergebnis == -10)
				continue;
			counter[ergebnis] += note.getCredits();
		}
		return counter;
	}
	
	/**
	 * Gibt die gesamten Credits für jeden Notenschritt aus
	 */
	public void printCounter(){
		for(int i = 0; i < unterschiedlicheNoten; i++){
			System.out.println(((float)(i+10))/10 + " : " + counter[i]);
		}
	}
	
	/**
	 * 
	 * @return Gibt die totale Anzahl von Credits zurück von Fächern, die benotet sind
	 */
	private int getAnzahlCredits(){
		int credits = 0;
		for(int i = 0; i < unterschiedlicheNoten; i++){
			credits += counter[i];
		}
		return credits;
	}
	
	/**
	 * 
	 * @return Gibt die totale Anzahl von Credits zurück
	 */
	public int getGesamtAnzahlCredits(){
		int credits = 0;
		for(Note note : nutzer.getNoten()){
			credits += note.getCredits();
		}
		return credits;
	}
	
	/**
	 * Berechnet die Durchschnittsnote von allen benoteten Fächern
	 * @return Gibt Durchschnittsnote zurück
	 */
	public double getDurchschnittsNote(){
		count();
		double ergebnis = 0.0;
		for(int i = 0; i < unterschiedlicheNoten; i++){
			ergebnis += counter[i] * ((float)(i+10))/10;
		}
		return ergebnis/getAnzahlCredits();
	}
}
