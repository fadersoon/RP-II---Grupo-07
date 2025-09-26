package luxusproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class LuxCompanyTest {

    private LuxCompany empresa;
    private City cidade;

    @BeforeEach
    void setUp(){
        this.cidade = new City(20,20);
        this.empresa = new LuxCompany(this.cidade);
    }


    @Test
    void testarSeACompanhiaIniciaCom4Veiculos() {
        assertEquals(4, empresa.getVehicles().size(),"A empresa deve iniciar com 4 veículos.");
    }

    @Test
    void testarSeRequestPickupFuncionaComVeiculoLivre() {
        Location locPartida = new Location(10,10);
        Location locChegada = new Location(50,50);
        Passenger passageiro = new Passenger("Jorge",locPartida,locChegada);

        boolean resultado = empresa.requestPickup(passageiro);

        assertTrue(resultado,"O pedido deveria ter sido aceito.");
    }

    @Test
    void testarSeRequestPickupFalhaQuandoTodosVeiculosEstaoOcupados(){
        int capacidadeOcupada = empresa.getVehicles().stream().mapToInt(Vehicle::getCapacity).sum();
        for(int i = 0; i < capacidadeOcupada; i++) {
            Passenger passageiroFantasma = new Passenger("Passenger" + i ,new Location(0,i), new Location(1,i));
            empresa.requestPickup(passageiroFantasma);
        }

        Passenger passageiroAzarado = new Passenger("azarado", new  Location(10,10), new Location(50,50));

        boolean resultado = empresa.requestPickup(passageiroAzarado);

        assertFalse(resultado,"O pedido deveria ter sido rejeitado, pois não há veículos livres.");
    }

    @Test
    @DisplayName("arrivedAtDestination deve mudar o status do passageiro para ARRIVED")
    void arrivedAtDestinationDeveMudarStatusDoPassageiro() {
        Passenger passageiro = new Passenger(new Location(1, 1), new Location(5, 5));
        passageiro.setStatus(Passenger.PassengerStatus.IN_TRIP);
        Vehicle veiculo = empresa.getVehicles().get(0);

        empresa.arrivedAtDestination(veiculo, passageiro);

        assertEquals(Passenger.PassengerStatus.ARRIVED, passageiro.getStatus(),
                "O status do passageiro deveria ser ARRIVED.");
    }

    @Test
    @DisplayName("requestPickup deve atribuir o veículo livre MAIS PRÓXIMO do passageiro")
    void requestPickupDeveAtribuirVeiculoLivreMaisProximo() {

        List<Vehicle> luxCars = empresa.getVehicles().stream()
                .filter(v -> v instanceof LuxCar)
                .collect(Collectors.toList());

        Vehicle carroProximo = luxCars.get(0);
        carroProximo.setLocation(new Location(12, 12));

        Vehicle carroLonge = luxCars.get(1);
        carroLonge.setLocation(new Location(50, 50));

        Passenger passageiro = new Passenger("Passageiro Teste", new Location(10, 10), new Location(20, 20));

        boolean sucesso = empresa.requestPickup(passageiro);
        assertTrue(sucesso, "O pedido deveria ter sido bem-sucedido.");

        Vehicle veiculoAtribuido = findVehicleForPassenger(passageiro);

        assertNotNull(veiculoAtribuido, "Um veículo deveria ter sido atribuído.");

        assertEquals(carroProximo, veiculoAtribuido,
                "A empresa não escolheu o veículo livre mais próximo do passageiro.");
    }

    @Test
    @DisplayName("isPickupLocation deve retornar true para um local de partida válido")
    void isPickupLocationDeveRetornarTrueParaLocalValido() {
        Location localDePartida = new Location(15, 15);
        Passenger passageiro = new Passenger(localDePartida, new Location(20, 20));
        empresa.requestPickup(passageiro);

        assertTrue(empresa.isPickupLocation(localDePartida),
                "Deveria retornar true, pois um passageiro foi registrado neste local.");
    }

    @Test
    @DisplayName("isPickupLocation deve retornar false para um local sem passageiros")
    void isPickupLocationDeveRetornarFalseParaLocalInvalido() {
        Location localVazio = new Location(99, 99);

        assertFalse(empresa.isPickupLocation(localVazio),
                "Deveria retornar false, pois não há passageiros esperando neste local.");
    }

    @Test
    @DisplayName("Empresa deve atribuir passageiro a um LuxCar se o Shuttle estiver cheio")
    void testCompanyAtribuiAoLuxCarQuandoShuttleEstaCheio() {
        Vehicle shuttle = empresa.getVehicles().stream()
                .filter(v -> v instanceof Shuttle)
                .findFirst()
                .orElse(null);
        assertNotNull(shuttle, "Shuttle não encontrado para o teste.");
        shuttle.setLocation(new Location(0, 0));

        empresa.getVehicles().stream()
                .filter(v -> v instanceof LuxCar)
                .forEach(luxCar -> luxCar.setLocation(new Location(100, 100)));

        // Ocupa o Shuttle com 10 passageiros
        for (int i = 1; i <= 10; i++) {
            empresa.requestPickup(new Passenger(new Location(i, i), new Location(i + 1, i + 1)));
        }

        assertFalse(shuttle.isFree(), "O Shuttle deveria estar ocupado após 11 atribuições.");

        Passenger eleventhPassenger = new Passenger(new Location(11, 11), new Location(12, 12));


        boolean success = empresa.requestPickup(eleventhPassenger);

        assertTrue(success, "A empresa deveria conseguir atender o 11º passageiro com um LuxCar.");

        Vehicle assignedVehicle = findVehicleForPassenger(eleventhPassenger);
        assertNotNull(assignedVehicle, "O 11º passageiro não foi atribuído a nenhum veículo.");
        assertTrue(assignedVehicle instanceof LuxCar,
                "O 11º passageiro deveria ter sido atribuído a um LuxCar, pois o Shuttle estava cheio.");
    }

    @Test
    @DisplayName("arrivedAtPickup deve embarcar passageiro e atualizar destino do veículo")
    void arrivedAtPickupDeveEmbarcarPassageiro() {
        Location localDePartida = new Location(10, 10);
        Location destinoFinal = new Location(50, 50);
        Passenger passageiro = new Passenger("Fade", localDePartida, destinoFinal);

        empresa.getCity().addItem(passageiro);
        assertTrue(empresa.getCity().getItems().contains(passageiro));

        empresa.requestPickup(passageiro);
        Vehicle veiculoAtribuido = findVehicleForPassenger(passageiro);
        assertNotNull(veiculoAtribuido);

        veiculoAtribuido.setLocation(localDePartida);

        empresa.arrivedAtPickup(veiculoAtribuido);

        // Destino do veículo tem que ser atualizado para o destino final
        assertEquals(destinoFinal, veiculoAtribuido.getDestination(),
                "O destino do veículo deveria ter sido atualizado para o destino do passageiro.");

        // Passageiro removido da imagem
        assertFalse(empresa.getCity().getItems().contains(passageiro),
                "O passageiro deveria ter sido removido da cidade após o embarque.");

        if (veiculoAtribuido instanceof LuxCar) {
            assertEquals(passageiro, ((LuxCar) veiculoAtribuido).getPassenger());
        }
    }

    /**
     Metodo auxiliar para encontrar qual veículo foi atribuído a um passageiro específico.
     Itera sobre o mapa de atribuições da empresa.
     */
    private Vehicle findVehicleForPassenger(Passenger passenger) {
        for (var entry : empresa.getAssignments().entrySet()) {
            if (entry.getValue().contains(passenger)) {
                return entry.getKey();
            }
        }
        return null;
    }
}