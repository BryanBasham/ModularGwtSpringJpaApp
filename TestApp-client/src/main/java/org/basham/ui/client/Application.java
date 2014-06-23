package org.basham.ui.client;

import org.basham.ui.client.activities.ListDepartmentsActivity;
import org.basham.ui.client.activities.Page;

import com.allen_sauer.gwt.log.client.Log;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.RootLayoutPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {

    //
    // Singleton
    //

	public static Application get() {
		return INSTANCE;
	}
	private static Application INSTANCE;

    //
    // Attributes
    //

	private BiMap<Page, ResizeComposite> pageViewMap = HashBiMap.create();
	private DeckLayoutPanel deckPanel = new DeckLayoutPanel();

    //
    // Application methods
    //

	public void navigateToPage(Page page) {
		ResizeComposite view = pageViewMap.get(page);
		if (view == null) {
			view = page.getView();
		}
		if (deckPanel.getWidgetIndex(view) == -1) {
			Log.debug("Adding new page: " + page);
			deckPanel.add(view);
			pageViewMap.put(page, view);
		} else {
			Log.debug("Reusing page.");
			// get the original Page object
			page = pageViewMap.inverse().get(view);
		}
		deckPanel.showWidget(view);
		page.initalize();
	}

    //
    // EntryPoint methods
    //

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
        // Save the Singleton instance created by GWT
        INSTANCE = this;

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
        
        // setup page deck panel
        RootLayoutPanel.get().add(deckPanel);
        // display the first View
        navigateToPage(new ListDepartmentsActivity());
	}
	
}
