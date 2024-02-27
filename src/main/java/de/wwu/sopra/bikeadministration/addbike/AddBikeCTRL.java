package de.wwu.sopra.bikeadministration.addbike;

import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.BikeType;
import de.wwu.sopra.entity.CargoBike;
import de.wwu.sopra.entity.EBike;
import de.wwu.sopra.entity.StandardType;

public class AddBikeCTRL {
	
	public void addBike(String frameId, String bikeType, String model, int size, 
			Availability availability, int charge, int capacity) {
		
		int price = 0;
		
		BikeType type;
		switch(bikeType) {
		case "Standard":
			type = new StandardType(model, size, price);
			break;
			
		case "Cargo":
			type = new CargoBike(model, size, price, capacity);
			break;
			
		case "EBIKE":
			type = new EBike(model, size, price, charge);
			break;
			
		default:
			type = null;
			break;
		}
		
		
	}
	
}
