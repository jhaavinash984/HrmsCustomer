package com.ssa.hrmsCustomer.webservices.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.ssa.hrmsCustomer.dao.OrganisationRoleRepository;
import com.ssa.hrmsCustomer.dao.OrganisationSubRoleRepository;
import com.ssa.hrmsCustomer.dao.specifications.DepartmentManagementSpecification;
import com.ssa.hrmsCustomer.dao.specifications.OrganisationRoleSpecification;
import com.ssa.hrmsCustomer.dto.model.DepartmentModel;
import com.ssa.hrmsCustomer.dto.model.MessageDTO;
import com.ssa.hrmsCustomer.dto.model.OrganisationRoleModel;
import com.ssa.hrmsCustomer.dto.model.PageRequestModel;
import com.ssa.hrmsCustomer.dto.model.ResponseModel;
import com.ssa.hrmsCustomer.dto.mysqlentities.Department;
import com.ssa.hrmsCustomer.dto.mysqlentities.OrganisationRole;
import com.ssa.hrmsCustomer.dto.mysqlentities.OrganisationSubRole;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;
import com.ssa.hrmsCustomer.webservices.helper.CommonFunction;
import com.ssa.hrmsCustomer.webservices.services.DepartmentService;
import com.ssa.hrmsCustomer.webservices.services.OrganisationRoleService;

@RestController
@RequestMapping("/webapi/Administration")
public class RoleManagementController{
	
	@Autowired
	private OrganisationRoleService organisationRoleService;
	
	@Autowired 
	CommonFunction commonFunction;
	
	@Autowired
	private OrganisationSubRoleRepository OrganisationSubRoleRepo;
	
	@Autowired
	private OrganisationRoleRepository organisationRoleRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@PreAuthorize("hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/addRole", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel addRole(@RequestBody List<OrganisationRoleModel> roleObject) {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
			ResponseModel respModel=null;
			organisationRoleService.saveOrganisationRole(roleObject,loggedInUser);
			MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
					MessageReader.getProperty("organisation_role.create.success"));
			respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
	        return respModel;
			}
			catch(Exception e){
				 throw new ApplicationException(MessageReader.getProperty("organisation_role.create.fail"));
			}
				
	}
	
	@PreAuthorize("hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/fetchRole", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel fetchRole(@RequestBody PageRequestModel  pageRequestModel) {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
			ResponseModel respModel=null;
			OrganisationRoleModel organisationRoleModel = new OrganisationRoleModel();
			organisationRoleModel.setGroupId(loggedInUser.getGroupId());
			if(pageRequestModel.getSearchText() !=null && !pageRequestModel.getSearchText().trim().isEmpty()){
				organisationRoleModel = organisationRoleFilterObject(pageRequestModel.getSearchText().trim());
			}
			PageRequest pageRequestObj = commonFunction.getPageRequestObject(pageRequestModel);
			OrganisationRoleSpecification orgRoleSpec = new OrganisationRoleSpecification(organisationRoleModel);
			Map<String, Object> organisationRoleData = organisationRoleService.fetchRole(orgRoleSpec, pageRequestObj);
			MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
					MessageReader.getProperty("organisation_role.fetch.success"));
			respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
			respModel.setDataModel(organisationRoleData);
	        return respModel;
			}
			catch(Exception e){
				 throw new ApplicationException(MessageReader.getProperty("organisation_role.fetch.fail"));
			}
				
	}
	
	@PreAuthorize("hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/fetchSubRole/{roleId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseModel fetchSubRole(@PathVariable Integer roleId) {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
			ResponseModel respModel=null;
			List<OrganisationSubRole> organisationSubRoleClct = OrganisationSubRoleRepo.findByOrgRoleId(roleId);
			MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
					"Fetch Success for subrole based on roleid");
			respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
			respModel.setDataModel(organisationSubRoleClct);
	        return respModel;
			}
			catch(Exception e){
				 throw new ApplicationException("Fetch Not Success for subrole based on roleid");
			}
				
	}
	
	@PreAuthorize("hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/fetchRole", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseModel fetchRole() {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
			ResponseModel respModel=null;
			List<OrganisationRole> organisationSubRoleClct = organisationRoleRepo.findAll();
			List<OrganisationRoleModel> organisationRoleModelClct = organisationSubRoleClct.stream().map(obj -> modelMapper.map(obj,OrganisationRoleModel.class)).collect(Collectors.toList());
			MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
					"Fetch Success for Role");
			respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
			respModel.setDataModel(organisationRoleModelClct);
	        return respModel;
			}
			catch(Exception e){
				 throw new ApplicationException("Fetch Not Success for Role");
			}
				
	}
	
	public OrganisationRoleModel organisationRoleFilterObject(String searchAttribute){
		OrganisationRoleModel organisationRoleModel = new OrganisationRoleModel();
		organisationRoleModel.setRoleName(searchAttribute);
		organisationRoleModel.setLevel(searchAttribute);
		return organisationRoleModel;
	}

}
