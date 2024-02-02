package de.wwu.sopra.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import org.junit.jupiter.api.Test;

public class WorldTest {

    @Test
    public void testToString() {
        World world = new World();
        String expected = "World";
        String actual = world.toString();
        assertEquals(expected, actual, "World.toString() should return 'World'");
    }

    @Test
    public void testNotRunByCI() {
        assumeFalse(System.getenv().containsKey("CI"));
    }
}
