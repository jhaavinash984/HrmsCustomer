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
import com.ssa.hrmsCustomer.dto.model.OrganisationRoleModel;
import com.ssa.hrmsCustomer.dto.mysqlentities.Department;
import com.ssa.hrmsCustomer.dto.mysqlentities.OrganisationRole;

public class OrganisationRoleSpecification implements Specification<OrganisationRole> {
	
	private OrganisationRoleModel filter;

	public OrganisationRoleSpecification(OrganisationRoleModel filter) {
		super();
		this.filter = filter;
	}

	@Override
	public Predicate toPredicate(Root<OrganisationRole> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		
		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> searchFilterPredicates = new ArrayList<>();
		if(filter.getGroupId() != null){
			predicates.add(cb.equal(root.<Integer>get("groupId"), filter.getGroupId()));
		}
		if (filter.getRoleName() != null) {
			String containsLikePattern = getContainsLikePattern(filter.getRoleName());
			searchFilterPredicates.add(cb.like(cb.lower(root.<String>get("roleName")), containsLikePattern));
		}
		if (filter.getLevel() != null) {
			String containsLikePattern = getContainsLikePattern(filter.getLevel());
			searchFilterPredicates.add(cb.like(cb.lower(root.<String>get("level")), containsLikePattern));
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
