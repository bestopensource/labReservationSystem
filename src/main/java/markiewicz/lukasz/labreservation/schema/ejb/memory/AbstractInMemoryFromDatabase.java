/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.schema.ejb.memory;

import java.util.List;
import markiewicz.lukasz.labreservation.schema.ejb.memory.facade.AbstractFacade;
import makiewicz.lukasz.labreservation.schema.entity.EntityId;

public abstract class AbstractInMemoryFromDatabase<T extends EntityId> {

    private List<T> items = null;
    protected abstract AbstractFacade<T> getFacade();

    public List<T> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return getFacade().findAll();
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public T getEntity(Long id) {
        return getItems().stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }
    
    public void edit(T object) {
        getFacade().edit(object);
        reset();
    }
    
    public void remove(T object) {
        getFacade().remove(object);
        reset();
    }
    
    public void reset() {
        this.items = null;
    }
}
