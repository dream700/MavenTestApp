/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.Application;

import java.awt.Cursor;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
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
public class frGetTicketJInternalFrame extends javax.swing.JInternalFrame {

    String login = "hfaoUUkggxfrPJ";
    String password = "8O4OofKi4Nsz";
    private Ticket ticket;

    /**
     * Creates new form frGetTicketJInternalFrame
     */
    public frGetTicketJInternalFrame() {
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        em = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("PERSISTENCE-TEST").createEntityManager();
        qu = java.beans.Beans.isDesignTime() ? null : em.createQuery("SELECT h FROM Historyrecord h");
        quList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : qu.getResultList();
        jLabel1 = new javax.swing.JLabel();
        edBarcode = new javax.swing.JTextField();
        btGetTicket = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();

        jLabel1.setText("Введите номер ШПИ");

        edBarcode.setText("Введите номер ШПИ");
        edBarcode.setToolTipText("Введите номер ШПИ");

        btGetTicket.setText("Запросить");
        btGetTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGetTicketActionPerformed(evt);
            }
        });

        jTable.getTableHeader().setReorderingAllowed(false);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, quList, jTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${operationAddressIndex}"));
        columnBinding.setColumnName("Индекс");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${operDate}"));
        columnBinding.setColumnName("Дата");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${operTypeID}"));
        columnBinding.setColumnName("Тип ID");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${operTypeName}"));
        columnBinding.setColumnName("Тип операции");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${operAttrID}"));
        columnBinding.setColumnName("Атр ID");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${operAttrName}"));
        columnBinding.setColumnName("Атрибут");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${operatonDelta}"));
        columnBinding.setColumnName("Минут");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
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

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /* 
    Кривое обновление таблицы, нужен переход на TableModel
     */
    private void RefreshTable() {
        quList = qu.getResultList();
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, quList, jTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${operationAddressIndex}"));
        columnBinding.setColumnName("Индекс");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${operDate}"));
        columnBinding.setColumnName("Дата");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${operTypeID}"));
        columnBinding.setColumnName("Тип ID");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${operTypeName}"));
        columnBinding.setColumnName("Тип операции");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${operAttrID}"));
        columnBinding.setColumnName("Атр ID");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${operAttrName}"));
        columnBinding.setColumnName("Атрибут");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${operatonDelta}"));
        columnBinding.setColumnName("Минут");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
    }

    private void btGetTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGetTicketActionPerformed
        this.setCursor((Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)));
        SOAPRequest instance = new SOAPRequest(login, password);
        SOAPMessage soapmessage;
        try {
            soapmessage = instance.GetTicket(edBarcode.getText());
//            if (soapBody.hasFault()) {
//                System.out.println("Fault with code: " + soapBody.getFault().getFaultCode());
//            }

            Document doc = soapmessage.getSOAPBody().extractContentAsDocument();

            doc.getDocumentElement().normalize();
            System.out.println(doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("ns3:OperationHistoryData");

            ticket = new Ticket(edBarcode.getText());
            his = null;
            for (int i = 0; i < nList.getLength(); i++) {
                getData(nList);
            }
            if (his instanceof Historyrecord) {
                ticket.getHistoryrecordCollection().add(his);
            }
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("PERSISTENCE-TEST");
            EntityManager db = emf.createEntityManager();
            db.getTransaction().begin();
            db.persist(ticket);
            db.getTransaction().commit();
            db.close();
            RefreshTable();
        this.setCursor((Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)));            
        } catch (SOAPException ex) {
            Logger.getLogger(frGetTicketJInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btGetTicketActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btGetTicket;
    private javax.swing.JTextField edBarcode;
    private javax.persistence.EntityManager em;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable;
    private javax.persistence.Query qu;
    private java.util.List<ru.russianpost.siberia.maventestapp.DataAccess.Historyrecord> quList;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
