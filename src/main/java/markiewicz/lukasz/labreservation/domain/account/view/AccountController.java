package markiewicz.lukasz.labreservation.domain.account.view;

import markiewicz.lukasz.labreservation.domain.account.entity.Account;
import markiewicz.lukasz.labreservation.domain.account.ejb.AccountEndpoint;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;
import makiewicz.lukasz.labreservation.schema.view.SupportFacesConverter;

@Named("accountController")
@SessionScoped
public class AccountController implements Serializable, SupportFacesConverter<Account> {

    private final AccountEndpoint endpoint;

    @Inject
    public AccountController(AccountEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public String create() {
        return endpoint.create();
    }

    public Account getSelected() {
        return endpoint.getSelected();
    }

    public void setSelected(Account selected) {
        endpoint.setSelected(selected);
    }

    public void update() {
        endpoint.update();
    }

    public Account prepareCreate() {
        return endpoint.prepareCreate();
    }

    public void destroy() {
        endpoint.destroy();
    }

    public void setItems(List<Account> items) {
        endpoint.setItems(items);
    }

    public List<Account> getItems() {
        return endpoint.getItems();
    }

    @Override
    public Account getItem(Long id) {
        return endpoint.getEntity(id);
    }

    public String activate() {
        return endpoint.activate();
    }
    
    public String deactivate() {
        return endpoint.deactivate();
    }
    
    public boolean isActivated() {
        return endpoint.isActvated();
    }
    
    public boolean isDeactivated() {
        return !endpoint.isActvated();
    }

}
