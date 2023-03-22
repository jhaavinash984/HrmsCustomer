package com.ssa.hrmsCustomer.dto.model;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.ssa.hrmsCustomer.dto.mysqlentities.Role;

import coms.ssa.hrmsCustomer.dto.entities.key.CreateUpdateDetails;
import lombok.Data;

@Data
public class UserManagementDTO {
	
	@NotNull
	@Size(min = 1, max = 50)
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String fullName;
	
	private Integer organisationRoleId;
	
	private String organisationRoleName;
	
    private Integer organisationSubRoleId;
	
	private String organisationSubRoleName;
	
	@NotNull
	@Size(min = 5, max = 100)
	private String email;
	
	@NotNull
	@Size(max = 50)
	private String contactNumber;
	
	private String gender;
	
	private Integer isActive = 0;
	
	private String timezone;
	
	private PersonalInformationModel personalInformation;
	
	private List<ProfessionalInformationModel> professionalInformationCollection;
	
	private Integer systemRoleId;
	
	private Integer roleTypeId;

	private String dob;
    
	private String id;
	
	private String departmentId;
	
	private String departmentName;
	
	private String groupManagerId;
	
	private String groupManagerName;
	
	private String projectManagerId;
	
	private String projectManagerName;

	
}
