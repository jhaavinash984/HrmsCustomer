package com.ssa.hrmsCustomer.webservices.controller;

import java.util.Map;

import javax.validation.Valid;

import com.ssa.hrmsCustomer.common.exception.UserAlreadyExist;
import com.ssa.hrmsCustomer.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.ssa.hrmsCustomer.dao.specifications.DepartmentManagementSpecification;
import com.ssa.hrmsCustomer.dao.specifications.ProjectManagementSpecification;
import com.ssa.hrmsCustomer.dao.specifications.UserManagementSpecification;
import com.ssa.hrmsCustomer.dto.model.DepartmentModel;
import com.ssa.hrmsCustomer.dto.model.MessageDTO;
import com.ssa.hrmsCustomer.dto.model.PageRequestModel;
import com.ssa.hrmsCustomer.dto.model.ProjectDTO;
import com.ssa.hrmsCustomer.dto.model.ResponseModel;
import com.ssa.hrmsCustomer.dto.model.RoleType;
import com.ssa.hrmsCustomer.dto.model.UserManagementDTO;
import com.ssa.hrmsCustomer.dto.mysqlentities.User;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;
import com.ssa.hrmsCustomer.webservices.helper.CommonFunction;
import com.ssa.hrmsCustomer.webservices.services.UserService;

@RestController
@RequestMapping("/webapi/Administration")
public class UserManagementController extends ExceptionHandlerController{

	
	@Autowired 
	CommonFunction commonFunction;
	
	@Autowired
	@Qualifier("systemAdmin")
	private UserService userServiceSystem;
	
	@Autowired
	@Qualifier("customerAdmin")
	private UserService userServiceCustomer;
    @Autowired
    private UserRepository userRepository;


    @PreAuthorize("hasAuthority('SYSTEMADMIN') or hasAuthority('CUSTOMERADMIN')")
	@RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel addUser(@Valid @RequestBody UserManagementDTO user, BindingResult bindingResult) throws hrmsValidationException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    LoggedInUser loggedInUser = (LoggedInUser)auth.getPrincipal();
		ResponseModel respModel=null;
		if (bindingResult.hasErrors()) {
			 throw new hrmsValidationException(bindingResult);
			
		}else{
            User userExist = userRepository.findByUsername(user.getUsername());
            if(userExist != null){
                throw new UserAlreadyExist("User Already Exist");
            }
			try{
				UserManagementDTO newUser = null;
				Integer roleTypeId = loggedInUser.getRole().get(0).getRoleTypeId();
				if(loggedInUser.getRole().get(0).getRoleTypeId().equals(RoleType.SYSTEMADMIN.getRoleTypeId())){
				    newUser=userServiceSystem.saveUser(user,loggedInUser);
				}
				else{
					newUser=userServiceCustomer.saveUser(user,loggedInUser);
				}
				MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
						MessageReader.getProperty("user.create.success"));
				respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
				respModel.setDataModel(newUser);
		        return respModel;
			}
			catch(Exception e){
				MessageDTO msgDTO = new MessageDTO(MessageType.FAILED,
						MessageReader.getProperty("user.create.failure"),e.getMessage());
				respModel = new ResponseModel(Constants.RESULT_FAILED_CODE, msgDTO);
		        return respModel;
			}
				
			}
	}
	
	/**@PreAuthorize("hasAuthority('SYSTEMADMIN') or hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/fetchUser", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel fetchUser(@RequestBody UserManagementDTO user, BindingResult bindingResult) throws hrmsValidationException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    LoggedInUser loggedInUser = (LoggedInUser)auth.getPrincipal();
		ResponseModel respModel=null; 
			try{
				//user.setGroupId(currentUser.getGroupId());
				User newUser=userService.saveUser(user,loggedInUser);
				MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
						MessageReader.getProperty("user.create.success"));
				respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
		        return respModel;
			}
			catch(Exception e){
				MessageDTO msgDTO = new MessageDTO(MessageType.FAILED,
						MessageReader.getProperty("user.create.failure"),e.getMessage());
				respModel = new ResponseModel(Constants.RESULT_FAILED_CODE, msgDTO);
		        return respModel;
			}
				
	 }**/
	
	@PreAuthorize("hasAuthority('SYSTEMADMIN') or hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/fetchUser", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseModel fetchUser(@RequestBody PageRequestModel  pageRequestModel) {
		
		 LoggedInUser loggedInUser = commonFunction.loggedInUser();
		  try{
				ResponseModel respModel=null;
				UserManagementDTO userManagementDTO = new UserManagementDTO();
				if(loggedInUser.getRole().get(0).getRoleTypeId().equals(RoleType.SYSTEMADMIN.getRoleTypeId())){
					userManagementDTO.setRoleTypeId(RoleType.CUSTOMERADMIN.getRoleTypeId());
				}else{
					userManagementDTO.setRoleTypeId(RoleType.USER.getRoleTypeId());
				}
				if((pageRequestModel.getSearchText() != null) && (!pageRequestModel.getSearchText().trim().isEmpty())){
					userManagementDTO = userManagementFilterObject(pageRequestModel.getSearchText().trim());
				}
				PageRequest pageRequestObj = commonFunction.getPageRequestObject(pageRequestModel);
				UserManagementSpecification userMgmtSpec = new UserManagementSpecification(userManagementDTO);
				Map<String, Object> userData = null;
				if(loggedInUser.getRole().get(0).getRoleTypeId().equals(RoleType.SYSTEMADMIN.getRoleTypeId())){
					 userData = userServiceSystem.fetchUser(userMgmtSpec, pageRequestObj,loggedInUser);
				}else{
					userData = userServiceCustomer.fetchUser(userMgmtSpec, pageRequestObj,loggedInUser);
				}
				
				MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
						MessageReader.getProperty("user.fetch.success"));
				respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
				respModel.setDataModel(userData);
		        return respModel;
				}
				catch(Exception e){
					 throw new ApplicationException(MessageReader.getProperty("user.fetch.fail"));
				}
				
	}
	
	@PreAuthorize("hasAuthority('SYSTEMADMIN') or hasAuthority('CUSTOMERADMIN')") 
	@RequestMapping(value = "/checkuser/{username}", method = RequestMethod.GET, produces = "application/json")
	public ResponseModel checkUsername(@PathVariable String username) {
		ResponseModel respModel=null;
		UserManagementDTO existUser = null;
		if(!username.trim().isEmpty()){
			existUser =  userServiceCustomer.fetchUserByUsername(username);
		}
		MessageDTO msgDTO = new MessageDTO(MessageType.SUCCESS,
				MessageReader.getProperty("user.fetch.success"));
		respModel = new ResponseModel(Constants.RESULT_SUCCESS_CODE, msgDTO);
		respModel.setDataModel(existUser);
        return respModel;
		
	}

	@RequestMapping(value="updateUser/{id}",method = RequestMethod.PUT,produces="application/json")
	@ResponseBody
	public ResponseModel updateUser(@PathVariable("id") String userId,@RequestBody UserManagementDTO userManagementDTO){
		LoggedInUser loggedInUser = commonFunction.loggedInUser();
		return userServiceCustomer.updateUser(userId,loggedInUser,userManagementDTO);
	}
	
	
	
	public UserManagementDTO userManagementFilterObject(String searchAttribute){
		UserManagementDTO userManagementDTO = new UserManagementDTO();
		userManagementDTO.setUsername(searchAttribute);
		userManagementDTO.setFirstName(searchAttribute);
		return userManagementDTO;
	}

}
