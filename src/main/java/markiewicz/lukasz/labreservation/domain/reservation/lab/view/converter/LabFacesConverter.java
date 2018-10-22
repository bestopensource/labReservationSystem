/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.lab.view.converter;

import markiewicz.lukasz.labreservation.domain.reservation.lab.entity.Lab;
import javax.faces.convert.FacesConverter;
import markiewicz.lukasz.labreservation.schema.view.converter.AbstractFacesConverter;

@FacesConverter(forClass = Lab.class)
public class LabFacesConverter extends AbstractFacesConverter {
    
    public LabFacesConverter() {
        super("labController");
    }
}
