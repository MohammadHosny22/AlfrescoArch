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
@Table(name = "correspondence_type")
@NamedQueries({
    @NamedQuery(name = "CorrespondenceType.findAll", query = "SELECT c FROM CorrespondenceType c"),
    @NamedQuery(name = "CorrespondenceType.findByCorrTypeId", query = "SELECT c FROM CorrespondenceType c WHERE c.corrTypeId = :corrTypeId"),
    @NamedQuery(name = "CorrespondenceType.findByCorrTypeNameAr", query = "SELECT c FROM CorrespondenceType c WHERE c.corrTypeNameAr = :corrTypeNameAr"),
    @NamedQuery(name = "CorrespondenceType.findByCorrTypeNameEn", query = "SELECT c FROM CorrespondenceType c WHERE c.corrTypeNameEn = :corrTypeNameEn"),
    @NamedQuery(name = "CorrespondenceType.findByCorrTypeNameType", query = "SELECT c FROM CorrespondenceType c WHERE c.corrTypeNameType = :corrTypeNameType")})
public class CorrespondenceType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "corr_type_id")
    private Integer corrTypeId;
    @Size(max = 60)
    @Column(name = "corr_type_name_ar")
    private String corrTypeNameAr;
    @Size(max = 45)
    @Column(name = "corr_type_name_en")
    private String corrTypeNameEn;
    @Column(name = "corr_type_name_type")
    private Integer corrTypeNameType;

    public CorrespondenceType() {
    }

    public CorrespondenceType(Integer corrTypeId) {
        this.corrTypeId = corrTypeId;
    }

    public Integer getCorrTypeId() {
        return corrTypeId;
    }

    public void setCorrTypeId(Integer corrTypeId) {
        this.corrTypeId = corrTypeId;
    }

    public String getCorrTypeNameAr() {
        return corrTypeNameAr;
    }

    public void setCorrTypeNameAr(String corrTypeNameAr) {
        this.corrTypeNameAr = corrTypeNameAr;
    }

    public String getCorrTypeNameEn() {
        return corrTypeNameEn;
    }

    public void setCorrTypeNameEn(String corrTypeNameEn) {
        this.corrTypeNameEn = corrTypeNameEn;
    }

    public Integer getCorrTypeNameType() {
        return corrTypeNameType;
    }

    public void setCorrTypeNameType(Integer corrTypeNameType) {
        this.corrTypeNameType = corrTypeNameType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrTypeId != null ? corrTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CorrespondenceType)) {
            return false;
        }
        CorrespondenceType other = (CorrespondenceType) object;
        if ((this.corrTypeId == null && other.corrTypeId != null) || (this.corrTypeId != null && !this.corrTypeId.equals(other.corrTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tech.arch.entity.CorrespondenceType[ corrTypeId=" + corrTypeId + " ]";
    }
    
}
