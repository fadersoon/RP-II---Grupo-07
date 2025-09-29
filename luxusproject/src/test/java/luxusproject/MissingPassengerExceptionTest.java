package luxusproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Image;


class MissingPassengerExceptionTest {

    private Vehicle testVehicle;


    private static class TestVehicle extends Vehicle {
        public TestVehicle(LuxCompany company, Location location, String id) {

            super(company, location);

        }

        @Override
        public void act() {}
        @Override
        public Image getImage() { return null; }
        @Override
        public int getCapacity() { return 0; }
        @Override
        public void pickup(Passenger passenger) {}
        @Override
        public void offloadPassenger() {}
    }


    @BeforeEach
    void setUp() {

        testVehicle = new TestVehicle(null, null, "TestVehicle 123");
        Vehicle.nextId = 1;
        testVehicle = new TestVehicle(null, null, null); // ID será "TestVehicle 1"
    }


    @Test
    void testConstructorWithVehicle() {
        MissingPassengerException exception = new MissingPassengerException(testVehicle);

        assertNotNull(exception);
        assertSame(testVehicle, exception.getVehicle(), "O veículo armazenado na exceção deve ser o mesmo que foi passado.");
        assertTrue(exception.getMessage().contains(testVehicle.getId()), "A mensagem de erro deve conter o ID do veículo.");
    }


    @Test
    void testConstructorWithVehicleAndMessage() {
        String customMessage = "Passageiro não encontrado no ponto de encontro.";
        MissingPassengerException exception = new MissingPassengerException(testVehicle, customMessage);

        assertNotNull(exception);
        assertSame(testVehicle, exception.getVehicle());
        assertEquals(customMessage, exception.getMessage(), "A mensagem da exceção deve ser a mensagem customizada.");
    }


    @Test
    void testConstructorWithVehicleMessageAndCause() {
        String customMessage = "Falha ao tentar pegar o passageiro.";
        Throwable cause = new IllegalStateException("Causa raiz do problema.");
        MissingPassengerException exception = new MissingPassengerException(testVehicle, customMessage, cause);

        assertNotNull(exception);
        assertSame(testVehicle, exception.getVehicle());
        assertEquals(customMessage, exception.getMessage());
        assertSame(cause, exception.getCause(), "A causa da exceção deve ser a mesma que foi passada.");
    }


    @Test
    void testConstructorWithNullVehicle() {
        MissingPassengerException exception = new MissingPassengerException(null);

        assertNotNull(exception);
        assertNull(exception.getVehicle(), "O veículo na exceção deve ser nulo.");
        assertTrue(exception.getMessage().contains("unknown"), "A mensagem deve indicar que o veículo é 'unknown' quando ele é nulo.");
    }


    @Test
    void testToString() {
        MissingPassengerException exception = new MissingPassengerException(testVehicle);
        String exceptionString = exception.toString();

        assertTrue(exceptionString.contains("MissingPassengerException"), "toString() deve conter o nome da classe.");
        assertTrue(exceptionString.contains("vehicle=" + testVehicle.getId()), "toString() deve conter o ID do veículo.");
        assertTrue(exceptionString.contains("message=" + exception.getMessage()), "toString() deve conter a mensagem da exceção.");
    }


    @Test
    void testToStringWithNullVehicle() {
        MissingPassengerException exception = new MissingPassengerException(null, "Mensagem com veículo nulo");
        String exceptionString = exception.toString();

        assertTrue(exceptionString.contains("vehicle=unknown"), "toString() deve conter 'vehicle=unknown' para um veículo nulo.");
        assertTrue(exceptionString.contains("message=Mensagem com veículo nulo"), "toString() deve conter a mensagem correta.");
    }
}
