package org.basham.dao;

import org.basham.domain.Employee;
import org.springframework.stereotype.Repository;


@Repository("employeeDAO")
public class EmployeeDAO extends AbstractJpaDAO<Long, Employee> {

	//
	// Constructor
	//

	public EmployeeDAO() {
		super(Employee.class);
	}

}