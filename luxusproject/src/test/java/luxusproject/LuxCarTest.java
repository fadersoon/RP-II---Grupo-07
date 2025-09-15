/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package luxusproject;

import java.awt.Image;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Lenovo
 */
public class LuxCarTest {
    
    public LuxCarTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of act method, of class LuxCar.
     */
    @Test
    public void testAct() {
        System.out.println("act");
        LuxCar instance = null;
        instance.act();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isFree method, of class LuxCar.
     */
    @Test
    public void testIsFree() {
        System.out.println("isFree");
        LuxCar instance = null;
        boolean expResult = false;
        boolean result = instance.isFree();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getImage method, of class LuxCar.
     */
    @Test
    public void testGetImage() {
        System.out.println("getImage");
        LuxCar instance = null;
        Image expResult = null;
        Image result = instance.getImage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pickup method, of class LuxCar.
     */
    @Test
    public void testPickup() {
        System.out.println("pickup");
        Passenger passenger = null;
        LuxCar instance = null;
        instance.pickup(passenger);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of offloadPassenger method, of class LuxCar.
     */
    @Test
    public void testOffloadPassenger() {
        System.out.println("offloadPassenger");
        LuxCar instance = null;
        instance.offloadPassenger();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
