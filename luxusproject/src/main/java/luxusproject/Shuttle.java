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
    private final int MAX_PASSENGERS = 10;

    public Shuttle(LuxCompany company, Location location)
    {
        super(company, location);
        destinations = new LinkedList<Location>();
        passengers = new LinkedList<Passenger>();

        this.emptyImage = new ImageIcon(getClass().getResource("/images/shuttle.png")).getImage();
        this.passengerImage = new ImageIcon(getClass().getResource("/images/shuttle.png")).getImage();
    }

    public void act() {
    Location destination = getDestination();
    if (destination == null) {
        return; // Não faz nada se não tiver destino
    }

    Location currentLocation = getLocation();
    Location nextLocation = currentLocation.nextLocation(destination);
    setLocation(nextLocation);

    // Se chegou ao destino
    if (nextLocation.equals(destination)) {
        // Primeiro, desembarca quem precisa descer aqui
        offloadPassengersAtCurrentLocation();
        
        // Depois, embarca quem está esperando aqui
        if (getCompany().isPickupLocation(destination)) {
            getCompany().arrivedAtPickup(this);
        }
        
        // Por fim, define o próximo alvo
        destinations.remove(0);
        chooseTargetLocation();
    }
}
    
   private void offloadPassengersAtCurrentLocation() {
    if (passengers.isEmpty()) {
        return;
    }
    
    Iterator<Passenger> it = passengers.iterator();
    while (it.hasNext()) {
        Passenger passenger = it.next();
        if (passenger.getDestination().equals(getLocation())) {
            System.out.println(getId() + " deixou o " + passenger.getName() + " no ponto: " + passenger.getDestination());
            getCompany().arrivedAtDestination(this, passenger);
            it.remove(); // Remove o passageiro da lista do shuttle
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

    @Override
    public Image getImage() {
        if (passengers.size() > 0) {
            return passengerImage;
        } else {
            return emptyImage;
        }
    }

    public int getCapacity() {
        return MAX_PASSENGERS;
    }
}