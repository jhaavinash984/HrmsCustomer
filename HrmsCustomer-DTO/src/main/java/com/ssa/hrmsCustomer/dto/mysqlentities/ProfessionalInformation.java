package com.ssa.hrmsCustomer.dto.mysqlentities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="professional_information")
public class ProfessionalInformation implements Serializable{
	
    private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Integer id;
	
	@NotNull
	@Size(min = 5, max = 50)
	@Column(name = "COMPANY_NAME")
    private String companyName;
	
	@NotNull
	@Size(min = 5, max = 50)
	@Column(name = "LAST_DESIGNATION")
    private String lastDesignation;
	
	@Column(name = "START_DATE")
	private LocalDate startDate;
	
	@Column(name = "END_DATE")
	private LocalDate endDate;
	
	/**@Column(name = "USER_ID")
	private Integer userId;**/
	
	@JoinColumn(name = "USER_ID")
	@ManyToOne(cascade = CascadeType.ALL)
	private User userProfessionalInformation;
	
	/**public ProfessionalInformation(String companyName, String lastDesignation, LocalDate startDate, LocalDate endDate){
		this.companyName=companyName;
		this.lastDesignation=lastDesignation;
		this.startDate=startDate;
		this.endDate=endDate;
	}**/
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLastDesignation() {
		return lastDesignation;
	}

	public void setLastDesignation(String lastDesignation) {
		this.lastDesignation = lastDesignation;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	/**public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}**/

	public User getUserProfessionalInformation() {
		return userProfessionalInformation;
	}

	public void setUserProfessionalInformation(User userProfessionalInformation) {
		this.userProfessionalInformation = userProfessionalInformation;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProfessionalInformation)) {
            return false;
        }
        ProfessionalInformation other = (ProfessionalInformation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ssa.hrms.dto.mysqlentities.ProfessionalInformation[ id=" + id + " ]";
    }
	

}
