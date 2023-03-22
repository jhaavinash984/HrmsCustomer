package com.ssa.hrmsCustomer.webservices.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssa.hrmsCustomer.common.Constants;
import com.ssa.hrmsCustomer.common.MessageReader;
import com.ssa.hrmsCustomer.common.MessageType;
import com.ssa.hrmsCustomer.common.exception.ApplicationException;
import com.ssa.hrmsCustomer.dto.model.MenuModel;
import com.ssa.hrmsCustomer.dto.model.MessageDTO;
import com.ssa.hrmsCustomer.dto.model.ResponseModel;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;
import com.ssa.hrmsCustomer.webservices.helper.CommonFunction;
import com.ssa.hrmsCustomer.webservices.services.MenuSubMenuService;
import com.ssa.hrmsCustomer.webservices.services.OrganisationRoleService;

@RestController
@RequestMapping("/webapi/Administration")
public class MenuSubMenuController {
	
	@Autowired
	private MenuSubMenuService menuSubMenuService;
	
	@Autowired 
	CommonFunction commonFunction;
	
	
	@PreAuthorize("hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/addFeaturesToRole", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel saveFeatureBasedOnRole(@RequestBody Map<String, Map<String, Object>> featureBasedOnRoleObj) {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
			ResponseModel respModel=null;
			menuSubMenuService.saveFeatureBasedOnRole(featureBasedOnRoleObj,loggedInUser);
			MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
					MessageReader.getProperty("feature_based_on_role.create.success"));
			respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
	        return respModel;
			}
			catch(Exception e){
				 throw new ApplicationException(MessageReader.getProperty("feature_based_on_role.create.fail"));
			}
				
	}
	
	@PreAuthorize("hasAuthority('CUSTOMERADMIN') or hasAuthority('SYSTEMADMIN')") 
	@RequestMapping(value = "/fetchFeaturesToRole", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseModel fetchFeatureBasedOnRole() {
		
	    LoggedInUser loggedInUser = commonFunction.loggedInUser();
		try{
			ResponseModel respModel=null;
			List<MenuModel> menu = menuSubMenuService.fetchFeatureBasedOnRole(loggedInUser);
			MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
					MessageReader.getProperty("feature_based_on_role.fetch.success"));
			respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
			respModel.setDataModel(menu);
	        return respModel;
			}
			catch(Exception e){
				 throw new ApplicationException(MessageReader.getProperty("feature_based_on_role.fetch.fail"));
			}
				
	}

}
