package org.basham.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.basham.dao.DepartmentDAO;
import org.basham.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.nociar.jpacloner.JpaCloner;

@Service("departmentSvc")
public class DepartmentServiceImpl implements DepartmentService {
	private static final Logger LOG = Logger.getLogger(DepartmentServiceImpl.class);

	@Autowired
	private DepartmentDAO departmentDAO;

	//
	// DepartmentService methods
	//

	@Override
	@Transactional(readOnly = true)
	public List<Department> retrieveAll() {
		LOG.debug("retrieveAll");
		List<Department> list = departmentDAO.findAll();
		return JpaCloner.clone(list);
	}

	@Override
	@Transactional(readOnly = true)
	public Department findDepartment(long id) {
		LOG.debug("findDepartment: " + id);
		return JpaCloner.clone(departmentDAO.findById(id), "employees");
	}

	@Override
	@Transactional
	public void saveDepartment(Department department) {
		LOG.debug("saveDepartment: " + department);
		departmentDAO.saveEntity(department);
	}

	@Override
	@Transactional
	public void deleteDepartment(long id) {
		LOG.debug("deleteDepartment: " + id);
		departmentDAO.deleteById(id);
	}

}
