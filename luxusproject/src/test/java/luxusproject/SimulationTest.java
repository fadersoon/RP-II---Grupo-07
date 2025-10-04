package luxusproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.List;


class SimulationTest {

    @Test
    void testConstructorCreatesAndPopulatesActors() {
        try {

            Simulation simulation = new Simulation();

            Field actorsField = Simulation.class.getDeclaredField("actors");
            actorsField.setAccessible(true); // Permite o acesso a um campo privado

            @SuppressWarnings("unchecked") // Suprime o warning de cast inseguro
            List<Actor> actorsList = (List<Actor>) actorsField.get(simulation);


            assertNotNull(actorsList, "A lista de atores não deveria ser nula.");
            assertFalse(actorsList.isEmpty(), "A lista de atores deveria ser populada pelo construtor.");
            assertTrue(actorsList.size() > 1, "Deveria haver múltiplos atores na simulação.");

        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Teste falhou devido a Reflection. O campo 'actors' não foi encontrado na classe Simulation.", e);
        }
    }

    @Test
    void testStepIncrementsStepCounter() {
        try {
            Simulation simulation = new Simulation();

            simulation.step();

            Field stepField = Simulation.class.getDeclaredField("step");
            stepField.setAccessible(true);
            int stepValue = (int) stepField.get(simulation);

            assertEquals(1, stepValue, "O contador de passos deveria ser 1 após uma chamada a step().");

            simulation.step(); // Executa um segundo passo

            stepValue = (int) stepField.get(simulation);
            assertEquals(2, stepValue, "O contador de passos deveria ser 2 após duas chamadas a step().");

        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Teste falhou devido a Reflection. O campo 'step' não foi encontrado.", e);
        }
    }
}
