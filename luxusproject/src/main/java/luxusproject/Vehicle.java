
package luxusproject;

import java.awt.Image;

public abstract class Vehicle implements DrawableItem, Actor {

    private final LuxCompany company;
    private Location location;
    private Location destination;

    private final String id;
    private static int nextId = 1;

    public Vehicle(LuxCompany company, Location location) {
        this.company = company;
        this.location = location;
        this.destination = null;
        this.id = "V-" + nextId++;
    }

    @Override
    public abstract void act();
    
    @Override
    public abstract Image getImage();
    
    public abstract boolean isFree();
    
    public abstract void pickup(Passenger passenger);

    public abstract void offloadPassenger();

    @Override
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }
    
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + " at " + location;
    }
}
