package org.basham.ui.client;

import org.basham.domain.Employee;
import org.basham.service.EmployeeServiceAsync;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application
    implements EntryPoint
{

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again. The error is : ";
	
	/**
	 * Create a remote service proxy to talk to the server-side Employee service.
	 */
	private final EmployeeServiceAsync employeeService = RpcUtils.getEmployeeService();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

    	// global exception handler
        GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable error) {
            	if (error != null) {
            		Log.error("Global error", error);
            		// find root cause
            		while (error.getCause() != null) error = error.getCause();
            		// display the root cause to the user
            		Log.error("Root cause", error);
            	} else {
                	Log.error("Uncaught exception, but error is null");
            	}
            }
        });
        
		
		final Button saveOrUpdateButton = new Button("SaveOrUpdate");
		final Button retrieveButton = new Button("Retrieve");
		final TextBox employeeInfoField = new TextBox();
		employeeInfoField.setText("Employee Info");
		final TextBox employeeIdField = new TextBox();
		final Label errorLabel = new Label();

		// We can add style names to widgets
		saveOrUpdateButton.addStyleName("sendButton");
		retrieveButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("employeeInfoFieldContainer").add(employeeInfoField);
		RootPanel.get("updateEmployeeButtonContainer").add(saveOrUpdateButton);
		RootPanel.get("employeeIdFieldContainer").add(employeeIdField);
		RootPanel.get("retrieveEmployeeButtonContainer").add(retrieveButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		employeeInfoField.setFocus(true);
		employeeInfoField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending request to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				saveOrUpdateButton.setEnabled(true);
				saveOrUpdateButton.setFocus(true);
				retrieveButton.setEnabled(true);
			}
		});

		// Create a handler for the saveOrUpdateButton and employeeInfoField
		class SaveOrUpdateEmployeeHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the saveOrUpdateButton.
			 */
			public void onClick(ClickEvent event) {
				sendEmployeeInfoToServer();
			}

			/**
			 * Fired when the user types in the employeeInfoField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendEmployeeInfoToServer();
				}
			}

			/**
			 * Send the employee info from the employeeInfoField to the server and wait for a response.
			 */
			private void sendEmployeeInfoToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = employeeInfoField.getText();

				// Then, we send the input to the server.
				saveOrUpdateButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");

				String[] employeeInfo = textToServer.split(" ");
				
				long employeeId = Long.parseLong(employeeInfo[0]);
				String employeeName = employeeInfo[1];
				String employeeSurname = employeeInfo[2];
				String employeeJobTitle = employeeInfo[3];
				
				Employee employee = new Employee(employeeId, employeeName, employeeSurname, employeeJobTitle);
				employeeService.saveEmployee(employee, 
						new AsyncCallback<Void>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR + caught.toString());
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(Void noAnswer) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML("OK");
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}
		
		// Create a handler for the retrieveButton and employeeIdField
		class RetrieveEmployeeHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the retrieveButton.
			 */
			public void onClick(ClickEvent event) {
				sendEmployeeIServer();
			}

			/**
			 * Fired when the user types in the employeeIdField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendEmployeeIServer();
				}
			}

			/**
			 * Send the id from the employeeIdField to the server and wait for a response.
			 */
			private void sendEmployeeIServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = employeeIdField.getText();

				// Then, we send the input to the server.
				retrieveButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");

				employeeService.findEmployee(Long.parseLong(textToServer),  
						new AsyncCallback<Employee>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR + caught.toString());
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(Employee employee) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								if(employee != null)
									serverResponseLabel.setHTML("Employee Information <br>DB id : " + employee.getId() + "<br>Name : " + employee.getEmployeeName() + "<br>Surname : " + employee.getEmployeeSurname() + "<br>Job Title : " + employee.getJob());
								else
									serverResponseLabel.setHTML("No employee with the specified id found");
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}

		// Add a handler to send the employee info to the server
		SaveOrUpdateEmployeeHandler saveOrUpdateEmployeehandler = new SaveOrUpdateEmployeeHandler();
		saveOrUpdateButton.addClickHandler(saveOrUpdateEmployeehandler);
		employeeInfoField.addKeyUpHandler(saveOrUpdateEmployeehandler);
		
		// Add a handler to send the employee id to the server
		RetrieveEmployeeHandler retrieveEmployeehandler = new RetrieveEmployeeHandler();
		retrieveButton.addClickHandler(retrieveEmployeehandler);
		employeeIdField.addKeyUpHandler(retrieveEmployeehandler);
	}
	
}
