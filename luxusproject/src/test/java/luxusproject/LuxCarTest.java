package luxusproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LuxCarTest {

    private LuxCompany company;
    private LuxCar car;
    private Location initialLocation;
    private City city;

    @BeforeEach
    void setUp() {
        city = new City();
        company = new LuxCompany(city);
        initialLocation = new Location(10, 10);
        car = (LuxCar) company.getVehicles().get(0);
        car.setLocation(initialLocation);
    }

}