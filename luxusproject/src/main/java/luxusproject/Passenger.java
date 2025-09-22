package luxusproject;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.net.URL;
import java.util.Objects;

public class Passenger implements DrawableItem {

    private final String name;
    private final Location pickup;
    private final Location destination;
    private Image image;
    private PassengerStatus status;
    private int waitingTime;

    private Vehicle currentVehicle;

    private static int nextId = 1;

    public enum PassengerStatus {
        WAITING,
        IN_TRIP,
        ARRIVED
    }

    public Passenger(String name, Location pickup, Location destination) {
        this.name = validateName(name);
        this.pickup = Objects.requireNonNull(pickup, "Pickup location cannot be null");
        this.destination = Objects.requireNonNull(destination, "Destination location cannot be null");
        this.status = PassengerStatus.WAITING;
        this.waitingTime = 0;
        this.currentVehicle = null;

        URL resource = getClass().getResource("/images/person.png");
        if (resource != null) {
            this.image = new ImageIcon(resource).getImage();
        } else {
            System.err.println("Warning: default passenger image not found.");
            this.image = null;
        }
    }

    public Passenger(Location pickup, Location destination) {
        this("Passenger #" + nextId++, pickup, destination);
    }

    @Override
    public Location getLocation() {
        if (status == PassengerStatus.IN_TRIP && currentVehicle != null) {
            return currentVehicle.getLocation();
        } else if (status == PassengerStatus.ARRIVED) {
            return destination;
        } else { // WAITING
            return pickup;
        }
    }

    public void boardVehicle(Vehicle vehicle) {
        this.currentVehicle = vehicle;
        setStatus(PassengerStatus.IN_TRIP);
    }

    public void leaveVehicle() {
        this.currentVehicle = null;
        setStatus(PassengerStatus.ARRIVED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passenger passenger = (Passenger) o;
        return name.equals(passenger.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    private static String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Passenger name cannot be empty");
        }
        return name;
    }

    @Override
    public String toString() {
        return String.format(
                "Passenger %s travelling from %s to %s (status: %s, waitingTime: %d)",
                name, pickup, destination, status, waitingTime
        );
    }

    public String getName() {
        return name;
    }

    public Location getPickupLocation() {
        return pickup;
    }

    public Location getDestination() {
        return destination;
    }

    public PassengerStatus getStatus() {
        return status;
    }

    public void setStatus(PassengerStatus status) {
        this.status = Objects.requireNonNull(status, "Status cannot be null");
    }

    @Override
    public Image getImage() {
        return image;
    }

    public void setImage(Image newImage) {
        this.image = newImage;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void incrementWaitingTime() {
        incrementWaitingTime(1);
    }

    public void incrementWaitingTime(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Waiting time increment cannot be negative");
        }
        this.waitingTime += amount;
    }

    public boolean hasArrived() {
        return this.status == PassengerStatus.ARRIVED;
    }
}
