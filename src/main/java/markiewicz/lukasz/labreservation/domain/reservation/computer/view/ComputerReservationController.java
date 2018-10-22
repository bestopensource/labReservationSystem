package markiewicz.lukasz.labreservation.domain.reservation.computer.view;

import markiewicz.lukasz.labreservation.domain.reservation.computer.entity.ComputerReservation;
import markiewicz.lukasz.labreservation.domain.reservation.computer.ejb.ComputerReservationEndpoint;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import markiewicz.lukasz.labreservation.domain.reservation.computer.ejb.ComputerEndpoint;
import markiewicz.lukasz.labreservation.domain.reservation.computer.entity.Computer;
import makiewicz.lukasz.labreservation.schema.view.SupportFacesConverter;

@Named("computerReservationController")
@SessionScoped
public class ComputerReservationController implements Serializable, SupportFacesConverter<ComputerReservation>  {

    private final ComputerReservationEndpoint endpoint;
    private final ComputerEndpoint computerEndpoint;
    
    @Inject
    public ComputerReservationController(ComputerReservationEndpoint endpoint, ComputerEndpoint labEndpoint) {
        this.endpoint = endpoint;
        this.computerEndpoint = labEndpoint;
    }

    public ComputerReservation getSelected() {
        return endpoint.getSelected();
    }

    public void setSelected(ComputerReservation selected) {
        endpoint.setSelected(selected);
    }

    public void update() {
        endpoint.update();
    }

    public ComputerReservation prepareCreate() {
        return endpoint.prepareCreate();
    }

    public String create() {
        return endpoint.create();
    }

    public List<ComputerReservation> getItems() {
        return endpoint.getItems();
    }

    @Override
    public ComputerReservation getItem(Long id) {
        return endpoint.getEntity(id);
    }

    public List<Computer> getComputersAvailable() {
        Date startDate = endpoint.getSelected().getStartDate();
        Date endDate = endpoint.getSelected().getEndDate();
        return computerEndpoint.findComputersAvailable(startDate, endDate);
    }

    public String cancel() {
        return endpoint.cancel();
    }

    public List<ComputerReservation> findArchivedForUserCurrent(boolean archived) {
        return endpoint.findArchivedForUserCurrent(archived);
    }

    public List<ComputerReservation> findCanceledForUserCurrent(boolean canceled) {
        return endpoint.findCanceledForUserCurrent(canceled);
    }

    public List<ComputerReservation> findCanceledAll(boolean canceled) {
        return endpoint.findCanceledAll(canceled);
    }

    public List<ComputerReservation> findArchivedAll(boolean archived) {
        return endpoint.findArchivedAll(archived);
    }

}
