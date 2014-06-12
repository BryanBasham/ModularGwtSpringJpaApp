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
	public Employee findEmployee(long employeeId) {
		LOG.debug("findEmployee: " + employeeId);
		return employeeDAO.findById(employeeId);
	}

	@Override
	@Transactional
	public void saveEmployee(Employee employee) throws Exception {
		LOG.debug("saveEmployee: " + employee);
		employeeDAO.saveEntity(employee);
	}

	@Override
	@Transactional
	public void deleteEmployee(long employeeId) throws Exception {
		LOG.debug("deleteEmployee: " + employeeId);
		employeeDAO.deleteById(employeeId);
	}

}
