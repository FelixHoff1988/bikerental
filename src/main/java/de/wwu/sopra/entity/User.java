package de.wwu.sopra.entity;

import de.wwu.sopra.PasswordHashing;

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
	 * Strasse der Addresse des Nutzers
	 */
	private String street;
	/**
	 * Hausnummer der Addresse des Nutzers
	 */
	private int houseNumber;
	/**
	 * PLZ der Addresse des Nutzers
	 */
	private int postalCode;
	/**
	 * Stadt der Addresse des Nutzers
	 */
	private String city;
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
	private PasswordHashing.PasswordHash passwordHash;

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
	 * @param street      Adresse des Nutzers
	 * @param houseNumber      Adresse des Nutzers
	 * @param postalCode      Adresse des Nutzers
	 * @param city      Adresse des Nutzers
	 * @param email        E-Mail-Adresse des Nutzers
	 * @param iban         IBAN des Nutzers
	 * @param bic          BIC des Nutzers
	 * @param passwordHash PasswordHash um das Passwort zu verifizieren
	 * @param role         Rolle des Nutzers
	 */
	public User(String firstName, String lastName, String street, int houseNumber, int postalCode, String city, String email, String iban, String bic,
				PasswordHashing.PasswordHash passwordHash, UserRole role) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.houseNumber = houseNumber;
		this.postalCode = postalCode;
		this.city = city;
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
	 * Rufe die Strasse der Adresse eines Nutzers ab
	 * 
	 * @return street Strasse der Adresse des Nutzers
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Setze die Strasse der Adresse eines Nutzers
	 * 
	 * @param street Neue Adresse des Nutzers
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	
	/**
	 * Rufe die Hausnummer der Adresse eines Nutzers ab
	 * 
	 * @return houseNumber HausnummerAdresse des Nutzers
	 */
	public int getHouseNumber() {
		return houseNumber;
	}

	/**
	 * Setze die Hausnummer der Adresse eines Nutzers
	 * 
	 * @param houseNumber neue Hausnummer
	 */
	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}
	
	/**
	 * Rufe die PLZ der Adresse eines Nutzers ab
	 * 
	 * @return postalCode PLZ der Adresse des Nutzers
	 */
	public int getPostalCode() {
		return postalCode;
	}

	/**
	 * Setze die PLZ der Straße der Adresse eines Nutzers
	 * 
	 * @param postalCode Neue Adresse des Nutzers
	 */
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}
	
	/**
	 * Rufe die Stadt der Adresse eines Nutzers ab
	 * 
	 * @return city Stadt der Adresse des Nutzers
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Setze die Stadt der Adresse eines Nutzers
	 * 
	 * @param city Neue Stadt der Adresse des Nutzers
	 */
	public void setCity(String city) {
		this.city = city;
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
	public PasswordHashing.PasswordHash getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setzte den PasswordHash eines Nutzers
	 * 
	 * @param passwordHash Hash des Passworts
	 */
	public void setPasswordHash(PasswordHashing.PasswordHash passwordHash) {
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
	 * @param reservation Hinzuzufügende Reservierung
	 */
	public void reservationListAdd(Reservation reservation) {
		this.reservationList.add(reservation);
	}
}