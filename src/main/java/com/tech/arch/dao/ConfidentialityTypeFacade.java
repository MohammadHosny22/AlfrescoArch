/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.arch.dao;

import com.tech.arch.entity.ConfidentialityType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class ConfidentialityTypeFacade extends AbstractFacade<ConfidentialityType> {

    @PersistenceContext(unitName = "LaundryApplicationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfidentialityTypeFacade() {
        super(ConfidentialityType.class);
    }
    
}
