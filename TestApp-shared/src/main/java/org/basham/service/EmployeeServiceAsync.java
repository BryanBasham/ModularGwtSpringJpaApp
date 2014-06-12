/**
 * Copyright 2012: Z2M4, LLC.
 * All Rights Reserved. Proprietary and Confidential information of Z2M4, LLC.
 * Disclosure, Use or Reproduction without the written authorization of Z2M4, LLC is prohibited
 */
package org.basham.service;

import org.basham.domain.Employee;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This EmployeeServiceAsync class provides 
 * 
 * @author <a href='mailto:basham47@gmail.com'>Bryan Basham</a> 
 */
public interface EmployeeServiceAsync {

	void findEmployee(long employeeId, AsyncCallback<Employee> callback);

	void deleteEmployee(long employeeId, AsyncCallback<Void> callback);

	void saveEmployee(Employee employee, AsyncCallback<Void> callback);
}
