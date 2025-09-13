package luxusproject;

import java.awt.Image;
import javax.swing.ImageIcon;

// Adicionei atributos relacionados ao veículo e modifiquei as outras classes para identificar LuxCar como a classe inexistente "Vehicle"
public class LuxCar implements DrawableItem {

    private Passenger passenger;
    private Location location;
    private Location destination;
    private LuxCompany company;
    private LuxCar car; // Atributo redundante
    private Image emptyImage, passengerImage;
    private int idleTime;

    // LuxCompany / luxCarCompany - classe errada
    public LuxCar(LuxCompany company, Location location) {
        // Substitui o super, já que a classe é independente
        this.company = company;
        this.location = location;
        emptyImage = new ImageIcon(getClass().getResource("images/luxCar.jpg")).getImage();

        passengerImage = new ImageIcon(getClass().getResource("images/luxCar+person.jpg")).getImage();
    }

    // Metodo criado: getTargetLocation envia o passageiro como parâmetro, devolve as coordenadas dele.
    public void act() {
        Location target = getTargetLocation(passenger);
        if (target != null) {
            Location next = getLocation().nextLocation(target);
            setLocation(next);
            if (next.equals(target)) {
                if (passenger != null) {
                    notifyPassengerArrival(passenger);
                    offloadPassenger();
                } else {
                    notifyPickupArrival();
                }
            }
        } else {
            incrementIdleCount();
        }
    }

    // Adicionei o metodo de aumentar o tempo em que o carro está parado
    public void incrementIdleCount() {
        idleTime++;
    }

    // Getter e setter implementado
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void notifyPickupArrival() {
        System.out.println("Pickup arrival!");
    }

    public void notifyPassengerArrival(Passenger passenger) {
        System.out.println("Passenger " + passenger.getImage() + " arrival!");
    }

    public Location getTargetLocation(Passenger passenger) {
        return passenger.getPickupLocation();
    }

    public boolean isFree() {
        return passenger == null; // Não precisa retornar boolean em relação a localização do passageiro
    }

    public void setPickupLocation(Location location) {
        this.location = location;
    }

    // Adicionei o metodo setter de destino
    public void setTargetLocation(Location destination) {
        this.destination = destination;
    }

    public void pickup(Passenger passenger) {
        this.passenger = passenger;
        setTargetLocation(passenger.getDestination());
    }

    // Não precisa de um metodo clearTargetLocation se você já está setando passageiro como null.
    public void offloadPassenger() {
        passenger = null;
        destination = null;
    }

    public Image getImage() {
        if (passenger != null) {
            return passengerImage;
        } else {
            return emptyImage;
        }
    }

    public String toString() {
        return "luxCar at " + getLocation();
    }
}
