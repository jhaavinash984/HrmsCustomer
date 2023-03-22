package com.ssa.hrmsCustomer.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssa.hrmsCustomer.dto.mysqlentities.Department;
import com.ssa.hrmsCustomer.dto.mysqlentities.OrganisationRole;
import com.ssa.hrmsCustomer.dto.mysqlentities.OrganisationSubRole;

public interface OrganisationSubRoleRepository extends JpaRepository<OrganisationSubRole, Integer> {
	
	Optional<OrganisationSubRole> findById(Integer id);
	
	List<OrganisationSubRole> findByOrgRoleId(Integer orgRoleId);

}
