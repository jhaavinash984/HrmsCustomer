package com.ssa.hrmsCustomer.dao;

import org.springframework.stereotype.Repository;

import com.ssa.hrmsCustomer.dto.mysqlentities.DepartmentChange;
import com.ssa.hrmsCustomer.dto.mysqlentities.ProjectManagerChange;

@Repository
public interface DepartmentChangeRepository extends UserPreviousHistoryBaseRepository<DepartmentChange> {

}
