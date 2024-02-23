package entity;

public abstract class User {

	protected String firstName;
	protected String lastName;
	protected String address;
	protected String email;
	protected String iban;
	protected String bic;
	protected String passwordHash;

	/**
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param email
	 * @param iban
	 * @param bic
	 * @param passwordHash
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
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return iban
	 */
	public String getIban() {
		return iban;
	}

	/**
	 * @param iban
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}

	/**
	 * @return bic
	 */
	public String getBic() {
		return bic;
	}

	/**
	 * @param bic
	 */
	public void setBic(String bic) {
		this.bic = bic;
	}

	/**
	 * @return passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}