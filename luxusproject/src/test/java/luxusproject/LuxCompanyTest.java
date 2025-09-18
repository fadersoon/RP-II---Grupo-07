package luxusproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class LuxCompanyTest {

    private LuxCompany empresa;
    private City cidade;

    @BeforeEach

    void setUp(){
        this.cidade = new City(100,100);
        this.empresa = new LuxCompany(this.cidade);
    }


    @Test
    void testarSeACompanhiaIniciaCom4Veiculos() {

        assertEquals(4,empresa.getVehicles().size(),"erro");


    }

    @Test
    void testarSeRequestPickupFuncionaComVeiculoLivre() {

        Location locPartida = new Location(10,10);
        Location locChegada = new Location(50,50);
        Passenger passageiro = new Passenger("Jorge",locPartida,locChegada);

        boolean resultado = empresa.requestPickup(passageiro);

        assertTrue(resultado,"Erro");


    }

    @Test
    void testarSeRequestPickupFalhaQuandoTodosVeiculosEstaoOcupados(){

        for(int i = 0; i < 4; i++) {

            Passenger passageiroFantasma = new Passenger("Passenger" + i ,new Location(0,i),
                    new Location(1,i));
            empresa.requestPickup(passageiroFantasma);
        }
        Passenger passageiroAzarado = new Passenger("azarado", new  Location(10,10),
                new Location(50,50));

        boolean resultado = empresa.requestPickup(passageiroAzarado);

        assertFalse(resultado,"O pedido deveria ter sido rejeitado, pois não há veículos livres.");

    }
}