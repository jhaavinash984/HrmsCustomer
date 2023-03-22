package com.ssa.hrmsCustomer.webservices.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.ssa.hrmsCustomer.dto.model.DepartmentModel;
import com.ssa.hrmsCustomer.dto.model.MessageDTO;
import com.ssa.hrmsCustomer.dto.model.PageRequestModel;
import com.ssa.hrmsCustomer.dto.model.ResponseModel;
import com.ssa.hrmsCustomer.dto.model.UserManagementDTO;
import com.ssa.hrmsCustomer.dto.mysqlentities.Department;
import com.ssa.hrmsCustomer.dto.mysqlentities.User;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;
import com.ssa.hrmsCustomer.webservices.helper.CommonFunction;
import com.ssa.hrmsCustomer.webservices.services.DepartmentService;
import com.ssa.hrmsCustomer.webservices.services.UserService;

@RestController
@RequestMapping("/webapi/Administration")
public class DepartmentManagementController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired 
	CommonFunction commonFunction;
	
	
	@PreAuthorize("hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/addDepartment", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel saveDepartment(@Valid @RequestBody DepartmentModel  departmentModel, BindingResult bindingResult) throws hrmsValidationException {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
			ResponseModel respModel=null;
			if (bindingResult.hasErrors()) {
				 throw new hrmsValidationException(bindingResult);
				
			}
			Department newDepartment = departmentService.saveDepartment(departmentModel,loggedInUser);
			MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
					MessageReader.getProperty("department.create.success"));
			respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
	        return respModel;
			}
			catch(Exception e){
				 throw new ApplicationException(MessageReader.getProperty("department.create.fail"));
			}
				
	}
	
	@PreAuthorize("hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/fetchDepartment", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel fetchDepartment(@RequestBody PageRequestModel  pageRequestModel) {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
			ResponseModel respModel=null;
			DepartmentModel departmentModel = new DepartmentModel();
			departmentModel.setGroupId(loggedInUser.getGroupId());
			if((pageRequestModel.getSearchText() != null) && (!pageRequestModel.getSearchText().trim().isEmpty())){
				departmentModel = departmentFilterObject(pageRequestModel.getSearchText().trim());
			}
			PageRequest pageRequestObj = commonFunction.getPageRequestObject(pageRequestModel);
			DepartmentManagementSpecification dptMgmtSpec = new DepartmentManagementSpecification(departmentModel);
			Map<String, Object> departmentData = departmentService.fetchDepartment(dptMgmtSpec, pageRequestObj);
			MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
					MessageReader.getProperty("department.fetch.success"));
			respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
			respModel.setDataModel(departmentData);
	        return respModel;
			}
			catch(Exception e){
				 throw new ApplicationException(MessageReader.getProperty("department.fetch.fail"));
			}
				
	}
	
	public DepartmentModel departmentFilterObject(String searchAttribute){
		DepartmentModel departmentModel = new DepartmentModel();
		departmentModel.setDepartmentName(searchAttribute);
		departmentModel.setDepartmentDesc(searchAttribute);
		return departmentModel;
	}
	

}
