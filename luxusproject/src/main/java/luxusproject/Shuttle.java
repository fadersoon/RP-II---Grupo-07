package luxusproject;

import java.util.List;
import java.util.LinkedList;
    
public class Shuttle extends LuxCar
{
    private List<Location> destinations;
    private List<Passenger> passengers;


    public Shuttle(LuxCompany company, Location location)
    {
        super(company, location);
        destinations = new LinkedList<Location>();
        passengers = new LinkedList<Passenger>();
    }

    public void act()
    {
    }


    public boolean isFree()
    {
        return true;
    }
    

    public void setPickupLocation(Location location)
    {
        destinations.add(location);
        chooseTargetLocation();
    }
    

    public void pickup(Passenger passenger)
    {
        passengers.add(passenger);
        destinations.add(passenger.getDestination());
        chooseTargetLocation();
    }


    private void chooseTargetLocation()
    {
    }


    public void offloadPassenger()
    {
    }
}
