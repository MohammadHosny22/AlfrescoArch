/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.arch.entity;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class CustomType implements Serializable {

    private static final long serialVersionUID = 1L;
    private String customTypeId;
    private String customTypeDesc;

    public CustomType(String customTypeId, String customTypeDesc) {
        this.customTypeId = customTypeId;
        this.customTypeDesc = customTypeDesc;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomType)) {
            return false;
        }
        CustomType other = (CustomType) object;
        if ((this.getCustomTypeId() == null && other.getCustomTypeId() != null) || (this.getCustomTypeId() != null && !this.customTypeId.equals(other.customTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tech.arch.entity.CustomType[ customTypeId=" + getCustomTypeId() + " ]";
    }

    /**
     * @return the customTypeId
     */
    public String getCustomTypeId() {
        return customTypeId;
    }

    /**
     * @param customTypeId the customTypeId to set
     */
    public void setCustomTypeId(String customTypeId) {
        this.customTypeId = customTypeId;
    }

    /**
     * @return the customTypeDesc
     */
    public String getCustomTypeDesc() {
        return customTypeDesc;
    }

    /**
     * @param customTypeDesc the customTypeDesc to set
     */
    public void setCustomTypeDesc(String customTypeDesc) {
        this.customTypeDesc = customTypeDesc;
    }
    
}
