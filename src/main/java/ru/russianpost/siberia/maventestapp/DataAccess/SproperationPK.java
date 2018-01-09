/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.DataAccess;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Andrey.Isakov
 */
@Embeddable
public class SproperationPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idtype")
    private int idtype;
    @Basic(optional = false)
    @Column(name = "idattr")
    private int idattr;

    public SproperationPK() {
    }

    public SproperationPK(int idtype, int idattr) {
        this.idtype = idtype;
        this.idattr = idattr;
    }

    public int getIdtype() {
        return idtype;
    }

    public void setIdtype(int idtype) {
        this.idtype = idtype;
    }

    public int getIdattr() {
        return idattr;
    }

    public void setIdattr(int idattr) {
        this.idattr = idattr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idtype;
        hash += (int) idattr;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SproperationPK)) {
            return false;
        }
        SproperationPK other = (SproperationPK) object;
        if (this.idtype != other.idtype) {
            return false;
        }
        if (this.idattr != other.idattr) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.russianpost.siberia.maventestapp.DataAccess.SproperationPK[ idtype=" + idtype + ", idattr=" + idattr + " ]";
    }
    
}
