/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.arch.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "recieved_method")
@NamedQueries({
    @NamedQuery(name = "RecievedMethod.findAll", query = "SELECT r FROM RecievedMethod r"),
    @NamedQuery(name = "RecievedMethod.findByRecievedMethodId", query = "SELECT r FROM RecievedMethod r WHERE r.recievedMethodId = :recievedMethodId"),
    @NamedQuery(name = "RecievedMethod.findByRecievedMethodAr", query = "SELECT r FROM RecievedMethod r WHERE r.recievedMethodAr = :recievedMethodAr"),
    @NamedQuery(name = "RecievedMethod.findByRecievedMethodEn", query = "SELECT r FROM RecievedMethod r WHERE r.recievedMethodEn = :recievedMethodEn")})
public class RecievedMethod implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "recieved_method_id")
    private Integer recievedMethodId;
    @Size(max = 60)
    @Column(name = "recieved_method_ar")
    private String recievedMethodAr;
    @Size(max = 45)
    @Column(name = "recieved_method_en")
    private String recievedMethodEn;

    public RecievedMethod() {
    }

    public RecievedMethod(Integer recievedMethodId) {
        this.recievedMethodId = recievedMethodId;
    }

    public Integer getRecievedMethodId() {
        return recievedMethodId;
    }

    public void setRecievedMethodId(Integer recievedMethodId) {
        this.recievedMethodId = recievedMethodId;
    }

    public String getRecievedMethodAr() {
        return recievedMethodAr;
    }

    public void setRecievedMethodAr(String recievedMethodAr) {
        this.recievedMethodAr = recievedMethodAr;
    }

    public String getRecievedMethodEn() {
        return recievedMethodEn;
    }

    public void setRecievedMethodEn(String recievedMethodEn) {
        this.recievedMethodEn = recievedMethodEn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recievedMethodId != null ? recievedMethodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecievedMethod)) {
            return false;
        }
        RecievedMethod other = (RecievedMethod) object;
        if ((this.recievedMethodId == null && other.recievedMethodId != null) || (this.recievedMethodId != null && !this.recievedMethodId.equals(other.recievedMethodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tech.arch.entity.RecievedMethod[ recievedMethodId=" + recievedMethodId + " ]";
    }
    
}
