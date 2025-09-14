package luxusproject;

import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import javax.swing.ImageIcon;

public class Shuttle extends Vehicle
{
    private List<Location> destinations;
    private List<Passenger> passengers;
    private final Image emptyImage;
    private final Image passengerImage;
    private final int MAX_PASSENGERS = 3;

    public Shuttle(LuxCompany company, Location location)
    {
        super(company, location);
        destinations = new LinkedList<Location>();
        passengers = new LinkedList<Passenger>();

        this.emptyImage = new ImageIcon(getClass().getResource("/images/shuttle.png")).getImage();
        this.passengerImage = new ImageIcon(getClass().getResource("/images/shuttle.png")).getImage();
    }

    public void act() {
        Location currentLocation = getLocation();

        if (getDestination() != null) {
            Location next = currentLocation.nextLocation(getDestination());
            setLocation(next);

            if (next.equals(getDestination())) {

                if (getCompany().isPickupLocation(getDestination())) {
                    getCompany().arrivedAtPickup(this);
                }

                if (!passengers.isEmpty()) {
                    Iterator<Passenger> it = passengers.iterator();
                    while (it.hasNext()) {
                        Passenger passenger = it.next();

                        if (passenger.getDestination().equals(getDestination())) {
                            getCompany().arrivedAtDestination(this, passenger);
                            offloadPassenger(passenger);
                            it.remove();
                        }
                    }
                }
                if (getCompany().isPickupLocation(getDestination())) {
                    getCompany().arrivedAtPickup(this);
                    if (!getCompany().isPickupLocation(getDestination())) {
                        destinations.remove(0);
                        chooseTargetLocation();
                    }
                } else {
                    destinations.remove(0);
                    chooseTargetLocation();
                }
            }
        }
    }

    public boolean isFree() {
        return passengers.size() + getAssignedPassengerCount() < MAX_PASSENGERS;
    }

    private int getAssignedPassengerCount() {
        // Pega do LuxCompany quantos passageiros já estão agendados (ainda não a bordo, mas esperando o shuttle)
        List<Passenger> assigned = getCompany().getAssignments().get(this);
        return (assigned != null) ? assigned.size() : 0;
    }


    public void setPickupLocation(Location location) {
        destinations.add(location);
        if (getDestination() == null) {
            chooseTargetLocation();
        }
    }

    @Override
    public void pickup(Passenger passenger)
    {
        if (!isFree()) { // Só permite se houver espaço
            return;
        }
        passengers.add(passenger);
        destinations.add(passenger.getDestination());
        chooseTargetLocation();
    }


    private void chooseTargetLocation() {
        if (!destinations.isEmpty()) {
            setDestination(destinations.get(0));
        } else {
            setDestination(null);
        }
    }

    @Override
    public void offloadPassenger(){
    }

    public void offloadPassenger(Passenger passenger) {
        System.out.println(getId() + " dropped off " + passenger.getName() + " at " + passenger.getDestination());
        getCompany().arrivedAtDestination(this, passenger); //
    }

    @Override
    public Image getImage() {
        if (passengers.size() > 0) {
            return passengerImage;
        } else {
            return emptyImage;
        }
    }
}