package com.ssa.hrmsCustomer.dao;

import javax.transaction.Transactional;

import com.ssa.hrmsCustomer.dto.mysqlentities.GroupManagerChange;

@Transactional
public interface GroupManagerChangeRepository extends UserPreviousHistoryBaseRepository<GroupManagerChange> { }
