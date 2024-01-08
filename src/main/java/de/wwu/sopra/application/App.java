package de.wwu.sopra.application;

import de.wwu.sopra.model.World;

public class App {
  public static void main(String [] args) {
    World world = new World();
    System.out.printf("Hello %s!\n", world.toString());
  }
}
