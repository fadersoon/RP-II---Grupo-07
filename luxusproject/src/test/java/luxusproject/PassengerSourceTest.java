package luxusproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


class PassengerSourceTest {

    private StubCity stubCity;
    private StubLuxCompany stubCompany;
    private PassengerSource passengerSource;


    private static class StubCity extends City {
        public int addItemCallCount = 0;
        public Item lastAddedItem = null;

        @Override
        public void addItem(Item item) {
            this.addItemCallCount++;
            this.lastAddedItem = item;
        }
    }

    private static class StubLuxCompany extends LuxCompany {
        public int requestPickupCallCount = 0;
        public boolean shouldSucceed = true;

        public StubLuxCompany(City city) {
            super(city);
        }

        @Override
        public boolean requestPickup(Passenger passenger) {
            this.requestPickupCallCount++;
            return this.shouldSucceed;
        }

        // AGORA ISTO VAI FUNCIONAR CORRETAMENTE
        @Override
        protected void setupVehicles() {
            // Vazio de propósito para não adicionar veículos durante os testes.
        }
    }


    @BeforeEach
    void setUp() {
        stubCity = new StubCity();
        stubCompany = new StubLuxCompany(stubCity);
    }

    @Test
    void testConstructorThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PassengerSource(null, stubCompany),
                "Deveria lançar exceção para City nulo.");

        assertThrows(NullPointerException.class, () -> new PassengerSource(stubCity, null),
                "Deveria lançar exceção para LuxCompany nula.");
    }


    @Test
    void testActDoesNothingWhenProbabilityIsLow() {
        Random predictableRandom = new Random() {
            @Override
            public double nextDouble() {
                return 0.5; // Valor > 0.06
            }
        };
        passengerSource = new PassengerSource(stubCity, stubCompany, predictableRandom);

        for (int i = 0; i < 100; i++) {
            passengerSource.act();
        }

        assertEquals(0, stubCompany.requestPickupCallCount, "requestPickup não deveria ter sido chamado.");
        assertEquals(0, stubCity.addItemCallCount, "addItem não deveria ter sido chamado.");
        assertEquals(0, passengerSource.getMissedPickups(), "Missed pickups deveria ser 0.");
    }


    @Test
    void testActCreatesPassengerAndPickupSucceeds() {
        stubCompany.shouldSucceed = true;

        final AtomicInteger callCount = new AtomicInteger(0);
        Random predictableRandom = new Random() {
            @Override
            public double nextDouble() {
                if (callCount.getAndIncrement() == 4) {
                    return 0.01; // Valor < 0.06
                }
                return 0.5;
            }
        };
        passengerSource = new PassengerSource(stubCity, stubCompany, predictableRandom);

        for (int i = 0; i < 5; i++) {
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

        final AtomicInteger callCount = new AtomicInteger(0);
        Random predictableRandom = new Random() {
            @Override
            public double nextDouble() {
                if (callCount.getAndIncrement() == 2) {
                    return 0.01; // Cria o passageiro
                }
                return 0.5; // Não cria
            }
        };
        passengerSource = new PassengerSource(stubCity, stubCompany, predictableRandom);

        for (int i = 0; i < 3; i++) {
            passengerSource.act();
        }

        assertEquals(1, stubCompany.requestPickupCallCount, "requestPickup deveria ter sido chamado uma vez.");
        assertEquals(0, stubCity.addItemCallCount, "addItem NÃO deveria ser chamado em caso de falha no pickup.");
        assertEquals(1, passengerSource.getMissedPickups(), "Missed pickups deveria ser 1 no caso de falha.");
    }
}