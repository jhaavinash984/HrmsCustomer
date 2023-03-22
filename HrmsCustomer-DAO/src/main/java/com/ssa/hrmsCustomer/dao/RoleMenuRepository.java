package com.ssa.hrmsCustomer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssa.hrmsCustomer.dto.mysqlentities.OrganisationRole;
import com.ssa.hrmsCustomer.dto.mysqlentities.RoleMenu;

public interface RoleMenuRepository extends JpaRepository<RoleMenu, Integer> {
	
	List<RoleMenu> findByOrgRoleId(Integer roleId);

	List<RoleMenu> findBySystemRoleIdAndSourceId(Integer systemRoleId,Integer sourceId);
	List<RoleMenu> findByOrgRoleIdAndSourceId(Integer orgRoleId, Integer sourceId);

}
