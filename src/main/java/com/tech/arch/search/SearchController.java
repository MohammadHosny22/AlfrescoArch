/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tech.arch.search;

import com.tech.arch.entity.ColumnModel;
import com.tech.arch.entity.CustomType;
import com.tech.arch.entity.GenericBean;
import com.tech.arch.entity.util.JsfUtil;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.PropertyData;
import org.apache.chemistry.opencmis.commons.definitions.Choice;
import org.apache.chemistry.opencmis.commons.definitions.PropertyDefinition;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.inputnumber.InputNumber;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.component.selectonemenu.SelectOneMenu;

/**
 *
 * @author Mohammad Hosny
 */
@ManagedBean
@ViewScoped
public class SearchController {

    private String selectedCTID;
    private List<CustomType> authorizedTypes = new ArrayList<>();
    private List<Document> docLst = new ArrayList<>();
    private List<GenericBean> genericBeanLst = new ArrayList<>();
    private List<GenericBean> filteredGenericBeanLst = new ArrayList<>();
    private String columnTemplateKey = "";
    private List<ColumnModel> columns;
    private Session session = null;
    private String modelName;
    private String parentCustomType;
    private String customProps = "";

    public SearchController() {

        try {
            // Load custom types by root one
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("alfresco.properties");
            Properties properties = new Properties();
            properties.load(input);
            modelName = properties.getProperty("ModelName");
            parentCustomType = properties.getProperty("ParentCustomType");

            // at the begging load session with authorized logged user
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession sessionFaces = (HttpSession) facesContext.getExternalContext().getSession(true);
            session = (Session) sessionFaces.getAttribute("sessionCMIS");

            String customTypeRoot = "D:" + modelName + ":" + parentCustomType;
            ItemIterable<ObjectType> customTypeLst = session.getTypeChildren(customTypeRoot, true);

            for (ObjectType objType : customTypeLst) {
                authorizedTypes.add(new CustomType(objType.getId(), objType.getDisplayName()));
//            System.out.println(objType.getId() + "   " + objType.getDisplayName());
            }
        } catch (IOException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadAllRropertyTypes(Session session, String typeId) throws Exception {
        ObjectType typeDef = session.getTypeDefinition(typeId);
        Map<String, PropertyDefinition<?>> propDefs = typeDef.getPropertyDefinitions();
        UIComponent component = JsfUtil.findComponent("pggrid");
        component.getChildren().clear();
        List<UIComponent> listU = new ArrayList<>();
        customProps = "";

        int index = 1;
        // classic way, loop a Map
        for (Map.Entry<String, PropertyDefinition<?>> entry : propDefs.entrySet()) {
            // Get only custom properities and ignore system
            if (entry.getKey().startsWith(modelName)) {
                System.out.println("Key : " + entry.getKey().split(":")[1] + " ---- Value : " + entry.getValue().getPropertyType());
                List<?> choices = entry.getValue().getChoices();

                if (entry.getValue().getPropertyType().toString().equalsIgnoreCase("STRING")) {
                    OutputLabel lab = new OutputLabel();
                    lab.setValue(entry.getValue().getDisplayName());
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
                    // Append selected columns to customProps var
                    customProps += ", " + modelName + ":" + entry.getKey().split(":")[1];

                    // Add AND / OR component to every condition
                    addBooleanComp(listU, index);
                } else if (entry.getValue().getPropertyType().toString().equalsIgnoreCase("DATETIME")) {
                    OutputLabel lab = new OutputLabel();
                    lab.setValue(entry.getValue().getDisplayName());
                    listU.add(lab);
                    Calendar cal = new Calendar();
                    cal.setId(entry.getKey().split(":")[1] + "000" + entry.getValue().getPropertyType());
                    cal.setPattern("MM/dd/yyyy HH:mm:ss");
                    listU.add(cal);
                    // Append selected columns to customProps var
                    customProps += ", " + modelName + ":" + entry.getKey().split(":")[1];

                    // Add AND / OR component to every condition
                    addBooleanComp(listU, index);
                } else if (entry.getValue().getPropertyType().toString().equalsIgnoreCase("INTEGER")) {
                    OutputLabel lab = new OutputLabel();
                    lab.setValue(entry.getValue().getDisplayName());
                    listU.add(lab);
                    InputText inputText = new InputText();
                    inputText.setId(entry.getKey().split(":")[1] + "000" + entry.getValue().getPropertyType());
                    inputText.setOnkeyup("$(this).val($(this).val().replace(/[^0-9]/g, ''))");
                    listU.add(inputText);
                    // Append selected columns to customProps var
                    customProps += ", " + modelName + ":" + entry.getKey().split(":")[1];

                    // Add AND / OR component to every condition
                    addBooleanComp(listU, index);
                } else if (entry.getValue().getPropertyType().toString().equalsIgnoreCase("DECIMAL")) {
                    OutputLabel lab = new OutputLabel();
                    lab.setValue(entry.getValue().getDisplayName());
                    listU.add(lab);
                    InputText inputText = new InputText();
                    inputText.setId(entry.getKey().split(":")[1] + "000" + entry.getValue().getPropertyType());
                    inputText.setOnkeyup("$(this).val($(this).val().replace(/[^0-9./]/g, ''))");
                    listU.add(inputText);
                    // Append selected columns to customProps var
                    customProps += ", " + modelName + ":" + entry.getKey().split(":")[1];

                    // Add AND / OR component to every condition
                    addBooleanComp(listU, index);
                } else if (entry.getValue().getPropertyType().toString().equalsIgnoreCase("BOOLEAN")) {
                    OutputLabel lab = new OutputLabel();
                    lab.setValue(entry.getValue().getDisplayName());
                    listU.add(lab);
                    SelectBooleanCheckbox checkBox = new SelectBooleanCheckbox();
                    checkBox.setId(entry.getKey().split(":")[1] + "000" + entry.getValue().getPropertyType());
                    listU.add(checkBox);
                    // Append selected columns to customProps var
                    customProps += ", " + modelName + ":" + entry.getKey().split(":")[1];

                    // Add AND / OR component to every condition
                    addBooleanComp(listU, index);
                }
            }
            index++;
        }
        component.getChildren().addAll(listU);
    }

    private void addBooleanComp(List<UIComponent> listU, int index) {
        SelectOneMenu selectOneMenu = new SelectOneMenu();
        selectOneMenu.setId("booleanComp_" + index);

        UISelectItem selectItem1 = new UISelectItem();
        selectItem1.setItemLabel("AND");
        selectItem1.setItemValue("AND");
        selectOneMenu.getChildren().add(selectItem1);

        UISelectItem selectItem2 = new UISelectItem();
        selectItem2.setItemLabel("OR");
        selectItem2.setItemValue("OR");
        selectOneMenu.getChildren().add(selectItem2);

        listU.add(selectOneMenu);
    }

    public void onModelChange() {
        try {
            System.out.println("selected Model------------" + selectedCTID);
            loadAllRropertyTypes(session, selectedCTID);
        } catch (Exception ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void search() {
        String returnedColumns = "cmis:objectId, cmis:versionSeriesId, cmis:creationDate" + customProps;
        String queryStatment = "SELECT " + returnedColumns + " FROM " + modelName + ":" + selectedCTID.split(":")[2];
        String whereCondition = "";
        UIComponent component = JsfUtil.findComponent("pggrid");
        boolean isMatchedType = false;

        String lastBooleanValue = "";
        for (int i = 0; i < component.getChildren().size(); i++) {
            UIComponent comp = component.getChildren().get(i);
            if (comp instanceof InputText && !comp.getId().startsWith("booleanComp_")) {
                InputText inText = (InputText) comp;
                if (inText.getValue() != null && !inText.getValue().toString().isEmpty()) {
                    String propType = inText.getId().split("000")[1];
                    String likeOrEq = (propType.equalsIgnoreCase("string") || propType.equalsIgnoreCase("DATETIME")) ? (" like '%" + inText.getValue().toString() + "%'") : " = " + inText.getValue().toString();
                    if (whereCondition.isEmpty()) {
                        whereCondition += " WHERE " + modelName + ":" + inText.getId().split("000")[0] + likeOrEq;
                    } else {
                        whereCondition += " " + lastBooleanValue + " " + modelName + ":" + inText.getId().split("000")[0] + likeOrEq;
                    }

                    // Set new boolean value (AND / OR)
                    UIComponent booleanComp = component.getChildren().get(i + 1);
                    if (booleanComp instanceof SelectOneMenu && booleanComp.getId().startsWith("booleanComp_")) {
                        SelectOneMenu booleanSelectItem = (SelectOneMenu) booleanComp;
                        lastBooleanValue = " " + booleanSelectItem.getValue() + " ";
                    }
                }
                System.out.println("id---" + inText.getId() + "-------value ---------" + inText.getValue());
                isMatchedType = true;
            } else if (comp instanceof Calendar && !comp.getId().startsWith("booleanComp_")) {
                Calendar cal = (Calendar) comp;
                if (cal.getValue() != null && !cal.getValue().toString().isEmpty()) {
                    String propType = cal.getId().split("000")[1];
                    String likeOrEq = ((propType.equalsIgnoreCase("string") || propType.equalsIgnoreCase("DATETIME")) || propType.equalsIgnoreCase("DATETIME"))? (" like '%" + cal.getValue().toString() + "%'") : " = " + cal.getValue().toString();
                    if (whereCondition.isEmpty()) {
                        whereCondition += " WHERE " + modelName + ":" + cal.getId().split("000")[0] + likeOrEq;
                    } else {
                        whereCondition += " " + lastBooleanValue + " " + modelName + ":" + cal.getId().split("000")[0] + likeOrEq;
                    }

                    // Set new boolean value (AND / OR)
                    UIComponent booleanComp = component.getChildren().get(i + 1);
                    if (booleanComp instanceof SelectOneMenu && booleanComp.getId().startsWith("booleanComp_")) {
                        SelectOneMenu booleanSelectItem = (SelectOneMenu) booleanComp;
                        lastBooleanValue = " " + booleanSelectItem.getValue() + " ";
                    }
                }
                System.out.println("id---" + cal.getId() + "-------value ---------" + cal.getValue());
                isMatchedType = true;
            } else if (comp instanceof InputNumber && !comp.getId().startsWith("booleanComp_")) {
                InputNumber inputNumber = (InputNumber) comp;
                if (inputNumber.getValue() != null && !inputNumber.getValue().toString().isEmpty()) {
                    String propType = inputNumber.getId().split("000")[1];
                    String likeOrEq = (propType.equalsIgnoreCase("string") || propType.equalsIgnoreCase("DATETIME")) ? (" like '%" + inputNumber.getValue().toString() + "%'") : " = " + inputNumber.getValue().toString();
                    if (whereCondition.isEmpty()) {
                        whereCondition += " WHERE " + modelName + ":" + inputNumber.getId().split("000")[0] + likeOrEq;
                    } else {
                        whereCondition += " " + lastBooleanValue + " " + modelName + ":" + inputNumber.getId().split("000")[0] + likeOrEq;
                    }

                    // Set new boolean value (AND / OR)
                    UIComponent booleanComp = component.getChildren().get(i + 1);
                    if (booleanComp instanceof SelectOneMenu && booleanComp.getId().startsWith("booleanComp_")) {
                        SelectOneMenu booleanSelectItem = (SelectOneMenu) booleanComp;
                        lastBooleanValue = " " + booleanSelectItem.getValue() + " ";
                    }
                }
                System.out.println("id---" + inputNumber.getId() + "-------value ---------" + inputNumber.getValue());
                isMatchedType = true;
            } else if (comp instanceof SelectBooleanCheckbox && !comp.getId().startsWith("booleanComp_")) {
                SelectBooleanCheckbox checkBox = (SelectBooleanCheckbox) comp;
                if (checkBox.getValue() != null && !checkBox.getValue().toString().isEmpty()) {
                    String propType = checkBox.getId().split("000")[1];
                    String likeOrEq = (propType.equalsIgnoreCase("string") || propType.equalsIgnoreCase("DATETIME")) ? (" like '%" + checkBox.getValue().toString() + "%'") : " = " + checkBox.getValue().toString();
                    if (whereCondition.isEmpty()) {
                        whereCondition += " WHERE " + modelName + ":" + checkBox.getId().split("000")[0] + likeOrEq;
                    } else {
                        whereCondition += " " + lastBooleanValue + " " + modelName + ":" + checkBox.getId().split("000")[0] + likeOrEq;
                    }

                    // Set new boolean value (AND / OR)
                    UIComponent booleanComp = component.getChildren().get(i + 1);
                    if (booleanComp instanceof SelectOneMenu && booleanComp.getId().startsWith("booleanComp_")) {
                        SelectOneMenu booleanSelectItem = (SelectOneMenu) booleanComp;
                        lastBooleanValue = " " + booleanSelectItem.getValue() + " ";
                    }
                }
                System.out.println("id---" + checkBox.getId() + "-------value ---------" + checkBox.getValue());
                isMatchedType = true;
            }
        }
        if (whereCondition.endsWith(" AND ")) {
            whereCondition = whereCondition.substring(0, whereCondition.lastIndexOf(" AND "));
        } else if (whereCondition.endsWith(" OR ")) {
            whereCondition = whereCondition.substring(0, whereCondition.lastIndexOf(" OR "));
        }
        queryStatment += whereCondition;
        System.out.println("queryStatment = " + queryStatment);
        if (isMatchedType) {
            fillDataTable(queryStatment);
        }
    }

    private void fillDataTable(String queryStatment) {
        String columnTemplateHeader = "";
        columnTemplateKey = "";
        setGenericBeanLst(new ArrayList<GenericBean>());
        ItemIterable<QueryResult> results = session.query(queryStatment, false);
        for (QueryResult hit : results) {
            int index = 1;
            GenericBean genericBean = new GenericBean();
            for (PropertyData<?> propData : hit.getProperties()) {
//                Property property = (Property) (org.apache.chemistry.opencmis.commons.data.Properties) propData;
                String propName = propData.getQueryName();
                Object value = propData.getFirstValue();

                if (value instanceof GregorianCalendar) {
                    GregorianCalendar gc = (GregorianCalendar) value;
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a dd/MM/yyyy");
                    value = sdf.format(gc.getTime());
                }

                // Set table hearder columns
                String displayName = propData.getDisplayName() != null ? propData.getDisplayName() : propName;
                if (!columnTemplateHeader.contains(displayName)) {
                    columnTemplateHeader += columnTemplateHeader.isEmpty() ? displayName : ("," + displayName);
                }

                // Remove unused start tokens
                if (value != null && value.toString() != null && value.toString().startsWith("workspace://SpacesStore/")) {
                    value = value.toString().substring("workspace://SpacesStore/".length());
                }
                setProperities(index, value, genericBean);
                index++;
                System.out.println(displayName + ": " + value);
            }
            genericBeanLst.add(genericBean);
            System.out.println("--------------------------------------");
        }

        // Set table hearder columns
        // reset table state
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":advancedSearchForm:docLst");
        table.setValueExpression("sortBy", null);

        String[] columnHeaders = columnTemplateHeader.split(",");
        String[] columnKeys = columnTemplateKey.split(",");
        columns = new ArrayList<>();

        for (int i = 0; i < columnKeys.length && !columnTemplateKey.isEmpty(); i++) {
            String columnKey = columnKeys[i];
            String columnHeader = columnHeaders[i];
            ColumnModel column = new ColumnModel(columnHeader.toUpperCase(), columnKey);
            columns.add(column);
        }
    }

    // Set all non system properities
    private void setProperities(int index, Object value, GenericBean genericBean) {
        switch (index) {
            case 1:
                genericBean.setProp1(value);
                if (!columnTemplateKey.contains("prop1")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop1" : ("," + "prop1");
                }
                break;
            case 2:
                genericBean.setProp2(value);
                if (!columnTemplateKey.contains("prop2")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop2" : ("," + "prop2");
                }
                break;
            case 3:
                genericBean.setProp3(value);
                if (!columnTemplateKey.contains("prop3")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop3" : ("," + "prop3");
                }
                break;
            case 4:
                genericBean.setProp4(value);
                if (!columnTemplateKey.contains("prop4")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop4" : ("," + "prop4");
                }
                break;
            case 5:
                genericBean.setProp5(value);
                if (!columnTemplateKey.contains("prop5")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop5" : ("," + "prop5");
                }
                break;
            case 6:
                genericBean.setProp6(value);
                if (!columnTemplateKey.contains("prop6")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop6" : ("," + "prop6");
                }
                break;
            case 7:
                genericBean.setProp7(value);
                if (!columnTemplateKey.contains("prop7")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop7" : ("," + "prop7");
                }
                break;
            case 8:
                genericBean.setProp8(value);
                if (!columnTemplateKey.contains("prop8")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop8" : ("," + "prop8");
                }
                break;
            case 9:
                genericBean.setProp9(value);
                if (!columnTemplateKey.contains("prop9")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop9" : ("," + "prop9");
                }
                break;
            case 10:
                genericBean.setProp10(value);
                if (!columnTemplateKey.contains("prop10")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop10" : ("," + "prop10");
                }
                break;
            case 11:
                genericBean.setProp11(value);
                if (!columnTemplateKey.contains("prop11")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop11" : ("," + "prop11");
                }
                break;
            case 12:
                genericBean.setProp12(value);
                if (!columnTemplateKey.contains("prop12")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop12" : ("," + "prop12");
                }
                break;
            case 13:
                genericBean.setProp13(value);
                if (!columnTemplateKey.contains("prop13")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop13" : ("," + "prop13");
                }
                break;
            case 14:
                genericBean.setProp14(value);
                if (!columnTemplateKey.contains("prop14")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop14" : ("," + "prop14");
                }
                break;
            case 15:
                genericBean.setProp15(value);
                if (!columnTemplateKey.contains("prop15")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop15" : ("," + "prop15");
                }
                break;
            case 16:
                genericBean.setProp16(value);
                if (!columnTemplateKey.contains("prop16")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop16" : ("," + "prop16");
                }
                break;
            case 17:
                genericBean.setProp17(value);
                if (!columnTemplateKey.contains("prop17")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop17" : ("," + "prop17");
                }
                break;
            case 18:
                genericBean.setProp18(value);
                if (!columnTemplateKey.contains("prop18")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop18" : ("," + "prop18");
                }
                break;
            case 19:
                genericBean.setProp19(value);
                if (!columnTemplateKey.contains("prop19")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop19" : ("," + "prop19");
                }
                break;
            case 20:
                genericBean.setProp20(value);
                if (!columnTemplateKey.contains("prop20")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop20" : ("," + "prop20");
                }
                break;
            case 21:
                genericBean.setProp21(value);
                if (!columnTemplateKey.contains("prop21")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop21" : ("," + "prop21");
                }
                break;
            case 22:
                genericBean.setProp22(value);
                if (!columnTemplateKey.contains("prop22")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop22" : ("," + "prop22");
                }
                break;
            case 23:
                genericBean.setProp23(value);
                if (!columnTemplateKey.contains("prop23")) {
                    columnTemplateKey += columnTemplateKey.isEmpty() ? "prop23" : ("," + "prop23");
                }
                break;
        }
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
     * @return the authorizedTypes
     */
    public List<CustomType> getAuthorizedTypes() {
        return authorizedTypes;
    }

    /**
     * @param authorizedTypes the authorizedTypes to set
     */
    public void setAuthorizedTypes(List<CustomType> authorizedTypes) {
        this.authorizedTypes = authorizedTypes;
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
     * @return the docLst
     */
    public List<Document> getDocLst() {
        return docLst;
    }

    /**
     * @param docLst the docLst to set
     */
    public void setDocLst(List<Document> docLst) {
        this.docLst = docLst;
    }

    /**
     * @return the filteredGenericBeanLst
     */
    public List<GenericBean> getFilteredGenericBeanLst() {
        return filteredGenericBeanLst;
    }

    /**
     * @param filteredGenericBeanLst the filteredGenericBeanLst to set
     */
    public void setFilteredGenericBeanLst(List<GenericBean> filteredGenericBeanLst) {
        this.filteredGenericBeanLst = filteredGenericBeanLst;
    }

    /**
     * @return the genericBeanLst
     */
    public List<GenericBean> getGenericBeanLst() {
        return genericBeanLst;
    }

    /**
     * @param genericBeanLst the genericBeanLst to set
     */
    public void setGenericBeanLst(List<GenericBean> genericBeanLst) {
        this.genericBeanLst = genericBeanLst;
    }

    /**
     * @return the columns
     */
    public List<ColumnModel> getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    /**
     * @return the columnTemplateKey
     */
    public String getColumnTemplateKey() {
        return columnTemplateKey;
    }

    /**
     * @param columnTemplateKey the columnTemplateKey to set
     */
    public void setColumnTemplateKey(String columnTemplateKey) {
        this.columnTemplateKey = columnTemplateKey;
    }

    /**
     * @return the customProps
     */
    public String getCustomProps() {
        return customProps;
    }

    /**
     * @param customProps the customProps to set
     */
    public void setCustomProps(String customProps) {
        this.customProps = customProps;
    }

}
