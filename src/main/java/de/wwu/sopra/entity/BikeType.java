package de.wwu.sopra.entity;

/**
 * Abstrakte Oberklasse aller Fahrradtypen
 */
public abstract class BikeType {
    /**
     * Name des Fahrradmodells
     */
    protected String model;
    /**
     * Größe des Fahrrads (in Zoll)
     */
    protected int size;
    /**
     * Preis des Fahrrads (in cent)
     */
    protected int price;
    
    /**
     * Rufe den Modellnamen ab
     * @return Der Modellname dieses Typs
     */
    public String getModel() {
        return this.model;
    }

    /**
     * Setze den Modellnamen
     * @param model Neuer Modellname
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Rufe die Größe ab
     * @return Größe dieses Fahrradtyps
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Setze die Größe dieses Fahrradtyps
     * @param size Neue Größe
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Rufe den Preis ab
     * @return Preis dieses Fahrradtyps
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Setze den Preis des Fahrradtyps
     * @param price Neuer Preis
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Konstruktor: Initialisiert alle Attribute mit den übergebenen Parametern
     * @param model Name des Fahrradmodells
     * @param size Größe des Fahrrads (in Zoll)
     * @param price Preis des Fahrrads (in cent)
     */
    public BikeType(String model, int size, int price) {
        this.model = model;
        this.size = size;
        this.price = price;
    }
    
    /**
     * Rufe den Typ als String ab
     * @return FahrradTyp als String
     */
    abstract public String getTypeString();


    /**
     * Abstrakte Methode die zusätzlich Wichtige Daten über das Fahrrad als String zurückgibt.
     * @return String mit wichtigen Informationen über das Rad
     */
    public abstract String getFeatureDescription();
}
