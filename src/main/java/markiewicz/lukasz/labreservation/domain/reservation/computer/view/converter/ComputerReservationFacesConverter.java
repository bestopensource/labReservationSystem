/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.computer.view.converter;

import markiewicz.lukasz.labreservation.domain.reservation.computer.entity.ComputerReservation;
import javax.faces.convert.FacesConverter;
import markiewicz.lukasz.labreservation.schema.view.converter.AbstractFacesConverter;

@FacesConverter(forClass = ComputerReservation.class)
public class ComputerReservationFacesConverter extends AbstractFacesConverter {

    public ComputerReservationFacesConverter() {
        super("computerReservationController");
    }
}
