package com.ssa.hrmsCustomer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.ssa.hrmsCustomer.dto.mysqlentities.Department;
import com.ssa.hrmsCustomer.dto.mysqlentities.User;

public interface DepartmentRepository extends JpaRepository<Department, Integer>,JpaSpecificationExecutor<Department>{

}
