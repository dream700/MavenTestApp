/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.DataAccess;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author andy
 */
public class HistoryrecordModel extends AbstractTableModel {

    private final List<Historyrecord> historyrecords;
    private final String[] columnNames = new String[]{
        "Индекс", "Дата", "Тип ID", "Операция", "Атр ID", "Атрибут", "Часов"
    };
    private final Class[] columnClass = new Class[]{
        String.class, Date.class, Integer.class, String.class, Integer.class, String.class, Integer.class
    };

    public HistoryrecordModel(List<Historyrecord> historyrecords) {
        this.historyrecords = historyrecords;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    @Override
    public int getRowCount() {
        return historyrecords.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Historyrecord row = historyrecords.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getOperationAddressIndex();
            case 1:
                return row.getOperDate();
            case 2:
                return row.getOperTypeID();
            case 3:
                return row.getOperTypeName();
            case 4:
                return row.getOperAttrID();
            case 5:
                return row.getOperAttrName();
            case 6:
                return row.getOperatonDelta();
            default:
                break;
        }
        return null;
    }
}

