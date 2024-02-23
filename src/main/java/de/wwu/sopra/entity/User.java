package de.wwu.sopra.entity;

import java.util.ArrayList;

/**
 * Repräsentiert einen Benutzer
 */
public class User {
	/**
	 * Vorname des Nutzers
	 */
	private String firstName;
	/**
	 * Nachname des Nutzers
	 */
	private String lastName;
	/**
	 * Addresse des Nutzers
	 */
	private String address;
	/**
	 * E-Mail Addresse des Nutzers
	 */
	private String email;
	/**
	 * IBAN des Nutzers
	 */
	private String iban;
	/**
	 * BIC des Nutzers
	 */
	private String bic;
	/**
	 * PasswordHash um das Passwort zu verifizieren
	 */
	private String passwordHash;

	/**
	 * Rolle des Nutzers (z.B. Admin)
	 */
	private UserRole role;
	/**
	 * Liste der Reservierungen
	 */
	private ArrayList<Reservation> reservationList;

	/**
	 * Konstruktor: Setzt die Attribute auf die eingegebenen Werte
	 * 
	 * @param firstName    Vorname des Nutzers
	 * @param lastName     Nachname des Nutzers
	 * @param address      Adresse des Nutzers
	 * @param email        E-Mail-Adresse des Nutzers
	 * @param iban         IBAN des Nutzers
	 * @param bic          BIC des Nutzers
	 * @param passwordHash PasswordHash um das Passwort zu verifizieren
	 * @param role         Rolle des Nutzers
	 */
	public User(String firstName, String lastName, String address, String email, String iban, String bic,
			String passwordHash, UserRole role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.iban = iban;
		this.bic = bic;
		this.passwordHash = passwordHash;
		this.role = role;

		ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
		this.reservationList = reservationList;
	}

	/**
	 * Rufe den Vornamen eines Nutzers ab
	 * 
	 * @return Vorname des Nutzers
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setze den Vornamen eines Nutzers
	 * 
	 * @param firstName Neuer Vorname des Nutzers
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Rufe den Nachnamen eines Nutzers ab
	 * 
	 * @return Nachname des Nutzers
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setze den Nachnamen eines Nutzers
	 * 
	 * @param lastName Neuer Nachname des Nutzers
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Rufe die Adresse eines Nutzers ab
	 * 
	 * @return Adresse des Nutzers
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Setze die Adresse eines Nutzers
	 * 
	 * @param address Neue Adresse des Nutzers
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Rufe die E-Mail-Adresse eines Nutzers ab
	 * 
	 * @return Email des Nutzers
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setze die E-Mail-Adresse eines Nutzers
	 * 
	 * @param email Neue Email des Nutzers
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Rufe die IBAN eines Nutzers ab
	 * 
	 * @return IBAN des Nutzers
	 */
	public String getIban() {
		return iban;
	}

	/**
	 * Setze die IBAN eines Nutzers
	 * 
	 * @param iban Neue Iban des Nutzers
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}

	/**
	 * Rufe die BIC eines Nutzers ab
	 * 
	 * @return BIC des Nutzers
	 */
	public String getBic() {
		return bic;
	}

	/**
	 * Setzte die BIC eines Nutzers
	 * 
	 * @param bic BIC des Nutzers
	 */
	public void setBic(String bic) {
		this.bic = bic;
	}

	/**
	 * Rufe den PasswordHash eines Nutzers ab
	 * 
	 * @return passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setzte den PasswordHash eines Nutzers
	 * 
	 * @param passwordHash Hash des Passworts
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Setze die Rolle des Nutzers
	 * 
	 * @param role Neue Rolle des Nutzers
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}

	/**
	 * Rufe die Rolle des Nutzers ab
	 * 
	 * @return Rolle des Nutzers
	 */
	public UserRole getRole() {
		return this.role;
	}

	/**
	 * Rufe die Reservierungsliste ab
	 * 
	 * @return aktuelle Reservierungsliste
	 */
	public ArrayList<Reservation> getReservationList() {
		return reservationList;
	}

	/**
	 * Füge eine Reservierung in die Liste hinzu
	 * 
	 * @param reservation
	 */
	public void reservationListAdd(Reservation reservation) {
		this.reservationList.add(reservation);
	}
}