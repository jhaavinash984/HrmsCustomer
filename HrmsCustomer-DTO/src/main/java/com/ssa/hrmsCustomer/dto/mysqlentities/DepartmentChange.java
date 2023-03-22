package com.ssa.hrmsCustomer.dto.mysqlentities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("Department")
public class DepartmentChange extends UserPreviousHistory{
	
	@Column(name = "DEPARTMENT_ID")
	private Integer departmentId;
	
	/**@JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private Department departmentDepartmentChange;**/

}
