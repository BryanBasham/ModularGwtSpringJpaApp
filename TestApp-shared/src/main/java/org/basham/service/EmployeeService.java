package org.basham.service;

import org.basham.domain.Employee;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * The contract for managing {@limk Employee} entities.
 * 
 * TODO:
 *   1) Do Service beans really need the GWT {@link RemoteService} marker interface?
 *      See my comments in SpringGwtRemoteServiceServlet in the server module for more discussion.
 * 
 * @author <a href='mailto:basham47@gmail.com'>Bryan Basham</a>
 */
public interface EmployeeService extends RemoteService {
	
	public Employee findEmployee(long employeeId);
	public void saveEmployee(long employeeId, String name, String surname, String jobDescription) throws Exception;
	public void updateEmployee(long employeeId, String name, String surname, String jobDescription) throws Exception;
	public void saveOrUpdateEmployee(long employeeId, String name, String surname, String jobDescription) throws Exception;
	public void deleteEmployee(long employeeId) throws Exception;
	
}
