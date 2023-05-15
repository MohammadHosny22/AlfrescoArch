package com.tech.arch.converter;

import com.tech.arch.dao.CustomTypeFacade;
import com.tech.arch.entity.CustomType;
import com.tech.arch.entity.util.JsfUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@FacesConverter(value = "customTypesConverter")
public class CustomTypesConverter implements Converter {

    @Inject
    private CustomTypeFacade ejbFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        if(ejbFacade == null) {
            ejbFacade = new CustomTypeFacade();
        }
        return this.ejbFacade.find(getKey(value));
    }

    java.lang.String getKey(String value) {
        return value;
    }

    String getStringKey(java.lang.String value) {
        StringBuffer sb = new StringBuffer();
        sb.append(value);
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof CustomType) {
            CustomType o = (CustomType) object;
            return o.getCustomTypeId();
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CustomType.class.getName()});
            return null;
        }
    }

}
