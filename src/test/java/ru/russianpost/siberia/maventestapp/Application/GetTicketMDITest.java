/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.Application;

import java.io.File;
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

    String FILENAME;

    public GetTicketMDITest() {
        FILENAME = "C:\\Users\\Andrey.Isakov\\Documents\\NetBeansProjects\\PostofficeApp\\application\\target\\postofficeapp\\005402570056800300004.xls";
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

    @Test
    public void testPath() {
        File file = new File(FILENAME);
        if (file.exists()) {
            final File parentFolder = new File(file.getAbsolutePath()
                    .substring(0, file.getAbsolutePath().lastIndexOf(
                                    File.separator))+"\\005402570056800300004.xls");
        String f = file.getAbsolutePath();
        boolean _f1 = parentFolder.exists();
        String f1 = parentFolder.getAbsolutePath();
        f1=f1+"\\";
        }
    }

    /**
     * Test of EJBGetTicket method, of class GetTicketMDI.
     */
//    @Test
//    public void testEJBGetTicket() {
//        System.out.println("EJBGetTicket");
//        String barcode = "63010217232797";
//        GetTicketMDI instance = new GetTicketMDI();
//        instance.EJBGetTicket(barcode);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}
