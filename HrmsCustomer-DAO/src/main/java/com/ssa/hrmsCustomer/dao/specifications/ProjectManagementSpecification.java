package com.ssa.hrmsCustomer.dao.specifications;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ssa.hrmsCustomer.dto.model.DepartmentModel;
import com.ssa.hrmsCustomer.dto.model.ProjectDTO;
import com.ssa.hrmsCustomer.dto.mysqlentities.Department;
import com.ssa.hrmsCustomer.dto.mysqlentities.Project;

public class ProjectManagementSpecification implements Specification<Project>{
	
	private ProjectDTO filter;

	public ProjectManagementSpecification(ProjectDTO filter) {
		super();
		this.filter = filter;
	}

	@Override
	public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		
		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> searchFilterPredicates = new ArrayList<>();
		if(filter.getGroupId() != null){
			predicates.add(cb.equal(root.<Integer>get("groupId"), filter.getGroupId()));
		}
		if (filter.getName() != null) {
			Path<String> projectName = root.<String>get("name");
			String containsLikePattern = getContainsLikePattern(filter.getName());
			searchFilterPredicates.add(cb.like(cb.lower(projectName), containsLikePattern));
		}
		Predicate mandatoryPredicates = null;
		Predicate searchPredicates = null;
		if (!predicates.isEmpty()) {
			mandatoryPredicates = cb.and(predicates.toArray(new Predicate[] {}));
		}
		if (!searchFilterPredicates.isEmpty()) {
			searchPredicates = cb.or(searchFilterPredicates.toArray(new Predicate[] {}));
		}
		if (null == mandatoryPredicates) {
			return searchPredicates;
		} else if (null == searchPredicates) {
			return mandatoryPredicates;
		} else {
			return cb.and(mandatoryPredicates, searchPredicates);
		}
	}

	private static String getContainsLikePattern(String searchTerm) {
		if (searchTerm == null || searchTerm.isEmpty()) {
			return "%";
		} else {
			return "%" + searchTerm.toLowerCase() + "%";
		}
	}
	

}
