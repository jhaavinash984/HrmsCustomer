package com.ssa.hrmsCustomer.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.ssa.hrmsCustomer.dto.mysqlentities.UserPreviousHistory;
@NoRepositoryBean
public interface UserPreviousHistoryBaseRepository<T extends UserPreviousHistory> 
extends CrudRepository<T, Integer> {

  public T findByUserId(Integer userId);
  
} 
