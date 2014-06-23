/**
 * Copyright 2012: Z2M4, LLC.
 * All Rights Reserved. Proprietary and Confidential information of Z2M4, LLC.
 * Disclosure, Use or Reproduction without the written authorization of Z2M4, LLC is prohibited
 */
package org.basham.service;

import java.util.List;

import org.basham.domain.Department;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The contract for the GWT RPC asynchronous version of the {@link DepartmentService}
 * interface.
 * 
 * @author <a href='mailto:basham47@gmail.com'>Bryan Basham</a> 
 */
public interface DepartmentServiceAsync {
	void retrieveAll(AsyncCallback<List<Department>> callback);
	void findDepartment(long id, AsyncCallback<Department> callback);
	void deleteDepartment(long id, AsyncCallback<Void> callback);
	void saveDepartment(Department employee, AsyncCallback<Void> callback);
}
