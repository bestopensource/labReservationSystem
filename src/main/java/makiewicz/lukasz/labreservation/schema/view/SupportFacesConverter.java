/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makiewicz.lukasz.labreservation.schema.view;

import makiewicz.lukasz.labreservation.schema.entity.EntityId;

public interface SupportFacesConverter<T extends EntityId> {
    T getItem(Long id);
}
