/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.russianpost.siberia.maventestapp.DataAccess;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andrey.Isakov
 */
@Entity
@Table(name = "sproperation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sproperation.findAll", query = "SELECT s FROM Sproperation s")
    , @NamedQuery(name = "Sproperation.findByIdtype", query = "SELECT s FROM Sproperation s WHERE s.sproperationPK.idtype = :idtype")
    , @NamedQuery(name = "Sproperation.findByIdattr", query = "SELECT s FROM Sproperation s WHERE s.sproperationPK.idattr = :idattr")
    , @NamedQuery(name = "Sproperation.findByNametype", query = "SELECT s FROM Sproperation s WHERE s.nametype = :nametype")
    , @NamedQuery(name = "Sproperation.findByNameattr", query = "SELECT s FROM Sproperation s WHERE s.nameattr = :nameattr")
    , @NamedQuery(name = "Sproperation.findByIsfinal", query = "SELECT s FROM Sproperation s WHERE s.isfinal = :isfinal")})
public class Sproperation implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SproperationPK sproperationPK;
    @Column(name = "nametype")
    private String nametype;
    @Column(name = "nameattr")
    private String nameattr;
    @Column(name = "isfinal")
    private Boolean isfinal;

    public Sproperation() {
    }

    public Sproperation(SproperationPK sproperationPK) {
        this.sproperationPK = sproperationPK;
    }

    public Sproperation(int idtype, int idattr) {
        this.sproperationPK = new SproperationPK(idtype, idattr);
    }

    public SproperationPK getSproperationPK() {
        return sproperationPK;
    }

    public void setSproperationPK(SproperationPK sproperationPK) {
        this.sproperationPK = sproperationPK;
    }

    public String getNametype() {
        return nametype;
    }

    public void setNametype(String nametype) {
        this.nametype = nametype;
    }

    public String getNameattr() {
        return nameattr;
    }

    public void setNameattr(String nameattr) {
        this.nameattr = nameattr;
    }

    public Boolean getIsfinal() {
        return isfinal;
    }

    public void setIsfinal(Boolean isfinal) {
        this.isfinal = isfinal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sproperationPK != null ? sproperationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sproperation)) {
            return false;
        }
        Sproperation other = (Sproperation) object;
        if ((this.sproperationPK == null && other.sproperationPK != null) || (this.sproperationPK != null && !this.sproperationPK.equals(other.sproperationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.russianpost.siberia.maventestapp.DataAccess.Sproperation[ sproperationPK=" + sproperationPK + " ]";
    }
    
}
