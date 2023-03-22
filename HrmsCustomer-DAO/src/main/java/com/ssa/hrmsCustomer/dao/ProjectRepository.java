package com.ssa.hrmsCustomer.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.ssa.hrmsCustomer.dto.mysqlentities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>,JpaSpecificationExecutor<Project>{
	
	Optional<Project> findById(Integer id);

}
