/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.Application;

import javax.xml.soap.SOAPElement;

/**
 *
 * @author Andrey.Isakov
 */
public class Barcodes {

    public Barcodes(String barcode) {
        this.barcode = barcode;
    }
    String barcode;
    SOAPElement item;
}
