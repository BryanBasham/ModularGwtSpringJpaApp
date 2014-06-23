package org.basham.service;

import org.apache.log4j.Logger;
import org.basham.dao.EmployeeDAO;
import org.basham.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("employeeSvc")
public class EmployeeServiceImpl implements EmployeeService {
	private static final Logger LOG = Logger.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeDAO employeeDAO;

	//
	// EmployeeService methods
	//

	@Override
	@Transactional(readOnly = true)
	public Employee findEmployee(long id) {
		LOG.debug("findEmployee: " + id);
		return employeeDAO.findById(id);
	}

	@Override
	@Transactional
	public void saveEmployee(Employee employee) {
		LOG.debug("saveEmployee: " + employee);
		employeeDAO.saveEntity(employee);
	}

	@Override
	@Transactional
	public void deleteEmployee(long id) {
		LOG.debug("deleteEmployee: " + id);
		employeeDAO.deleteById(id);
	}

}
