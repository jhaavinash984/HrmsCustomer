package com.ssa.hrmsCustomer.dto.model;

import java.util.List;

import lombok.Data;

@Data
public class MenuModel {
	
	private String id;
	private String menuName;
	private String menuLink;
	private List<SubMenuModel> subMenu;

}
