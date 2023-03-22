package com.ssa.hrmsCustomer.dao;

import javax.transaction.Transactional;

import com.ssa.hrmsCustomer.dto.mysqlentities.DepartmentChange;
import com.ssa.hrmsCustomer.dto.mysqlentities.UserPreviousHistory;

@Transactional
public interface UserPreviousHistoryRepository extends UserPreviousHistoryBaseRepository<UserPreviousHistory> {

}
