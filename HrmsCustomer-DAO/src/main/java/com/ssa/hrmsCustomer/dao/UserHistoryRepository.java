package com.ssa.hrmsCustomer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ssa.hrmsCustomer.dto.mysqlentities.User;
import com.ssa.hrmsCustomer.dto.mysqlentities.UserHistory;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Integer>,JpaSpecificationExecutor<UserHistory> {

}
