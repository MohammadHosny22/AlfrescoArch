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
@Table(name = "confidentiality_type")
@NamedQueries({
    @NamedQuery(name = "ConfidentialityType.findAll", query = "SELECT c FROM ConfidentialityType c"),
    @NamedQuery(name = "ConfidentialityType.findByConfId", query = "SELECT c FROM ConfidentialityType c WHERE c.confId = :confId"),
    @NamedQuery(name = "ConfidentialityType.findByConfNameAr", query = "SELECT c FROM ConfidentialityType c WHERE c.confNameAr = :confNameAr"),
    @NamedQuery(name = "ConfidentialityType.findByConfNameEn", query = "SELECT c FROM ConfidentialityType c WHERE c.confNameEn = :confNameEn"),
    @NamedQuery(name = "ConfidentialityType.findByConfType", query = "SELECT c FROM ConfidentialityType c WHERE c.confType = :confType")})
public class ConfidentialityType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "conf_id")
    private Integer confId;
    @Size(max = 60)
    @Column(name = "conf_name_ar")
    private String confNameAr;
    @Size(max = 45)
    @Column(name = "conf_name_en")
    private String confNameEn;
    @Column(name = "conf_type")
    private Integer confType;

    public ConfidentialityType() {
    }

    public ConfidentialityType(Integer confId) {
        this.confId = confId;
    }

    public Integer getConfId() {
        return confId;
    }

    public void setConfId(Integer confId) {
        this.confId = confId;
    }

    public String getConfNameAr() {
        return confNameAr;
    }

    public void setConfNameAr(String confNameAr) {
        this.confNameAr = confNameAr;
    }

    public String getConfNameEn() {
        return confNameEn;
    }

    public void setConfNameEn(String confNameEn) {
        this.confNameEn = confNameEn;
    }

    public Integer getConfType() {
        return confType;
    }

    public void setConfType(Integer confType) {
        this.confType = confType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (confId != null ? confId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfidentialityType)) {
            return false;
        }
        ConfidentialityType other = (ConfidentialityType) object;
        if ((this.confId == null && other.confId != null) || (this.confId != null && !this.confId.equals(other.confId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tech.arch.entity.ConfidentialityType[ confId=" + confId + " ]";
    }
    
}
