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

    public Shuttle(LuxCompany company, Location location)
    {
        super(company, location);
        destinations = new LinkedList<Location>();
        passengers = new LinkedList<Passenger>();

        this.emptyImage = new ImageIcon(getClass().getResource("/images/shuttle.png")).getImage();
        this.passengerImage = new ImageIcon(getClass().getResource("/images/shuttle+person.png")).getImage();
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

                Iterator<Passenger> it = passengers.iterator();
                while(it.hasNext()){
                    Passenger passenger = it.next();

                    if (passenger.getDestination().equals(getDestination())) {
                        getCompany().arrivedAtDestination(this, passenger);
                        offloadPassenger(passenger);
                        it.remove();
                    }
                }
                destinations.remove(0);
                chooseTargetLocation();
            }
        }
    }

    @Override
    public boolean isFree()
    {
        return destinations.isEmpty() && passengers.isEmpty();
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
    }

    @Override
    public Image getImage() {
        return (passengers.isEmpty()) ? emptyImage : passengerImage;
    }
}
