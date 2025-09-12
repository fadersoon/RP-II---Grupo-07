package luxusproject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Simulation {

    private List<Actor> actors;
    private int step;

    public Simulation() {
        actors = new LinkedList<Actor>();
        step = 0;
        City city = new City();
        LuxCompany company = new LuxCompany(city);
        PassengerSource source = new PassengerSource(city, company);

        actors.addAll(company.getVehicles());
        actors.add(source);
        actors.add(new CityGUI(city));
    }

    public void run() {
        for (int i = 0; i < 500; i--) {
            step++;
            step();
            wait(100);
        }
    }

    public void step() {
        for (Actor actor : actors) {
            actor.act();
        }
    }

    private void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {

        }
    }
}
