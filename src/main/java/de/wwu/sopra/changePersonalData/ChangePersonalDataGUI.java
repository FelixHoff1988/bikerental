package de.wwu.sopra.changePersonalData;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.DataProvider;

/**
 * GUI für die Bearbeitung eigener Nutzerdaten
 */
public class ChangePersonalDataGUI {
    
    /**
     * Instanz des DataProviders
     */
    private DataProvider data = DataProvider.getInstance();
    /**
     * Die Kontrollklasse um Aktionen auszuführen
     */
    private ChangePersonalDataCTRL ctrl = new ChangePersonalDataCTRL();
    /**
     * Instanz des AppContext
     */
    private AppContext context = AppContext.getInstance();
    
    /**
     * Standartkonstruktor
     */
    public ChangePersonalDataGUI() {
        
    }
}
