package com.ssa.hrmsCustomer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssa.hrmsCustomer.dto.mysqlentities.Role;
import com.ssa.hrmsCustomer.dto.mysqlentities.RoleMenu;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	List<Role> findByRoleTypeId(Integer roleTypeId);
	
	

}
