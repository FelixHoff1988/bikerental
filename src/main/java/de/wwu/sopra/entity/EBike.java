package de.wwu.sopra.entity;

/**
 * Standard Fahrradtyp
 */
public class EBike extends BikeType {
    /**
     * Ladestand (in Prozent)
     */
    private int charge;

    /**
     * Konstruktor: Initialisiert alle Attribute mit den übergebenen Parametern
     *
     * @param model Name des Fahrradmodells
     * @param size  Größe des Fahrrads (in Zoll)
     * @param price Preis des Fahrrads (in cent)
     * @param charge Ladestand (in Prozent)
     */
    public EBike(String model, int size, int price, int charge) {
        super(model, size, price);
        this.charge = charge;
    }

    /**
     * Rufe den aktuellen Ladestand des Fahrrads ab
     * @return Aktueller Ladestand des Fahrrads (in Prozent)
     */
    public int getCharge() {
        return charge;
    }

    /**
     * Setze den aktuellen Ladestand des Fahrrads
     * @param charge Neuer Ladestand des Fahrrads (in Prozent)
     */
    public void setCharge(int charge) {
        this.charge = charge;
    }
    
    /**
     * Gebe aktuellen Ladestand eines EBikes als String aus
     * @return String mit Ladestand
     */
    public String getFeatureDescription() {
        String description = "Ladestand: ";
        description += String.valueOf(this.charge);
        description += "%";
        return description;
    }

    @Override
    public String getTypeString() {
        // TODO Auto-generated method stub
        return "EBike";
    }
}
