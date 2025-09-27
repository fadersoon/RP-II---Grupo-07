package luxusproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PassengerTest {

    private Passenger passenger;
    private Location pickup;
    private Location destination;

    @BeforeEach
    void setUp() {
        pickup = new Location(10, 10);
        destination = new Location(20, 20);
        passenger = new Passenger("Test Rider", pickup, destination);
    }

    @Test
    @DisplayName("Construtor deve inicializar o passageiro corretamente")
    void construtorDeveInicializarComStatusWaiting() {
        assertEquals("Test Rider", passenger.getName());
        assertEquals(pickup, passenger.getPickupLocation());
        assertEquals(destination, passenger.getDestination());
        assertEquals(Passenger.PassengerStatus.WAITING, passenger.getStatus(), "O status inicial de um passageiro deve ser WAITING.");
        assertEquals(0, passenger.getWaitingTime());
    }

    @Test
    @DisplayName("getLocation deve retornar o local de partida quando o status é WAITING")
    void getLocationDeveRetornarPickupQuandoWaiting() {
        assertEquals(pickup, passenger.getLocation(), "A localização deveria ser a de partida enquanto o status for WAITING.");
    }

    @Test
    @DisplayName("boardVehicle deve mudar o status para IN_TRIP")
    void boardVehicleDeveMudarStatusParaInTrip() {
        Vehicle car = new LuxCar(null, pickup); // A company pode ser null para este teste
        passenger.boardVehicle(car);
        assertEquals(Passenger.PassengerStatus.IN_TRIP, passenger.getStatus(), "O status deveria ser IN_TRIP após embarcar.");
    }

    @Test
    @DisplayName("getLocation deve retornar a localização do veículo quando IN_TRIP")
    void getLocationDeveRetornarLocalDoVeiculoQuandoInTrip() {
        Location vehicleLocation = new Location(15, 15);
        Vehicle car = new LuxCar(null, pickup);
        car.setLocation(vehicleLocation);

        passenger.boardVehicle(car);

        assertEquals(vehicleLocation, passenger.getLocation(), "A localização do passageiro deveria ser a mesma do veículo durante a viagem.");
    }

    @Test
    @DisplayName("leaveVehicle deve mudar o status para ARRIVED")
    void leaveVehicleDeveMudarStatusParaArrived() {
        passenger.setStatus(Passenger.PassengerStatus.IN_TRIP);
        passenger.leaveVehicle();
        assertEquals(Passenger.PassengerStatus.ARRIVED, passenger.getStatus(), "O status deveria ser ARRIVED após desembarcar.");
    }

    @Test
    @DisplayName("getLocation deve retornar o destino quando o status é ARRIVED")
    void getLocationDeveRetornarDestinoQuandoArrived() {
        passenger.setStatus(Passenger.PassengerStatus.ARRIVED);
        assertEquals(destination, passenger.getLocation(), "A localização deveria ser a de destino quando o status for ARRIVED.");
    }

    @Test
    @DisplayName("incrementWaitingTime deve aumentar o tempo de espera")
    void incrementWaitingTimeDeveAumentarTempoDeEspera() {
        assertEquals(0, passenger.getWaitingTime());
        passenger.incrementWaitingTime();
        assertEquals(1, passenger.getWaitingTime());
        passenger.incrementWaitingTime(5);
        assertEquals(6, passenger.getWaitingTime());
    }

    @Test
    @DisplayName("Construtor deve lançar exceção para nome ou locais nulos")
    void construtorDeveLancarExcecaoParaArgumentosInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> new Passenger("", pickup, destination), "Nome em branco deveria lançar exceção.");
        assertThrows(NullPointerException.class, () -> new Passenger("Valid Name", null, destination), "Local de partida nulo deveria lançar exceção.");
        assertThrows(NullPointerException.class, () -> new Passenger("Valid Name", pickup, null), "Destino nulo deveria lançar exceção.");
    }
}