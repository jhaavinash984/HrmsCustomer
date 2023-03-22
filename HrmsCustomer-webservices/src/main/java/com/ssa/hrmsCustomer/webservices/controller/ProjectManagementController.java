package com.ssa.hrmsCustomer.webservices.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssa.hrmsCustomer.common.Constants;
import com.ssa.hrmsCustomer.common.MessageReader;
import com.ssa.hrmsCustomer.common.MessageType;
import com.ssa.hrmsCustomer.common.exception.ApplicationException;
import com.ssa.hrmsCustomer.common.exception.hrmsValidationException;
import com.ssa.hrmsCustomer.dao.specifications.DepartmentManagementSpecification;
import com.ssa.hrmsCustomer.dao.specifications.ProjectManagementSpecification;
import com.ssa.hrmsCustomer.dto.model.DepartmentModel;
import com.ssa.hrmsCustomer.dto.model.MessageDTO;
import com.ssa.hrmsCustomer.dto.model.PageRequestModel;
import com.ssa.hrmsCustomer.dto.model.ProjectDTO;
import com.ssa.hrmsCustomer.dto.model.ResponseModel;
import com.ssa.hrmsCustomer.dto.mysqlentities.Department;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;
import com.ssa.hrmsCustomer.webservices.helper.CommonFunction;
import com.ssa.hrmsCustomer.webservices.services.ProjectManagementService;

@RestController
@RequestMapping("/webapi/Administration")
public class ProjectManagementController {
	
	@Autowired 
	CommonFunction commonFunction;
	
	@Autowired
	ProjectManagementService projectManagementService;
	
	
	@PreAuthorize("hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/addProject", method = RequestMethod.POST, produces = "application/json")
	public ResponseModel addProject(@RequestBody ProjectDTO  projrctDTO, BindingResult bindingResult) throws hrmsValidationException {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
			ResponseModel respModel=null;
			if (bindingResult.hasErrors()) {
				 throw new hrmsValidationException(bindingResult);
				
			}
			projectManagementService.saveOrUpdateProject(projrctDTO,loggedInUser);
			MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
					MessageReader.getProperty("project.create.success"));
			respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
	        return respModel;
			}
			catch(Exception e){
				 throw new ApplicationException(MessageReader.getProperty("project.create.fail"));
			}
				
	}
	
	@PreAuthorize("hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/fetchProject", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel fetchProject(@RequestBody PageRequestModel  pageRequestModel) {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
			ResponseModel respModel=null;
			ProjectDTO projectDTO = new ProjectDTO();
			projectDTO.setGroupId(loggedInUser.getGroupId());
			if((pageRequestModel.getSearchText() != null) && (!pageRequestModel.getSearchText().trim().isEmpty())){
				projectDTO = projectFilterObject(pageRequestModel.getSearchText().trim());
			}
			PageRequest pageRequestObj = commonFunction.getPageRequestObject(pageRequestModel);
			ProjectManagementSpecification projectMgmtSpec = new ProjectManagementSpecification(projectDTO);
			Map<String, Object> projectData = projectManagementService.fetchProject(projectMgmtSpec, pageRequestObj);
			MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
					MessageReader.getProperty("project.fetch.success"));
			respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
			respModel.setDataModel(projectData);
	        return respModel;
			}
			catch(Exception e){
				 throw new ApplicationException(MessageReader.getProperty("project.fetch.fail"));
			}
				
	}
	
	public ProjectDTO projectFilterObject(String searchAttribute){
		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setName(searchAttribute);
		return projectDTO;
	}

}
