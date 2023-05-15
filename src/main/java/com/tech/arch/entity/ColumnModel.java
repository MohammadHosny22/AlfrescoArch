/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.arch.entity;

import java.io.Serializable;

/**
 *
 * @author Mohammad Hosny
 */
public class ColumnModel implements Serializable{
    
    private String property;
    private String header;

    public ColumnModel(String header, String property) {
        this.property = property;
        this.header = header;
    }
    

    /**
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * @return the header
     */
    public String getHeader() {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(String header) {
        this.header = header;
    }
    
}
