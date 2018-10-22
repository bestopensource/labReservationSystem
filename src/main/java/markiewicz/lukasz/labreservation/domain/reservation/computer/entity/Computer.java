/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.computer.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import markiewicz.lukasz.labreservation.domain.reservation.lab.entity.Lab;
import makiewicz.lukasz.labreservation.schema.entity.EntityId;

@Entity
@XmlRootElement
@NamedNativeQueries({
    @NamedNativeQuery(name = "Computer.findAvailable", query = "SELECT * "
                + "FROM computer comp WHERE (comp.id IN (SELECT cres.computer_id "
                + "FROM computerReservation cres WHERE cres.archived=false AND "
                + "((DATE((:startDate)) NOT BETWEEN cres.startDate AND cres.endDate) "
                + "AND (DATE((:endDate)) NOT BETWEEN cres.startDate AND cres.endDate)))) "
                + "OR (comp.id NOT IN (SELECT cres1.computer_id FROM computerReservation cres1 WHERE cres1.computer_id=comp.id))", 
                resultClass = Computer.class),
    @NamedNativeQuery(name = "Computer.findReserved", query = "SELECT * "
                + "FROM computer comp WHERE comp.id IN (SELECT cres.computer_id "
                + "FROM computerReservation cres WHERE cres.archived=false AND "
                + "((DATE((:startDate)) BETWEEN cres.startDate AND cres.endDate) "
                + "OR (DATE((:endDate)) BETWEEN cres.startDate AND cres.endDate)))", 
                resultClass = Computer.class)
})
public class Computer implements EntityId, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String number;
    @NotNull
    @ManyToOne
    private Lab lab;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.number);
        hash = 53 * hash + Objects.hashCode(this.lab);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Computer other = (Computer) obj;
        if (!Objects.equals(this.number, other.number)) {
            return false;
        }
        return Objects.equals(this.lab, other.lab);
    }

    @Override
    public String toString() {
        return number;
    }

}
