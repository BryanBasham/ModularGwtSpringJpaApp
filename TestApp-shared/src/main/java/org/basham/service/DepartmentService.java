package org.basham.service;

import java.util.List;

import org.basham.domain.Department;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * The contract for managing {@limk Department} entities.
 * 
 * TODO:
 *   1) Do Service beans really need the GWT {@link RemoteService} marker interface?
 *      See my comments in SpringGwtRemoteServiceServlet in the server module for more discussion.
 * 
 * @author <a href='mailto:basham47@gmail.com'>Bryan Basham</a>
 */
public interface DepartmentService extends RemoteService {
	public List<Department> retrieveAll();
	public Department findDepartment(long id);
	public void saveDepartment(Department department);
	public void deleteDepartment(long id);
	
}
