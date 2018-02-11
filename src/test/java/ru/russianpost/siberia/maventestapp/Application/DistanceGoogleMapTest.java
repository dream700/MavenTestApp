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
public class DistanceGoogleMapTest {

    public DistanceGoogleMapTest() {
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
     * Test of GetDistance method, of class DistanceGoogleMap.
     */
    @Test
    public void testGetDistance() throws Exception {
        double EARTH_RADIUS = 6371.; // Радиус Земли
        System.out.println("GetDistance");
        String[] args = (new String[]{"Россия, Москва, улица Поклонная, 12", "Россия, Москва, станция метро Парк Победы"});
        double expResult = 9.746281621496951E-5;
        double result = DistanceGoogleMap.GetDistance(args);
        System.out.println("distance: " + result ); // получаем расстояние в километрах
        assertEquals(expResult, result, 0.0);
    }

}
