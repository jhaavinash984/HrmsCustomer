package com.ssa.hrmsCustomer.webservices.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ssa.hrmsCustomer.common.ApplicationProperty;
import com.ssa.hrmsCustomer.dao.ProjectRepository;
import com.ssa.hrmsCustomer.dao.specifications.OrganisationRoleSpecification;
import com.ssa.hrmsCustomer.dao.specifications.ProjectManagementSpecification;
import com.ssa.hrmsCustomer.dto.model.GroupModel;
import com.ssa.hrmsCustomer.dto.model.OrganisationRoleModel;
import com.ssa.hrmsCustomer.dto.model.ProjectDTO;
import com.ssa.hrmsCustomer.dto.mysqlentities.OrganisationRole;
import com.ssa.hrmsCustomer.dto.mysqlentities.Project;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;
import com.ssa.hrmsCustomer.security.util.Encryptor;
import com.ssa.hrmsCustomer.webservices.services.OrganisationRoleService;
import com.ssa.hrmsCustomer.webservices.services.ProjectManagementService;

import coms.ssa.hrmsCustomer.dto.entities.key.CreateUpdateDetails;

@Service
public class ProjectManagementServiceImpl implements ProjectManagementService {
	
	@Autowired
	Encryptor encryptor;
	
	@Autowired
	ProjectRepository projectRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public void saveOrUpdateProject(ProjectDTO projectDTO,LoggedInUser loggedInUser){
		System.out.println("department id :"+encryptor.encrypt("9")+",Group manager id : "+encryptor.encrypt("37")+",Project manager id : "+encryptor.encrypt("38"));
		LocalDate startDate = null;
		LocalDate endDate = null;
		try{
		    startDate = LocalDate.parse(projectDTO.getStartDate());
		    endDate = LocalDate.parse(projectDTO.getEndDate());
		}catch(Exception e){
			System.out.println("date parsing issue while adding group "+e.getMessage());
		}
		ZonedDateTime nowZonedDate = ZonedDateTime.now(TimeZone.getTimeZone(ApplicationProperty.UTCTimeZone).toZoneId());
		LocalDateTime now = nowZonedDate.toLocalDateTime();
		CreateUpdateDetails createUpdateDetails = new CreateUpdateDetails();
		if(projectDTO.getId() == null){
			Project projectNew = new Project();
			projectNew.setName(projectDTO.getName());
			projectNew.setDescription(projectDTO.getDescription());
			projectNew.setStartDate(startDate);
			projectNew.setEndDate(endDate);
			projectNew.setIsActive(0);
			projectNew.setDepartmentId(Integer.parseInt(encryptor.decrypt(projectDTO.getDepartmentId())));
			projectNew.setGroupManagerId(Integer.parseInt(encryptor.decrypt(projectDTO.getGroupManagerId())));
			projectNew.setProjectManagerId(Integer.parseInt(encryptor.decrypt(projectDTO.getProjectManagerId())));
			createUpdateDetails.setCreatedBy(loggedInUser.getId());
			createUpdateDetails.setCreatedDate(now);
			projectNew.setCreateUpdateDetails(createUpdateDetails);
			projectRepo.save(projectNew);	
			
		}else{
			Optional<Project> projectNew = projectRepo.findById(Integer.parseInt(encryptor.decrypt(projectDTO.getId())));
			if(projectNew.isPresent()){
				Project projectEdit = projectNew.get();
				projectEdit.setName(projectDTO.getName());
				projectEdit.setDescription(projectDTO.getDescription());
				projectEdit.setStartDate(startDate);
				projectEdit.setEndDate(endDate);
				projectEdit.setIsActive(projectDTO.getIsActive());
				projectEdit.setDepartmentId(Integer.parseInt(encryptor.decrypt(projectDTO.getDepartmentId())));
				projectEdit.setGroupManagerId(Integer.parseInt(encryptor.decrypt(projectDTO.getGroupManagerId())));
				projectEdit.setProjectManagerId(Integer.parseInt(encryptor.decrypt(projectDTO.getProjectManagerId())));
				createUpdateDetails.setCreatedBy(loggedInUser.getId());
				createUpdateDetails.setCreatedDate(now);
				projectEdit.setCreateUpdateDetails(createUpdateDetails);
				projectRepo.save(projectEdit);
			}
		}
	}
	
	@Override
	public Map<String,Object> fetchProject(ProjectManagementSpecification projectManagementSpec, PageRequest pageRequestObj){
		Map<String, Object> totalData = new HashMap<>();
		List<ProjectDTO> projectListToReturn = new ArrayList<>();
		List<Project> allProject = projectRepo.findAll(projectManagementSpec, pageRequestObj).getContent();
		for(Project project : allProject){
			ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);
			projectDTO.setId(encryptor.encrypt(project.getId().toString()));
			projectDTO.setDepartmentName(project.getDepartmentProject().getDepartmentName());
			projectDTO.setGroupManagerName(project.getUserProjectGroupManager().getUsername());
			projectDTO.setProjectManagerName(project.getUserProjectProjectManager().getUsername());
			projectListToReturn.add(projectDTO);
		}
	   totalData.put("data",projectListToReturn);
	   totalData.put("length", projectRepo.count(projectManagementSpec));
		return totalData;
		
	}

}
