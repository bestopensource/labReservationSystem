package markiewicz.lukasz.labreservation.domain.account.view;

import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import markiewicz.lukasz.labreservation.domain.account.entity.RoleSystem;
import markiewicz.lukasz.labreservation.domain.account.ejb.RoleSystemEndpoint;
import makiewicz.lukasz.labreservation.schema.view.SupportFacesConverter;

@Named("roleSystemController")
@SessionScoped
public class RoleSystemController implements Serializable, SupportFacesConverter<RoleSystem> {
    
    private final RoleSystemEndpoint endpoint;

    @Inject
    public RoleSystemController(RoleSystemEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public RoleSystem getSelected() {
        return endpoint.getSelected();
    }

    public void setSelected(RoleSystem selected) {
        endpoint.setSelected(selected);
    }

    public void update() {
        endpoint.update();
    }

    public RoleSystem prepareCreate() {
        return endpoint.prepareCreate();
    }

    public String create() {
        return endpoint.create();
    }

    public void destroy() {
        endpoint.destroy();
    }

    public void setItems(List<RoleSystem> items) {
        endpoint.setItems(items);
    }

    public List<RoleSystem> getItems() {
        return endpoint.getItems();
    }

    @Override
    public RoleSystem getItem(Long id) {
        return endpoint.getEntity(id);
    }

    public List<RoleSystem> getItemsWithoutAdmin() {
        return endpoint.getItemsWithoutAdmin();
    }
}
