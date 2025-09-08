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

    // Enum para o estado do passageiro
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

        // Tenta carregar imagem padrão
        URL resource = getClass().getResource("/images/person.jpg");
        if (resource != null) {
            this.image = new ImageIcon(resource).getImage();
        } else {
            System.err.println("Warning: default passenger image not found.");
            this.image = null; // ou poderia ser uma imagem alternativa
        }
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

    // --- Getters e Setters ---
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
