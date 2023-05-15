/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.arch.entity.util;

import com.tech.arch.home.IndexController;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.chemistry.opencmis.client.api.Session;

/**
 *
 * @author Mohammad.Hosny
 */
public class LoginFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
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

    public LoginFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("LoginFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("LoginFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
//        if (debug) {
//            log("LoginFilter:doFilter()");
//        }
//        
//        doBeforeProcessing(request, response);
//        
        Throwable problem = null;
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            Session session = (Session) req.getSession().getAttribute("sessionCMIS");
            String contextPath = ((HttpServletRequest) request).getContextPath();

            if (session == null) {
                ((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");
            } else {
                chain.doFilter(request, response);
            }

        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            t.printStackTrace();
        }

        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    private void resetRoles() {
        renderBillClosesTypes = false;
        renderBills = false;
        renderBillsTab = false;
        renderBurchases = false;
        renderIoCustomers = false;
        renderCustomizeTypeCosts = false;
        renderIoCustType = false;
        renderIoRoles = false;
        renderIoUsers = false;
        renderNewParticipation = false;
        renderIoTypes = false;
        renderIoOperations = false;
        renderIoTypesOperation = false;
        renderDefaultPartnershipValues = false;
        renderCustomizePartnershipValues = false;
        renderIoCustomerPartnership = false;
        renderReportsMainPage = false;
        renderLaundryInfo = false;
        renderSendSMS = false;
        renderSMSTemplates = false;
        renderDrivers = false;
        renderBranches = false;
        renderGroups = false;
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("LoginFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("LoginFilter()");
        }
        StringBuffer sb = new StringBuffer("LoginFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
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
