package com.ssa.hrmsCustomer.webservices.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ssa.hrmsCustomer.dao.OrganisationRoleRepository;
import com.ssa.hrmsCustomer.dao.specifications.DepartmentManagementSpecification;
import com.ssa.hrmsCustomer.dao.specifications.OrganisationRoleSpecification;
import com.ssa.hrmsCustomer.dto.model.DepartmentModel;
import com.ssa.hrmsCustomer.dto.model.OrganisationRoleModel;
import com.ssa.hrmsCustomer.dto.mysqlentities.Department;
import com.ssa.hrmsCustomer.dto.mysqlentities.OrganisationRole;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;
import com.ssa.hrmsCustomer.security.util.Encryptor;
import com.ssa.hrmsCustomer.webservices.helper.CommonFunction;
import com.ssa.hrmsCustomer.webservices.services.OrganisationRoleService;
import com.ssa.hrmsCustomer.webservices.services.UserService;

import coms.ssa.hrmsCustomer.dto.entities.key.CreateUpdateDetails;

@Service
public class OrganisationRoleServiceImpl implements OrganisationRoleService {
	
	@Autowired
	OrganisationRoleRepository OrganisationRoleRepo;
	
	@Autowired 
	CommonFunction commonFunction;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Encryptor encryptor;
	
	@Override
	public void saveOrganisationRole(List<OrganisationRoleModel> organisationRole, LoggedInUser loggedInUser){
		CreateUpdateDetails creatorInfo = commonFunction.creatorInfo(loggedInUser);
		List<OrganisationRole> organisationRoles = OrganisationRoleRepo.findByLevel("L0");
		if(organisationRoles.isEmpty()){
			OrganisationRole newOrganisationRole = new OrganisationRole();
			newOrganisationRole.setLevel("L0");
			newOrganisationRole.setRoleName("CUSTOMERADMIN");
			newOrganisationRole.setCreateUpdateDetails(creatorInfo);
			OrganisationRoleRepo.save(newOrganisationRole);
			
		}
		organisationRole.forEach(obj->{
			OrganisationRole newOrganisationRole = new OrganisationRole();
			newOrganisationRole.setLevel(obj.getLevel());
			newOrganisationRole.setRoleName(obj.getRoleName());
			newOrganisationRole.setCategory(obj.getCategoryId());
			newOrganisationRole.setCreateUpdateDetails(creatorInfo);
			OrganisationRoleRepo.save(newOrganisationRole);
		});
		
	}
	
	@Override
	public Map<String,Object> fetchRole(OrganisationRoleSpecification organisationRoleSpec, PageRequest pageRequestObj){
		Map<String, Object> totalData = new HashMap<>();
		List<OrganisationRoleModel> organisationRoleListToReturn = new ArrayList<>();
		List<OrganisationRole> allRole = OrganisationRoleRepo.findAll(organisationRoleSpec, pageRequestObj).getContent();
		if(!allRole.isEmpty()){
		   organisationRoleListToReturn = allRole.stream().map(value -> modelMapper.map(value, OrganisationRoleModel.class)).collect(Collectors.toList());
		   organisationRoleListToReturn.forEach(value -> value.setId(encryptor.encrypt(value.getId())));
		   totalData.put("data",organisationRoleListToReturn);
		   totalData.put("length", OrganisationRoleRepo.count(organisationRoleSpec));
		}
		return totalData;
		
	}

}
