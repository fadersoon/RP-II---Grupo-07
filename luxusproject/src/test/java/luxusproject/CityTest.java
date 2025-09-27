package luxusproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CityTest {

    private City city;
    private Item testItem;

    @BeforeEach
    void setUp() {
        city = new City(50, 50);
        // Usamos um passageiro como um Item genérico para o teste
        testItem = new Passenger(new Location(5, 5), new Location(10, 10));
    }

    @Test
    @DisplayName("addItem deve adicionar um item à cidade com sucesso")
    void addItemDeveFuncionarCorretamente() {
        assertTrue(city.getItems().isEmpty(), "A cidade deveria iniciar vazia.");
        city.addItem(testItem);
        assertEquals(1, city.getItems().size(), "A cidade deveria conter 1 item após a adição.");
        assertTrue(city.getItems().contains(testItem), "A cidade deveria conter o item que foi adicionado.");
    }

    @Test
    @DisplayName("addItem deve lançar exceção ao adicionar um item duplicado")
    void addItemDeveLancarExcecaoParaItemDuplicado() {
        city.addItem(testItem);
        assertThrows(IllegalArgumentException.class, () -> {
            city.addItem(testItem);
        }, "Deveria lançar uma exceção ao tentar adicionar um item que já existe.");
    }

    @Test
    @DisplayName("removeItem deve remover um item existente com sucesso")
    void removeItemDeveFuncionarCorretamente() {
        city.addItem(testItem);
        assertFalse(city.getItems().isEmpty());

        city.removeItem(testItem);
        assertTrue(city.getItems().isEmpty(), "A cidade deveria ficar vazia após a remoção do item.");
    }

    @Test
    @DisplayName("removeItem deve lançar exceção ao tentar remover um item inexistente")
    void removeItemDeveLancarExcecaoParaItemInexistente() {
        // Tenta remover um item de uma cidade vazia
        assertThrows(IllegalArgumentException.class, () -> {
            city.removeItem(testItem);
        }, "Deveria lançar uma exceção ao tentar remover um item que não está na cidade.");
    }
}