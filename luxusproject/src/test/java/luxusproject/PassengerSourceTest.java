package luxusproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class PassengerSourceTest {

    private StubCity stubCity;
    private StubLuxCompany stubCompany;
    private PassengerSource passengerSource;


    private class StubCity extends City {
        public int addItemCallCount = 0;
        public Item lastAddedItem = null;

        public StubCity() {
            super(100, 100);
        }

        @Override
        public void addItem(Item item) {
            this.addItemCallCount++;
            this.lastAddedItem = item;
        }
    }

    private class StubLuxCompany extends LuxCompany {
        public int requestPickupCallCount = 0;
        public Passenger lastRequestedPassenger = null;
        public boolean shouldSucceed = true;

        public StubLuxCompany(City city) {
            super(city);
        }

        @Override
        public boolean requestPickup(Passenger passenger) {
            this.requestPickupCallCount++;
            this.lastRequestedPassenger = passenger;
            return this.shouldSucceed;
        }
    }


    @BeforeEach
    void setUp() {
        stubCity = new StubCity();
        stubCompany = new StubLuxCompany(stubCity);
        passengerSource = new PassengerSource(stubCity, stubCompany);
    }

    @Test
    void testConstructorThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new PassengerSource(null, stubCompany);
        }, "Deveria lançar exceção para City nulo.");

        assertThrows(NullPointerException.class, () -> {
            new PassengerSource(stubCity, null);
        }, "Deveria lançar exceção para LuxCompany nula.");
    }


    @Test
    void testActDoesNothingWhenProbabilityIsLow() {

        for (int i = 0; i < 7; i++) {
            passengerSource.act();
        }

        assertEquals(0, stubCompany.requestPickupCallCount, "requestPickup não deveria ter sido chamado.");
        assertEquals(0, stubCity.addItemCallCount, "addItem não deveria ter sido chamado.");
        assertEquals(0, passengerSource.getMissedPickups(), "Missed pickups deveria ser 0.");
    }


    @Test
    void testActCreatesPassengerAndPickupSucceeds() {

        stubCompany.shouldSucceed = true;
        for (int i = 0; i < 8; i++) {
            passengerSource.act();
        }


        assertEquals(1, stubCompany.requestPickupCallCount, "requestPickup deveria ter sido chamado uma vez.");
        assertEquals(1, stubCity.addItemCallCount, "addItem deveria ter sido chamado uma vez.");
        assertEquals(0, passengerSource.getMissedPickups(), "Missed pickups deveria ser 0 no caso de sucesso.");
        assertNotNull(stubCity.lastAddedItem, "Um passageiro deveria ter sido adicionado à cidade.");
        assertTrue(stubCity.lastAddedItem instanceof Passenger, "O item adicionado deveria ser um Passageiro.");
    }

    @Test
    void testActCreatesPassengerAndPickupFails() {

        stubCompany.shouldSucceed = false;
        for (int i = 0; i < 8; i++) {
            passengerSource.act();
        }


        assertEquals(1, stubCompany.requestPickupCallCount, "requestPickup deveria ter sido chamado uma vez.");
        assertEquals(0, stubCity.addItemCallCount, "addItem NÃO deveria ser chamado em caso de falha no pickup.");
        assertEquals(1, passengerSource.getMissedPickups(), "Missed pickups deveria ser 1 no caso de falha.");
    }
}
