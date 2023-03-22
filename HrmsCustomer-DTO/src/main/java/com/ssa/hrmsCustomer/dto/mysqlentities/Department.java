package com.ssa.hrmsCustomer.dto.mysqlentities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
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
@Table(name="department")
public class Department implements Serializable {
	
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
	@Size(min = 2, max = 200)
	@Column(name = "DEPARTMENT_NAME")
    private String departmentName;
	
	@Column(name = "DEPARTMENT_DESC")
    private String departmentDesc;
	
	/**@Column(name = "GROUP_ID")
	private Integer groupId;**/
	
	@Column(name = "DEPARTMENT_CREATION_DATE")
	private Date departmentCreationDate;
	
	/**@JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID", insertable = false, updatable = false)
	@ManyToOne
	private Group group;**/

	@OneToMany(mappedBy = "departmentIdUserHistory")
	private List<UserHistory> userHistoryCollection;
	
	//@OneToMany(mappedBy = "departmentDepartmentChange")
	//private List<DepartmentChange> departmentChange;
    
	public Date getDepartmentCreationDate() {
		return departmentCreationDate;
	}

	public void setDepartmentCreationDate(Date departmentCreationDate) {
		this.departmentCreationDate = departmentCreationDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentDesc() {
		return departmentDesc;
	}

	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	public CreateUpdateDetails getCreateUpdateDetails() {
		return createUpdateDetails;
	}

	public void setCreateUpdateDetails(CreateUpdateDetails createUpdateDetails) {
		this.createUpdateDetails = createUpdateDetails;
	}
}
