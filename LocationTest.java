package luxus;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    @Test
    void testCriacaoEGetters() {

        Location loc = new Location(-15, -30);

        assertEquals(-15, loc.getX(), "O getter de X retornou o valor errado.");
        assertEquals(-30, loc.getY(), "O getter de Y retornou o valor errado.");
    }
}
    
     