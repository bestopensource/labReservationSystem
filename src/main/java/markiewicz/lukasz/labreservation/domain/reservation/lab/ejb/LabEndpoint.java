/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.lab.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import markiewicz.lukasz.labreservation.domain.reservation.computer.ejb.memory.ComputerInMemoryFromDatabase;
import markiewicz.lukasz.labreservation.domain.reservation.computer.entity.Computer;
import markiewicz.lukasz.labreservation.domain.reservation.lab.ejb.memory.LabInMemoryFromDatabase;
import markiewicz.lukasz.labreservation.schema.ejb.AbstractEndpoint;
import markiewicz.lukasz.labreservation.domain.reservation.lab.entity.Lab;
import markiewicz.lukasz.labreservation.schema.util.JsfTools;

@Stateful
@SessionScoped
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class LabEndpoint extends AbstractEndpoint<Lab> implements Serializable {

    private ComputerInMemoryFromDatabase computerDatabaseInMemory;
    private LabInMemoryFromDatabase databaseInMemory;
    private ResourceBundle bundle;
 
    @Inject
    public void init(LabInMemoryFromDatabase databaseInMemory, ComputerInMemoryFromDatabase computerDatabaseInMemory) {
        super.init(new Lab(), databaseInMemory);
        this.computerDatabaseInMemory = computerDatabaseInMemory;
        this.databaseInMemory = databaseInMemory;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        this.bundle = ResourceBundle.getBundle("Bundle", viewRoot.getLocale());
    }
    
   @Override
    public String create() {
        Lab selected = super.getSelected();
        boolean exists = databaseInMemory
                .getItems()
                .stream()
                .anyMatch(a -> a.equals(selected));
        if(exists) {
            JsfTools.addErrorMessage(bundle.getString("CreateLabExists"));
            return "";
        }
        return super.create();
    }
    
    public List<Lab> findLabsAvailableWhole(Date startDate, Date endDate, int workstation) {
        List<Computer> reservedComputers = computerDatabaseInMemory.findReserved(startDate, endDate);
        System.out.println("startDate: " + startDate);
        System.out.println("endDate: " + endDate);
        System.out.println("reservedComputers: " + reservedComputers);
        List<Lab> availableLabs = databaseInMemory.findAvailable(startDate, endDate);
        System.out.println("availableLabs: " + availableLabs);
        List<Lab> partReservedLabs = toUniqueLabs(reservedComputers);
        availableLabs.removeAll(partReservedLabs);
        availableLabs.removeIf(a -> databaseInMemory.countComputers(a) < workstation);
        System.out.println("availableLabs2: " + availableLabs);
        return availableLabs;
    }
    
    public List<Lab> findLabsAvailablePart(Date startDate, Date endDate) {
        List<Computer> availableComputers = computerDatabaseInMemory.findAvailable(startDate, endDate);
        System.out.println("available: " + availableComputers);
        List<Lab> reservatedLabs = databaseInMemory.findReserved(startDate, endDate);
        System.out.println("labsReservated: " + reservatedLabs);
        List<Lab> availableLabs = toUniqueLabs(availableComputers);
        availableLabs.removeAll(reservatedLabs);
        System.out.println("labsAvailable: " + availableLabs);
        return availableLabs;
    }

    private List<Lab> toUniqueLabs(List<Computer> available) {
        Set<Lab> labsAvailable = new HashSet<>();
        available.forEach(a -> {
            labsAvailable.add(a.getLab());
        });
        return new ArrayList<>(labsAvailable);
    }

    @Override
    public String getCallerPrincipalRoleName() {
        return "";
    }

    public List<Lab> getItems() {
        return databaseInMemory.getItems();
    }

    public Lab getEntity(Long id) {
        return databaseInMemory.getEntity(id);
    }
}
