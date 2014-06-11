package org.basham.service;

import org.basham.dao.EmployeeDAO;
import org.basham.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("employeeSvc")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDAO employeeDAO;

	public Employee findEmployee(long employeeId) {
		return employeeDAO.findById(employeeId);
	}

	@Transactional
	public void saveEmployee(long employeeId, String name, String surname,
			String jobDescription) throws Exception {
		Employee employee = employeeDAO.findById(employeeId);
		if (employee == null) {
			employee = new Employee(employeeId, name, surname, jobDescription);
			employeeDAO.persist(employee);
		}
	}

	@Transactional
	public void updateEmployee(long employeeId, String name, String surname,
			String jobDescription) throws Exception {
		Employee employee = employeeDAO.findById(employeeId);
		if (employee != null) {
			employee.setEmployeeName(name);
			employee.setEmployeeSurname(surname);
			employee.setJob(jobDescription);
		}
	}

	@Transactional
	public void deleteEmployee(long employeeId) throws Exception {
		Employee employee = employeeDAO.findById(employeeId);
		if (employee != null) {
			employeeDAO.remove(employee);
		}
	}

	@Transactional
	public void saveOrUpdateEmployee(long employeeId, String name,
			String surname, String jobDescription) throws Exception {
		Employee employee = new Employee(employeeId, name, surname, jobDescription);
		employeeDAO.merge(employee);
	}

}
