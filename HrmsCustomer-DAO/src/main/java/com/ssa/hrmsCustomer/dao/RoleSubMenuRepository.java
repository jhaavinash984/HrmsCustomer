package com.ssa.hrmsCustomer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssa.hrmsCustomer.dto.mysqlentities.RoleMenu;
import com.ssa.hrmsCustomer.dto.mysqlentities.RoleSubMenu;

public interface RoleSubMenuRepository extends JpaRepository<RoleSubMenu, Integer> {
	
	List<RoleSubMenu> findByOrgRoleId(Integer roleId);

	List<RoleSubMenu> findBySystemRoleIdAndSourceId(Integer roleId,Integer sourceId);
	List<RoleSubMenu> findByOrgRoleIdAndSourceId(Integer orgRoleId, Integer sourceId);

}
