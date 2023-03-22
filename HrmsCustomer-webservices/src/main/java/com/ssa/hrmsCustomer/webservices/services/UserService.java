package com.ssa.hrmsCustomer.webservices.services;

import java.util.Map;

import com.ssa.hrmsCustomer.dto.model.ResponseModel;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ssa.hrmsCustomer.dao.specifications.UserManagementSpecification;
import com.ssa.hrmsCustomer.dto.model.UserManagementDTO;
import com.ssa.hrmsCustomer.dto.mysqlentities.User;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;


public interface UserService {
	
	public UserManagementDTO saveUser(UserManagementDTO user,LoggedInUser loggedInUser);

	public Map<String, Object> fetchUser(UserManagementSpecification userManagementSpec, PageRequest pageRequestObj,
			LoggedInUser loggedInUser);
	
	public UserManagementDTO fetchUserByUsername(String username);

	public ResponseModel updateUser(String userId, LoggedInUser loggedInUser, UserManagementDTO user);

}
