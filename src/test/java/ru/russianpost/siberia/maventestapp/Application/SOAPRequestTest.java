/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.Application;

import java.util.Collection;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.russianpost.siberia.maventestapp.DataAccess.Historyrecord;
import ru.russianpost.siberia.maventestapp.DataAccess.Ticket;
import ru.russianpost.siberia.maventestapp.DataAccess.TicketJpaController;

/**
 *
 * @author Andrey.Isakov
 */
public class SOAPRequestTest {

    String login = "hfaoUUkggxfrPJ";
    String password = "8O4OofKi4Nsz";
    private Ticket ticket;

    public SOAPRequestTest() {
        ticket = null;
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

    /*
    Дополнительные функции
     */
    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValus = (Node) nlList.item(0);
        return nValus.getNodeValue();
    }

    Historyrecord his;

    public Historyrecord getHis() {
        return his;
    }

    private static String getValue(Element element) {
        String ret = "";
        if (element.hasChildNodes()) {
            ret = element.getChildNodes().item(0).getNodeValue();
        }
        return ret;
    }

    private Object getLastElement(final Collection c) {
        final Iterator itr = c.iterator();
        Object lastElement = itr.next();
        while (itr.hasNext()) {
            lastElement = itr.next();
        }
        return lastElement;
    }

    private Element getData(NodeList nList) {
        Element retNodeList = null;
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                if ("historyRecord".equals(eElement.getLocalName())) {
                    if (his instanceof Historyrecord) {
                        ticket.getHistoryrecordCollection().add(his);
                    }
                    if (ticket.getHistoryrecordCollection().size() > 0) {
                        his = new Historyrecord(((Historyrecord) getLastElement(ticket.getHistoryrecordCollection())).getOperDate());
                    } else {
                        his = new Historyrecord();
                    }
                }
                if ("Index".equals(eElement.getLocalName()) & "DestinationAddress".equals(eElement.getParentNode().getLocalName())) {
                    his.setDestinationAddressIndex(getValue(eElement));
                }
                if ("Description".equals(eElement.getLocalName()) & "DestinationAddress".equals(eElement.getParentNode().getLocalName())) {
                    his.setDestinationaddressDescription(getValue(eElement));
                }
                if ("Index".equals(eElement.getLocalName()) & "OperationAddress".equals(eElement.getParentNode().getLocalName())) {
                    his.setOperationAddressIndex(getValue(eElement));
                }
                if ("Description".equals(eElement.getLocalName()) & "OperationAddress".equals(eElement.getParentNode().getLocalName())) {
                    his.setOperationAddressDescription(getValue(eElement));
                }
                if ("ComplexItemName".equals(eElement.getLocalName())) {
                    his.setComplexItemName(getValue(eElement));
                }
                if ("Mass".equals(eElement.getLocalName())) {
                    his.setMass(getValue(eElement));
                }
                if ("Id".equals(eElement.getLocalName()) & "OperType".equals(eElement.getParentNode().getLocalName())) {
                    his.setOperTypeID(getValue(eElement));
                }
                if ("Name".equals(eElement.getLocalName()) & "OperType".equals(eElement.getParentNode().getLocalName())) {
                    his.setOperTypeName(getValue(eElement));
                }
                if ("Id".equals(eElement.getLocalName()) & "OperAttr".equals(eElement.getParentNode().getLocalName())) {
                    his.setOperAttrID(getValue(eElement));
                }
                if ("Name".equals(eElement.getLocalName()) & "OperAttr".equals(eElement.getParentNode().getLocalName())) {
                    his.setOperAttrName(getValue(eElement));
                }
                if ("OperDate".equals(eElement.getLocalName())) {
                    his.setOperDate(getValue(eElement), false);
                }
                if ("Sndr".equals(eElement.getLocalName())) {
                    his.setSndr(getValue(eElement));
                }
                if ("Rcpn".equals(eElement.getLocalName())) {
                    his.setRcpn(getValue(eElement));
                }
                if (eElement.hasChildNodes()) {
                    getData(eElement.getChildNodes());
                }
            }
        }
        return retNodeList;
    }

    /**
     * Test of GetTicket method, of class SOAPRequest.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetTicket() throws Exception {
        System.out.println("GetTicket");
        String Ticket = "64405017729544";
        SOAPRequest instance = new SOAPRequest(login, password);
        String expResult = "644060";
        SOAPMessage result = instance.GetTicket(Ticket);

        SOAPBody soapBody = result.getSOAPBody();
        if (soapBody.hasFault()) {
            System.out.println("Fault with code: " + soapBody.getFault().getFaultCode());
            fail("Fault with code: " + soapBody.getFault().getFaultCode());
        }

        Document doc = result.getSOAPBody().extractContentAsDocument();

        doc.getDocumentElement().normalize();
        System.out.println(doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("ns3:OperationHistoryData");

        ticket = new Ticket(Ticket);
        his = null;
        for (int i = 0; i < nList.getLength(); i++) {
            getData(nList);
        }
        if (his instanceof Historyrecord) {
            ticket.getHistoryrecordCollection().add(his);
        }

        System.out.println(ticket.toString());

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PERSISTENCE-TEST");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(ticket);
        em.getTransaction().commit();
        em.close();
    }

}
