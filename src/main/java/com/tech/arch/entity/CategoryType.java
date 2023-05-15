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
@Table(name = "category_type")
@NamedQueries({
    @NamedQuery(name = "CategoryType.findAll", query = "SELECT c FROM CategoryType c"),
    @NamedQuery(name = "CategoryType.findByCategoryTypeId", query = "SELECT c FROM CategoryType c WHERE c.categoryTypeId = :categoryTypeId"),
    @NamedQuery(name = "CategoryType.findByCategoryTypeNameAr", query = "SELECT c FROM CategoryType c WHERE c.categoryTypeNameAr = :categoryTypeNameAr"),
    @NamedQuery(name = "CategoryType.findByCategoryTypeNameEn", query = "SELECT c FROM CategoryType c WHERE c.categoryTypeNameEn = :categoryTypeNameEn"),
    @NamedQuery(name = "CategoryType.findByCategoryType", query = "SELECT c FROM CategoryType c WHERE c.categoryType = :categoryType")})
public class CategoryType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "category_type_id")
    private Integer categoryTypeId;
    @Size(max = 60)
    @Column(name = "category_type_name_ar")
    private String categoryTypeNameAr;
    @Size(max = 45)
    @Column(name = "category_type_name_en")
    private String categoryTypeNameEn;
    @Column(name = "category_type")
    private Integer categoryType;

    public CategoryType() {
    }

    public CategoryType(Integer categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    public Integer getCategoryTypeId() {
        return categoryTypeId;
    }

    public void setCategoryTypeId(Integer categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    public String getCategoryTypeNameAr() {
        return categoryTypeNameAr;
    }

    public void setCategoryTypeNameAr(String categoryTypeNameAr) {
        this.categoryTypeNameAr = categoryTypeNameAr;
    }

    public String getCategoryTypeNameEn() {
        return categoryTypeNameEn;
    }

    public void setCategoryTypeNameEn(String categoryTypeNameEn) {
        this.categoryTypeNameEn = categoryTypeNameEn;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryTypeId != null ? categoryTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoryType)) {
            return false;
        }
        CategoryType other = (CategoryType) object;
        if ((this.categoryTypeId == null && other.categoryTypeId != null) || (this.categoryTypeId != null && !this.categoryTypeId.equals(other.categoryTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tech.arch.entity.CategoryType[ categoryTypeId=" + categoryTypeId + " ]";
    }
    
}
