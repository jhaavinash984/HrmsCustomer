package com.ssa.hrmsCustomer.webservices.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import com.ssa.hrmsCustomer.common.MessageType;
import com.ssa.hrmsCustomer.dto.model.*;
import com.ssa.hrmsCustomer.webservices.helper.DateFormat;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ssa.hrmsCustomer.dao.OrganisationRoleRepository;
import com.ssa.hrmsCustomer.dao.OrganisationSubRoleRepository;
import com.ssa.hrmsCustomer.dao.RoleRepository;
import com.ssa.hrmsCustomer.dao.UserRepository;
import com.ssa.hrmsCustomer.dao.specifications.ProjectManagementSpecification;
import com.ssa.hrmsCustomer.dao.specifications.UserManagementSpecification;
import com.ssa.hrmsCustomer.dto.mysqlentities.OrganisationRole;
import com.ssa.hrmsCustomer.dto.mysqlentities.OrganisationSubRole;
import com.ssa.hrmsCustomer.dto.mysqlentities.PersonalInformation;
import com.ssa.hrmsCustomer.dto.mysqlentities.ProfessionalInformation;
import com.ssa.hrmsCustomer.dto.mysqlentities.Project;
import com.ssa.hrmsCustomer.dto.mysqlentities.Role;
import com.ssa.hrmsCustomer.dto.mysqlentities.User;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;
import com.ssa.hrmsCustomer.security.util.Encryptor;
import com.ssa.hrmsCustomer.webservices.helper.CommonFunction;
import com.ssa.hrmsCustomer.webservices.services.UserService;

import coms.ssa.hrmsCustomer.dto.entities.key.CreateUpdateDetails;

import static com.ssa.hrmsCustomer.common.Constants.DDMMYYYY;
import static com.ssa.hrmsCustomer.common.Constants.YYYYMMDD;
import static com.ssa.hrmsCustomer.common.MessageType.SUCCESS;

@Service
@Qualifier("customerAdmin")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private OrganisationRoleRepository OrganisationRoleRepo;
	
	@Autowired
	private OrganisationSubRoleRepository organisationSubRoleRepo;
	
	@Autowired
	Encryptor encryptor;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	CommonFunction common;

	@Autowired
	DateFormat dateFormat;
	
	@Override
	public UserManagementDTO saveUser(UserManagementDTO user,LoggedInUser loggedInUser){
		User newUser = modelMapper.map(user, User.class);
		newUser.setDob(dateFormat.getLocalDateFromString(user.getDob()));
		String password=common.generateRandomPassword(10);
		common.excelSheetWrite(user.getUsername(),password);
		newUser.setPassword(bcryptEncoder.encode(password));
		newUser.setGender(Gender.getGenderCodeFromValue(user.getGender()));
		//System Role Save
		List<Role> roles = roleRepo.findByRoleTypeId(RoleType.USER.getRoleTypeId());
		newUser.setRole(roles);
		CreateUpdateDetails creatorInfo = new CreateUpdateDetails();
		creatorInfo.setCreatedBy(loggedInUser.getId());
		 LocalDateTime currentDateAndTime = LocalDateTime.ofInstant(new Date().toInstant(), 
                 ZoneId.systemDefault());
		creatorInfo.setCreatedDate(currentDateAndTime);
		newUser.setCreateUpdateDetails(creatorInfo);
		/*Optional<OrganisationRole> organisationRole = OrganisationRoleRepo.findById(user.getOrganisationRoleId());
		newUser.setOrganisationRoleId(organisationRole.get().getId());*/
		if(user.getOrganisationSubRoleId()!=null){
			Optional<OrganisationSubRole> organisationSubRole = organisationSubRoleRepo.findById(user.getOrganisationSubRoleId());
			newUser.setOrganisationSubRoleId(organisationSubRole.get().getId());
		}
		if(user.getPersonalInformation()!=null){
			PersonalInformation personalInformation = modelMapper.map(user.getPersonalInformation(), PersonalInformation.class);
			personalInformation.setUserPersonalInformation(newUser);
			
			newUser.setPersonalInformation(personalInformation);
		}
		if(user.getProfessionalInformationCollection() !=null && !user.getProfessionalInformationCollection().isEmpty()){
			List<ProfessionalInformation> professionalInformationCollection = new ArrayList<>();
			for(ProfessionalInformationModel professionalInformationModel : user.getProfessionalInformationCollection()){
				ProfessionalInformation professionalInformation = modelMapper.map(professionalInformationModel, ProfessionalInformation.class);
				professionalInformation.setStartDate(dateFormat.getLocalDateFromString(professionalInformationModel.getStartDate()));
				professionalInformation.setEndDate(dateFormat.getLocalDateFromString(professionalInformationModel.getEndDate()));
				professionalInformation.setUserProfessionalInformation(newUser);
				professionalInformationCollection.add(professionalInformation);
			}
			newUser.setProfessionalInformationCollection(professionalInformationCollection);
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
			UserManagementDTO userMgmtDto = modelMapper.map(user, UserManagementDTO.class);
			userMgmtDto.setId(encryptor.encrypt(user.getId().toString()));
				//userMgmtModel.setGroupId(encryptor.encrypt(loggedInUser.getGroupId().toString()));
				//userMgmtModel.setGroupName(user.getGroup().getGroupName());
				//userMgmtModel.setGroupManagerId(user.getUserIdCollection() != null ? encryptor.encrypt(user.getUserIdCollection().getUserId().toString()) : null);
				//userMgmtModel.setGroupManagerName(user.getUserIdCollection() != null ? user.getUserIdCollection().getGroupManagerIdUserHistory().getUsername() : null);
				//userMgmtModel.setProjectManagerId(user.getUserIdCollection() != null ? encryptor.encrypt(user.getUserIdCollection().getProjectManagerId().toString()) : null);
				//userMgmtModel.setProjectManagerName(user.getUserIdCollection() != null ? user.getUserIdCollection().getProjectManagerIdUserHistory().getUsername() : null);
				//userMgmtModel.setDepartmentId(user.getUserIdCollection() != null ? encryptor.encrypt(user.getUserIdCollection().getDepartmentId().toString()) : null);
				//userMgmtModel.setDepartmentName(user.getUserIdCollection() != null ? user.getUserIdCollection().getDepartmentIdUserHistory().getDepartmentName() : null);
			userListToReturn.add(userMgmtDto);
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
	public ResponseModel updateUser(String userId,LoggedInUser loggedInUser,UserManagementDTO user){
		ResponseModel responseModel = null;
         Optional<User> userSearch = userRepo.findById(Integer.parseInt(user.getId()));
		 if(userSearch.isPresent()){
			 User userToBeUpdate = userSearch.get();
			 userToBeUpdate.setFirstName(user.getFirstName());
			 userToBeUpdate.setLastName(user.getLastName());
			 userToBeUpdate.setEmail(user.getEmail());
			 userToBeUpdate.setContactNumber(user.getContactNumber());
			 userToBeUpdate.setGender(user.getGender().charAt(0));
			 userToBeUpdate.setDob(dateFormat.getLocalDateFromString(user.getDob()));
			 if(user.getPersonalInformation()!=null){
				 userToBeUpdate.getPersonalInformation().setFatherName(user.getPersonalInformation().getFatherName());
				 userToBeUpdate.getPersonalInformation().setMotherName(user.getPersonalInformation().getMotherName());
				 userToBeUpdate.getPersonalInformation().setAddress(user.getPersonalInformation().getAddress());
				 userToBeUpdate.getPersonalInformation().setPanNumber(user.getPersonalInformation().getPanNumber());
				 userToBeUpdate.getPersonalInformation().setPassportNumber(user.getPersonalInformation().getPassportNumber());
				 userToBeUpdate.getPersonalInformation().setAdharcardNumber(user.getPersonalInformation().getAdharcardNumber());
			 }
			 if(user.getProfessionalInformationCollection() !=null && !user.getProfessionalInformationCollection().isEmpty()){
/*				 List<ProfessionalInformation> professionInfoUpdate = new ArrayList<>();
                 Map<Integer,ProfessionalInformation> mapInDb = userToBeUpdate.getProfessionalInformationCollection().stream().collect(Collectors.toMap(x -> x.getId(),obj -> obj));
				 Map<Integer,ProfessionalInformationModel> mapInRequest = user.getProfessionalInformationCollection().stream().collect(Collectors.toMap(x -> Integer.parseInt(encryptor.decrypt(x.getId())),obj -> obj));*/
				 List<ProfessionalInformation> professionalInformationCollection = new ArrayList<>();
				 for(ProfessionalInformationModel professionalInformationModel : user.getProfessionalInformationCollection()){
					 ProfessionalInformation professionalInformation = modelMapper.map(professionalInformationModel, ProfessionalInformation.class);
					 professionalInformation.setStartDate(dateFormat.getLocalDateFromString(professionalInformationModel.getStartDate()));
					 professionalInformation.setEndDate(dateFormat.getLocalDateFromString(professionalInformationModel.getEndDate()));
					 professionalInformation.setUserProfessionalInformation(userToBeUpdate);
					 professionalInformationCollection.add(professionalInformation);
				 }
				 userToBeUpdate.setProfessionalInformationCollection(professionalInformationCollection);
			 }
			 CreateUpdateDetails updaterInfo = new CreateUpdateDetails();
			 updaterInfo.setUpdatedBy(loggedInUser.getId());
			 LocalDateTime currentDateAndTime = LocalDateTime.ofInstant(new Date().toInstant(),
					 ZoneId.systemDefault());
			 updaterInfo.setUpdatedDate(currentDateAndTime);
			 userToBeUpdate.setCreateUpdateDetails(updaterInfo);
			 User updatedObject = userRepo.save(userToBeUpdate);
			 MessageDTO messageDTO = new MessageDTO( MessageType.SUCCESS,"User Updated Successfully","User");
			 responseModel = new ResponseModel(200, Arrays.asList(user),messageDTO);

		 }
		return responseModel;
	}

}
