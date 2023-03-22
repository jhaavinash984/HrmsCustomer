package com.ssa.hrmsCustomer.dto.mysqlentities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("Project Manager")
public class ProjectManagerChange extends UserPreviousHistory{
	
	@Column(name = "PROJECT_MANAGER_ID")
	private Integer projectManagerId;
	
	/**@JoinColumn(name = "PROJECT_MANAGER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private User userIdProjectManagerChange;**/

}
