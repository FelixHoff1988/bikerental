package de.wwu.sopra.entity;

import java.util.ArrayList;

public class GeofencingArea {
	
	//Liste zum Speichern der Kanten der GeofencingArea
	private ArrayList<Coordinate> edges = new ArrayList<Coordinate>();
	
	//Eine Kante zur GeodencingArea hinzufügen
	public void addEdge(Coordinate c) {
		edges.add(c);
	}
	//Eine Kante der GeofencingArea löschen
	public void removeEdge(Coordinate c) {
		edges.remove(c);
	}
	
	//Liste der Kanten der GeofencingArea zurückgeben
	public ArrayList<Coordinate> getEdges() {
		return edges;
	}
	//Liste der Kanten der GeofencingArea setzten
	public void setEdges(ArrayList<Coordinate> edges) {
		this.edges = edges;
	}
	
}
