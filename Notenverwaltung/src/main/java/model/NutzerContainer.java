package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
/**
 * 
 * Speichern aller Nutzer mit ihren Noten in einer Liste
 */
public class NutzerContainer implements Iterable<Nutzer> {
	private ArrayList<Nutzer> nutzerListe = new ArrayList<Nutzer>();
	private static NutzerContainer unique = null;

	private NutzerContainer() {	}
	

	private boolean contains(Nutzer nutzer){
		for(Nutzer n : nutzerListe){
			if(n.getName().equals(nutzer.getName()) && n.getPasswort().equals(nutzer.getPasswort()))
				return true;
		}
		return false;
	}

	public static NutzerContainer instance() {
		if (unique == null)
			unique = new NutzerContainer();
		return unique;
	}

	/**
	 * Fügt einen Nutzer hinzu, falls noch nicht enthalten
	 * @param nutzer: Nutzer welcher hinzugefügt werden soll
	 */
	public void addNutzer(Nutzer nutzer) {
		if (!contains(nutzer)) 
			nutzerListe.add(nutzer);
		
		for (Nutzer n : nutzerListe) {
			n.setAktiv(false);
		}
	}
	
	/**
	 * Überprüft ob Nutzer neu ist
	 * @param nutzer
	 * @return Gibt true zurück falls nutzer neu, sonst false
	 */
	public boolean isNewNutzer(Nutzer nutzer){
		for(Nutzer n : nutzerListe) {
			if(n.getName().equals(nutzer.getName()))
				return false;
		}
		return true;
	}

	/**
	 * Überprüft Nutzungsdaten des Nutzers und fügt ihn der Liste hinzu
	 * @param nutzer
	 * @return Gibt false zurück falls Nutzerdaten falsch
	 */
	public boolean checkNutzer(Nutzer nutzer) {
		for (Nutzer n : nutzerListe) {
			if (n.getName().equals(nutzer.getName()) && !n.getPasswort().equals(nutzer.getPasswort()))
				return false;
		}
		addNutzer(nutzer);
		for(Nutzer n : nutzerListe){
			if(n.getName().equals(nutzer.getName()))
				nutzer = n;
		}
		nutzer.setAktiv(true);
		return true;
	}

	public Nutzer getAktivNutzer() {
		for (Nutzer n : nutzerListe) {
			if (n.isAktiv()){
				return n;
			}
		}
		return null;
	}
	
	public void setAktivNutzer(Nutzer nutzer){
		nutzer.setAktiv(true);
	}

	public void removeAll() {
		for (Nutzer n : nutzerListe)
			nutzerListe.remove(n);
	}

	@Override
	public Iterator<Nutzer> iterator() {
		return nutzerListe.iterator();
	}

}
