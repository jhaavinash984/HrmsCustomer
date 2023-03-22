package com.ssa.hrmsCustomer.dto.mysqlentities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="country")
public class Country implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 3, max = 20)
	@Column(name = "COUNTRY_NAME")
    private String countryName;
	
	@OneToMany(mappedBy = "country")
	private List<State> stateClct;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	

}
