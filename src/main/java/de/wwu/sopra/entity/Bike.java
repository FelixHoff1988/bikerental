package de.wwu.sopra.entity;

import com.sothawo.mapjfx.Coordinate;
import java.util.ArrayList;

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
	 * 
	 * @see Availability
	 */
	private Availability availability;
	/**
	 * Rahmennummer
	 */
	private String frameId;
	/**
	 * Eigenschaften des Fahrrads
	 */
	private BikeType type;
	/**
	 * Die aktuelle Geofencing Area
	 */
	private GeofencingArea currentArea;
	/**
	 * Liste aller Wartungen und Reperaturen
	 */
	private ArrayList<Service> serviceList;
	/**
	 * Liste aller Reservierungen
	 */
	private ArrayList<Reservation> reservationList;

	/**
	 * Konstruktor mit allen initialen Attributen als Parametern
	 * 
	 * @param type        Typ dieses Fahrrads
	 * @param frameId     Rahmennummer des Fahrrads
	 * @param location    Initialer Standort des Fahrrads
	 * @param currentArea Initiale Area
	 */
	public Bike(BikeType type, String frameId, Coordinate location, GeofencingArea currentArea) {
		this.type = type;
		this.frameId = frameId;
		this.location = location;
		this.currentArea = currentArea;

		ArrayList<Service> serviceList = new ArrayList<Service>();
		this.serviceList = serviceList;

		ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
		this.reservationList = reservationList;

		this.availability = Availability.AVAILABLE;
	}

	/**
	 * Rufe den aktuellen Standort des Fahrrads ab
	 * 
	 * @return Standort des Fahrrads
	 */
	public Coordinate getLocation() {
		return location;
	}

	/**
	 * Setzte den aktuellen Standort des Fahrrads
	 * 
	 * @param location Neuer Standort des Fahrrads
	 */
	public void setLocation(Coordinate location) {
		this.location = location;
	}

	/**
	 * Rufe die aktuelle Verfügbarkeit ab
	 * 
	 * @return Verfügbarkeit
	 */
	public Availability getAvailability() {
		return availability;
	}

	/**
	 * Setzte die aktuelle Verfügbarkeit des Fahrrads
	 * 
	 * @param availability Neue Verfügbarkeit des Fahrrads
	 */
	public void setAvailability(Availability availability) {
		this.availability = availability;
	}

	/**
	 * Rufe die Rahmennummer ab
	 * 
	 * @return Rahmennummer
	 */
	public String getFrameId() {
		return frameId;
	}

	/**
	 * Setzte die Rahmennummer des Fahrrads
	 * 
	 * @param frameId Neue Rahmennummer des Fahrrads
	 */
	public void setFrameId(String frameId) {
		this.frameId = frameId;
	}

	/**
	 * Rufe den Fahrradtypen auf
	 * 
	 * @return Fahrradtyp
	 */
	public BikeType getType() {
		return type;
	}

	/**
	 * Setzte den Typ des Fahrrads
	 * 
	 * @param type Neuer Typ des Fahrrads
	 */
	public void setType(BikeType type) {
		this.type = type;
	}

	/**
	 * Rufe die aktuelle Area ab
	 * 
	 * @return aktuelle Geofencing Area
	 */
	public GeofencingArea getCurrentArea() {
		return currentArea;
	}

	/**
	 * Setze die aktuelle Geofencing Area
	 * 
	 * @param currentArea Neue GeofencingArea des Fahrrads
	 */
	public void setCurrentArea(GeofencingArea currentArea) {
		this.currentArea = currentArea;
	}

	/**
	 * Rufe die Wartungsliste ab
	 * 
	 * @return aktuelle Wartungsliste
	 */
	public ArrayList<Service> getServiceList() {
		return serviceList;
	}

	/**
	 * Füge eine Wartung oder Reparatur in die Liste hinzu
	 * 
	 * @param service Neuer dem Fahrrad zugeordneter Service
	 */
	public void serviceListAdd(Service service) {
		this.serviceList.add(service);
	}

	/**
	 * Rufe die Reservierungsliste ab
	 * 
	 * @return aktuelle Reservierungsliste
	 */
	public ArrayList<Reservation> getReservationList() {
		return reservationList;
	}

	/**
	 * Füge eine Reservierung in die Liste hinzu
	 * 
	 * @param reservation Neue dem Fahrrad hinzuzufügende Reservierung
	 */
	public void reservationListAdd(Reservation reservation) {
		this.reservationList.add(reservation);
	}
}
