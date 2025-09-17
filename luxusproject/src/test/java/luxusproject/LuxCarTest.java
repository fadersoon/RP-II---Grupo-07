package luxusproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LuxCarTest {

    private LuxCompany company;
    private LuxCar car;
    private Location initialLocation;
    private City city;

    @BeforeEach
    void setUp() {
        city = new City();
        company = new LuxCompany(city);
        initialLocation = new Location(10, 10);

        car = (LuxCar) company.getVehicles().get(0);
        car.setLocation(initialLocation);
    }

    @Test
    @DisplayName("Um carro recém-criado deve estar livre e na localização inicial")
    void carroNovoDeveTerInicializacaoCorreta() {
        assertTrue(car.isFree(), "Um carro novo deveria estar livre.");
        assertNull(car.getPassenger(), "Um carro novo não deveria ter passageiro.");
        assertEquals(initialLocation, car.getLocation(), "A localização inicial está incorreta.");
        assertEquals(0, car.getIdleTime(), "O tempo ocioso inicial deveria ser 0.");
    }

    @Test
    @DisplayName("Deve embarcar um passageiro corretamente")
    void pickupDeveMudarEstadoParaOcupado() {
        Passenger passenger = new Passenger(new Location(10, 10), new Location(20, 20));

        car.pickup(passenger);

        // Verifica se o carro não está mais livre.
        assertFalse(car.isFree(), "O carro deveria estar ocupado após o embarque.");

        // Verifica se o passageiro a bordo é o mesmo que embarcamos.
        assertEquals(passenger, car.getPassenger(), "O passageiro a bordo não é o correto.");
    }

    /* Testa se o método offloadPassenger() reverte o carro para o estado "livre",
      removendo o passageiro e o destino */
    @Test
    @DisplayName("Deve desembarcar um passageiro e ficar livre")
    void offloadPassenger_deveLimparEstadoDoCarro() {
        Passenger passenger = new Passenger(new Location(10, 10), new Location(20, 20));
        car.pickup(passenger);
        car.setDestination(passenger.getDestination());

        assertFalse(car.isFree(), "A preparação do teste falhou: o carro deveria estar ocupado.");

        car.offloadPassenger();

        // Verifica se o estado do carro foi limpo corretamente.
        assertTrue(car.isFree(), "O carro deveria estar livre após desembarcar o passageiro.");
        assertNull(car.getPassenger(), "O carro não deveria ter passageiro após o desembarque.");
        assertNull(car.getDestination(), "O carro não deveria ter destino após o desembarque.");
    }
    
    // ... (depois dos testes anteriores)

    
     // Testa o cenário de movimento. 
     // Verifica se, ao ter um destino, o carro se move um passo em sua direção.
    @Test
    @DisplayName("act() deve mover o carro em direção ao destino")
    void actDeveMoverCarroQuandoTemDestino() {
        // Carro um destino diferente de sua localização atual.
        Location destination = new Location(15, 15);
        car.setDestination(destination);
        Location locationBeforeAct = car.getLocation(); // Guarda posição original
        
        car.act();

        // Verifica se a localização mudou.
        assertNotEquals(locationBeforeAct, car.getLocation(), "O carro deveria ter se movido.");
        
        // Verifica se ele se moveu para o local esperado(1 passo na diagonal).
        assertEquals(new Location(11, 11), car.getLocation(), "O carro não se moveu para a localização correta.");
    }

     // Testa o cenário de ociosidade.
     // Verifica se, sem um destino, o carro incrementa seu tempo ocioso e não se move.
    @Test
    @DisplayName("act() deve incrementar o tempo ocioso quando não há destino")
    void actDeveIncrementarIdleTimeQuandoOcioso() {
        // O carro já começa sem destino no nosso setUp().
        int idleTimeBefore = car.getIdleTime();

        car.act();

        assertEquals(idleTimeBefore + 1, car.getIdleTime(), "O tempo ocioso deveria ter sido incrementado.");
        assertEquals(initialLocation, car.getLocation(), "O carro ocioso não deveria se mover.");
    }
    
    // ... (depois dos testes anteriores)

   
     // Testa o cenário de chegada.
    // Coloca o carro a um passo do destino, para que o método seja chamado somente uma vez, e verifica se o act() finaliza a viagem.
    @Test
    @DisplayName("act() deve desembarcar o passageiro ao chegar no destino")
    void actDeveDesembarcarPassageiroAoChegarNoDestino() {
   
        Location destination = new Location(15, 15);
        Passenger passenger = new Passenger(new Location(0, 0), destination);
        
        Location oneStepAway = new Location(14, 14);
        car.setLocation(oneStepAway);
        
        // Embarca o passageiro e define o destino.
        car.pickup(passenger);
        car.setDestination(destination);
        
        // Garante que o carro está ocupado antes da ação.
        assertFalse(car.isFree(), "Preparação falhou: o carro deveria estar ocupado.");

        car.act();

        // Verifica se o carro chegou ao local correto.
        assertEquals(destination, car.getLocation(), "O carro não chegou ao destino final.");
        // Verifica se, após a chegada, o carro ficou livre.
        assertTrue(car.isFree(), "O carro deveria ficar livre após a chegada.");
        // Verifica se o passageiro foi de fato desembarcado.
        assertNull(car.getPassenger(), "O carro ainda tem um passageiro após a chegada.");
    }

}
