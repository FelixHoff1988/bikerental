package de.wwu.sopra.entity;

/**
 * Die Verfügbarkeit eines Fahrrads
 */
public enum Availability {
    /**
     * Kann vom Kunden gebucht werden
     */
    AVAILABLE,
    /**
     * Ist gerade von einem Kunden gebucht
     */
    RESERVED,
    /**
     * Ist gerade von einem Kunden gebucht
     */
    BOOKED,
    /**
     * Ist zur Umverteilung blockiert
     */
    BLOCKED,
    /**
     * Wird gerade gewartet
     */
    MAINTENANCE,
    /**
     * Fahrrad ist defekt
     */
    FAULTY,
}
