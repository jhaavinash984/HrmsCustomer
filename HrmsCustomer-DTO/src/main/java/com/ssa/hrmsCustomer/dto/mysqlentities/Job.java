package com.ssa.hrmsCustomer.dto.mysqlentities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import coms.ssa.hrmsCustomer.dto.entities.key.CreateUpdateDetails;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "job")
public class Job implements Serializable {
	
   private static final long serialVersionUID = 1L;
	
	@Embedded
    private CreateUpdateDetails createUpdateDetails;
	
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "JOB_ID")
	private Integer jobId;
	
	@NotEmpty
	@Size(max = 500)
	@Column(name = "JOB_DESCRIPTION")
	private String jobDescription;
	
	@NotEmpty
	@Size(max = 50)
	@Column(name = "LOCATION")
	private String location;
	
	@Column(name = "POSITION")
	private String position;
	
	@NotNull
	@Column(name = "TECHNICAL_SKILL_ID",nullable=false)
	private Integer technicalSkillId;
	
	@JoinColumn(name = "TECHNICAL_SKILL_ID", referencedColumnName = "SKILL_ID", insertable = false, updatable = false)
	@ManyToOne
	private TechnicalSkills technicalSkills;
	
	@NotNull
	@Column(name = "EXPERIENCE_REQUIRED",nullable=false)
	private String experienceRequired;
	
	@NotNull
	@Column(name = "HR_ID",nullable=false)
	private Integer hrId;
	
	@JoinColumn(name = "HR_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private User userHR;
	
	@NotNull
	@Column(name = "MANAGER_ID",nullable=false)
	private Integer managerId;
	
	@JoinColumn(name = "MANAGER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private User userManager;

	public CreateUpdateDetails getCreateUpdateDetails() {
		return createUpdateDetails;
	}

	public void setCreateUpdateDetails(CreateUpdateDetails createUpdateDetails) {
		this.createUpdateDetails = createUpdateDetails;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getHrId() {
		return hrId;
	}

	public void setHrId(Integer hrId) {
		this.hrId = hrId;
	}

	public User getUserHR() {
		return userHR;
	}

	public void setUserHR(User userHR) {
		this.userHR = userHR;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public User getUserManager() {
		return userManager;
	}

	public void setUserManager(User userManager) {
		this.userManager = userManager;
	}

	

	public String getExperienceRequired() {
		return experienceRequired;
	}

	public void setExperienceRequired(String experienceRequired) {
		this.experienceRequired = experienceRequired;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getTechnicalSkillId() {
		return technicalSkillId;
	}

	public void setTechnicalSkillId(Integer technicalSkillId) {
		this.technicalSkillId = technicalSkillId;
	}

	public TechnicalSkills getTechnicalSkills() {
		return technicalSkills;
	}

	public void setTechnicalSkills(TechnicalSkills technicalSkills) {
		this.technicalSkills = technicalSkills;
	}
	
	
	

}
