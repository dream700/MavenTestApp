/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.DataAccess;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author andy
 */
public class HistoryrecordTableCell extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int col) {

        Component c = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, col);
            Integer hour = Integer.valueOf(table.getModel().getValueAt(row, col).toString());

            if (table.getModel().getValueAt(row, 2).toString().equals("8") && table.getModel().getValueAt(row, 4).toString().equals("2") && (hour > 12)) {
                 c.setForeground(Color.RED);
            }
            else {
                c.setForeground(Color.BLACK);
            }
        return c;

    }
}
