package com.ssa.hrmsCustomer.dto.mysqlentities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import coms.ssa.hrmsCustomer.dto.entities.key.CreateUpdateDetails;

@Entity
@Table(name = "project")
public class Project implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "PROJECT_ID", unique = true)
	private Integer id;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "NAME",unique = true)
	private String name;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 25, max = 500)
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "START_DATE")
	private LocalDate startDate;
	
	@Column(name = "END_DATE")
	private LocalDate endDate;
	
	@Column(name = "IS_ACTIVE")
	private Integer isActive;
	
	@Column(name = "DEPARTMENT_ID")
	private Integer departmentId;
	
	@JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private Department departmentProject;
	
	@Column(name = "GROUP_MANAGER_ID")
	private Integer groupManagerId;
	
	@JoinColumn(name = "GROUP_MANAGER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private User userProjectGroupManager;
	
	@Column(name = "PROJECT_MANAGER_ID")
	private Integer projectManagerId;
	
	@JoinColumn(name = "PROJECT_MANAGER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private User userProjectProjectManager;
	
	/**@Column(name = "GROUP_ID")
	private Integer groupId;
	
	@JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID", insertable = false, updatable = false)
	@ManyToOne
	private Group group;**/
	
	@Embedded
    private CreateUpdateDetails createUpdateDetails;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Department getDepartmentProject() {
		return departmentProject;
	}

	public void setDepartmentProject(Department departmentProject) {
		this.departmentProject = departmentProject;
	}

	public Integer getGroupManagerId() {
		return groupManagerId;
	}

	public void setGroupManagerId(Integer groupManagerId) {
		this.groupManagerId = groupManagerId;
	}

	public User getUserProjectGroupManager() {
		return userProjectGroupManager;
	}

	public void setUserProjectGroupManager(User userProjectGroupManager) {
		this.userProjectGroupManager = userProjectGroupManager;
	}

	public Integer getProjectManagerId() {
		return projectManagerId;
	}

	public void setProjectManagerId(Integer projectManagerId) {
		this.projectManagerId = projectManagerId;
	}

	public User getUserProjectProjectManager() {
		return userProjectProjectManager;
	}

	public void setUserProjectProjectManager(User userProjectProjectManager) {
		this.userProjectProjectManager = userProjectProjectManager;
	}

	public CreateUpdateDetails getCreateUpdateDetails() {
		return createUpdateDetails;
	}

	public void setCreateUpdateDetails(CreateUpdateDetails createUpdateDetails) {
		this.createUpdateDetails = createUpdateDetails;
	}


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Project)) 
        {
            return false;
        }
        Project other = (Project) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ssa.hrms.dto.mysqlentities.Project[ id=" + id + " ]";
    }
	
	

}
