package de.wwu.sopra.entity;

import com.sothawo.mapjfx.Coordinate;
import de.wwu.sopra.map.MapCoordinateLineCandidate;

import java.util.ArrayList;

/**
 * Klasse für die Entität der GeofencingAreas
 */
public class GeofencingArea implements MapCoordinateLineCandidate {

	/**
	 * Liste zum Speichern der Ecke der GeofencingArea
	 */
	private ArrayList<Coordinate> vertices;

	/**
	 * Konstruktor setzt initiale Werte
	 * 
	 * @param vertices Initiale Eckpunkte der GeofencingArea
	 */
	public GeofencingArea(ArrayList<Coordinate> vertices) {
		this.vertices = vertices;
	}

	/**
	 * Eine Ecke zur GeofencingArea hinzufügen
	 * 
	 * @param vertex Ecke die hinzugefügt werden soll
	 */
	public void addVertex(Coordinate vertex) {
		vertices.add(vertex);
	}

	/**
	 * Eine Ecke der GeofencingArea löschen
	 * 
	 * @param vertex Ecke die gelöscht werden soll
	 */
	public void removeVertex(Coordinate vertex) {
		vertices.remove(vertex);
	}

	/**
	 * Liste der Eckpunkte der GeofencingArea zurückgeben
	 * 
	 * @return Liste der Eckpunkte der GeofencingArea
	 */
	public ArrayList<Coordinate> getVertices() {
		return this.vertices;
	}

	/**
	 * Liste der Eckpunkte der GeofencingArea setzten
	 * 
	 * @param edges Neue Eckpunkte der GeofencingArea
	 */
	public void setVertices(ArrayList<Coordinate> edges) {
		this.vertices = edges;
	}

}
