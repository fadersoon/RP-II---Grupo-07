package luxusproject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class LuxCompany {

    private List<Vehicle> vehicles;
    private City city;
    private Map<Vehicle, List<Passenger>> assignments;

    private static final int TOTAL_LUXCARS = 3;

    public LuxCompany(City city) {
        this.city = city;
        vehicles = new LinkedList<>();
        assignments = new HashMap<>();
        setupVehicles();
    }

    public boolean requestPickup(Passenger passenger) {
        Vehicle vehicle = scheduleVehicle();

        if (vehicle != null && vehicle.isFree()) {
            assignments.putIfAbsent(vehicle, new LinkedList<>());
            assignments.get(vehicle).add(passenger);
            if (vehicle instanceof LuxCar) {
                vehicle.setDestination(passenger.getPickupLocation());
            } else if (vehicle instanceof Shuttle) {
                Shuttle shuttle = (Shuttle) vehicle;
                shuttle.setPickupLocation(passenger.getPickupLocation());
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean isPickupLocation(Location location) {
        for (List<Passenger> list : assignments.values()) {
            for (Passenger p : list) {
                if (p.getPickupLocation().equals(location)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void arrivedAtPickup(Vehicle vehicle) {
        List<Passenger> assigned = assignments.get(vehicle);
        if (assigned == null || assigned.isEmpty()) {
            System.out.println(vehicle.getId() + " arrived at pickup but no passengers assigned");
            throw new MissingPassengerException(vehicle);
        }

        Iterator<Passenger> it = assigned.iterator();
        while (it.hasNext()) {
            Passenger passenger = it.next();
            if (passenger.getPickupLocation().equals(vehicle.getLocation())) {
                System.out.println(vehicle.getId() + " picking up " + passenger.getName() + " at " + passenger.getPickupLocation());
                city.removeItem(passenger);
                vehicle.pickup(passenger);
                it.remove();

                // Atualiza destino dependendo do tipo do veículo
                if (vehicle instanceof Shuttle) {
                    ((Shuttle) vehicle).setPickupLocation(passenger.getDestination());
                } else if (vehicle instanceof LuxCar) {
                    vehicle.setDestination(passenger.getDestination());
                }
            }
        }

        if (assigned.isEmpty()) {
            assignments.remove(vehicle);
        }
    }

    public void arrivedAtDestination(Vehicle vehicle, Passenger passenger) {
        passenger.setStatus(Passenger.PassengerStatus.ARRIVED);
        System.out.println("Passageiro " + passenger.getName() + " chegou ao destino.");
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    private Vehicle scheduleVehicle() {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isFree()) {
                return vehicle;
            }
        }
        return null;
    }

    public Map<Vehicle, List<Passenger>> getAssignments() {
        return assignments;
    }

    public City getCity() {
        return city;
    }

    private void setupVehicles() {
        int cityWidth = city.getWidth();
        int cityHeight = city.getHeight();
        Random rand = new Random(12345);
        Shuttle shuttle = new Shuttle(this,
                new Location(rand.nextInt(cityWidth),
                        rand.nextInt(cityHeight)));
        vehicles.add(shuttle);
        city.addItem(shuttle);
        for (int i = 0; i < TOTAL_LUXCARS; i++) {
            LuxCar luxcar = new LuxCar(this,
                    new Location(rand.nextInt(cityWidth),
                            rand.nextInt(cityHeight)));
            vehicles.add(luxcar);
            city.addItem(luxcar);
        }
    }
}
