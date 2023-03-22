package com.ssa.hrmsCustomer.webservices.services;

import java.util.Map;

import org.springframework.data.domain.PageRequest;

import com.ssa.hrmsCustomer.dao.specifications.DepartmentManagementSpecification;
import com.ssa.hrmsCustomer.dto.model.DepartmentModel;
import com.ssa.hrmsCustomer.dto.mysqlentities.Department;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;

public interface DepartmentService {
	
	public Department saveDepartment(DepartmentModel department, LoggedInUser currentUser);

	Map<String, Object> fetchDepartment(DepartmentManagementSpecification deptMgmtSpec, PageRequest pageRequestObj);

}
