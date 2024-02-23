package entity;

public class Manager extends User {

	/**
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param email
	 * @param iban
	 * @param bic
	 * @param passwordHash
	 */
	public Manager(String firstName, String lastName, String address, String email, String iban, String bic,
			String passwordHash) {
		super(firstName, lastName, address, email, iban, bic, passwordHash);
	}
}