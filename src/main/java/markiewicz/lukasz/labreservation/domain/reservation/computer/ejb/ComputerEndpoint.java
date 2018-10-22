/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.computer.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import markiewicz.lukasz.labreservation.domain.reservation.computer.entity.Computer;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import markiewicz.lukasz.labreservation.domain.reservation.computer.ejb.memory.ComputerInMemoryFromDatabase;
import markiewicz.lukasz.labreservation.schema.ejb.AbstractEndpoint;
import markiewicz.lukasz.labreservation.domain.reservation.lab.ejb.memory.LabInMemoryFromDatabase;
import markiewicz.lukasz.labreservation.domain.reservation.lab.entity.Lab;
import markiewicz.lukasz.labreservation.schema.util.JsfTools;

@Stateful
@SessionScoped
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ComputerEndpoint extends AbstractEndpoint<Computer> implements Serializable {

    private ComputerInMemoryFromDatabase databaseInMemory;
    private LabInMemoryFromDatabase labDatabaseInMemory;
    private ResourceBundle bundle;

    @Inject
    public void init(ComputerInMemoryFromDatabase databaseInMemory, LabInMemoryFromDatabase labDatabaseInMemory) {
        super.init(new Computer(), databaseInMemory);
        this.databaseInMemory = databaseInMemory;
        this.labDatabaseInMemory = labDatabaseInMemory;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        this.bundle = ResourceBundle.getBundle("Bundle", viewRoot.getLocale());
    }
    
    @Override
    public String create() {
        Computer selected = super.getSelected();
        boolean exists = databaseInMemory
                .getItems()
                .stream()
                .anyMatch(a -> a.equals(selected));
        if(exists) {
            JsfTools.addErrorMessage(bundle.getString("CreateComputerExists"));
            return "";
        }
        return super.create();
    }

    @Override
    protected String getCallerPrincipalRoleName() {
        return "";
    }
    
    public List<Computer> findComputersAvailable(Date startDate, Date endDate) {
        List<Computer> available = databaseInMemory.findAvailable(startDate, endDate);
        System.out.println("available: " + available);
        List<Lab> labsReservated = labDatabaseInMemory.findReserved(startDate, endDate);
        System.out.println("labsReservated: " + labsReservated);
        List<Computer> labs = toUniqueComputers(labsReservated);
        available.removeAll(labs);
        System.out.println("labs: " + available);
        return available;
    }

    private List<Computer> toUniqueComputers(List<Lab> available) {
        Set<Computer> computersAvailable = new HashSet<>();
        available.forEach(a -> {
            computersAvailable.addAll(a.getComputers());
        });
        return new ArrayList<>(computersAvailable);
    }

    public List<Computer> getItems() {
        return databaseInMemory.getItems();
    }

    public Computer getEntity(Long id) {
        return databaseInMemory.getEntity(id);
    }
}
