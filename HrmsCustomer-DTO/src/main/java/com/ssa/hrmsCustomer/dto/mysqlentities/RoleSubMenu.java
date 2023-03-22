package com.ssa.hrmsCustomer.dto.mysqlentities;

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
@Table(name = "submenu_role_r")
public class RoleSubMenu {
	
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
	
	@JoinColumn(name = "SUB_MENU_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private SubMenu roleSubMenuSubMenu;
	
	@Column(name = "SUB_MENU_ID")
	private Integer subMenuId;
	
	@Column(name = "SOURCE_ID")
    private Integer sourceId;
	
	/**@JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID", insertable = false, updatable = false)
	@ManyToOne
	private Group group;
	
	@Column(name = "GROUP_ID")
	private Integer groupId;**/
	
	

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

    

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public SubMenu getRoleSubMenuSubMenu() {
		return roleSubMenuSubMenu;
	}

	public void setRoleSubMenuSubMenu(SubMenu roleSubMenuSubMenu) {
		this.roleSubMenuSubMenu = roleSubMenuSubMenu;
	}

	public Integer getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(Integer subMenuId) {
		this.subMenuId = subMenuId;
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
	
	
	

}
