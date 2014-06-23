package org.basham.ui.client.activities;

import com.google.gwt.user.client.ui.ResizeComposite;

/**
 * Marker interface for all Page-based activities.
 */
public interface Page {

	void initalize();
	ResizeComposite getView();
}
