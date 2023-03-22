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
@Table(name="organisation_role")
public class OrganisationRole implements Serializable {
	
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
	@Size(min = 2, max = 100)
	@Column(name = "ROLE_NAME")
    private String roleName;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 2, max = 10)
	@Column(name = "LEVEL")
    private String level;
	
	@Column(name = "CATEGORY_ID")
	private Integer categoryId;
	
	/**@Column(name = "GROUP_ID")
	private Integer groupId;
	
	@JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID", insertable = false, updatable = false)
	@ManyToOne
	private Group group;**/
	
	@OneToMany(mappedBy = "orgRole")
	private List<RoleMenu> roleMenu;
	
	@OneToMany(mappedBy = "orgRole")
	private List<RoleSubMenu> roleSubMenu;
	
	@OneToMany(mappedBy = "organisationRole")
	private List<User> user;
	
	

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<RoleMenu> getRoleMenu() {
		return roleMenu;
	}

	public void setRoleMenu(List<RoleMenu> roleMenu) {
		this.roleMenu = roleMenu;
	}

	public List<RoleSubMenu> getRoleSubMenu() {
		return roleSubMenu;
	}

	public void setRoleSubMenu(List<RoleSubMenu> roleSubMenu) {
		this.roleSubMenu = roleSubMenu;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategory(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	

}
