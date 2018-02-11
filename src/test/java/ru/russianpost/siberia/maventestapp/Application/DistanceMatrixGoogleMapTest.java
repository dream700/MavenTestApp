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
public class DistanceMatrixGoogleMapTest {

    public DistanceMatrixGoogleMapTest() {
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
     * Test of GetDistanceMatrix method, of class DistanceMatrixGoogleMap.
     */
    @Test
    public void testGetDistanceMatrix() throws Exception {
        System.out.println("GetDistanceMatrix");
        String[] AddrFrom = (new String[]{"Россия, Новосибирск, улица Семьи Шамшиных, 58"});
        String[] waypoints = (new String[] {"Россия, Новосибирск, улица Челюскинцев, 9","Россия, Новосибирск, улица Гоголя, 13"});
        String[] AddrDest = (new String[]{"Россия, Новосибирск, улица Ленина, 5"});
        String expResult = "4,3 км";
        String[] result = DistanceMatrixGoogleMap.GetDistanceMatrix(AddrFrom,waypoints, AddrDest);
        System.out.println(result[0] + "\n" + result[1] + "\n" + result[2]);
        assertEquals(expResult, result[1]);
    }

}
