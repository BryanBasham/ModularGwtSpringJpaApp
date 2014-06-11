package org.basham.ui.client;

import org.basham.service.EmployeeService;
import org.basham.service.EmployeeServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class RpcUtils {

	public static EmployeeServiceAsync getEmployeeService() {
		if (employeeRPC == null) {
			employeeRPC = (EmployeeServiceAsync) GWT.create(EmployeeService.class);
			ServiceDefTarget target = (ServiceDefTarget) employeeRPC;
			String svcURL = GWT.getModuleBaseURL() + "rpc/employeeSvc";
			target.setServiceEntryPoint(svcURL);
		}
		return employeeRPC;
	}
	private static EmployeeServiceAsync employeeRPC;
}
