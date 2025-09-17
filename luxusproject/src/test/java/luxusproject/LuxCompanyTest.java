package luxusproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;



class LuxCompanyTest {

    @Test
    void testarSeACompanhiaIniciaCom4Veiculos() {

        City cidade = new City(100,100);
        LuxCompany empresa = new LuxCompany(cidade);
        assertEquals(4,empresa.getVehicles().size(),"erro");


    }

    @Test
    void testarSeRequestPickupFuncionaComVeiculoLivre() {
        City cidade = new City(100,100);
        LuxCompany empresa = new LuxCompany(cidade);

        Location locPartida = new Location(10,10);
        Location locChegada = new Location(50,50);
        Passenger passageiro = new Passenger("Jorge",locPartida,locChegada);

        boolean resultado = empresa.requestPickup(passageiro);

        assertEquals(true,resultado,"erro");


    }
}