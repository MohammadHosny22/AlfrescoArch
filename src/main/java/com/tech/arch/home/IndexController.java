package com.tech.arch.home;

import com.tech.arch.entity.util.NavigationArchBean;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named(value = "indexArchController")
@SessionScoped
public class IndexController implements Serializable{

    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
    private String localeStr2;

    private boolean renderBillClosesTypes;
    private boolean renderBills;
    private boolean renderBillsTab;
    private boolean renderBurchases;
    private boolean renderIoCustomers;
    private boolean renderCustomizeTypeCosts;
    private boolean renderIoCustType;
    private boolean renderIoRoles;
    private boolean renderIoUsers;
    private boolean renderNewParticipation;
    private boolean renderIoTypes;
    private boolean renderIoOperations;
    private boolean renderIoTypesOperation;
    private boolean renderDefaultPartnershipValues;
    private boolean renderCustomizePartnershipValues;
    private boolean renderIoCustomerPartnership;
    private boolean renderReportsMainPage;
    private boolean renderLaundryInfo;
    private boolean renderSendSMS;
    private boolean renderSMSTemplates;
    private boolean renderDrivers;
    private boolean renderBranches;
    private boolean renderGroups;
    private boolean renderBankTransfer;

    public IndexController() {
        
    }

    public void redirectToBillClosesTypes() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/billClosesTypes/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToBillsTab() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/bills/BillsTab.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToBills() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/bills/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToBurchases() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/burchases/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToCustomizeTypeCosts() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/customizeTypeCosts/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToIoCustType() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/ioCustType/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToIoCustomers() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/ioCustomers/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToIoCustomerPartnership() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/ioCustomerPartnership/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToIoOperations() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/ioOperations/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToLaundryInfo() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/laundryInfo/List.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToSMSInfo() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/SMS/SendSMS.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToSMSTemplates() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/smsMessageTemplates/List.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToIoRoles() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/ioRoles/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToBankTransfer() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/bankTransfer/List.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToIoTypes() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/ioTypes/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToIoTypesOperation() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/ioTypesOperation/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToIoUsersRoles() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/ioUsersRoles/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToIoUsers() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/ioUsers/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToDefaultPartnershipValues() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/defaultPartnershipValues/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToCustomizePartnershipValues() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/customizePartnershipValues/index.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToNewParticipation() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/defaultPartnershipValues/NewParticipation.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToReportsMainPage() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/reports/ReportsMainPage.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToDrivers() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/ioDrivers/List.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToGroups() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/ioGroups/List.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void redirectToBranches() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/LaundrySysMaven/com/tech/arch/lkup/ioBranches/List.xhtml");
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the renderBillClosesTypes
     */
    public boolean isRenderBillClosesTypes() {
        return renderBillClosesTypes;
    }

    /**
     * @param renderBillClosesTypes the renderBillClosesTypes to set
     */
    public void setRenderBillClosesTypes(boolean renderBillClosesTypes) {
        this.renderBillClosesTypes = renderBillClosesTypes;
    }

    /**
     * @return the renderBills
     */
    public boolean isRenderBills() {
        return renderBills;
    }

    /**
     * @param renderBills the renderBills to set
     */
    public void setRenderBills(boolean renderBills) {
        this.renderBills = renderBills;
    }

    /**
     * @return the renderBillsTab
     */
    public boolean isRenderBillsTab() {
        return renderBillsTab;
    }

    /**
     * @param renderBillsTab the renderBillsTab to set
     */
    public void setRenderBillsTab(boolean renderBillsTab) {
        this.renderBillsTab = renderBillsTab;
    }

    /**
     * @return the renderBurchases
     */
    public boolean isRenderBurchases() {
        return renderBurchases;
    }

    /**
     * @param renderBurchases the renderBurchases to set
     */
    public void setRenderBurchases(boolean renderBurchases) {
        this.renderBurchases = renderBurchases;
    }

    /**
     * @return the renderIoCustomers
     */
    public boolean isRenderIoCustomers() {
        return renderIoCustomers;
    }

    /**
     * @param renderIoCustomers the renderIoCustomers to set
     */
    public void setRenderIoCustomers(boolean renderIoCustomers) {
        this.renderIoCustomers = renderIoCustomers;
    }

    /**
     * @return the renderCustomizeTypeCosts
     */
    public boolean isRenderCustomizeTypeCosts() {
        return renderCustomizeTypeCosts;
    }

    /**
     * @param renderCustomizeTypeCosts the renderCustomizeTypeCosts to set
     */
    public void setRenderCustomizeTypeCosts(boolean renderCustomizeTypeCosts) {
        this.renderCustomizeTypeCosts = renderCustomizeTypeCosts;
    }

    /**
     * @return the renderIoCustType
     */
    public boolean isRenderIoCustType() {
        return renderIoCustType;
    }

    /**
     * @param renderIoCustType the renderIoCustType to set
     */
    public void setRenderIoCustType(boolean renderIoCustType) {
        this.renderIoCustType = renderIoCustType;
    }

    /**
     * @return the renderIoRoles
     */
    public boolean isRenderIoRoles() {
        return renderIoRoles;
    }

    /**
     * @param renderIoRoles the renderIoRoles to set
     */
    public void setRenderIoRoles(boolean renderIoRoles) {
        this.renderIoRoles = renderIoRoles;
    }

    /**
     * @return the renderIoUsers
     */
    public boolean isRenderIoUsers() {
        return renderIoUsers;
    }

    /**
     * @param renderIoUsers the renderIoUsers to set
     */
    public void setRenderIoUsers(boolean renderIoUsers) {
        this.renderIoUsers = renderIoUsers;
    }

    /**
     * @return the renderNewParticipation
     */
    public boolean isRenderNewParticipation() {
        return renderNewParticipation;
    }

    /**
     * @param renderNewParticipation the renderNewParticipation to set
     */
    public void setRenderNewParticipation(boolean renderNewParticipation) {
        this.renderNewParticipation = renderNewParticipation;
    }

    /**
     * @return the renderIoTypes
     */
    public boolean isRenderIoTypes() {
        return renderIoTypes;
    }

    /**
     * @param renderIoTypes the renderIoTypes to set
     */
    public void setRenderIoTypes(boolean renderIoTypes) {
        this.renderIoTypes = renderIoTypes;
    }

    /**
     * @return the renderIoOperations
     */
    public boolean isRenderIoOperations() {
        return renderIoOperations;
    }

    /**
     * @param renderIoOperations the renderIoOperations to set
     */
    public void setRenderIoOperations(boolean renderIoOperations) {
        this.renderIoOperations = renderIoOperations;
    }

    /**
     * @return the renderIoTypesOperation
     */
    public boolean isRenderIoTypesOperation() {
        return renderIoTypesOperation;
    }

    /**
     * @param renderIoTypesOperation the renderIoTypesOperation to set
     */
    public void setRenderIoTypesOperation(boolean renderIoTypesOperation) {
        this.renderIoTypesOperation = renderIoTypesOperation;
    }

    /**
     * @return the renderDefaultPartnershipValues
     */
    public boolean isRenderDefaultPartnershipValues() {
        return renderDefaultPartnershipValues;
    }

    /**
     * @param renderDefaultPartnershipValues the renderDefaultPartnershipValues
     * to set
     */
    public void setRenderDefaultPartnershipValues(boolean renderDefaultPartnershipValues) {
        this.renderDefaultPartnershipValues = renderDefaultPartnershipValues;
    }

    /**
     * @return the renderCustomizePartnershipValues
     */
    public boolean isRenderCustomizePartnershipValues() {
        return renderCustomizePartnershipValues;
    }

    /**
     * @param renderCustomizePartnershipValues the
     * renderCustomizePartnershipValues to set
     */
    public void setRenderCustomizePartnershipValues(boolean renderCustomizePartnershipValues) {
        this.renderCustomizePartnershipValues = renderCustomizePartnershipValues;
    }

    /**
     * @return the renderIoCustomerPartnership
     */
    public boolean isRenderIoCustomerPartnership() {
        return renderIoCustomerPartnership;
    }

    /**
     * @param renderIoCustomerPartnership the renderIoCustomerPartnership to set
     */
    public void setRenderIoCustomerPartnership(boolean renderIoCustomerPartnership) {
        this.renderIoCustomerPartnership = renderIoCustomerPartnership;
    }

    /**
     * @return the renderReportsMainPage
     */
    public boolean isRenderReportsMainPage() {
        return renderReportsMainPage;
    }

    /**
     * @param renderReportsMainPage the renderReportsMainPage to set
     */
    public void setRenderReportsMainPage(boolean renderReportsMainPage) {
        this.renderReportsMainPage = renderReportsMainPage;
    }

    public String logout() {
        System.out.println("session.getAttribute()2 = " + session.getAttribute("user"));
        session.invalidate();
//        session.removeAttribute("currentLocale2");
        return new NavigationArchBean().redirectToLogin();
    }

    public String getDirection() {
        String currentLocale = (String) session.getAttribute("currentLocale2");
        System.out.println("currentLocale2 = " + currentLocale);
        String direction;
        System.out.println("FacesContext.getCurrentInstance().getExternalContext().getRequestLocale().toString().equalsIgnoreCase(\"ar_SA\") = " + FacesContext.getCurrentInstance().getExternalContext().getRequestLocale().toString());
        if (currentLocale == null) {
            if (FacesContext.getCurrentInstance().getExternalContext().getRequestLocale().toString().contains("ar")) {
                direction = "RTL";
            } else {
                direction = "LTR";
            }
        } else if (currentLocale.equalsIgnoreCase("ar")) {
            direction = "RTL";
        } else {
            direction = "LTR";
        }
        return direction;
    }

    //set bundle to English
    public String changeLocaleToEnglish() {
        session.setAttribute("currentLocale2", "en");
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("en"));
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String[] newURLArr = (((HttpServletRequest) ec.getRequest()).getRequestURI()).split("/LaundrySysMaven");
        String newURL = newURLArr[1];
        return newURL + "?faces-redirect=true";
    }

    //set bundle to Arabic
    public String changeLocaleToArabic() {
        session.setAttribute("currentLocale2", "ar");
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("ar"));
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String[] newURLArr = (((HttpServletRequest) ec.getRequest()).getRequestURI()).split("/LaundrySysMaven");
        String newURL = newURLArr[1];
        return newURL + "?faces-redirect=true";
    }

    /**
     * @return the renderLaundryInfo
     */
    public boolean isRenderLaundryInfo() {
        return renderLaundryInfo;
    }

    /**
     * @param renderLaundryInfo the renderLaundryInfo to set
     */
    public void setRenderLaundryInfo(boolean renderLaundryInfo) {
        this.renderLaundryInfo = renderLaundryInfo;
    }

    /**
     * @return the renderSendSMS
     */
    public boolean isRenderSendSMS() {
        return renderSendSMS;
    }

    /**
     * @param renderSendSMS the renderSendSMS to set
     */
    public void setRenderSendSMS(boolean renderSendSMS) {
        this.renderSendSMS = renderSendSMS;
    }

    /**
     * @return the renderSMSTemplates
     */
    public boolean isRenderSMSTemplates() {
        return renderSMSTemplates;
    }

    /**
     * @param renderSMSTemplates the renderSMSTemplates to set
     */
    public void setRenderSMSTemplates(boolean renderSMSTemplates) {
        this.renderSMSTemplates = renderSMSTemplates;
    }

    /**
     * @return the localeStr2
     */
    public String getLocaleStr2() {
        localeStr2 = (String) session.getAttribute("currentLocale2");
        return localeStr2;
    }

    /**
     * @param localeStr2 the localeStr2 to set
     */
    public void setLocaleStr2(String localeStr2) {
        this.localeStr2 = localeStr2;
    }

    /**
     * @return the renderDrivers
     */
    public boolean isRenderDrivers() {
        return renderDrivers;
    }

    /**
     * @param renderDrivers the renderDrivers to set
     */
    public void setRenderDrivers(boolean renderDrivers) {
        this.renderDrivers = renderDrivers;
    }

    /**
     * @return the renderBranches
     */
    public boolean isRenderBranches() {
        return renderBranches;
    }

    /**
     * @param renderBranches the renderBranches to set
     */
    public void setRenderBranches(boolean renderBranches) {
        this.renderBranches = renderBranches;
    }

    public boolean isRenderBankTransfer() {
        return renderBankTransfer;
    }

    public void setRenderBankTransfer(boolean renderBankTransfer) {
        this.renderBankTransfer = renderBankTransfer;
    }

    /**
     * @return the renderGroups
     */
    public boolean isRenderGroups() {
        return renderGroups;
    }

    /**
     * @param renderGroups the renderGroups to set
     */
    public void setRenderGroups(boolean renderGroups) {
        this.renderGroups = renderGroups;
    }
}
