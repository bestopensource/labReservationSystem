/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.account.view.converter;

import markiewicz.lukasz.labreservation.domain.account.entity.Privilege;
import javax.faces.convert.FacesConverter;
import markiewicz.lukasz.labreservation.schema.view.converter.AbstractFacesConverter;

@FacesConverter(forClass = Privilege.class)
public class PrivilegeFacesConverter extends AbstractFacesConverter {
    
    public PrivilegeFacesConverter() {
        super("privilegeController");
    }
}
