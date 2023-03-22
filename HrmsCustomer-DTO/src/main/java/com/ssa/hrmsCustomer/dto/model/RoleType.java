package com.ssa.hrmsCustomer.dto.model;

public enum RoleType {
	
	SYSTEMADMIN(600), CUSTOMERADMIN(500), USER(400);
	
	private Integer roleTypeId;
	
	RoleType(Integer roleTypeId){
		this.roleTypeId = roleTypeId;
	}
	
	public Integer getRoleTypeId(){
		return roleTypeId;
	}

}
