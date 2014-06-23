package org.basham.ui.client.views;

import java.util.List;

import org.basham.domain.Department;
import org.basham.ui.client.Application;
import org.basham.ui.client.activities.ViewDepartmentActivity;
import org.basham.ui.client.widgets.AnchorTextCell;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;

public class ListDepartmentsView extends ResizeComposite {

    //
    // GWT UI Binding mechanism
    //

    interface MyUiBinder extends UiBinder<DockLayoutPanel, ListDepartmentsView> { }
    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

    @UiField DataGrid<Department> departmentGrid;

	private final Cell<String> editUserLinkCell = new AnchorTextCell() {
		@Override
		protected void handleClick(final Context context, final String value) {
			Department dept = (Department) context.getKey();
			ViewDepartmentActivity page = new ViewDepartmentActivity();
			Application.get().navigateToPage(page);
			page.setDepartment(dept);
		}
	};

    public ListDepartmentsView() {
        // createAndBindUi initializes fields
        initWidget(uiBinder.createAndBindUi(this));

		Column<Department, String> nameColumn = new Column<Department, String>(editUserLinkCell) {
			@Override
			public String getValue(final Department row) {
				return row.getName();
			}
		};
		nameColumn.setSortable(false);
		departmentGrid.addColumn(nameColumn, "Department");
		departmentGrid.setColumnWidth(nameColumn, 100, Unit.PCT);
	}
    
    public void setDeparmentList(List<Department> departments) {
    	departmentGrid.setRowData(departments);
    }
}
