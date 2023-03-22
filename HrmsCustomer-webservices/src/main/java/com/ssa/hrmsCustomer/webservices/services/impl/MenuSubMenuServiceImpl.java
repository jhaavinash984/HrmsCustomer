package com.ssa.hrmsCustomer.webservices.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssa.hrmsCustomer.dao.RoleMenuRepository;
import com.ssa.hrmsCustomer.dao.RoleSubMenuRepository;
import com.ssa.hrmsCustomer.dao.SubMenuRepository;
import com.ssa.hrmsCustomer.dto.model.MenuModel;
import com.ssa.hrmsCustomer.dto.model.OrganisationRoleModel;
import com.ssa.hrmsCustomer.dto.model.Source;
import com.ssa.hrmsCustomer.dto.model.SubMenuModel;
import com.ssa.hrmsCustomer.dto.mysqlentities.RoleMenu;
import com.ssa.hrmsCustomer.dto.mysqlentities.RoleSubMenu;
import com.ssa.hrmsCustomer.dto.mysqlentities.SubMenu;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;
import com.ssa.hrmsCustomer.security.util.Encryptor;
import com.ssa.hrmsCustomer.webservices.helper.CommonFunction;
import com.ssa.hrmsCustomer.webservices.services.MenuSubMenuService;

import coms.ssa.hrmsCustomer.dto.entities.key.CreateUpdateDetails;

@Service
public class MenuSubMenuServiceImpl implements MenuSubMenuService  {
	
	@Autowired
	RoleMenuRepository roleMenuRepo;
	
	@Autowired
	RoleSubMenuRepository roleSubMenuRepo;
	
	@Autowired
	SubMenuRepository subMenuRepo;
	
	@Autowired
	Encryptor encryptor;
	
	@Autowired 
	CommonFunction commonFunction;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public void saveFeatureBasedOnRole(Map<String, Map<String, Object>> featureBasedOnRoleObj, LoggedInUser loggedInUser){
		String roleId = featureBasedOnRoleObj.entrySet().iterator().next().getKey();
		CreateUpdateDetails creatorInfo = commonFunction.creatorInfo(loggedInUser);
		Map<String, Object> menuAndSubmenu = featureBasedOnRoleObj.entrySet().iterator().next().getValue();
		for(Map.Entry<String, Object> entry : menuAndSubmenu.entrySet()){
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setOrgRoleId(Integer.parseInt(encryptor.decrypt(roleId)));
			roleMenu.setMenuId(Integer.parseInt(encryptor.decrypt(entry.getKey())));
			roleMenu.setCreateUpdateDetails(creatorInfo);
			roleMenuRepo.save(roleMenu);
			if(entry.getValue()!=null){
				List<String> subMenu = (List<String>)entry.getValue();
				subMenu.forEach(value ->{
					RoleSubMenu roleSubMenu = new RoleSubMenu();
					roleSubMenu.setOrgRoleId(Integer.parseInt(encryptor.decrypt(roleId)));
					roleSubMenu.setSubMenuId(Integer.parseInt(encryptor.decrypt(value)));
					roleSubMenu.setCreateUpdateDetails(creatorInfo);
					roleSubMenuRepo.save(roleSubMenu);
				});
			}
			
			
		}
	}
	
	@Override
	public List<MenuModel> fetchFeatureBasedOnRole(LoggedInUser loggedInUser){

		List<MenuModel> finalListToreturn = new ArrayList<>();
		//List<RoleMenu> roleMenuList = roleMenuRepo.findByOrgRoleId(loggedInUser.getOrganisationRoleId());
		List<RoleMenu> roleMenuList = null;
	    Integer sourceId = loggedInUser.getSource().equals("hrms") ? Source.hrms.getSourceId() : Source.hrmsCustomer.getSourceId();
		List<RoleSubMenu> subMenuBasedOnrole = new ArrayList<>();
		if(loggedInUser.getRole().get(0).getRoleName().equals("CUSTOMERADMIN")){
			roleMenuList = roleMenuRepo.findBySystemRoleIdAndSourceId(loggedInUser.getRole().get(0).getId(), sourceId);
			subMenuBasedOnrole = roleSubMenuRepo.findBySystemRoleIdAndSourceId(loggedInUser.getRole().get(0).getId(), sourceId);
		}else {
			roleMenuList = roleMenuRepo.findByOrgRoleIdAndSourceId(loggedInUser.getOrganisationRoleId(), sourceId);
			subMenuBasedOnrole = roleSubMenuRepo.findByOrgRoleIdAndSourceId(loggedInUser.getOrganisationRoleId(), sourceId);
		}
		//List<RoleSubMenu> subMenuBasedOnrole = roleSubMenuRepo.findByOrgRoleId(loggedInUser.getOrganisationRoleId());
		List<Integer> subMenuIds = subMenuBasedOnrole.stream().map(value -> value.getSubMenuId()).collect(Collectors.toList());
		if(!roleMenuList.isEmpty()){
			for(RoleMenu roleMenuVal : roleMenuList){
				MenuModel menuModel = new MenuModel();
				menuModel.setId(encryptor.encrypt(String.valueOf(roleMenuVal.getMenuId())));
				menuModel.setMenuName(roleMenuVal.getRoleMenuMenu().getMenuName());
				menuModel.setMenuLink(roleMenuVal.getRoleMenuMenu().getMenuLink());
				List<SubMenu> subMenu = subMenuRepo.findByMenuId(roleMenuVal.getMenuId());
				subMenu = subMenu.stream().filter(value -> subMenuIds.contains(value.getId())).collect(Collectors.toList());
				List<SubMenuModel> subMenuModel = new ArrayList<>();
				if(!subMenu.isEmpty()){
					subMenuModel = subMenu.stream().map(value -> modelMapper.map(value, SubMenuModel.class)).collect(Collectors.toList());
				}
				menuModel.setSubMenu(subMenuModel); 
				finalListToreturn.add(menuModel);
			}
			
		}
		return finalListToreturn;
	}

}
