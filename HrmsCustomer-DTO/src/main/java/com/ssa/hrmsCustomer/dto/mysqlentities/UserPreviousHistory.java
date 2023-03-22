package com.ssa.hrmsCustomer.dto.mysqlentities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ACTIVITY")
public class UserPreviousHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Integer id;
	 
	@Column(name = "USER_ID")
	private Integer userId;
	
//	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
//	@ManyToOne
//	private User userIdUserPreviousHistory;
//	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	

}