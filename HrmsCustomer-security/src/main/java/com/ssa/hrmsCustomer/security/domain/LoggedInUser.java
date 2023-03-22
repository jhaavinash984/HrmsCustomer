package com.ssa.hrmsCustomer.security.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.ssa.hrmsCustomer.dto.mysqlentities.Role;

import coms.ssa.hrmsCustomer.dto.entities.key.CreateUpdateDetails;
import lombok.Data;

@Data
public class LoggedInUser extends User{
	
	private Integer id;

	
	private String firstName;
	
	private String lastName;
	
	private Integer groupId;
	
	private String email;
	
	private String mobileNumber;
	
	private char gender;
	
	private Integer isActive;

	private List<Role> role;
	
	private Integer organisationRoleId;
	
	private List<GrantedAuthority> authorities;
	
	private String source;
	
	  public LoggedInUser(String username, String password, Integer id,
		         String firstName, String lastName, String email, String mobileNumber, char gender, Integer isActive, List<Role> role, List<GrantedAuthority> authorities, Integer organisationRoleId, String source) {
	
		             super(username, password, new ArrayList<>());
		             this.id = id;
		             this.firstName = firstName;
		             this.lastName = lastName;
		             this.groupId = groupId;
		             this.email = email;
		             this.mobileNumber = mobileNumber;
		             this.gender = gender;
		             this.isActive = isActive;
		             this.role = role;
		             this.authorities = authorities;
		             this.organisationRoleId = organisationRoleId;
		             this.source = source;
		     }



		

}
