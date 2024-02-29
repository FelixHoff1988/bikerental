package de.wwu.sopra.entity;

import com.sothawo.mapjfx.Coordinate;
import java.util.ArrayList;

/**
 * Repräsentiert eine Fahrrad-Station
 */
public class BikeStation {

	/**
	 * Name der Station
	 */
	private String name;
	/**
	 * Standort der Station
	 */
	private Coordinate location;
	/**
	 * Kapazität der Station
	 */
	private int capacity;
	/**
	 * Die Fahrräder die an der Station stehen
	 */
	private ArrayList<Bike> bikes;

	/**
	 * Konstruktor setzt die initialen Werte
	 * 
	 * @param name Name der Station
	 * @param location Standort der Station
	 * @param capacity Anzahl der Fahrräder, welche die Station halten kann
	 */
	public BikeStation(String name, Coordinate location, int capacity) {
		this.name = name;
		this.location = location;
		this.capacity = capacity;
        this.bikes = new ArrayList<>();
	}

	/**
	 * Rufe den Namen der Station ab
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setze den Namen
	 * 
	 * @param name Neuer Name der Station
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Rufe den Standort der Station ab
	 * 
	 * @return location
	 */
	public Coordinate getLocation() {
		return location;
	}

	/**
	 * Setze den Standort
	 * 
	 * @param location Neuer Standort der Station
	 */
	public void setLocation(Coordinate location) {
		this.location = location;
	}

	/**
	 * Rufe die Kapazität der Station ab
	 * 
	 * @return capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Setze die Kapazität
	 * 
	 * @param capacity Neue Kapazität der Station
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Rufe die Fahrradliste auf
	 * 
	 * @return Liste mit Fahrrädern
	 */
	public ArrayList<Bike> getBikes() {
		return bikes;
	}

	/**
	 * Füge ein Fahrrad der Station zu
	 * 
	 * @param bike Neues der Station zugeordnetes Fahrrad
	 */
	public void addBike(Bike bike) {
		if (this.bikes.size() < this.capacity) {
			this.bikes.add(bike);
		}
	}
}
