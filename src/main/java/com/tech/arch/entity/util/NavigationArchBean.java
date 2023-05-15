/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.arch.entity.util;

/**
 *
 * @author Mohammad.Hosny
 */
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *  * Simple navigation bean  * @author itcuties  *
 
 */
@ManagedBean
@SessionScoped
public class NavigationArchBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Redirect to login page.
     *
     * @return Login page name.
     */
    public String redirectToLogin() {
        return "/login.xhtml?faces-redirect=true";
    }

    /**
     * Go to login page.
     *
     * @return Login page name.
     */
    public String toLogin() {
        return "/pages/login.xhtml";
    }

    /**
     * Redirect to Home page.
     *
     * @return Home page name.
     */
    public String redirectToHome() {
        return "/com/tech/arch/search/advancedSearch?faces-redirect=true";
    }
    
    /**
     * Redirect to Reports page.
     *
     * @return Reports page name.
     */
    public String redirectToReports() {
        return "/com/tech/arch/reports/ReportsMainPage.xhtml?faces-redirect=true";
    }
    
    /**
     * Redirect to Participate page.
     *
     * @return Participate page name.
     */
    public String redirectToParticipate() {
        return "/com/tech/arch/lkup/defaultPartnershipValues/NewParticipation.xhtml?faces-redirect=true";
    }

    /**
     * Go to welcome page.
     *
     * @return Welcome page name.
     */
    public String toHome() {
        return "/com/tech/arch/lkup/bills/BillsTab.xhtml";
    }
    
}
