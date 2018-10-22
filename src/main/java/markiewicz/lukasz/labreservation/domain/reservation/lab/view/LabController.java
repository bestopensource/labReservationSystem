package markiewicz.lukasz.labreservation.domain.reservation.lab.view;

import markiewicz.lukasz.labreservation.domain.reservation.lab.entity.Lab;
import markiewicz.lukasz.labreservation.domain.reservation.lab.ejb.LabEndpoint;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import makiewicz.lukasz.labreservation.schema.view.SupportFacesConverter;

@Named("labController")
@SessionScoped
public class LabController implements Serializable, SupportFacesConverter<Lab> {
    
    private final LabEndpoint endpoint;

    @Inject
    public LabController(LabEndpoint endpoint) {
        this.endpoint = endpoint;
    }   

    public Lab getSelected() {
        return endpoint.getSelected();
    }

    public void setSelected(Lab selected) {
        endpoint.setSelected(selected);
    }

    public void update() {
        endpoint.update();
    }

    public Lab prepareCreate() {
        return endpoint.prepareCreate();
    }

    public String create() {
        return endpoint.create();
    }

    public void destroy() {
        endpoint.destroy();
    }

    public List<Lab> getItems() {
        return endpoint.getItems();
    }

    @Override
    public Lab getItem(Long id) {
        return endpoint.getEntity(id);
    }
}
