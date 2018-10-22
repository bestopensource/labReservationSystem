/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.account.view.converter;

import markiewicz.lukasz.labreservation.domain.account.entity.Account;
import markiewicz.lukasz.labreservation.schema.view.converter.AbstractFacesConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Account.class)
public class AccountFacesConverter extends AbstractFacesConverter {

    public AccountFacesConverter() {
        super("accountController");
    }
}
