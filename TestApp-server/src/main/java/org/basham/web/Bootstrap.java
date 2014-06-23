package org.basham.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.basham.domain.Department;
import org.basham.domain.Employee;
import org.basham.service.DepartmentService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@WebListener("Bootstrap the in-memory database with objects.")
public class Bootstrap implements ServletContextListener {
	private static final Logger LOG = Logger.getLogger(Bootstrap.class);
	
	private DepartmentService departmentSvc;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		WebApplicationContext springCtx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		departmentSvc = springCtx.getBean(DepartmentService.class);
		
		Department Eng = new Department("Engineering");
		addEmployee("Bryan", "Basham", "Application Architect", Eng);
		addEmployee("Simon", "Roberts", "Security Architect", Eng);
		addEmployee("Brad", "Francis", "Software Developer", Eng);
		addEmployee("Katie", "McNeill", "Software Developer", Eng);
		departmentSvc.saveDepartment(Eng);
		LOG.debug("Eng department saved.");

		Department HR = new Department("Mgmt");
		addEmployee("Michael", "Scott", "Branch Manager", HR);
		addEmployee("Rossane", "Barr", "Morale Officer", HR);
		departmentSvc.saveDepartment(HR);
		LOG.debug("HR department saved.");
	}

	private void addEmployee(String name, String subrname, String job, Department department) {
		Employee emp = new Employee(name, subrname, job);
		department.addEmployee(emp);
		emp.setDepartment(department);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// do nothing
	}

}
