package com.ssa.hrmsCustomer.webservices.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;

import com.ssa.hrmsCustomer.dao.specifications.OrganisationRoleSpecification;
import com.ssa.hrmsCustomer.dto.model.OrganisationRoleModel;
import com.ssa.hrmsCustomer.security.domain.LoggedInUser;

public interface OrganisationRoleService {

	void saveOrganisationRole(List<OrganisationRoleModel> organisationRole, LoggedInUser loggedInUser);

	Map<String, Object> fetchRole(OrganisationRoleSpecification organisationRoleSpec, PageRequest pageRequestObj);

}
