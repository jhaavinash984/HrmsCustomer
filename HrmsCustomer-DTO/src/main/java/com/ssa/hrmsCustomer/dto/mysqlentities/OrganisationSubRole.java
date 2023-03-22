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

@Entity
@Table(name="organisation_sub_role")
public class OrganisationSubRole implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
	@Embedded
    private CreateUpdateDetails createUpdateDetails;
	
	@Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 5, max = 50)
	@Column(name = "SUB_ROLE_NAME")
    private String subRoleName;
	
	@Column(name = "ORG_ROLE_ID")
	private Integer orgRoleId;
	
	@JoinColumn(name = "ORG_ROLE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private OrganisationRole organisationRole;
	
	@OneToMany(mappedBy = "organisationSubRole")
	private List<User> user;
	
	

	public CreateUpdateDetails getCreateUpdateDetails() {
		return createUpdateDetails;
	}

	public void setCreateUpdateDetails(CreateUpdateDetails createUpdateDetails) {
		this.createUpdateDetails = createUpdateDetails;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubRoleName() {
		return subRoleName;
	}

	public void setSubRoleName(String subRoleName) {
		this.subRoleName = subRoleName;
	}

	public Integer getOrgRoleId() {
		return orgRoleId;
	}

	public void setOrgRoleId(Integer orgRoleId) {
		this.orgRoleId = orgRoleId;
	}
	
	

}
