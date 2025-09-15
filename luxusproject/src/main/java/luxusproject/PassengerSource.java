package luxusproject;

import java.util.Random;

public class PassengerSource implements Actor {

    private City city;
    private LuxCompany company;
    private Random rand;
    private static final double CREATION_PROBABILITY = 0.06;
    private int missedPickups;

    public PassengerSource(City city, LuxCompany company) {
        if (city == null) {
            throw new NullPointerException("city");
        }
        if (company == null) {
            throw new NullPointerException("company");
        }
        this.city = city;
        this.company = company;

        rand = new Random(1234);
        missedPickups = 0;
    }

    public void act() {
        if (rand.nextDouble() < CREATION_PROBABILITY) {
            Passenger passenger = createPassenger();
            if (company.requestPickup(passenger)) {
                city.addItem(passenger);
            } else {
                missedPickups++;
            }
        }
    }

    public int getMissedPickups() {
        return missedPickups;
    }

    private Passenger createPassenger() {
        int cityWidth = city.getWidth();
        int cityHeight = city.getHeight();

        Location pickupLocation
                = new Location(rand.nextInt(cityWidth),
                        rand.nextInt(cityHeight));
        Location destination;
        do {
            destination
                    = new Location(rand.nextInt(cityWidth),
                            rand.nextInt(cityHeight));
        } while (pickupLocation.equals(destination));
        return new Passenger(pickupLocation, destination);
    }
}