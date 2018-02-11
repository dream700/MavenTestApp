/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.Application;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andy
 */
public class DirectionGoogleMapTest {
    
    public DirectionGoogleMapTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of GetDirection method, of class DirectionGoogleMap.
     */
    @Test
    public void testGetDirection() throws Exception {
        System.out.println("GetDirection");
        String[] args = (new String[] {"Россия, Новосибирск, улица Семьи Шамшиных, 58","Россия, Новосибирск, улица Ленина, 5"});
        String[] waypoints = (new String[] {"Россия, Новосибирск, улица Гоголя, 13","Россия, Новосибирск, улица Челюскинцев, 9"});
        String[] expResult = (new String[] {"0,9 км","2 мин."});
        String[] result = DirectionGoogleMap.GetDirection(args,waypoints);
        System.out.println(result[0] + "\n" + result[1]);
        assertArrayEquals(expResult, result);
    }
    
}
