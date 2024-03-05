package de.wwu.sopra.usagehistory;

import com.sothawo.mapjfx.Coordinate;

import de.wwu.sopra.AppContext;
import de.wwu.sopra.bikeadministration.editbike.EditBikeCTRL;
import de.wwu.sopra.entity.Availability;
import de.wwu.sopra.entity.Bike;
import de.wwu.sopra.entity.Reservation;
import de.wwu.sopra.map.MapGUI;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class UsageHistoryGUI extends HBox {
    
    private UsageHistoryCTRL ctrl = new UsageHistoryCTRL();
     /**
     * Konstruktor.
     */
    public UsageHistoryGUI(){
        init();
    }
    
      /**
     * Initialisiert das GUI-Layout für die Benutzer-Editierung.
     */
    public void init() {
        
        //Tabelle mit den Spalten erstellen
        TableView<Reservation> tableView = new TableView<>();
        TableColumn<Reservation,String> frameIdColumn = new TableColumn<>("Rahmennummer");
        TableColumn<Reservation,String> modelColumn = new TableColumn<>("Modell");
        TableColumn<Reservation,String> typeColumn = new TableColumn<>("Typ");
        TableColumn<Reservation,String> startColumn = new TableColumn<>("Startzeit");
        TableColumn<Reservation,String> endColumn = new TableColumn<>("Endzeit");
        TableColumn<Reservation,String> statusColumn = new TableColumn<>("Status");
        TableColumn<Reservation,String> priceColumn = new TableColumn<>("Preis");
        
        //Werte für die Spalten der Tabelle konfigurieren
        frameIdColumn.setCellValueFactory(data -> {
            return new ReadOnlyStringWrapper(data.getValue().getBike().getFrameId());
        });
        modelColumn.setCellValueFactory(data -> {
            return new ReadOnlyStringWrapper(data.getValue().getBike().getType().getModel());
        });
        typeColumn.setCellValueFactory(data -> {
            return new ReadOnlyStringWrapper(data.getValue().getBike().getType().getTypeString());
        });
        startColumn.setCellValueFactory(data -> {
            var startTime = data.getValue().getStartTime();
            String s = "";
            if(startTime!=null)
                s = startTime.getDayOfMonth() + "."+ startTime.getMonthValue()+"."+startTime.getYear()+ " , " + startTime.getHour()+":"+startTime.getMinute()+" Uhr"  ;
            return new ReadOnlyStringWrapper(s);
        });
        endColumn.setCellValueFactory(data -> {
            var endTime = data.getValue().getEndTime();
            String s = "";
            if(endTime!=null)
                s = endTime.getDayOfMonth() + "."+ endTime.getMonthValue()+"."+endTime.getYear()+ " , " + endTime.getHour()+":"+endTime.getMinute()+" Uhr"  ;
            return new ReadOnlyStringWrapper(s);
        });
        statusColumn.setCellValueFactory(data -> {
            String s = "";
             
            if (data.getValue().getEndTime() == null) {
                if (data.getValue().getBookingTime() == null)
                    s = "in Reservierung";
                else 
                    s = "in Buchung";
            }
            else {
                if (data.getValue().getBookingTime() == null) 
                    s = "Reservierung";
                else 
                    s = "Buchung";
                
            }
            
            return new ReadOnlyStringWrapper(s);
            
        });
        
        
        //Spalten zur Tabelle hinzufügen
        tableView.getColumns().add(frameIdColumn);
        tableView.getColumns().add(modelColumn);
        tableView.getColumns().add(typeColumn);
        tableView.getColumns().add(startColumn);
        tableView.getColumns().add(endColumn);
        tableView.getColumns().add(priceColumn);
        tableView.getColumns().add(statusColumn);
     
        //Breite der Tabelle festlegen
        tableView.setMaxWidth(875);
        tableView.setMinWidth(875);
        
        //Breite der Spalten festlegen
        frameIdColumn.setPrefWidth(125);
      
        modelColumn.setPrefWidth(125);
        typeColumn.setPrefWidth(125);
        endColumn.setPrefWidth(150);
        startColumn.setPrefWidth(150);
        priceColumn.setPrefWidth(100);
        statusColumn.setPrefWidth(100);
        
        
        
        //Liste zum Verwalten der Fahrräder, verknüpft mit der Tabelle
        ObservableList<Reservation> Reservations = FXCollections.observableArrayList((ctrl.loadReservations()));
        tableView.setItems(Reservations);
        
        

        
        this.getChildren().add(tableView);
        this.setAlignment(Pos.CENTER);
        
        
   
       
        
    }


}
