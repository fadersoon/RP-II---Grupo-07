
package luxusproject;

import java.awt.Image;

public abstract class Vehicle implements DrawableItem, Actor {

    private final LuxCompany company;
    private Location location;
    private Location destination;
    private boolean occupied;

    private final String id;
    protected static int nextId = 1;

    public Vehicle(LuxCompany company, Location location) {
        this.company = company;
        this.location = location;
        this.destination = null;
        this.id = getClass().getSimpleName() + " " + nextId++;
        this.occupied = false;
    }

    public void setOccupied(boolean status){
        this.occupied = status;
    }

    @Override
    public abstract void act();


    @Override
    public abstract Image getImage();

    public abstract int getCapacity();

    public boolean isFree(){
        return !occupied;
    }
    
    public abstract void pickup(Passenger passenger);

    public abstract void offloadPassenger();

    @Override
    public Location getLocation() {
        return location;
    }

    public String getID() {
        return id;
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

    public LuxCompany getCompany() {
        return company;
    }

}