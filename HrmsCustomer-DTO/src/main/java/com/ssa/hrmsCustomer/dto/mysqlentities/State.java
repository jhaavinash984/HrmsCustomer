package com.ssa.hrmsCustomer.dto.mysqlentities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="state")
public class State implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 3, max = 30)
	@Column(name = "STATE_NAME")
    private String stateName;
	
	@Basic(optional = false)
	@NotNull
	@Size(min = 3, max = 50)
	@Column(name = "CITY_NAME")
    private String cityName;
	
	@Column(name = "COUNTRY_ID")
	private Integer countryId;
	
	@JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private Country country;

}
