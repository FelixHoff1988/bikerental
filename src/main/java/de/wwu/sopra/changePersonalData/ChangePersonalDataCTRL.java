package de.wwu.sopra.changePersonalData;

import de.wwu.sopra.entity.User;

/**
 * Kontrollklasse um eigene Benutzerdaten einzusehen und zu ändern
 */
public class ChangePersonalDataCTRL {

    /**
     * Standartkonstruktor
     */
    public ChangePersonalDataCTRL() {

    }

    /**
     * Gibt einen String mit der Rolle des Nutzers zurück
     * 
     * @param user angemeldeter User
     * @return String mit der aktuellen Rolle
     */
    public String getRoleString(User user) {
        var role = (String) "";
        switch (user.getRole()) {
        case CUSTOMER -> {
            role += "Kunde*in";
        }
        case ADMIN -> {
            role += "Systemadministrator*in";
        }
        case EXECUTIVE -> {
            role += "Geschäftsführer*in";
        }
        case MAINTAINER -> {
            role += "Wartungstechniker*in";
        }
        case MANAGER -> {
            role += "Stations-Manager*in";
        }
        }
        return role;
    }

}
