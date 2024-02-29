package de.wwu.sopra.entity;

/**
 * Standard Fahrradtyp
 */
public class CargoBike extends BikeType {
    /**
     * Ladekapazität (in kg)
     */
    private int capacity;

    /**
     * Konstruktor: Initialisiert alle Attribute mit den übergebenen Parametern
     *
     * @param model Name des Fahrradmodells
     * @param size  Größe des Fahrrads (in Zoll)
     * @param price Preis des Fahrrads (in cent)
     * @param capacity Ladekapazität (in kg)
     */
    public CargoBike(String model, int size, int price, int capacity) {
        super(model, size, price);
        this.capacity = capacity;
    }

    /**
     * Rufe die Ladekapazität des Fahrrads ab
     * @return Ladekapazität des Fahrrads (in kg)
     */
    public int getCharge() {
        return capacity;
    }

    /**
     * Setze die Ladekapazität des Fahrrads
     * @param capacity Neue Ladekapazität des Fahrrads (in kg)
     */
    public void setCharge(int capacity) {
        this.capacity = capacity;
    }
    
    /**
     * Gebe die zulässige Kapazität eines CargoBikes als String aus
     * @return String mit Kapazitätslimit
     */
    public String getFeatureDescription() {
        String description = "Kapazität: ";
        description += String.valueOf(this.capacity);
        description += " kg";
        return description;
    }
}
