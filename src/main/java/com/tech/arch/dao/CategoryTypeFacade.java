/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.arch.dao;

import com.tech.arch.entity.CategoryType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Administrator
 */
@Stateless
public class CategoryTypeFacade extends AbstractFacade<CategoryType> {

    @PersistenceContext(unitName = "LaundryApplicationPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryTypeFacade() {
        super(CategoryType.class);
    }
    
}
