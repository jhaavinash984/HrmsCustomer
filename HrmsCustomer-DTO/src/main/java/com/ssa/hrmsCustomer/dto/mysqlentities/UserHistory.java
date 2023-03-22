package com.ssa.hrmsCustomer.dto.mysqlentities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_history")
public class UserHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID", unique = true)
	private Integer id;
	
	@Column(name = "USER_ID")
	private Integer userId;
	
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private User userIdUserHistory;
	
	@Column(name = "PROJECT_MANAGER_ID")
	private Integer projectManagerId;
	
	@JoinColumn(name = "PROJECT_MANAGER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private User projectManagerIdUserHistory;
	
	@Column(name = "GROUP_MANAGER_ID")
	private Integer groupManagerId;
	
	@JoinColumn(name = "GROUP_MANAGER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@OneToOne
	private User groupManagerIdUserHistory;
	
	@Column(name = "DEPARTMENT_ID")
	private Integer departmentId;
	
	@JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	@ManyToOne
	private Department departmentIdUserHistory;
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public User getUserIdUserHistory() {
		return userIdUserHistory;
	}

	public void setUserIdUserHistory(User userIdUserHistory) {
		this.userIdUserHistory = userIdUserHistory;
	}

	public Integer getProjectManagerId() {
		return projectManagerId;
	}

	public void setProjectManagerId(Integer projectManagerId) {
		this.projectManagerId = projectManagerId;
	}

	public User getProjectManagerIdUserHistory() {
		return projectManagerIdUserHistory;
	}

	public void setProjectManagerIdUserHistory(User projectManagerIdUserHistory) {
		this.projectManagerIdUserHistory = projectManagerIdUserHistory;
	}

	public Integer getGroupManagerId() {
		return groupManagerId;
	}

	public void setGroupManagerId(Integer groupManagerId) {
		this.groupManagerId = groupManagerId;
	}

	public User getGroupManagerIdUserHistory() {
		return groupManagerIdUserHistory;
	}

	public void setGroupManagerIdUserHistory(User groupManagerIdUserHistory) {
		this.groupManagerIdUserHistory = groupManagerIdUserHistory;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Department getDepartmentIdUserHistory() {
		return departmentIdUserHistory;
	}

	public void setDepartmentIdUserHistory(Department departmentIdUserHistory) {
		this.departmentIdUserHistory = departmentIdUserHistory;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserHistory)) {
            return false;
        }
        UserHistory other = (UserHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ssa.hrms.dto.mysqlentities.UserManagerR[ id=" + id + " ]";
    }

}
