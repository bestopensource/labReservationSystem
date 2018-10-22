/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.lab.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import markiewicz.lukasz.labreservation.domain.reservation.computer.entity.Computer;
import makiewicz.lukasz.labreservation.schema.entity.EntityId;

@Entity
@XmlRootElement
@NamedNativeQueries({
    @NamedNativeQuery(name = "Lab.findAvailable", query = "SELECT * "
                + "FROM lab lab WHERE (lab.id IN (SELECT lres.lab_id "
                + "FROM labreservation lres WHERE lres.archived=false AND "
                + "((DATE((:startDate)) NOT BETWEEN lres.startdate AND lres.enddate) "
                + "AND (DATE((:endDate)) NOT BETWEEN lres.startdate AND lres.enddate)))) "
                + "OR (lab.id NOT IN (SELECT lres1.lab_id FROM labReservation lres1 WHERE lres1.lab_id=lab.id))", resultClass = Lab.class),
    @NamedNativeQuery(name = "Lab.findReserved", query = "SELECT * "
                + "FROM lab WHERE id IN (select lres.lab_id "
                + "FROM labreservation lres WHERE lres.archived=false AND "
                + "((DATE((:startDate)) BETWEEN lres.startdate AND lres.enddate) "
                + "OR (DATE((:endDate)) BETWEEN lres.startdate AND lres.enddate)))", resultClass = Lab.class)
})
public class Lab implements EntityId, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String number;
    @OneToMany(mappedBy = "lab")
    private Collection<Computer> computers;

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

    @XmlTransient
    public Collection<Computer> getComputers() {
        return computers;
    }

    public void setComputers(Collection<Computer> computers) {
        this.computers = computers;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.number);
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
        final Lab other = (Lab) obj;
        return Objects.equals(this.number, other.number);
    }

    @Override
    public String toString() {
        return number;
    }

}
