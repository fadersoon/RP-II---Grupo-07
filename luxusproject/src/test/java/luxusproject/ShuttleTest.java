package luxusproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

class ShuttleTest {

    private LuxCompany company;
    private City city;
    private Shuttle shuttle;
    private Location initialLocation;

    @BeforeEach
    void setUp() {
        city = new City();
        company = new LuxCompany(city);
        initialLocation = new Location(10, 10);

        // Encontra o Shuttle na lista de veiculos
        shuttle = (Shuttle) company.getVehicles().stream()
                .filter(v -> v instanceof Shuttle)
                .findFirst()
                .orElse(null);

        assertNotNull(shuttle, "Nenhum Shuttle foi encontrado na frota para ser testado.");

        // Define uma localização inicial conhecida para o Shuttle, nesse caso 10, 10
        shuttle.setLocation(initialLocation);
    }


    @Test
    @DisplayName("Um shuttle recém-criado deve estar livre e na localização correta")
    void shuttleNovoDeveTerEstadoInicialCorreto() {
        assertTrue(shuttle.isFree(), "Um shuttle novo deveria estar livre.");
        assertNull(shuttle.getDestination(), "Um shuttle novo não deveria ter um destino.");
        assertEquals(initialLocation, shuttle.getLocation(), "A localização inicial do shuttle está incorreta.");
    }


    @Test
    @DisplayName("isFree() deve retornar true quando não há passageiros embarcados ou atribuídos")
    void isFreeDeveSerVerdadeiroParaShuttleVazio() {
        assertTrue(shuttle.isFree(), "isFree() deveria retornar true para um shuttle sem passageiros ou atribuições.");
    }

    @Test
    @DisplayName("isFree() deve retornar false quando a capacidade máxima é atingida")
    void isFreeDeveSerFalsoParaShuttleLotado() {
        // Atribui 10 passageiros para lotar o shuttle
        for (int i = 0; i < shuttle.getCapacity(); i++) {
            Passenger p = new Passenger(new Location(i, i), new Location(i+1, i+1));
            company.requestPickup(p);
        }

        assertFalse(shuttle.isFree(), "isFree() deveria retornar false quando o shuttle atinge a capacidade máxima.");
    }

    @Test
    @DisplayName("pickup() deve adicionar um passageiro e seu destino")
    void pickupDeveAdicionarPassageiroEDestino() {
        Passenger passenger = new Passenger(new Location(1, 1), new Location(2, 2));
        shuttle.pickup(passenger);

        // Lista é privada, mas deve ser atualizada após acionar o metodo pickup
        assertEquals(passenger.getDestination(), shuttle.getDestination(), "O destino do shuttle não foi atualizado após o pickup.");
    }

    @Test
    @DisplayName("offloadPassenger() deve notificar a companhia sobre a chegada")
    void offloadPassengerDeveNotificarCompanhia() {

        Passenger passenger = new Passenger(new Location(1, 1), new Location(2, 2));
        assertDoesNotThrow(() -> shuttle.offloadPassenger(passenger));
    }

    @Test
    @DisplayName("setPickupLocation() deve definir o destino se o shuttle estiver ocioso")
    void setPickupLocationDeveDefinirDestino() {
        assertNull(shuttle.getDestination(), "O shuttle deveria iniciar sem destino.");

        Location pickupLocation = new Location(20, 20);
        shuttle.setPickupLocation(pickupLocation);

        assertEquals(pickupLocation, shuttle.getDestination(), "setPickupLocation falhou em definir o destino do shuttle.");
    }

    // Mover até o passageiro
    @Test
    @DisplayName("act() deve mover o shuttle em direção ao destino")
    void actDeveMoverShuttleQuandoTemDestino() {
        Location destination = new Location(15, 15);
        shuttle.setDestination(destination);

        shuttle.act();

        assertNotEquals(initialLocation, shuttle.getLocation(), "O shuttle deveria ter se movido.");
        assertEquals(new Location(11, 11), shuttle.getLocation(), "O shuttle não se moveu para a localização correta.");
    }

    @Test
    @DisplayName("act() deve embarcar um passageiro ao chegar no local de pickup")
    void actDeveEmbarcarPassageiroNaChegadaAoPickup() {
        Location pickupLocation = new Location(11, 11);
        Passenger passenger = new Passenger(pickupLocation, new Location(20, 20));
        company.requestPickup(passenger); // Atribui o passageiro ao shuttle

        shuttle.setLocation(new Location(10, 10)); // A um passo do local
        shuttle.setDestination(pickupLocation);

        // Adiciona o passageiro a cidade para que possa ser removido
        city.addItem(passenger);

        shuttle.act();

        // Verifica se chegou
        assertEquals(pickupLocation, shuttle.getLocation());

        // Passageiro foi removido da cidade
        assertFalse(city.getItems().contains(passenger), "O passageiro não foi embarcado (removido da cidade).");
    }

    @Test
    @DisplayName("act() deve desembarcar o passageiro ao chegar no destino final")
    void actDeveDesembarcarPassageiroNaChegadaAoDestino() {
        Location destination = new Location(11, 11);
        Passenger passenger = new Passenger(new Location(1, 1), destination);

        shuttle.pickup(passenger);
        shuttle.setLocation(new Location(10, 10)); // A um passo do destino

        shuttle.setDestination(destination);

        shuttle.act();

        assertEquals(destination, shuttle.getLocation(), "O shuttle não chegou ao destino final.");
        // Verifica se o status do passageiro foi atualizado para ARRIVED
        assertEquals(Passenger.PassengerStatus.ARRIVED, passenger.getStatus(), "O status do passageiro não foi atualizado para ARRIVED.");
    }

    @Test
    @DisplayName("getCapacity() deve retornar o valor máximo de passageiros")
    void getCapacityDeveRetornarValorCorreto() {
        assertEquals(10, shuttle.getCapacity(), "A capacidade do shuttle não é a esperada.");
    }

    @Test
    @DisplayName("getImage() deve retornar a imagem correta com e sem passageiros")
    void getImageDeveRetornarImagemCorreta() {
        Image emptyImage = shuttle.getImage();
        assertNotNull(emptyImage);

        // Embarca um passageiro
        shuttle.pickup(new Passenger(new Location(1,1), new Location(2,2)));
        Image passengerImage = shuttle.getImage();
        assertNotNull(passengerImage);

        // Atualmente sao imagens iguais pra onibus vazio e com passageiro
        // Caso modificado para imagens diferentes, o teste devera ser mudado pra "assertNotEquals"
        assertEquals(emptyImage, passengerImage, "A imagem deveria ser a mesma com ou sem passageiros, conforme a implementação atual.");
    }
}