/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.Application;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import ru.russianpost.siberia.maventestapp.DataAccess.Ticket;

/**
 *
 * @author andy
 */
public class GetBatchTicketMDI extends javax.swing.JInternalFrame {

    /**
     * Creates new form GetBatchTicketMDI
     */
    public GetBatchTicketMDI() {
        initComponents();
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

        btFileLoad.setText("Загрузить");
        btFileLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFileLoadActionPerformed(evt);
            }
        });

        lbFilename.setText("Файл не выбран (загрузите .txt или Excel список)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btFileLoad)
                .addGap(35, 35, 35)
                .addComponent(lbFilename)
                .addContainerGap(141, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btFileLoad)
                    .addComponent(lbFilename))
                .addContainerGap(229, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btFileLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFileLoadActionPerformed
        JFileChooser saveFile = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "TXT & CVS Files", "txt", "cvs");
        saveFile.setFileFilter(filter);
        if (saveFile.showDialog(null, "Выберите файл") == JFileChooser.APPROVE_OPTION) {
            File file = saveFile.getSelectedFile();
            lbFilename.setText(file.getName());
            super.update(this.getGraphics());
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("PERSISTENCE");
            EntityManager db = emf.createEntityManager();
            TypedQuery<Ticket> query = db.createNamedQuery("Ticket.findByBarcode", Ticket.class);
            db.getTransaction().begin();
            try {
                List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
                for (String line : lines) {
                    query.setParameter("barcode", line);
                    List<Ticket> tickets = query.getResultList();
                    if (tickets.isEmpty()) {
                        Ticket ticket = new Ticket(line);
                        db.persist(ticket);
                    }
                }
                db.getTransaction().commit();
            } catch (IOException ex) {
                Logger.getLogger(GetBatchTicketMDI.class.getName()).log(Level.SEVERE, null, ex);
                db.getTransaction().rollback();
            }
            db.close();
        }
    }//GEN-LAST:event_btFileLoadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btFileLoad;
    private javax.swing.JLabel lbFilename;
    // End of variables declaration//GEN-END:variables
}