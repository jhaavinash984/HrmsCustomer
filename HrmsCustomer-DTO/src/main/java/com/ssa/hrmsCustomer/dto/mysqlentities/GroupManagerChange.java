package com.ssa.hrmsCustomer.dto.mysqlentities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("Group Manager")
public class GroupManagerChange extends UserPreviousHistory {
	
	@Column(name = "GROUP_MANAGER_ID")
	private Integer groupManagerId;
	
	/**@JoinColumn(name = "GROUP_MANAGER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private User userIdGroupManagerChange;**/

}
