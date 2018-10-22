/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.lab.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import markiewicz.lukasz.labreservation.domain.account.entity.Account;
import makiewicz.lukasz.labreservation.schema.entity.EntityId;
import markiewicz.lukasz.labreservation.schema.entity.domain.reservation.EntityReservation;

@Entity
@XmlRootElement
@NamedNativeQueries({
    @NamedNativeQuery(name = "LabReservation.findArchivedAll", query = "SELECT * "
                + "FROM labreservation labr WHERE labr.archived=:archived", 
                resultClass = LabReservation.class),
    @NamedNativeQuery(name = "LabReservation.findCanceledAll", query = "SELECT * "
                + "FROM labreservation labr WHERE labr.canceled=:canceled AND labr.archived='true'", 
                resultClass = LabReservation.class),
    @NamedNativeQuery(name = "LabReservation.findCanceledFor", query = "SELECT * "
                + "FROM labreservation labr WHERE labr.canceled=:canceled AND labr.archived='true' "
                + "AND labr.account_id=:account_id", 
                resultClass = LabReservation.class),
    @NamedNativeQuery(name = "LabReservation.findArchivedFor", query = "SELECT * "
                + "FROM labreservation labr WHERE labr.archived=:archived "
                + "AND labr.account_id=:account_id", 
                resultClass = LabReservation.class),
    @NamedNativeQuery(name = "LabReservation.updateOutOfDate", query = "UPDATE "
            + " labreservation SET archived=true WHERE NOW() > endDate", 
            resultClass = LabReservation.class)
})
public class LabReservation implements EntityId, EntityReservation, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @OneToOne
    private Lab lab;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createDate;
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date startDate;
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endDate;
    @NotNull
    @OneToOne
    private Account account;
    private int workstations;
    private boolean archived;
    private boolean canceled;

    public LabReservation() {
        this.createDate = new Date();
        this.workstations = 1;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getWorkstations() {
        return workstations;
    }

    public void setWorkstations(int workstations) {
        this.workstations = workstations;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.createDate);
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
        final LabReservation other = (LabReservation) obj;
        return Objects.equals(this.createDate, other.createDate);
    }

    @Override
    public String toString() {
        return "LabReservation{" + "id=" + id + ", lab=" + lab + ", createDate=" + createDate + ", account=" + account + ", workstations=" + workstations + '}';
    }

}
