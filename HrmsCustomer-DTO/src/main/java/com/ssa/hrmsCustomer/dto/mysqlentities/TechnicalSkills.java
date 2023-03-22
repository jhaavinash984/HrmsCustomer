package com.ssa.hrmsCustomer.dto.mysqlentities;

import coms.ssa.hrmsCustomer.dto.entities.key.CreateUpdateDetails;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="technical_skills")
public class TechnicalSkills implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Embedded
    private CreateUpdateDetails createUpdateDetails;
	
	@Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "SKILL_ID")
    private Integer skillId;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 3, max = 100)
	@Column(name = "SKILL_NAME")
    private String skillName;
	
	@OneToMany(mappedBy = "technicalSkills")
	private List<Job> jobCollection ;

	public CreateUpdateDetails getCreateUpdateDetails() {
		return createUpdateDetails;
	}

	public void setCreateUpdateDetails(CreateUpdateDetails createUpdateDetails) {
		this.createUpdateDetails = createUpdateDetails;
	}

	public Integer getSkillId() {
		return skillId;
	}

	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}	

}
