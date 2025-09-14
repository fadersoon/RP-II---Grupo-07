package luxusproject;

import java.awt.Image;
import javax.swing.ImageIcon;

public class LuxCar extends Vehicle {

    private Passenger passenger;
    private final Image emptyImage;
    private final Image passengerImage;
    private int idleTime;

    public LuxCar(LuxCompany company, Location location) {
        super(company, location);

        this.passenger = null;
        this.idleTime = 0;

        this.emptyImage = new ImageIcon(getClass().getResource("/images/LuxCar.png")).getImage();
        this.passengerImage = new ImageIcon(getClass().getResource("/images/luxCar+person.jpg")).getImage();
    }

    @Override
    public void act() {
        Location target = getDestination();
        Location currentLocation = getLocation();

        if (target != null) {
            Location next = currentLocation.nextLocation(target);
            setLocation(next);

            if (next.equals(target)) {
                if (passenger != null) {
                    System.out.println(getId() + " arrived at the destination with the passenger.");
                    getCompany().arrivedAtDestination(this, passenger);
                    offloadPassenger();
                } else {
                    System.out.println(getId() + " arrived at the starting point");
                    getCompany().arrivedAtPickup(this);
                }
            }
        } else {
            idleTime++;
        }
    }

    @Override
    public boolean isFree() {
        return passenger == null && getDestination() == null;
    }

    @Override
    public Image getImage() {
        return (passenger != null) ? passengerImage : emptyImage;
    }

    @Override
    public void pickup(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public void offloadPassenger() {
        this.passenger = null;
        setDestination(null);
    }

}