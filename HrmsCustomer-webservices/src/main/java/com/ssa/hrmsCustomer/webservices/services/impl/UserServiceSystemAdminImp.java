package com.ssa.hrmsCustomer.webservices.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssa.hrmsCustomer.dto.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssa.hrmsCustomer.dao.OrganisationRoleRepository;
import com.ssa.hrmsCustomer.dao.RoleRepository;
import com.ssa.hrmsCustomer.dao.UserRepository;
import com.ssa.hrmsCustomer.dao.specifications.UserManagementSpecification;
import com.ssa.hrmsCustomer.dto.mysqlentities.OrganisationRole;
import com.ssa.hrmsCustomer.dto.mysqlentities.PersonalInformation;
import com.ssa.hrmsCustomer.dto.mysqlentities.Role;
import com.ssa.hrmsCustomer.dto.mysqlentities.User;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;
import com.ssa.hrmsCustomer.security.util.Encryptor;
import com.ssa.hrmsCustomer.webservices.helper.CommonFunction;
import com.ssa.hrmsCustomer.webservices.services.UserService;

import coms.ssa.hrmsCustomer.dto.entities.key.CreateUpdateDetails;

@Service
@Qualifier("systemAdmin")
public class UserServiceSystemAdminImp implements UserService{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CommonFunction common;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private OrganisationRoleRepository organisationRoleRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	Encryptor encryptor;
	
	@Override
	public UserManagementDTO saveUser(UserManagementDTO user,LoggedInUser loggedInUser){
		
		User newUser = modelMapper.map(user, User.class);
		String password=common.generateRandomPassword(10);
		common.excelSheetWrite(user.getUsername(),password);
		newUser.setPassword(bcryptEncoder.encode(password));
		newUser.setGender(Gender.getGenderCodeFromValue(user.getGender()));
		List<Role> roles = roleRepo.findByRoleTypeId(RoleType.CUSTOMERADMIN.getRoleTypeId());
		newUser.setRole(roles);
		CreateUpdateDetails creatorInfo = new CreateUpdateDetails();
		creatorInfo.setCreatedBy(loggedInUser.getId());
		 LocalDateTime currentDateAndTime = LocalDateTime.ofInstant(new Date().toInstant(), 
                 ZoneId.systemDefault());
		creatorInfo.setCreatedDate(currentDateAndTime);
		newUser.setCreateUpdateDetails(creatorInfo);
		List<OrganisationRole> organisationRole = organisationRoleRepo.findByLevel("L0");
		newUser.setOrganisationRoleId(organisationRole.get(0).getId());
		if(user.getPersonalInformation()!=null){
			PersonalInformation personalInformation = modelMapper.map(user.getPersonalInformation(), PersonalInformation.class);
			personalInformation.setUserPersonalInformation(newUser);
			
			newUser.setPersonalInformation(personalInformation);
		}
		User createdObject = userRepo.save(newUser);
		UserManagementDTO userManagementDTO = modelMapper.map(createdObject,UserManagementDTO.class);
		return userManagementDTO;
		
	}
	
	@Override
	public Map<String,Object> fetchUser(UserManagementSpecification userManagementSpec, PageRequest pageRequestObj,LoggedInUser loggedInUser){
		Map<String, Object> totalData = new HashMap<>();
		List<UserManagementDTO> userListToReturn = new ArrayList<>();
		List<User> allUsers = userRepo.findAll(userManagementSpec, pageRequestObj).getContent();
		for(User user : allUsers){
			UserManagementDTO userMgmtModel = modelMapper.map(user, UserManagementDTO.class);
			userMgmtModel.setId(encryptor.encrypt(user.getId().toString()));
				//userMgmtModel.setGroupId(encryptor.encrypt(loggedInUser.getGroupId().toString()));
				//userMgmtModel.setGroupName(user.getGroup().getGroupName());
			userListToReturn.add(userMgmtModel);
		}
	    totalData.put("data",userListToReturn);
	    totalData.put("length", userRepo.count(userManagementSpec));
		return totalData;
		
	}
	
	 @Override
	    public UserManagementDTO fetchUserByUsername(String username){
	    	User existUser = userRepo.findByUsernameIgnoreCase(username);
	    	UserManagementDTO userManagementDTO = existUser != null ? modelMapper.map(existUser, UserManagementDTO.class) : null;
	    	return userManagementDTO;
	    }

	@Override
	public ResponseModel updateUser(String userId, LoggedInUser loggedInUser, UserManagementDTO user) {
	   return new ResponseModel();
	}
	
	

}
