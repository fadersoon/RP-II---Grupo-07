package luxus;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void testCriacaoEGetters() {
        Location loc = new Location(15, 30);
        assertEquals(15, loc.getX(), "O getter de X retornou o valor errado.");
        assertEquals(30, loc.getY(), "O getter de Y retornou o valor errado.");
    }

    @Test
    void testConstrutorDeveLancarExcecaoParaXNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Location(-1, 10);
        });
    }

    @Test
    void testConstrutorDeveLancarExcecaoParaYNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Location(10, -1);
        });
    }
}
    
     