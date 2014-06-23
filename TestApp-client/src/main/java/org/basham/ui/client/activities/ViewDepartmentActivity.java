package org.basham.ui.client.activities;

import org.basham.domain.Department;
import org.basham.ui.client.RpcUtils;
import org.basham.ui.client.views.ViewDepartmentView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ResizeComposite;

public class ViewDepartmentActivity implements Page {
	
	private ViewDepartmentView view;

	@Override
	public ResizeComposite getView() {
		view = new ViewDepartmentView();
		return view;
	}

	@Override
	public void initalize() {
		view.reset();
	}
	
	public void setDepartment(Department department) {
		// Retrieve a full copy from the Server
		AsyncCallback<Department> callback = new AsyncCallback<Department>() {
			@Override
			public void onSuccess(Department result) {
				view.setDepartment(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("not yet implemented");
			}
		};
		RpcUtils.getDepartmentService().findDepartment(department.getId(), callback);
	}
}
