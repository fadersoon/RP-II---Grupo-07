package luxusproject;

public class MissingPassengerException extends RuntimeException {

    private final Vehicle vehicle;

    // Construtor principal: com veículo e mensagem padrão
    public MissingPassengerException(Vehicle vehicle) {
        super("Missing passenger at pickup location for vehicle: "
                + (vehicle != null ? vehicle.getId() : "unknown"));
        this.vehicle = vehicle;
    }

    // Construtor alternativo: com veículo e mensagem customizada
    public MissingPassengerException(Vehicle vehicle, String message) {
        super(message);
        this.vehicle = vehicle;
    }

    // Construtor alternativo: com veículo, mensagem e causa
    public MissingPassengerException(Vehicle vehicle, String message, Throwable cause) {
        super(message, cause);
        this.vehicle = vehicle;
    }

    // Getter
    public Vehicle getVehicle() {
        return vehicle;
    }

    // Override para facilitar debug/log
    @Override
    public String toString() {
        return "MissingPassengerException{"
                + "vehicle=" + (vehicle != null ? vehicle.getId() : "unknown")
                + ", message=" + getMessage()
                + '}';
    }
}
