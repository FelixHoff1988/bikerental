package de.wwu.sopra.entity;

import com.sothawo.mapjfx.Coordinate;

/**
 * Repräsentiert ein Fahrrad
 */
public class Bike {
    /**
     * Aktueller Standort des Fahrrads
     */
    private Coordinate location;
    /**
     * Verfügbarkeit des Fahrrads
     * @see Availability
     */
    private Availability available;
    /**
     * Rahmennummer
     */
    private String frameId;
    /**
     * Eigenschaften des Fahrrads
     */
    private BikeType type;

    /**
     * Konstruktor mit allen initialen Attributen als Parametern
     * @param type Typ dieses Fahrrads
     * @param frameId Rahmennummer des Fahrrads
     * @param location Initialer Standort des Fahrrads
     * @param available Initiale Verfügbarkeit
     */
    public Bike(BikeType type, String frameId, Coordinate location, Availability available) {
        this.type = type;
        this.frameId = frameId;
        this.location = location;
        this.available = available;
    }

    /**
     * Rufe den aktuellen Standort des Fahrrads ab
     * @return Standort des Fahrrads
     */
    public Coordinate getLocation() {
        return location;
    }

    /**
     * Setzte den aktuellen Standort des Fahrrads
     * @param location Neuer Standort des Fahrrads
     */
    public void setLocation(Coordinate location) {
        this.location = location;
    }

    public Availability getAvailable() {
        return available;
    }

    /**
     * Setzte die aktuelle Verfügbarkeit des Fahrrads
     * @param available Neue Verfügbarkeit des Fahrrads
     */
    public void setAvailable(Availability available) {
        this.available = available;
    }

    public String getFrameId() {
        return frameId;
    }

    /**
     * Setzte die Rahmennummer des Fahrrads
     * @param frameId Neue Rahmennummer des Fahrrads
     */
    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }

    public BikeType getType() {
        return type;
    }

    /**
     * Setzte den Typ des Fahrrads
     * @param type Neuer Typ des Fahrrads
     */
    public void setType(BikeType type) {
        this.type = type;
    }
}
