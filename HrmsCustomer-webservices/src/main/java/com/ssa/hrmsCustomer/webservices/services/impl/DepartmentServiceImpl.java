package com.ssa.hrmsCustomer.webservices.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ssa.hrmsCustomer.common.ApplicationProperty;
import com.ssa.hrmsCustomer.dao.DepartmentRepository;
import com.ssa.hrmsCustomer.dao.UserRepository;
import com.ssa.hrmsCustomer.dao.specifications.DepartmentManagementSpecification;
import com.ssa.hrmsCustomer.dto.model.DepartmentModel;
import com.ssa.hrmsCustomer.dto.model.UserManagementDTO;
import com.ssa.hrmsCustomer.dto.mysqlentities.Department;
import com.ssa.hrmsCustomer.dto.mysqlentities.User;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;
import com.ssa.hrmsCustomer.webservices.services.DepartmentService;

import coms.ssa.hrmsCustomer.dto.entities.key.CreateUpdateDetails;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	private DepartmentRepository departmentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public Department saveDepartment(DepartmentModel department, LoggedInUser currentUser){
		Department newDepartment = new Department();
		newDepartment.setDepartmentName(department.getDepartmentName());
		newDepartment.setDepartmentDesc(department.getDepartmentDesc());
		SimpleDateFormat formatter = new SimpleDateFormat(ApplicationProperty.date_format);
		Date dpmtCreationDate = null;
		try{
			dpmtCreationDate = formatter.parse(department.getDepartmentCreationDate());
		}catch(Exception e){
			System.out.println("Exception in Department management date format change"+e);
		}
		newDepartment.setDepartmentCreationDate(dpmtCreationDate);
		CreateUpdateDetails creatorInfo = new CreateUpdateDetails();
		creatorInfo.setCreatedBy(currentUser.getId());
		 LocalDateTime currentDateAndTime = LocalDateTime.ofInstant(new Date().toInstant(), 
                 ZoneId.systemDefault());
		creatorInfo.setCreatedDate(currentDateAndTime);
		newDepartment.setCreateUpdateDetails(creatorInfo);
		Department savedDepartment = departmentRepo.save(newDepartment);
		return savedDepartment;
	}
	
	@Override
	public Map<String,Object> fetchDepartment(DepartmentManagementSpecification deptMgmtSpec, PageRequest pageRequestObj){
		Map<String, Object> totalData = new HashMap<>();
		List<DepartmentModel> departmentListToReturn = new ArrayList<>();
		List<Department> allDepartment = departmentRepo.findAll(deptMgmtSpec, pageRequestObj).getContent();
		if(!allDepartment.isEmpty()){
		   departmentListToReturn = allDepartment.stream().map(value -> modelMapper.map(value, DepartmentModel.class)).collect(Collectors.toList());
		   totalData.put("data",departmentListToReturn);
		   totalData.put("length", departmentRepo.count(deptMgmtSpec));
		}
		return totalData;
		
	}


}
