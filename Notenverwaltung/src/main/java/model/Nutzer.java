package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 */
public class Nutzer implements Serializable, Iterable<Note>{
	private static final long serialVersionUID = 1L;
	private String name;
	private String passwort;
	private ArrayList<Note> notenListe = new ArrayList<Note>();
	private boolean aktiv;


	/**
	 * Neuen Nutzer erstellen mit Name und Passwort
	 * @param name: Benutzername
	 * @param passwort: Passwort des jeweiligen Nutzers
	 */
	public Nutzer(String name, String passwort){
		this.name = name;
		this.passwort = passwort;
	}
	
	public boolean isAktiv() {
		return aktiv;
	}

	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	
	public void addNote(Note note){
		if(!notenListe.contains(note))
			notenListe.add(note);
	}
	
	public void deleteNote(Note note){
		for(int i = 0; i < notenListe.size(); i++){
			Note n = notenListe.get(i);
			if(n.equals(note)){
				notenListe.remove(n);
				break;
			}
		}
	}
	
	public ArrayList<Note> getNoten(){
		return notenListe;
	}
	
	public void printNoten(){
		System.out.print("Bisherige Noten: ");
		for(Note n : notenListe){
			System.out.print(n.toString());
		}
		System.out.println("");
	}

	@Override
	public Iterator<Note> iterator() {
		return notenListe.iterator();
	}
	
	/**
	 * Umwandeln eines Strings in eine Note
	 * @param s: String der umzuwandeln ist
	 * @return Note die umgewandelt wurde oder null falls Umwaldung erfolglos
	 */
	public Note toNote(String s){
		String note = s.substring(0, s.indexOf(','));
		s = s.substring(s.indexOf(',') + 1);
		String modul = s.substring(1, s.indexOf(" am"));
		s = s.substring(s.indexOf("am") + 2);
		String tmp = s.substring(1, s.indexOf(" mit"));
		LocalDate datum = LocalDate.parse(tmp, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		s = s.substring(s.indexOf("mit") + 3);
		int credits = Integer.parseInt(s.substring(1, s.indexOf('C') - 1));
		s = s.substring(s.indexOf("Note") + 4);
		float ergebnis = Float.parseFloat(s.substring(1));
		try {
			return Note.createNote(note, modul, credits, datum, ergebnis);
		} catch (CreateNewException e) {
			return null;
		}
	}
}
