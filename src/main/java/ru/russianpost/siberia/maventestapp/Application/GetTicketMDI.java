/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.Application;

import java.awt.Cursor;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.russianpost.siberia.maventestapp.DataAccess.Historyrecord;
import ru.russianpost.siberia.maventestapp.DataAccess.HistoryrecordModel;
import ru.russianpost.siberia.maventestapp.DataAccess.Ticket;

/**
 *
 * @author Andrey.Isakov
 */
public class GetTicketMDI extends javax.swing.JInternalFrame {

    String login = "hfaoUUkggxfrPJ";
    String password = "8O4OofKi4Nsz";
    Historyrecord his;
    private Ticket ticket;

    /**
     * Creates new form frGetTicketJInternalFrame
     */
    public GetTicketMDI() {
        ticket = null;
        initComponents();
    }

    /*
    Дополнительные функции
     */
    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValus = (Node) nlList.item(0);
        return nValus.getNodeValue();
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        edBarcode = new javax.swing.JTextField();
        btGetTicket = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();

        jLabel1.setText("Введите номер ШПИ");

        edBarcode.setText("Введите номер ШПИ");
        edBarcode.setToolTipText("Введите номер ШПИ");
        edBarcode.setSelectionStart(0);
        edBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edBarcodeActionPerformed(evt);
            }
        });

        btGetTicket.setText("Запросить");
        btGetTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGetTicketActionPerformed(evt);
            }
        });

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btGetTicket)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(edBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btGetTicket))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /* 
    Кривое обновление таблицы, нужен переход на TableModel
     */
    private void RefreshTable() {
    }

    private void btGetTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGetTicketActionPerformed
        this.setCursor((Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)));
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PERSISTENCE");
        EntityManager db = emf.createEntityManager();
        TypedQuery<Ticket> query = db.createNamedQuery("Ticket.findByBarcode", Ticket.class);
        query.setParameter("barcode", edBarcode.getText());
        List<Ticket> tickets = query.getResultList();
        if (tickets.isEmpty()) {
            ticket = new Ticket(edBarcode.getText());
        } else if (tickets.size() == 1) {
            ticket = tickets.get(0);
        } else {
            throw new NonUniqueResultException();
        }
        if (!ticket.isIsFinal()) {
            ticket.getHistoryrecordCollection().clear();
            SOAPRequest instance = new SOAPRequest(login, password);
            SOAPMessage soapmessage;
            try {
                soapmessage = instance.GetTicket(edBarcode.getText());
                if (soapmessage.getSOAPBody().hasFault()) {
                    System.out.println("Fault with code: " + soapmessage.getSOAPBody().getFault().getFaultCode());
                }

                Document doc = soapmessage.getSOAPBody().extractContentAsDocument();

                doc.getDocumentElement().normalize();
                System.out.println(doc.getDocumentElement().getNodeName());
                NodeList nList = doc.getElementsByTagName("ns3:OperationHistoryData");

                his = null;
                for (int i = 0; i < nList.getLength(); i++) {
                    getData(nList);
                }
                if (his instanceof Historyrecord) {
                    ticket.getHistoryrecordCollection().add(his);
                    if ((his.getOperTypeID() == 2) | ((his.getOperAttrID() == 1) | (his.getOperAttrID() == 2)) & (his.getOperTypeID() == 5)) {
                        ticket.setIsFinal(true);
                    }
                }
                db.getTransaction().begin();
                ticket.setDateFetch(new Date());
                if (ticket.isIsNewTicket()) {
                    db.persist(ticket);
                } else {
                    db.merge(ticket);
                }
                db.getTransaction().commit();
                db.close();
            } catch (SOAPException ex) {
                Logger.getLogger(GetTicketMDI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        HistoryrecordModel tm = new HistoryrecordModel((List<Historyrecord>) ticket.getHistoryrecordCollection());
        jTable.setModel(tm);
        this.setCursor((Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)));
    }//GEN-LAST:event_btGetTicketActionPerformed

    private void edBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edBarcodeActionPerformed
        btGetTicketActionPerformed(evt);
    }//GEN-LAST:event_edBarcodeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btGetTicket;
    private javax.swing.JTextField edBarcode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable;
    // End of variables declaration//GEN-END:variables
}
