/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.arch.controller;

import com.tech.arch.entity.util.NavigationArchBean;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

/**
 *
 * @author Mohammad.Hosny
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    @ManagedProperty(value = "#{navigationArchBean}")
    private NavigationArchBean navigationBean;

    private static final long serialVersionUID = 1L;
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String loginProject() {
        FacesMessage message = null;

        SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(SessionParameter.USER, getUsername());
        parameters.put(SessionParameter.PASSWORD, getPassword());
        parameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
        parameters.put(SessionParameter.ATOMPUB_URL, "http://localhost:8080/alfresco/cmisatom");
        try {
            Session session = sessionFactory.getRepositories(parameters).get(0).createSession();
            System.out.println("session.getSessionParameters() = " + session.getSessionParameters());
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession sessionFaces = (HttpSession) facesContext.getExternalContext().getSession(true);
            sessionFaces.setAttribute("sessionCMIS", session);

            /*ObjectType type = session.getTypeDefinition("cmis:document");

            if (type instanceof DocumentType) {
                DocumentType docType = (DocumentType) type;
                boolean isVersionable = docType.isVersionable();
                System.out.println("relType.getLocalName() = " + docType.getLocalName());
            } else if (type instanceof RelationshipType) {
                RelationshipType relType = (RelationshipType) type;
                System.out.println("relType.getLocalName() = " + relType.getLocalName());
            } else {
                System.out.println("Not local type = " + type.getLocalName());
            }*/
            // Get service registry
//            ServiceRegistry serviceRegistry = (ServiceRegistry) beanFactory.getBean(ServiceRegistry.SERVICE_REGISTRY);
//
//            // Get services
//            AuthenticationService authService = (AuthenticationService) serviceRegistry.getAuthenticationService();
//            PersonService personService = (PersonService) serviceRegistry.getPersonService();
//
//            // Get current user
//            NodeRef person = personService.getPerson(authService.getCurrentUserName());
            return navigationBean.redirectToHome();
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "خطأ في الدخول", "تأكد من اسم المستخدم وكلمة السر");
            navigationBean.toLogin();
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        return "";
    }

    public void setNavigationBean(NavigationArchBean navigationBean) {
        this.navigationBean = navigationBean;
    }
}
