/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.schema.ejb;

import markiewicz.lukasz.labreservation.schema.ejb.memory.AbstractInMemoryFromDatabase;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import markiewicz.lukasz.labreservation.schema.util.JsfTools;
import markiewicz.lukasz.labreservation.schema.util.JsfTools.PersistAction;
import makiewicz.lukasz.labreservation.schema.entity.EntityId;


public abstract class AbstractEndpoint<T extends EntityId> {

    private Class<T> type;
    private T selected;
    private AbstractInMemoryFromDatabase<T> databaseInMemory;

    protected void init(T selected, AbstractInMemoryFromDatabase<T> databaseInMemory) {
        this.selected = selected;
        this.type = (Class<T>) selected.getClass();
        this.databaseInMemory = databaseInMemory;
    }
    
    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        this.selected = selected;
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString(selected.getClass().getSimpleName() + "Updated"));
        isFacesValidationOkThenResetItems();
    }

    public T prepareCreate() {
        try {
            this.selected = type.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(AbstractEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.selected;
    }

    public String create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString(selected.getClass().getSimpleName() + "Created"));
        return resume();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString(selected.getClass().getSimpleName() + "Deleted"));
        isFacesValidationOkThenResetSelected();
        isFacesValidationOkThenResetItems();
    }
    
    protected String getCallerPrincipalRoleName() {
        return "";
    }
    
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                persistExecute(persistAction, successMessage);
            } catch (Exception ex) {
                persistFailed(ex);
            }
        }
    }
    
    private void persistFailed(Exception ex) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        JsfTools.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
    }
    
    private void persistExecute(JsfTools.PersistAction persistAction, String successMessage) throws Exception {
        if (persistAction != JsfTools.PersistAction.DELETE) {
            databaseInMemory.edit(selected);
        } else {
            databaseInMemory.remove(selected);
        }
        JsfTools.addSuccessMessage(successMessage);
    }

    private boolean isFacesValidationOkThenResetSelected() {
        if (isFacesValidationOk()) {
            try {
                this.selected = this.type.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(AbstractEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
        return false;
    }
    
    private String resume() {
        if (isFacesValidationOkThenResetItems()) {
            return getCallerPrincipalRoleName() + "/success";
        } 
        JsfTools.addErrorMessage("ERROR");
        return getCallerPrincipalRoleName() + "/failed";
    }
    
    private boolean isFacesValidationOkThenResetItems() {
        if (isFacesValidationOk()) {
            databaseInMemory.reset();
            return true;
        }
        return false;
    }

    private static boolean isFacesValidationOk() {
        return !JsfTools.isValidationFailed();
    }
}

