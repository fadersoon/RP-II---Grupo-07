
public class MissingPassengerException extends RuntimeException
{
    private Vehicle vehicle;

    public MissingPassengerException(Vehicle vehicle)
    {
        super("Missing passenger at pickup location.");
    }

    public Vehicle getVehicle()
    {
        return vehicle;
    }
}
