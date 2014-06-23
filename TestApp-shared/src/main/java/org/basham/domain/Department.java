package org.basham.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;

@Entity
@Table(name = "department")
public class Department implements DomainEntity<Long> {
	private static final long serialVersionUID = 7440297955003302414L;

	//
	// Attributes
	//

	@Id
	private Long id = createID();
	
	@Column(name="name", nullable = false, length=30)
	private String name;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="department", cascade=CascadeType.ALL)
	private Set<Employee> employees = Sets.newHashSet();
	
	//
	// Constructors
	//

	public Department() {
	}

	public Department(String name) {
		this.name = name;
	}

	//
	// DomainEntity methods
	//

	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public boolean isNew() {
		return id == null;
	}

	//
	// Accessor methods
	//

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Employee> getEmployees() {
		return employees;
	}
	public void addEmployee(final Employee emp) {
		employees.add(emp);
		emp.setDepartment(this);
	}
	
	//
    // Object methods
    //

	@Override
	public String toString() {
		return Objects.toStringHelper(Department.class)
				.add("id", id)
				.add("isNew", isNew())
				.add("name", name)
				.toString();
	}

	private static Long createID() {
		return ++idCounter;
	}
	private static long idCounter;

}