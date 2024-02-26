package de.wwu.sopra.entity;

import com.sothawo.mapjfx.Coordinate;
import java.util.ArrayList;

/**
 * Klasse für die Entität der GeofencingAreas
 */
public class GeofencingArea {

	/**
	 * Liste zum Speichern der Kanten der GeofencingArea
	 */
	private ArrayList<Coordinate> edges;

	/**
	 * Konstruktor setzt initiale Werte
	 * 
	 * @param edges Initiale Eckpunkte der GeofencingArea
	 */
	public GeofencingArea(ArrayList<Coordinate> edges) {
		this.edges = edges;
	}

	/**
	 * Eine Kante zur GeofencingArea hinzufügen
	 * 
	 * @param c Kante die hinzugefügt werden soll
	 */
	public void addEdge(Coordinate c) {
		edges.add(c);
	}

	/**
	 * Eine Kante der GeofencingArea löschen
	 * 
	 * @param c Kante die gelöscht werden soll
	 */
	public void removeEdge(Coordinate c) {
		edges.remove(c);
	}

	/**
	 * Liste der Kanten der GeofencingArea zurückgeben
	 * 
	 * @return Liste der Eckpunkte der GeofencingArea
	 */
	public ArrayList<Coordinate> getEdges() {
		return edges;
	}

	/**
	 * Liste der Kanten der GeofencingArea setzten
	 * 
	 * @param edges Neue Eckpunkte der GeofencingArea
	 */
	public void setEdges(ArrayList<Coordinate> edges) {
		this.edges = edges;
	}

}
