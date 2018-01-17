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
 * @author Andrey.Isakov
 */
public class GetTicketMDITest {
    
    public GetTicketMDITest() {
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
     * Test of EJBGetTicket method, of class GetTicketMDI.
     */
    @Test
    public void testEJBGetTicket() {
        System.out.println("EJBGetTicket");
        String barcode = "63010217232797";
        GetTicketMDI instance = new GetTicketMDI();
        instance.EJBGetTicket(barcode);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
