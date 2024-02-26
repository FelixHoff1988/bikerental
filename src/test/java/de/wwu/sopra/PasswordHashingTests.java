package de.wwu.sopra;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für das Passwort-Hashing
 */
class PasswordHashingTests {
    /**
     * Testet das Hashing eines Passworts und das Validieren
     */
    @Test
    void hashAndValidatePassword() {
        var password = "¦'?>4g¾ì©íc¨0IfûÃßO`»)c={kAl¡Ñ`d;;ÝxgrkK#÷Ý¿M°XãËÿ¦³;Ãf~\\c(huÏMú";

        var hash = PasswordHashing.hashPassword(password);

        assertNotNull(hash);
        assertTrue(PasswordHashing.validatePassword(password, hash));
        assertFalse(PasswordHashing.validatePassword(password.replace('c', 'v'), hash));
    }
}