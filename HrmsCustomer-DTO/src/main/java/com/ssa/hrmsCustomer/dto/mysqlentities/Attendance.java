package com.ssa.hrmsCustomer.dto.mysqlentities;

import coms.ssa.hrmsCustomer.dto.entities.key.CreateUpdateDetails;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity
public class Attendance implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Embedded
    private CreateUpdateDetails createUpdateDetails;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	@Size(min=3,max=8)
	@Column(name="IN_TIME")
	private String inTime;
	
	@Size(min=3,max=8)
	@Column(name="OUT_TIME")
	private String outTime;
	
	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName="ID")
	private User userAttendance;
	
	@Column(name="ATTENDANCE_DATE")
	private LocalDate attendanceDate;
	
	
	
	
	
	
	
	

}
