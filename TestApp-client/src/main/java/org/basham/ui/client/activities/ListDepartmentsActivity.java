package org.basham.ui.client.activities;

import java.util.List;

import org.basham.domain.Department;
import org.basham.ui.client.RpcUtils;
import org.basham.ui.client.views.ListDepartmentsView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ResizeComposite;

public class ListDepartmentsActivity implements Page {
	
	private ListDepartmentsView view;

	@Override
	public ResizeComposite getView() {
		view = new ListDepartmentsView();
		return view;
	}

	@Override
	public void initalize() {
		AsyncCallback<List<Department>> callback = new AsyncCallback<List<Department>>() {
			@Override
			public void onSuccess(List<Department> result) {
				view.setDeparmentList(result);
			}
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("ERROR: " + caught.getMessage());
			}
		};
		RpcUtils.getDepartmentService().retrieveAll(callback);
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getClass().equals(obj.getClass());
	}
	
	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}
}
