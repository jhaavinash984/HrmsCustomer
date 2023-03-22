package com.ssa.hrmsCustomer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssa.hrmsCustomer.dto.mysqlentities.Department;
import com.ssa.hrmsCustomer.dto.mysqlentities.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

}
