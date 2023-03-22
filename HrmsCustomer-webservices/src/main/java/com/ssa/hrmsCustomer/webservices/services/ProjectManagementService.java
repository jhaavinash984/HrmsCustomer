package com.ssa.hrmsCustomer.webservices.services;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import com.ssa.hrmsCustomer.dao.specifications.ProjectManagementSpecification;
import com.ssa.hrmsCustomer.dto.model.GroupModel;
import com.ssa.hrmsCustomer.dto.model.ProjectDTO;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;


public interface ProjectManagementService {
	
	public void saveOrUpdateProject(ProjectDTO projectDTO, LoggedInUser loggedInUser);

	Map<String, Object> fetchProject(ProjectManagementSpecification projectMgmtSpec, PageRequest pageRequestObj);

}
