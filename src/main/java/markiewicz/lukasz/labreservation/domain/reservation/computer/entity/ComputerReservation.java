/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package markiewicz.lukasz.labreservation.domain.reservation.computer.entity;

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
    @NamedNativeQuery(name = "ComputerReservation.findArchivedAll", query = "SELECT * "
                + "FROM computerreservation compr WHERE compr.archived=:archived", 
                resultClass = ComputerReservation.class),
    @NamedNativeQuery(name = "ComputerReservation.findCanceledAll", query = "SELECT * "
                + "FROM computerreservation compr WHERE compr.canceled=:canceled "
                + "AND compr.archived='true'", 
                resultClass = ComputerReservation.class),
    @NamedNativeQuery(name = "ComputerReservation.findCanceledFor", query = "SELECT * "
                + "FROM computerreservation compr WHERE compr.canceled=:canceled AND compr.archived='true' "
                + "AND compr.account_id=:account_id", 
                resultClass = ComputerReservation.class),
    @NamedNativeQuery(name = "ComputerReservation.findArchivedFor", query = "SELECT * "
                + "FROM computerreservation compr WHERE compr.archived=:archived "
                + "AND compr.account_id=:account_id", 
                resultClass = ComputerReservation.class),
    @NamedNativeQuery(name = "ComputerReservation.updateOutOfDate", query = "UPDATE "
                + " computerreservation SET archived=true WHERE NOW() > endDate", 
                resultClass = ComputerReservation.class)
})
public class ComputerReservation implements EntityId, EntityReservation, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @OneToOne
    private Computer computer;
    @NotNull
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
    private boolean archived;
    private boolean canceled;

    public ComputerReservation() {
        this.createDate = new Date();
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
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
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.createDate);
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
        final ComputerReservation other = (ComputerReservation) obj;
        return Objects.equals(this.createDate, other.createDate);
    }

    @Override
    public String toString() {
        return "ComputerReservation{" + "id=" + id + ", computer=" + computer + ", createDate=" + createDate + ", startDate=" + startDate + ", endDate=" + endDate + ", account=" + account + '}';
    }

}
