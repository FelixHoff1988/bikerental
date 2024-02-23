package entity;

/**
 * Repräsentiert einen Kunden
 */
public class Customer extends User {

	/**
	 * @param firstName Vorname des Nutzers
	 * @param lastName Nachname des Nutzers
	 * @param address Addresse des Nutzers
	 * @param email E-Mail Addresse des Nutzers
	 * @param iban IBAN des Nutzers
	 * @param bic BIC des Nutzers
	 * @param passwordHash PasswordHash um das Passwort zu verifizieren
	 */
	public Customer(String firstName, String lastName, String address, String email, String iban, String bic,
			String passwordHash) {
		super(firstName, lastName, address, email, iban, bic, passwordHash);
	}
	
}