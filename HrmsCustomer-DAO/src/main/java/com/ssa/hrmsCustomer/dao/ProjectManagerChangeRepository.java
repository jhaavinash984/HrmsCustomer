package com.ssa.hrmsCustomer.dao;

import javax.transaction.Transactional;

import com.ssa.hrmsCustomer.dto.mysqlentities.ProjectManagerChange;
@Transactional
public interface ProjectManagerChangeRepository extends UserPreviousHistoryBaseRepository<ProjectManagerChange> { }
