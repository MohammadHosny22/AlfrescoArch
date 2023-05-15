/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.arch.dao;

import com.tech.arch.entity.RecievedMethod;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class RecievedMethodFacade extends AbstractFacade<RecievedMethod> {

    @PersistenceContext(unitName = "LaundryApplicationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RecievedMethodFacade() {
        super(RecievedMethod.class);
    }
    
}
