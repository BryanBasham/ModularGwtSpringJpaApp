package org.basham.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.common.base.Objects;

@Entity
@Table(name = "employee")
public class Employee implements DomainEntity<Long> {
	private static final long serialVersionUID = 7440297955003302414L;

	//
	// Attributes
	//

	@Id
	private Long id = createID();
	
	@Column(name="employee_name", nullable = false, length=30)
	private String employeeName;
	
	@Column(name="employee_surname", nullable = false, length=30)
	private String employeeSurname;
	
	@Column(name="job", length=50)
	private String job;

	@ManyToOne(fetch=FetchType.EAGER)
	private Department department;

	//
	// Constructors
	//

	public Employee() {
	}

	public Employee(String employeeName, String employeeSurname, String job) {
		this.employeeName = employeeName;
		this.employeeSurname = employeeSurname;
		this.job = job;
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

	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeSurname() {
		return employeeSurname;
	}
	public void setEmployeeSurname(String employeeSurname) {
		this.employeeSurname = employeeSurname;
	}

	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}

	//
    // Object methods
    //

	@Override
	public String toString() {
		return Objects.toStringHelper(Employee.class)
				.add("id", id)
				.add("isNew", isNew())
				.add("employeeName", employeeName)
				.add("job", job)
				.toString();
	}

	private static Long createID() {
		return ++idCounter;
	}
	private static long idCounter;

}