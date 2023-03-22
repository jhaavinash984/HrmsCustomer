package com.ssa.hrmsCustomer.webservices.services;

import java.util.List;
import java.util.Map;

import com.ssa.hrmsCustomer.dto.model.MenuModel;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;

public interface MenuSubMenuService {

	void saveFeatureBasedOnRole(Map<String, Map<String, Object>> featureBasedOnRoleObj, LoggedInUser loggedInUser);

	public List<MenuModel> fetchFeatureBasedOnRole(LoggedInUser loggedInUser);

}
