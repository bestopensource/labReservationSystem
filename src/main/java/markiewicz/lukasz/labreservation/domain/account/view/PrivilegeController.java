package markiewicz.lukasz.labreservation.domain.account.view;

import markiewicz.lukasz.labreservation.domain.account.entity.Privilege;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import markiewicz.lukasz.labreservation.domain.account.ejb.PrivilegeEndpoint;
import makiewicz.lukasz.labreservation.schema.view.SupportFacesConverter;

@Named("privilegeController")
@SessionScoped
public class PrivilegeController implements Serializable, SupportFacesConverter<Privilege> {
    
    private final PrivilegeEndpoint endpoint;

    @Inject
    public PrivilegeController(PrivilegeEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public Privilege getSelected() {
        return endpoint.getSelected();
    }

    public void setSelected(Privilege selected) {
        endpoint.setSelected(selected);
    }

    public void update() {
        endpoint.update();
    }

    public Privilege prepareCreate() {
        return endpoint.prepareCreate();
    }

    public String create() {
        return endpoint.create();
    }

    public void destroy() {
        endpoint.destroy();
    }

    public void setItems(List<Privilege> items) {
        endpoint.setItems(items);
    }

    public List<Privilege> getItems() {
        return endpoint.getItems();
    }

    @Override
    public Privilege getItem(Long id) {
        return endpoint.getEntity(id);
    }
}
