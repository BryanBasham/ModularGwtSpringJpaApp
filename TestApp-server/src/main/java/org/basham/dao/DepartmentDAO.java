package org.basham.dao;

import org.basham.domain.Department;
import org.springframework.stereotype.Repository;


@Repository("departmentDAO")
public class DepartmentDAO extends AbstractJpaDAO<Long, Department> {

	//
	// Constructor
	//

	public DepartmentDAO() {
		super(Department.class);
	}

}