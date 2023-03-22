package com.ssa.hrmsCustomer.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ssa.hrmsCustomer.dto.mysqlentities.Department;
import com.ssa.hrmsCustomer.dto.mysqlentities.OrganisationRole;
import com.ssa.hrmsCustomer.dto.mysqlentities.User;

public interface OrganisationRoleRepository extends JpaRepository<OrganisationRole, Integer>,JpaSpecificationExecutor<OrganisationRole>{
	
	List<OrganisationRole> findByLevel(String level);
	
	//OrganisationRole findByGroupIdAndLevelAndId(Integer groupId, String level, Integer id);
	
	Optional<OrganisationRole> findById(Integer id);

}
