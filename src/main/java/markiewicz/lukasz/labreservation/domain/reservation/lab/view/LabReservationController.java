package markiewicz.lukasz.labreservation.domain.reservation.lab.view;

import markiewicz.lukasz.labreservation.domain.reservation.lab.entity.LabReservation;
import markiewicz.lukasz.labreservation.domain.reservation.lab.ejb.LabReservationEndpoint;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import markiewicz.lukasz.labreservation.domain.reservation.lab.ejb.LabEndpoint;
import markiewicz.lukasz.labreservation.domain.reservation.lab.entity.Lab;
import makiewicz.lukasz.labreservation.schema.view.SupportFacesConverter;

@Named("labReservationController")
@SessionScoped
public class LabReservationController implements Serializable, SupportFacesConverter<LabReservation>  {

    private final LabReservationEndpoint endpoint;
    private final LabEndpoint labEndpoint;
    
    @Inject
    public LabReservationController(LabReservationEndpoint endpoint, LabEndpoint labEndpoint) {
        this.endpoint = endpoint;
        this.labEndpoint = labEndpoint;
    }

    public LabReservation getSelected() {
        return endpoint.getSelected();
    }

    public void setSelected(LabReservation selected) {
        endpoint.setSelected(selected);
    }

    public void update() {
        endpoint.update();
    }

    public LabReservation prepareCreate() {
        return endpoint.prepareCreate();
    }

    public String create() {
        return endpoint.create();
    }

    public void destroy() {
        endpoint.destroy();
    }

    public List<LabReservation> getItems() {
        return endpoint.getItems();
    }

    @Override
    public LabReservation getItem(Long id) {
        return endpoint.getEntity(id);
    }

    public List<Lab> getLabsAvailablePart() {
        Date startDate = endpoint.getSelected().getStartDate();
        Date endDate = endpoint.getSelected().getEndDate();
        return labEndpoint.findLabsAvailablePart(startDate, endDate);
    }
    
    public List<Lab> getLabsAvailableWhole() {
        Date startDate = endpoint.getSelected().getStartDate();
        Date endDate = endpoint.getSelected().getEndDate();
        int workstations = endpoint.getSelected().getWorkstations();
        return labEndpoint.findLabsAvailableWhole(startDate, endDate, workstations);
    }

    public String cancel() {
        return endpoint.cancel();
    }

    public List<LabReservation> findArchivedForUserCurrent(boolean archived) {
        return endpoint.findArchivedForUserCurrent(archived);
    }

    public List<LabReservation> findCanceledForUserCurrent(boolean canceled) {
        return endpoint.findCanceledForUserCurrent(canceled);
    }

    public List<LabReservation> findCanceledAll(boolean canceled) {
        return endpoint.findCanceledAll(canceled);
    }

    public List<LabReservation> findArchivedAll(boolean archived) {
        return endpoint.findArchivedAll(archived);
    }
}
