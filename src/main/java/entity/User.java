package entity;

/**
 * Repräsentiert einen Benutzer
 */
public abstract class User {
	/**
	 * Vorname des Nutzers
	 */
	protected String firstName;
	/**
	 * Nachname des Nutzers
	 */
	protected String lastName;
	/**
	 * Addresse des Nutzers
	 */
	protected String address;
	/**
	 * E-Mail Addresse des Nutzers
	 */
	protected String email;
	/**
	 * IBAN des Nutzers
	 */
	protected String iban;
	/**
	 * BIC des Nutzers
	 */
	protected String bic;
	/**
	 * PasswordHash um das Passwort zu verifizieren
	 */
	protected String passwordHash;

	/**
	 * Konstruktor: Setzt die Attribute auf die Eingegebenen Werte
	 * @param firstName Vorname des Nutzers
	 * @param lastName Nachname des Nutzers
	 * @param address Addresse des Nutzers
	 * @param email E-Mail Addresse des Nutzers
	 * @param iban IBAN des Nutzers
	 * @param bic BIC des Nutzers
	 * @param passwordHash PasswordHash um das Passwort zu verifizieren
	 */
	public User(String firstName, String lastName, String address, String email, String iban, String bic,
			String passwordHash) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.iban = iban;
		this.bic = bic;
		this.passwordHash = passwordHash;
	}

	/**
	 * Rufe den Vornamen eines Nutzers ab
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setze den Vornamen eines Nutzers
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Rufe den Nachnamen eines Nutzers ab
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setze den Nachnamen eines Nutzers
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Rufe die Addresse eines Nutzers ab
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Setze die Addresse eines Nutzers
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Rufe die E-Mail Addresse eines Nutzers ab
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setze die E-Mail Addresse eines Nutzers
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Rufe die IBAN eines Nutzers ab
	 * @return iban
	 */
	public String getIban() {
		return iban;
	}

	/**
	 * Setze die IBAN eines Nutzers
	 * @param iban
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}

	/**
	 * Rufe die BIC eines Nutzers ab
	 * @return bic
	 */
	public String getBic() {
		return bic;
	}

	/**
	 * Setzte die BIC eines Nutzers
	 * @param bic
	 */
	public void setBic(String bic) {
		this.bic = bic;
	}

	/**
	 * Rufe den PasswordHash eines Nutzers ab
	 * @return passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setzte den PasswordHash eines Nutzers
	 * @param passwordHash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}