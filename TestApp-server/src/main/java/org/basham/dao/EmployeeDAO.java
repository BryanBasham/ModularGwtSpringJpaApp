package org.basham.dao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.basham.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("employeeDAO")
public class EmployeeDAO extends JpaDAO<Long, Employee> {
	
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@PostConstruct
	public void init() {
		super.setEntityManagerFactory(entityManagerFactory);
	}
	
}