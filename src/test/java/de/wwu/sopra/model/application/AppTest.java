package de.wwu.sopra.model.application;

import de.wwu.sopra.application.App;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AppTest {
    @Test
    public void TestMain() {
    	App.main(new String[]{});
    	assert(true);
        
    }
}
