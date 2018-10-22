/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.schema.view.converter;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import makiewicz.lukasz.labreservation.schema.entity.EntityId;
import makiewicz.lukasz.labreservation.schema.view.SupportFacesConverter;

public abstract class AbstractFacesConverter implements Converter<Object> {
    
    private final String controllerName;

    public AbstractFacesConverter(String controllerName) {
        this.controllerName = controllerName;
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        /*System.out.println("controllerName[getAsObject]: " + controllerName);
        System.out.println("value[getAsObject]: " + value);*/
        if (value == null || value.length() == 0)
            return null;
        SupportFacesConverter<?> controller = (SupportFacesConverter<?>)facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, controllerName);
        return controller.getItem(getKey(value));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        /*System.out.println("controllerName[getAsString]: " + controllerName);
        System.out.println("object[getAsString]: " + object);*/
        if (object == null)
            return null;
        if (object instanceof EntityId) {
            EntityId obj = (EntityId) object;
            return getStringKey(obj.getId());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EntityId.class.getName()});
            return null;
        }
    }
    
    private Long getKey(String value) {
        java.lang.Long key;
        key = Long.valueOf(value);
        return key;
    }

    private String getStringKey(java.lang.Long value) {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        return sb.toString();
    }
}