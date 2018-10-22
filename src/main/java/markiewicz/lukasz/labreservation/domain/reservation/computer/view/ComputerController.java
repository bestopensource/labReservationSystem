package markiewicz.lukasz.labreservation.domain.reservation.computer.view;

import markiewicz.lukasz.labreservation.domain.reservation.computer.ejb.ComputerEndpoint;
import markiewicz.lukasz.labreservation.domain.reservation.computer.entity.Computer;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import makiewicz.lukasz.labreservation.schema.view.SupportFacesConverter;

@Named("computerController")
@SessionScoped
public class ComputerController implements Serializable, SupportFacesConverter<Computer>  {

    private final ComputerEndpoint endpoint;
    
    @Inject
    public ComputerController(ComputerEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public Computer getSelected() {
        return endpoint.getSelected();
    }

    public void setSelected(Computer selected) {
        endpoint.setSelected(selected);
    }

    public void update() {
        endpoint.update();
    }

    public Computer prepareCreate() {
        return endpoint.prepareCreate();
    }

    public String create() {
        return endpoint.create();
    }

    public void destroy() {
        endpoint.destroy();
    }

    public List<Computer> getItems() {
        return endpoint.getItems();
    }

    @Override
    public Computer getItem(Long id) {
        return endpoint.getEntity(id);
    }
}
