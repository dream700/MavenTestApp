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
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.russianpost.siberia.maventestapp.DataAccess.Historyrecord;
import ru.russianpost.siberia.maventestapp.DataAccess.HistoryrecordModel;
import ru.russianpost.siberia.maventestapp.DataAccess.HistoryrecordTableCell;
import ru.russianpost.siberia.maventestapp.DataAccess.Ticket;

/**
 *
 * @author Andrey.Isakov
 */
public class GetTicketMDI extends javax.swing.JInternalFrame {

    /**
     * Creates new form frGetTicketJInternalFrame
     */
    public GetTicketMDI() {
        initComponents();
    }

    @Override
    public boolean isClosed() {
        return super.isClosed(); //To change body of generated methods, choose Tools | Templates.
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

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Запрос одиночного ШПИ");

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
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btGetTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGetTicketActionPerformed
        this.setCursor((Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)));        
        try { // Call Web Service Operation
            ViewHistorySRV_Service service = new ViewHistorySRV_Service();
            ViewHistorySRV port = service.getViewHistorySRVPort();
            String barcode = edBarcode.getText();
            ViewhistoryModel tm = new ViewhistoryModel(port.findBarcode(barcode));
            jTable.setModel(tm);
            tm.fireTableDataChanged();
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
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
