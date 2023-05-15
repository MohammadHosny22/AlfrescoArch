/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.arch.controller;

import com.tech.arch.entity.CustomType;
import com.tech.arch.entity.util.JsfUtil;
import com.tech.arch.util.Util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.definitions.Choice;
import org.apache.chemistry.opencmis.commons.definitions.PropertyDefinition;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.primefaces.PrimeFaces;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Mohammad Hosny
 */
@ManagedBean
public class FileUploadController {

    private UploadedFile file;
    private String selectedCTID;
    private Session session = null;
    private String modelName;
    private String parentCustomType;
    private HtmlCommandButton hiddenSaveBtn = new HtmlCommandButton();

    // Custom Type List authorized to logged in user
    private List<CustomType> customTypeLst = new ArrayList<>();

    public FileUploadController() {
        try {
            // at the begging load session with authorized logged user
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession sessionFaces = (HttpSession) facesContext.getExternalContext().getSession(true);
            session = (Session) sessionFaces.getAttribute("sessionCMIS");

            // Load custom types by root one
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("alfresco.properties");
            Properties properties = new Properties();
            properties.load(input);
            modelName = properties.getProperty("ModelName");
            parentCustomType = properties.getProperty("ParentCustomType");

            String customTypeRoot = "D:" + modelName + ":" + parentCustomType;
            ItemIterable<ObjectType> baseObjectTypes = session.getTypeChildren(customTypeRoot, true);

            for (ObjectType objType : baseObjectTypes) {
                customTypeLst.add(new CustomType(objType.getId(), objType.getDisplayName()));
//            System.out.println(objType.getId() + "   " + objType.getDisplayName());
            }

            String modelName = properties.getProperty("ModelName");
        } catch (IOException ex) {
            Logger.getLogger(FileUploadController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onModelChange() {
        try {
            System.out.println("selected Model------------" + selectedCTID);
            loadAllRropertyTypes(session, selectedCTID);
        } catch (Exception ex) {
            Logger.getLogger(FileUploadController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadAllRropertyTypes(Session session, String typeId) throws Exception {
        ObjectType typeDef = session.getTypeDefinition(typeId);
        Map<String, PropertyDefinition<?>> propDefs = typeDef.getPropertyDefinitions();
        UIComponent component = JsfUtil.findComponent("pggrid");
        component.getChildren().clear();
        List<UIComponent> listU = new ArrayList<>();
        List<String> checkValidObjectIds = new ArrayList<>();

        // classic way, loop a Map
        for (Map.Entry<String, PropertyDefinition<?>> entry : propDefs.entrySet()) {

            // Get only custom properities and ignore system 
            if (entry.getKey().startsWith(modelName)) {
                System.out.println("Key : " + entry.getKey().split(":")[1] + " ---- Value : " + entry.getValue());
                List<?> choices = entry.getValue().getChoices();

                if (entry.getValue().getPropertyType().toString().equalsIgnoreCase("STRING")) {
                    OutputLabel lab = new OutputLabel();
                    lab.setValue(entry.getKey().split(":")[1]);
                    listU.add(lab);
                    if (choices.size() > 0) {
                        SelectOneMenu selectOneMenu = new SelectOneMenu();
                        selectOneMenu.setId(entry.getKey().split(":")[1] + "000" + entry.getValue().getPropertyType());
                        UISelectItem selectItem;
                        for (Object obj : choices) {
                            if (obj instanceof Choice) {
                                Choice str = (Choice) obj;
                                selectItem = new UISelectItem();
                                selectItem.setItemLabel(str.getDisplayName());
                                selectItem.setItemValue(str.getDisplayName());
                                selectOneMenu.getChildren().add(selectItem);
                                System.out.println("values---------" + str.getDisplayName());

                            }

                        }
                        listU.add(selectOneMenu);
                    } else {
                        InputText text = new InputText();
                        text.setId(entry.getKey().split(":")[1] + "000" + entry.getValue().getPropertyType());
                        listU.add(text);
                    }
                }
                if (entry.getValue().getPropertyType().toString().equalsIgnoreCase("DATETIME")) {
                    OutputLabel lab = new OutputLabel();
                    lab.setValue(entry.getKey().split(":")[1]);
                    listU.add(lab);
                    Calendar cal = new Calendar();
                    cal.setId(entry.getKey().split(":")[1] + "000" + entry.getValue().getPropertyType());
                    cal.setPattern("MM/dd/yyyy HH:mm:ss");
//                    cal.setReadonly(true);
                    listU.add(cal);
                } else if (entry.getValue().getPropertyType().toString().equalsIgnoreCase("INTEGER")) {
                    OutputLabel lab = new OutputLabel();
                    lab.setValue(entry.getValue().getDisplayName());
                    listU.add(lab);
                    InputText inputText = new InputText();
                    inputText.setId(entry.getKey().split(":")[1] + "000" + entry.getValue().getPropertyType());
                    inputText.setOnkeyup("$(this).val($(this).val().replace(/[^0-9]/g, ''))");
                    listU.add(inputText);
                } else if (entry.getValue().getPropertyType().toString().equalsIgnoreCase("DECIMAL")) {
                    OutputLabel lab = new OutputLabel();
                    lab.setValue(entry.getValue().getDisplayName());
                    listU.add(lab);
                    InputText inputText = new InputText();
                    inputText.setId(entry.getKey().split(":")[1] + "000" + entry.getValue().getPropertyType());
                    inputText.setOnkeyup("$(this).val($(this).val().replace(/[^0-9./]/g, ''))");
                    listU.add(inputText);
                } else if (entry.getValue().getPropertyType().toString().equalsIgnoreCase("BOOLEAN")) {
                    OutputLabel lab = new OutputLabel();
                    lab.setValue(entry.getValue().getDisplayName());
                    listU.add(lab);
                    SelectBooleanCheckbox checkBox = new SelectBooleanCheckbox();
                    checkBox.setId(entry.getKey().split(":")[1] + "000" + entry.getValue().getPropertyType());
                    listU.add(checkBox);
                }

                // Add to check Validation Object Ids in case of mandatory field
                if (entry.getValue().isRequired()) {
                    checkValidObjectIds.add(entry.getKey().split(":")[1]);
                }
            }
        }

        // set checkValidObjectIds to session
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession sessionFaces = (HttpSession) facesContext.getExternalContext().getSession(true);
        sessionFaces.setAttribute("checkValidObjectIds", checkValidObjectIds);

        component.getChildren().addAll(listU);
    }

    public boolean createDocument(Session session) {

        // Grab a reference to the folder where we want to create content
        Folder folder = (Folder) session.getObjectByPath("/User Homes/" + selectedCTID.split(":")[2]);

        Map<String, Object> properties = new HashMap<>();
        String fileName = file.getFileName();

        // Generate random name not to duplicate names in alfresco store
        String generatedFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + Util.getRandomNumberInRange() + fileName.substring(fileName.lastIndexOf("."));
        System.out.println("generatedFileName = " + generatedFileName);
        properties.put(PropertyIds.NAME, generatedFileName);
        properties.put(PropertyIds.OBJECT_TYPE_ID, selectedCTID);

        // get checkValidObjectIds from session to validate mandatory fields
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession sessionFaces = (HttpSession) facesContext.getExternalContext().getSession(true);
        List<String> checkValidObjectIds = (List<String>) sessionFaces.getAttribute("checkValidObjectIds");

        UIComponent component = JsfUtil.findComponent("pggrid");
        boolean isValid = true;
        for (UIComponent comp : component.getChildren()) {
            if (comp instanceof InputText) {
                InputText inText = (InputText) comp;
                String inputId = inText.getId().split("000")[0];

                // Check impty mandatory field
                if (checkValidObjectIds.contains(inputId) && (inText.getValue() == null || inText.getValue().toString().isEmpty())) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, inputId + " " + ResourceBundle.getBundle("/IO_Bundle").getString("MustNotEmpty"), "");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    inText.setStyle("background: red;");
                    isValid = false;
                    break;
                } else {
                    String propType = inText.getId().split("000")[1];
                    if (propType.equalsIgnoreCase("string")) {
                        properties.put(modelName + ":" + inputId, inText.getValue().toString());
                    } else if (propType.equalsIgnoreCase("decimal")) {
                        properties.put(modelName + ":" + inputId, Double.valueOf((inText.getValue() == null || inText.getValue().toString().isEmpty()) ? "0" : inText.getValue().toString()));
                    } else if (propType.equalsIgnoreCase("integer")) {
                        properties.put(modelName + ":" + inputId, Integer.valueOf((inText.getValue() == null || inText.getValue().toString().isEmpty()) ? "0" : inText.getValue().toString()));
                    }

                    // reset background color
                    inText.setStyle("background: white;");
                }
            } else if (comp instanceof SelectOneMenu) {
                SelectOneMenu inNumber = (SelectOneMenu) comp;
                properties.put(modelName + ":" + inNumber.getId().split("000")[0], inNumber.getValue().toString());
//                System.out.println("id---" + inNumber.getId().split("000")[0] + "-------value ---------" + inNumber.getValue().toString());
            } else if (comp instanceof SelectBooleanCheckbox) {
                SelectBooleanCheckbox inNumber = (SelectBooleanCheckbox) comp;
                properties.put(modelName + ":" + inNumber.getId().split("000")[0], Boolean.valueOf(inNumber.getValue().toString()));
//                System.out.println("id---" + inNumber.getId().split("000")[0] + "-------value ---------" + Boolean.valueOf(inNumber.getValue().toString()));
            } else if (comp instanceof Calendar) {
                Calendar cal = (Calendar) comp;
                String inputId = cal.getId().split("000")[0];

                // Check impty mandatory field
                if (checkValidObjectIds.contains(inputId) && (cal.getValue() == null || cal.getValue().toString().isEmpty())) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, inputId + " " + ResourceBundle.getBundle("/IO_Bundle").getString("MustNotEmpty"), "");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    cal.setStyle("background: red;");
                    isValid = false;
                    break;
                } else {
                    properties.put(modelName + ":" + cal.getId().split("000")[0], cal.getValue());
                    // reset background color
                    cal.setStyle("background: white;");
                }
            }
        }

        // check if valid inputs
        if (isValid) {
            byte[] content = file.getContents();
            long fileLength = Long.valueOf(content.length);
            String fileType = file.getContentType();
            InputStream stream = new ByteArrayInputStream(content);
            ContentStream contentStream = session.getObjectFactory().createContentStream(generatedFileName, fileLength, fileType, stream);

            Document doc = folder.createDocument(
                    properties,
                    contentStream,
                    VersioningState.MAJOR);
            System.out.println("Created: " + doc.getId());
            System.out.println("Content Length: " + doc.getContentStreamLength());
        }
        return isValid;
    }

    public Map<String, Object> getProperities(Session session) {

        // Grab a reference to the folder where we want to create content
        Map<String, Object> properties = new HashMap<>();
        String fileName = "test.pdf";

        // Generate random name not to duplicate names in alfresco store
        String generatedFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + Util.getRandomNumberInRange() + fileName.substring(fileName.lastIndexOf("."));
        System.out.println("generatedFileName = " + generatedFileName);
        properties.put(PropertyIds.NAME, generatedFileName);
        properties.put(PropertyIds.OBJECT_TYPE_ID, selectedCTID);

        // get checkValidObjectIds from session to validate mandatory fields
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession sessionFaces = (HttpSession) facesContext.getExternalContext().getSession(true);
        List<String> checkValidObjectIds = (List<String>) sessionFaces.getAttribute("checkValidObjectIds");

        UIComponent component = JsfUtil.findComponent("pggrid");
        for (UIComponent comp : component.getChildren()) {
            if (comp instanceof InputText) {
                InputText inText = (InputText) comp;
                String inputId = inText.getId().split("000")[0];

                // Check impty mandatory field
                if (checkValidObjectIds.contains(inputId) && (inText.getValue() == null || inText.getValue().toString().isEmpty())) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, inputId + " " + ResourceBundle.getBundle("/IO_Bundle").getString("MustNotEmpty"), "");
                    inText.setStyle("background: red;");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    return null;
                } else {
                    String propType = inText.getId().split("000")[1];
                    if (propType.equalsIgnoreCase("string")) {
                        properties.put(modelName + ":" + inputId, inText.getValue().toString());
                    } else if (propType.equalsIgnoreCase("decimal")) {
                        properties.put(modelName + ":" + inputId, Double.valueOf((inText.getValue() == null || inText.getValue().toString().isEmpty()) ? "0" : inText.getValue().toString()));
                    } else if (propType.equalsIgnoreCase("integer")) {
                        properties.put(modelName + ":" + inputId, Integer.valueOf((inText.getValue() == null || inText.getValue().toString().isEmpty()) ? "0" : inText.getValue().toString()));
                    }

                    // reset background color
                    inText.setStyle("background: white;");
//                System.out.println("id---" + inputId + "-------value ---------" + inText.getValue().toString());
                }
            } else if (comp instanceof SelectOneMenu) {
                SelectOneMenu inNumber = (SelectOneMenu) comp;
                properties.put(modelName + ":" + inNumber.getId().split("000")[0], inNumber.getValue().toString());
//                System.out.println("id---" + inNumber.getId().split("000")[0] + "-------value ---------" + inNumber.getValue().toString());
            } else if (comp instanceof SelectBooleanCheckbox) {
                SelectBooleanCheckbox inNumber = (SelectBooleanCheckbox) comp;
                properties.put(modelName + ":" + inNumber.getId().split("000")[0], Boolean.valueOf(inNumber.getValue().toString()));
//                System.out.println("id---" + inNumber.getId().split("000")[0] + "-------value ---------" + Boolean.valueOf(inNumber.getValue().toString()));
            } else if (comp instanceof Calendar) {
                Calendar cal = (Calendar) comp;
                String inputId = cal.getId().split("000")[0];

                // Check impty mandatory field
                if (checkValidObjectIds.contains(inputId) && (cal.getValue() == null || cal.getValue().toString().isEmpty())) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, inputId + " " + ResourceBundle.getBundle("/IO_Bundle").getString("MustNotEmpty"), "");
                    cal.setStyle("background: red;");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    return null;
                } else {
                    properties.put(modelName + ":" + cal.getId().split("000")[0], cal.getValue());
                    // reset background color
                    cal.setStyle("background: white;");
//                System.out.println("id---" + cal.getId().split("000")[0] + "-------value ---------" + cal.getValue().toString());
                }
            }
        }
        return properties;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    /**
     * @return the selectedCTID
     */
    public String getSelectedCTID() {
        return selectedCTID;
    }

    /**
     * @param selectedCTID the selectedCTID to set
     */
    public void setSelectedCTID(String selectedCTID) {
        this.selectedCTID = selectedCTID;
    }

    /**
     * @return the customTypeLst
     */
    public List<CustomType> getCustomTypeLst() {
        return customTypeLst;
    }

    /**
     * @param customTypeLst the customTypeLst to set
     */
    public void setCustomTypeLst(List<CustomType> customTypeLst) {
        this.customTypeLst = customTypeLst;
    }

    public void uploadDocument() {
        System.out.println("selectedCTID = " + selectedCTID);
        if (file != null && file.getFileName() != null && !file.getFileName().isEmpty()) {
            System.out.println("fileName============ = " + file.getFileName());
            boolean isUploaded = createDocument(session);
            if (isUploaded) {
                FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You must select file!", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void scanDocument() {
        System.out.println("in scanDocument");
        Map<String, Object> propMap = getProperities(session);

        // Validate if valid properities or not
        if (propMap != null) {
            // click on hidden button to save scanned webtwain images
            PrimeFaces.current().executeScript("uploadScannedDoc(null, window.location.hostname, window.location.port);");
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "You must select file!", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        System.out.println("fileName============ = " + event.getFile().getFileName());
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<String> completeText(String query) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(query + i);
        }
        return results;
    }

    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * @return the modelName
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * @param modelName the modelName to set
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    /**
     * @return the parentCustomType
     */
    public String getParentCustomType() {
        return parentCustomType;
    }

    /**
     * @param parentCustomType the parentCustomType to set
     */
    public void setParentCustomType(String parentCustomType) {
        this.parentCustomType = parentCustomType;
    }

    /**
     * @return the hiddenSaveBtn
     */
    public HtmlCommandButton getHiddenSaveBtn() {
        return hiddenSaveBtn;
    }

    /**
     * @param hiddenSaveBtn the hiddenSaveBtn to set
     */
    public void setHiddenSaveBtn(HtmlCommandButton hiddenSaveBtn) {
        this.hiddenSaveBtn = hiddenSaveBtn;
    }
}
