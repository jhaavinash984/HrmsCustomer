package com.ssa.hrmsCustomer.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssa.hrmsCustomer.dto.mysqlentities.Department;
import com.ssa.hrmsCustomer.dto.mysqlentities.SubMenu;

public interface SubMenuRepository extends JpaRepository<SubMenu, Integer>{
	
	List<SubMenu> findByMenuId(Integer menuId);

}
