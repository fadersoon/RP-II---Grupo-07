package luxusproject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Image;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {
    // classe concreta para poder instanciar um Veículo para os testes.
    private static class TestVehicle extends Vehicle {
        private Passenger currentPassenger;

        public TestVehicle(LuxCompany company, Location location) {
            super(company, location);
            this.currentPassenger = null;
        }
        // Implementações mínimas para os métodos abstratos
        @Override public void act() { /* não testado aqui */ }
        @Override public Image getImage() { return null; }
        @Override public int getCapacity() { return 1; }
        // implemento do pickup para o teste
        @Override
        public void pickup(Passenger passenger) {
            this.currentPassenger = passenger;
            setOccupied(true);
        }
        @Override
        public void offloadPassenger() {
            this.currentPassenger = null;
            setOccupied(false);
        }
    }
    private LuxCompany testCompany;
    private Location initialLocation;
    private Vehicle testVehicle;

    @BeforeEach
    void setUp() {
        // Usamos as classes reais do projeto
        City city = new City();
        testCompany = new LuxCompany(city);
        initialLocation = new Location(10, 20);
        // Reseta o contador estático para que os testes de ID sejam previsíveis
        Vehicle.nextId = 1;
        testVehicle = new TestVehicle(testCompany, initialLocation);
    }

    @Test
    void testarEstadoInicialAposCriacao() {
        assertNull(testVehicle.getDestination(), "Um veículo novo não deve ter destino.");
        assertTrue(testVehicle.isFree(), "Um veículo novo deve estar livre (isFree() == true).");
        assertSame(initialLocation, testVehicle.getLocation(), "A localização inicial deve ser a mesma passada no construtor.");
        assertSame(testCompany, testVehicle.getCompany(), "A companhia deve ser a mesma passada no construtor.");
    }

    @Test
    void testarSettersEGetters() {
        Location newLocation = new Location(30, 40);
        Location newDestination = new Location(50, 60);

        testVehicle.setLocation(newLocation);
        testVehicle.setDestination(newDestination);

        assertEquals(newLocation, testVehicle.getLocation(),"o veículo deve ser criado na newLocation");
        assertEquals(newDestination, testVehicle.getDestination(), "o veículo deve ser criado na newDestination");
    }

    @Test
    void testarLogicaDeOcupacao() {
        assertTrue(testVehicle.isFree(), "o veículo deve estar livre no estado inicial");

        testVehicle.setOccupied(true);
        assertFalse(testVehicle.isFree(), "Deveria estar ocupado após setOccupied(true).");

        testVehicle.setOccupied(false);
        assertTrue(testVehicle.isFree(), "Deveria estar livre após setOccupied(false).");
    }

    @Test
    void testarGeracaoDeIdSequencial() {
        Vehicle vehicle2 = new TestVehicle(testCompany, initialLocation);
        Vehicle vehicle3 = new TestVehicle(testCompany, initialLocation);

        assertEquals("TestVehicle 1", testVehicle.getId(),"Deve ser o número 1");
        assertEquals("TestVehicle 2", vehicle2.getId(), "Deve ser o número 2");
        assertEquals("TestVehicle 3", vehicle3.getId(), "Deve ser o número 3");
    }

    @Test
    void testarMetodoToString() {
        String expectedString = "TestVehicle 1 at 10,20";
        String actualString = testVehicle.toString();
        assertEquals(expectedString, actualString, "Deveria aparecer a String esperada");
    }


    @Test
    void testarMetodoPickup() {
        assertTrue(testVehicle.isFree(), "Veículo deveria iniciar livre.");
        Passenger passenger = new Passenger(new Location(10, 20), new Location(30, 30));

        testVehicle.pickup(passenger);
        assertFalse(testVehicle.isFree(), "Veículo deveria estar ocupado após o pickup.");
    }
}