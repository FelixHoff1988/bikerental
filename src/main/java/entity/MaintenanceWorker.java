package entity;

public class MaintenanceWorker extends User {
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param email
	 * @param iban
	 * @param bic
	 * @param passwordHash
	 */
	public MaintenanceWorker(String firstName, String lastName, String address, String email, String iban, String bic,
			String passwordHash) {
		super(firstName, lastName, address, email, iban, bic, passwordHash);
	}

}