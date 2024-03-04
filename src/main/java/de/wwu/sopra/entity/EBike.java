package de.wwu.sopra.entity;

/**
 * Standard Fahrradtyp
 */
public class EBike extends BikeType {
    /**
     * Reichweite des Fahrrads (in Metern)
     */
    private int distance;
    
    /**
     * Konstruktor: Initialisiert alle Attribute mit den übergebenen Parametern
     *
     * @param model Name des Fahrradmodells
     * @param size  Größe des Fahrrads (in Zoll)
     * @param price Preis des Fahrrads (in cent)
     * @param distance Reichweite des Fahrrads (in Metern)
     */
    public EBike(String model, int size, int price, int distance) {
        super(model, size, price);
        this.distance = distance;
    }

    /**
     * Rufe die Reichweite des Fahrrads ab
     * @return Reichweite des Fahrrads (in Metern)
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Setze die Reichweite des Fahrrads
     * @param distance Neue Reichweite des Fahrrads (in Metern)
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }
    
	/**
	 * Rufe den FahrradTyp E-Bikes als String auf
	 */
	@Override
	public String getTypeString() {
		return "EBike";
	}
    
    /**
     * Gebe die Reichweite eines E-Bikes als String aus
     * @return String mit Reichweite
     */
    public String getFeatureDescription() {
        String description = "Reichweite: ";
        description += String.valueOf(this.distance / 1000);
        description += ",";
        description += String.valueOf(this.distance % 1000);
        description += " km";
        return description;
    }

}
