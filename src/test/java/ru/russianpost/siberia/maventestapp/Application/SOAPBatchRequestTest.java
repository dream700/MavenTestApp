/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.Application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.russianpost.siberia.maventestapp.DataAccess.Historyrecord;
import ru.russianpost.siberia.maventestapp.DataAccess.Ticket;

/**
 *
 * @author Andrey.Isakov
 */
public class SOAPBatchRequestTest {

    String login = "hfaoUUkggxfrPJ";
    String password = "8O4OofKi4Nsz";
    ArrayList<Ticket> tickets;
    Ticket ticket;

    public SOAPBatchRequestTest() {
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
        Attr attr = eElement.getAttributeNode(sTag);
        return attr.getNodeValue();
//        if (eElement.getElementsByTagName(sTag).getLength() > 0) {
//            NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
//            Node nValus = (Node) nlList.item(0);
//            Node nValus = eElement.getElementsByTagName(sTag).item(0);
//            return nValus.getNodeValue();
//        } else {
//            return "";
//        }
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
                if ("ns3:Item".equals(eElement.getNodeName())) {
                    if (ticket instanceof Ticket) {
                        tickets.add(ticket);
                    }
                    String barcode = getTagValue("Barcode", eElement);
                    ticket = new Ticket(barcode);
                }
                if ("Operation".equals(eElement.getLocalName())) {
                    if (ticket.getHistoryrecordCollection().size() > 0) {
                        his = new Historyrecord(((Historyrecord) getLastElement(ticket.getHistoryrecordCollection())).getOperDate());
                    } else {
                        his = new Historyrecord();
                    }
                    his.setOperTypeID(getTagValue("OperTypeID", eElement));
                    his.setOperTypeName(getTagValue("OperName", eElement));
                    his.setOperAttrID(getTagValue("OperCtgID", eElement));
                    his.setOperDate(getTagValue("DateOper", eElement), true);
                    his.setOperationAddressIndex(getTagValue("IndexOper", eElement));
                    ticket.getHistoryrecordCollection().add(his);
                }
                if (eElement.hasChildNodes()) {
                    getData(eElement.getChildNodes());
                }
            }
        }
        return retNodeList;
    }

    /**
     * Test of GetTicket method, of class SOAPBatchRequest.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetTicket() throws Exception {
        System.out.println("GetTicket");
        ArrayList<Barcodes> barcodes = new ArrayList<>();
        barcodes.add(new Barcodes("63010217306573"));
        barcodes.add(new Barcodes("63010217306573"));
        barcodes.add(new Barcodes("63010217306580"));
        barcodes.add(new Barcodes("63010217306832"));
        barcodes.add(new Barcodes("63010217306849"));
        barcodes.add(new Barcodes("63010217306856"));
        barcodes.add(new Barcodes("63010217307273"));
        barcodes.add(new Barcodes("63010217307280"));
        SOAPBatchRequest instance = new SOAPBatchRequest(login, password);
//        SOAPMessage result = instance.GetTicket(barcodes);
        SOAPMessage result = null;

        SOAPBody soapBody = result.getSOAPBody();
        if (soapBody.hasFault()) {
            System.out.println("Fault with code: " + soapBody.getFault().getFaultCode());
            fail("Fault with code: " + soapBody.getFault().getFaultCode());
        }

        Source sourceContent = result.getSOAPPart().getContent();
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult r_out = new StreamResult(System.out);
        t.transform(sourceContent, r_out);

        Document doc = result.getSOAPBody().extractContentAsDocument();

        doc.getDocumentElement().normalize();
//        System.out.println(doc.getDocumentElement().getNodeName());

//        assertEquals(expResult, result);
        barcodes.clear();
    }

    /**
     * Test of GetResponseByTicket method, of class SOAPBatchRequest.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetResponseByTicket() throws Exception {
        System.out.println("GetResponseByTicket");
        String TicketReq = "20171228080820310HFAOUUKGGXFRPJ";
        SOAPBatchRequest instance = new SOAPBatchRequest(login, password);
        SOAPMessage result = instance.GetResponseByTicket(TicketReq);

        SOAPBody soapBody = result.getSOAPBody();
        if (soapBody.hasFault()) {
            System.out.println("Fault with code: " + soapBody.getFault().getFaultCode());
            fail("Fault with code: " + soapBody.getFault().getFaultCode());
        }

        Document doc = result.getSOAPBody().extractContentAsDocument();

        doc.getDocumentElement().normalize();
        System.out.println(doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("ns2:answerByTicketResponse");

        tickets = new ArrayList<>();

        for (int i = 0; i < nList.getLength(); i++) {
            getData(nList);
        }
        if (ticket instanceof Ticket) {
            tickets.add(ticket);
        }

        System.out.println(tickets.toString());
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PERSISTENCE-TEST");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        tickets.forEach((tc) -> {
            em.persist(tc);
        });
        em.getTransaction().commit();
        em.close();
        tickets.clear();

    }

}
