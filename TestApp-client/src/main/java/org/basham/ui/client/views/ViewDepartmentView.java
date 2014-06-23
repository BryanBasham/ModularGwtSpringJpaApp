package org.basham.ui.client.views;

import java.util.Collections;

import org.basham.domain.Department;
import org.basham.domain.Employee;
import org.basham.ui.client.Application;
import org.basham.ui.client.activities.ListDepartmentsActivity;

import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.TextBox;

public class ViewDepartmentView extends ResizeComposite {

    //
    // GWT UI Binding mechanism
    //

    interface MyUiBinder extends UiBinder<DockLayoutPanel, ViewDepartmentView> { }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField TextBox nameField;
    @UiField DataGrid<Employee> employeeGrid;
    @UiField Button doneBtn;

    //
    // UI state variables
    //

    //
    // Constructor
    //

    public ViewDepartmentView() {
        // createAndBindUi initializes fields
        initWidget(uiBinder.createAndBindUi(this));
        //
        configureGrid();
	}

    //
    // View methods
    //

	public void reset() {
    	nameField.setValue(null);
    	employeeGrid.setRowData(Collections.<Employee>emptyList());
    }
    
    public void setDepartment(Department department) {
    	nameField.setValue(department.getName());
    	employeeGrid.setRowData(Lists.newArrayList(department.getEmployees()));
    }

    //
    // GWT Event handler methods
    //

	@UiHandler("doneBtn")
	void cancel(final ClickEvent event) {
		Application.get().navigateToPage(new ListDepartmentsActivity());
	}

    //
    // Private methods
    //

    private void configureGrid() {
		// configure the Employee grid
		Column<Employee, String> nameColumn = new TextColumn<Employee>() {
			@Override
			public String getValue(final Employee row) {
				return row.getEmployeeName() + " " + row.getEmployeeSurname();
			}
		};
		nameColumn.setSortable(false);
		employeeGrid.addColumn(nameColumn, "Name");
		employeeGrid.setColumnWidth(nameColumn, 50, Unit.PCT);
		Column<Employee, String> jobColumn = new TextColumn<Employee>() {
			@Override
			public String getValue(Employee row) {
				return row.getJob();
			}
		};
		jobColumn.setSortable(false);
		employeeGrid.addColumn(jobColumn, "Job Title");
		employeeGrid.setColumnWidth(jobColumn, 50, Unit.PCT);
	}

}
