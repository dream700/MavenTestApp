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
public class GeocodingGoogleMapTest {
    
    public GeocodingGoogleMapTest() {
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
     * Test of GetGeocoding method, of class GeocodingGoogleMap.
     */
    @Test
    public void testGetGeocoding() throws Exception {
        System.out.println("GetGeocoding");
        String address = "Россия, Москва, улица Поклонная, 12";
        double[] expResult = (new double[]{55.735961,37.527410});
        double[] result = GeocodingGoogleMap.GetGeocoding(address);
        System.out.println(address);
        System.out.println(String.format("%f,%f", result[0], result[1]));// итоговая широта и долгота
        assertArrayEquals(expResult, result,0);
        
    }
    
}
