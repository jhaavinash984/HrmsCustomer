package com.ssa.hrmsCustomer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ssa.hrmsCustomer.dto.mysqlentities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User> {
	
	User findByUsername(String username);
	User findByUsernameIgnoreCase(String username);
	User findByUsernameAndIsActive(String username,Integer isActive);
	Optional<User> findById(Integer id);

}
