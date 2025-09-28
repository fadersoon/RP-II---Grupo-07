package luxusproject;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.Dimension;

import static org.junit.jupiter.api.Assertions.*;

class CityGUITest {

    private City city;
    private CityGUI cityGUI;




    @BeforeEach
    void setUp() {

        city = new City(20, 20);
        cityGUI = new CityGUI(city);

    }


    @Test
    @DisplayName("Testa se o construtor da CityGUI define o título da janela corretamente")
    void testarTituloDaJanela() {
        // Act
        String tituloAtual = cityGUI.getTitle();

        // Assert
        assertEquals("Taxiville", tituloAtual, "O título da janela deve ser 'Taxiville'.");
    }

    @Test
    @DisplayName("Testa se o construtor da CityGUI define as dimensões iniciais da janela corretamente")

    void testarDimensoesIniciaisDaJanela() {
        // Act
        Dimension dimensoesAtuais = cityGUI.getSize();

        // Assert
        assertEquals(CityGUI.CITY_VIEW_WIDTH, dimensoesAtuais.width, "A largura inicial da janela não está correta.");
        assertEquals(CityGUI.CITY_VIEW_HEIGHT, dimensoesAtuais.height, "A altura inicial da janela não está correta.");
    }

    /**
     * Testa a robustez do método act(), garantindo que ele não lance uma exceção
     * quando a cidade está vazia.
     */
    @Test
    @DisplayName("Testa a robustez do método act(), garantindo que ele não lance uma exceção quando a cidade está vazia.")
    void testarMetodoActComCidadeVazia() {

        assertDoesNotThrow(() -> {cityGUI.act();}, "O método act() não deve lançar uma exceção para uma cidade vazia.");
    }
    /**
     * Testa se o construtor adicionou corretamente o painel CityView
     * ao contentor principal (contentPane) da janela.
     */
    @Test
    @DisplayName("Testa se o construtor adicionou corretamente o painel CityView ao contentor principal (contentPane) da janela.")
    void testarSeCityViewFoiAdicionada() {
        // Pegamos os componentes que estão dentro da janela.
        int numeroDeComponentes = cityGUI.getContentPane().getComponentCount();
        java.awt.Component primeiroComponente = cityGUI.getContentPane().getComponent(0);

        assertEquals(1, numeroDeComponentes, "A janela deveria conter exatamente 1 componente.");
        assertTrue(primeiroComponente instanceof CityGUI.CityView, "O componente na janela deveria ser uma instância de CityView.");
    }
}






