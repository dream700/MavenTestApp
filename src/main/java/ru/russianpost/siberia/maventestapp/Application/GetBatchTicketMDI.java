/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.Application;

import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.russianpost.siberia.maventestapp.DataAccess.Historyrecord;
import ru.russianpost.siberia.maventestapp.DataAccess.Ticket;
import ru.russianpost.siberia.maventestapp.DataAccess.TicketReq;

/**
 *
 * @author andy
 */
public class GetBatchTicketMDI extends javax.swing.JInternalFrame {

    private final String login = "hfaoUUkggxfrPJ";
    private final String password = "8O4OofKi4Nsz";
    private Historyrecord his;
    private Ticket ticket;
    private final EntityManagerFactory emf;
    private EntityManager db;

    /**
     * Creates new form GetBatchTicketMDI
     */
    public GetBatchTicketMDI() {
        initComponents();
        emf = Persistence.createEntityManagerFactory("PERSISTENCE");
        db = emf.createEntityManager();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btFileLoad = new javax.swing.JButton();
        lbFilename = new javax.swing.JLabel();
        btRequest = new javax.swing.JButton();

        btFileLoad.setText("Загрузить");
        btFileLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFileLoadActionPerformed(evt);
            }
        });

        lbFilename.setText("Файл не выбран (загрузите .txt или Excel список)");

        btRequest.setText("Запросить");
        btRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRequestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btRequest)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btFileLoad)
                        .addGap(35, 35, 35)
                        .addComponent(lbFilename)))
                .addContainerGap(141, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btFileLoad)
                    .addComponent(lbFilename))
                .addGap(18, 18, 18)
                .addComponent(btRequest)
                .addContainerGap(182, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private static String getTagValue(String sTag, Element eElement) {
        Attr attr = eElement.getAttributeNode(sTag);
        return attr.getNodeValue();
    }

    private Object getLastElement(final Collection c) {
        final Iterator itr = c.iterator();
        Object lastElement = itr.next();
        while (itr.hasNext()) {
            lastElement = itr.next();
        }
        return lastElement;
    }

    Date last;

    private Element getData(NodeList nList) {
        Element retNodeList = null;
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                if ("ns3:Item".equals(eElement.getNodeName())) {
                    String barcode = getTagValue("Barcode", eElement);
                    ticket = db.find(Ticket.class, barcode);
                    if (ticket == null) {
                        ticket = new Ticket(barcode);
                        db.persist(ticket);
                    } else {
                        Query query = db.createQuery("delete Historyrecord where barcode = :barcode");
                        query.setParameter("barcode", ticket);
                        query.executeUpdate();
                    }
                    ticket.setDateFetch(new Date());
                    last = null;
                }
                if ("Operation".equals(eElement.getLocalName())) {
                    his = new Historyrecord(last);
                    his.setOperTypeID(getTagValue("OperTypeID", eElement));
                    his.setOperTypeName(getTagValue("OperName", eElement));
                    his.setOperAttrID(getTagValue("OperCtgID", eElement));
                    his.setOperDate(getTagValue("DateOper", eElement), true);
                    his.setOperationAddressIndex(getTagValue("IndexOper", eElement));
                    his.setBarcode(ticket);
                    if ((his.getOperTypeID() == 2) | ((his.getOperAttrID() == 1) | (his.getOperAttrID() == 2)) & (his.getOperTypeID() == 5)) {
                        ticket.setIsFinal(true);
                    }
                    last = his.getOperDate();
                    db.persist(his);
                }
                if (eElement.hasChildNodes()) {
                    getData(eElement.getChildNodes());
                }
            }
        }
        return retNodeList;
    }

    public boolean getSOAPTicketAnswer() {
        TypedQuery<TicketReq> query = db.createNamedQuery("TicketReq.findAll", TicketReq.class);
        List<TicketReq> req = query.getResultList();
        for (TicketReq ticketReq : req) {
            SOAPBatchRequest instance = new SOAPBatchRequest(login, password);
            try {
                SOAPMessage result = instance.GetResponseByTicket(ticketReq.getTicketrequest());
                SOAPBody soapBody = result.getSOAPBody();
                if (soapBody.hasFault()) {
                    Logger.getLogger(GetBatchTicketMDI.class.getName()).log(Level.SEVERE, null, "Fault with code: " + soapBody.getFault().getFaultCode());
                    return false;
                }
                Document doc = result.getSOAPBody().extractContentAsDocument();
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("ns2:answerByTicketResponse");
                db.getTransaction().begin();
                for (int i = 0; i < nList.getLength(); i++) {
                    getData(nList);
                }
                db.remove(ticketReq);
                db.getTransaction().commit();
            } catch (SOAPException ex) {
                Logger.getLogger(GetBatchTicketMDI.class.getName()).log(Level.SEVERE, null, ex);
                db.getTransaction().rollback();
                return false;
            }
        }
        return true;
    }

    /*
    Читаем из файла данные и записываем в таблицу ticket
     */
    private boolean readFromFileTickets(File file) {
        lbFilename.setText(file.getName());
        super.update(this.getGraphics());
        try {
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            db.getTransaction().begin();
            for (String line : lines) {
                Ticket tk = db.find(Ticket.class, line);
                if (tk == null) {
                    tk = new Ticket(line);
                    db.persist(tk);
                } else if (!tk.isIsFinal()) {
                    tk.setDateFetch(null);
                }
            }
            db.getTransaction().commit();
            lines.clear();
        } catch (IOException ex) {
            Logger.getLogger(GetBatchTicketMDI.class.getName()).log(Level.SEVERE, null, ex);
            db.getTransaction().rollback();
            return false;
        }
        return true;
    }

    /*Формирование и запрос пакета SOAP
     */
    public boolean getSOAPTicketRequest() {
        TypedQuery<Ticket> query = db.createNamedQuery("Ticket.findDateFetchisNull", Ticket.class);
        List<Ticket> tks;
        db.getTransaction().begin();
        while (!(tks = query.getResultList()).isEmpty()) {
            SOAPBatchRequest instance = new SOAPBatchRequest(login, password);
            SOAPMessage result;
            try {
                result = instance.GetTicket(tks);
                if (result instanceof SOAPMessage) {
                    SOAPBody soapBody = result.getSOAPBody();
                    if (soapBody.hasFault()) {
                        throw new SOAPException("Fault with code: " + soapBody.getFault().getFaultCode());
                    }
                    Document doc = result.getSOAPBody().extractContentAsDocument();
                    doc.getDocumentElement().normalize();
                    String br = doc.getElementsByTagName("value").item(0).getFirstChild().getNodeValue();
                    TicketReq tr = new TicketReq(br);
                    for (Ticket tk : tks) {
                        tk.setDateFetch(new Date());
                    }
                    db.persist(tr);
                }
            } catch (SOAPException | TransformerException ex) {
                Logger.getLogger(GetBatchTicketMDI.class.getName()).log(Level.SEVERE, null, ex);
                if (db.getTransaction().isActive()) {
                    db.getTransaction().rollback();
                }
                return false;
            }
        }
        db.getTransaction().commit();
        return true;
    }

    @Override
    public boolean isClosed() {
        db.close();
        return super.isClosed();
    }


    private void btFileLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFileLoadActionPerformed
        this.setCursor((Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)));
        JFileChooser saveFile = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "TXT & CVS Files", "txt", "cvs");
        saveFile.setFileFilter(filter);
        if (saveFile.showDialog(null, "Выберите файл") == JFileChooser.APPROVE_OPTION) {
            if (readFromFileTickets(saveFile.getSelectedFile())) {
                getSOAPTicketRequest();
            }
        }
        this.setCursor((Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)));
    }//GEN-LAST:event_btFileLoadActionPerformed

    private void btRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRequestActionPerformed
        this.setCursor((Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)));
        getSOAPTicketAnswer();
        this.setCursor((Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)));

    }//GEN-LAST:event_btRequestActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btFileLoad;
    private javax.swing.JButton btRequest;
    private javax.swing.JLabel lbFilename;
    // End of variables declaration//GEN-END:variables
}
