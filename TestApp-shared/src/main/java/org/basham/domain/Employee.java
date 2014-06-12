package org.basham.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	@Column(name="id")
	private Long employeeId;
	
	@Column(name="employee_name", nullable = false, length=30)
	private String employeeName;
	
	@Column(name="employee_surname", nullable = false, length=30)
	private String employeeSurname;
	
	@Column(name="job", length=50)
	private String job;

	//
	// Constructors
	//

	public Employee() {
	}

	public Employee(Long employeeId) {
		this.employeeId = employeeId;		
	}

	public Employee(Long employeeId, String employeeName, String employeeSurname,
			String job) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.employeeSurname = employeeSurname;
		this.job = job;
	}

	//
	// DomainEntity methods
	//

	@Override
	public Long getId() {
		return employeeId;
	}
	
	@Override
	public boolean isNew() {
		return employeeId == null;
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

	//
    // Object methods
    //

	@Override
	public String toString() {
		return Objects.toStringHelper(Employee.class)
				.add("id", employeeId)
				.add("isNew", isNew())
				.add("employeeName", employeeName)
				.add("job", job)
				.toString();
	}

}