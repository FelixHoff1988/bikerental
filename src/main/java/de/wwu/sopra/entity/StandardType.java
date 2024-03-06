package de.wwu.sopra.entity;

/**
 * Standard Fahrradtyp
 */
public class StandardType extends BikeType {
	/**
	 * Konstruktor: Initialisiert alle Attribute mit den übergebenen Parametern
	 *
	 * @param model Name des Fahrradmodells
	 * @param size  Größe des Fahrrads (in Zoll)
	 * @param price Preis des Fahrrads (in cent)
	 */
	public StandardType(String model, int size, int price) {
		super(model, size, price);
	}
	
	/**
	 * Rufe den FahrradTyp Standart als String auf
	 */
	@Override
	public String getTypeString() {
		return "Standard";
	}
	/*
     * Implementierung der Methode, ohne jegliche Berechnungen
     * @return null weil ein StandartBike keine Features hat
     */
    public String getFeatureDescription() {
        return null;
    }
    
}
