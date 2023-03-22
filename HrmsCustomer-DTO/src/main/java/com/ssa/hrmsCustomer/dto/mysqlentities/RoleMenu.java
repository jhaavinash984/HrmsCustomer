package com.ssa.hrmsCustomer.dto.mysqlentities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ssa.hrmsCustomer.dto.model.Source;

import coms.ssa.hrmsCustomer.dto.entities.key.CreateUpdateDetails;

@Entity
@Table(name = "menu_role_r")
public class RoleMenu implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Embedded
    private CreateUpdateDetails createUpdateDetails;
	
	@Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
	
	@JoinColumn(name = "ORG_ROLE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private OrganisationRole orgRole;
	
	@Column(name = "ORG_ROLE_ID")
	private Integer orgRoleId;
	
	@JoinColumn(name = "SYSTEM_ROLE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private Role systemRole;
	
	@Column(name = "SYSTEM_ROLE_ID")
	private Integer systemRoleId;
	
	@JoinColumn(name = "MENU_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private Menu roleMenuMenu;
	
	@Column(name = "MENU_ID")
	private Integer menuId;
	
	/**@JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID", insertable = false, updatable = false)
	@ManyToOne
	private Group group;
	
	@Column(name = "GROUP_ID")
	private Integer groupId;**/
	 
	@Column(name = "SOURCE_ID")
    private Integer sourceId;
	
	
	
	
	public Role getSystemRole() {
		return systemRole;
	}

	public void setSystemRole(Role systemRole) {
		this.systemRole = systemRole;
	}

	public Integer getSystemRoleId() {
		return systemRoleId;
	}

	public void setSystemRoleId(Integer systemRoleId) {
		this.systemRoleId = systemRoleId;
	}


	public OrganisationRole getOrgRole() {
		return orgRole;
	}

	public void setOrgRole(OrganisationRole orgRole) {
		this.orgRole = orgRole;
	}

	public Integer getOrgRoleId() {
		return orgRoleId;
	}

	public void setOrgRoleId(Integer orgRoleId) {
		this.orgRoleId = orgRoleId;
	}

	public Menu getRoleMenuMenu() {
		return roleMenuMenu;
	}

	public void setRoleMenuMenu(Menu roleMenuMenu) {
		this.roleMenuMenu = roleMenuMenu;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CreateUpdateDetails getCreateUpdateDetails() {
		return createUpdateDetails;
	}

	public void setCreateUpdateDetails(CreateUpdateDetails createUpdateDetails) {
		this.createUpdateDetails = createUpdateDetails;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
	
	
	
}
