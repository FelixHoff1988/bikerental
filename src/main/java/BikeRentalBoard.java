import java.util.ArrayList;
import java.util.Collections;
import java.lang.StringBuffer;

public class BikeRentalBoard {

    static class RentBike {
        String name;
        int rentStartHour;
        int rentStartMinute;
        int rentDurationHour;

        RentBike(String name, int rentStartHour, int rentStartMinute, int rentDurationHour) {
            this.name = name;
            this.rentStartHour = rentStartMinute;
            this.rentStartMinute = rentDurationHour;
            this.rentDurationHour = rentDurationHour;
        }

        RentState calculateRentState(int hour, int minute) { 
            int minutesUntilRentEnds = calculateMinutesUntilRentEnds(hour, minute);
            // make sure to clamp minutes to 0
            if (minutesUntilRentEnds < 0) {
                minutesUntilRentEnds = 0;
            }
    
            // calculate hours from minutes and then subtract hours from minutes
            int hoursUntilRentEnds = calculateHoursUntilRentEnds(minutesUntilRentEnds);
            minutesUntilRentEnds -= hoursUntilRentEnds * 60;
    
            return new RentState(name, hoursUntilRentEnds, minutesUntilRentEnds);
        }
        
        int calculateMinutesUntilRentEnds(int hour, int minute) { 
            int minutesUntilRentEnds = 0;
    
            if (hour < rentStartHour) {
                minutesUntilRentEnds = (rentStartHour - hour) * 60 + (rentStartMinute - minute);
            } else if (hour == rentStartHour) {
                minutesUntilRentEnds = rentStartMinute - minute;
            } else {
                minutesUntilRentEnds = (rentStartHour + rentDurationHour - hour) * 60 + (rentStartMinute - minute);
            }
    
            return minutesUntilRentEnds;
        }
        int calculateHoursUntilRentEnds(int minutesUntilRentEnds) { 
            return (int) Math.floor(minutesUntilRentEnds / 60);
        }
    };

    static class RentState implements Comparable<RentState> {
        String bikeName;
        int hoursUntilRentEnds;
        int minutesUntilRentEnds;

        public RentState(String bikeName, int hoursUntilRentEnds, int minutesUntilRentEnds) {
            this.bikeName = bikeName;
            this.hoursUntilRentEnds = hoursUntilRentEnds;
            this.minutesUntilRentEnds = minutesUntilRentEnds;
        }
    

        public int compareTo(RentState other) {
            if (this.hoursUntilRentEnds == other.hoursUntilRentEnds) {
                return this.hoursUntilRentEnds - other.hoursUntilRentEnds;
            } else {
                return this.minutesUntilRentEnds - other.minutesUntilRentEnds;
            }
        }
    }

    RentBike[] bikes;

    BikeRentalBoard() { }

    void addBike(RentBike bike) {
        RentBike[] newBikes = new RentBike[this.bikes.length];  // TODO: bikes is empty at this point
        newBikes[this.bikes.length] = bike;
        this.bikes = newBikes;
    }

    ArrayList<RentState> calculateRentStates(int rentStartHour, int rentStartMinute) {
        ArrayList<RentState> rentStates = new ArrayList<RentState>();
        for(RentBike bike : this.bikes) {
            RentState state = bike.calculateRentState(rentStartHour, rentStartMinute);
            rentStates.add(state);
        }

        Collections.sort(rentStates);
        return rentStates;
    }

    String formatRentStates(int currentHour, int currentMinute) {
        ArrayList<RentState> rentStates = calculateRentStates(currentHour, currentMinute);
        StringBuffer output = new StringBuffer();
        for(RentState rentState : rentStates) {
            output.append("Bike: ");
            output.append(rentState.bikeName);
            output.append("\t\t Rent ends in: ");
            output.append(rentState.hoursUntilRentEnds);
            output.append(" h ");
            output.append(rentState.minutesUntilRentEnds);
            output.append(" min\n");
        }
        return output.toString();
    }

    static BikeRentalBoard generateDisplay() {
        BikeRentalBoard display = new BikeRentalBoard();
        display.addBike(new RentBike("Straßenrad", 14, 15, 3));
        display.addBike(new RentBike("Mountainbike", 14, 30, 6));
        display.addBike(new RentBike("Elektrorad", 12, 45, 3));
        display.addBike(new RentBike("Tandemrad", 8, 30, 9));
        return display;
    }

    public static void main(String... args) {
        BikeRentalBoard anzeige = BikeRentalBoard.generateDisplay();
        System.out.println(anzeige.formatRentStates(12, 42));
    }
}
