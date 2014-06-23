package org.basham.ui.client.widgets;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

/**
 * The AnchorTextCell class provides a GWT Cell widget that wraps the cell value
 * in an anchor tag.  This is just for show (to get the standard TR style).
 * Subclasses must provide a concrete click handler.
 */
public abstract class AnchorTextCell extends ClickableTextCell {

	/**
	 * The HTML templates used to render the cell.
	 */
	interface Templates extends SafeHtmlTemplates {
		@SafeHtmlTemplates.Template("<a href='#'>{0}</a>")
		SafeHtml cell(SafeHtml value);
	}

	/**
	 * Create a singleton instance of the templates used to render the cell.
	 */
	private static Templates templates = GWT.create(Templates.class);

	@Override
	public final void render(final Context context, final String value, final SafeHtmlBuilder sb) {
		if (value == null) {
			return;
		}

		// If the value comes from the user, we escape it to avoid XSS attacks.
		SafeHtml safeValue = SafeHtmlUtils.fromString(value);

		// Use the template to create the Cell's html.
		SafeHtml rendered = templates.cell(safeValue);
		sb.append(rendered);
	}

	@Override
	public final void onBrowserEvent(final Context context,
			final Element parent, final String value, final NativeEvent event,
			final ValueUpdater<String> valueUpdater) {
		// If a click event, then prevent the default action
		if ("click".equals(event.getType())) {
			event.preventDefault();
			handleClick(context, value);
		}
	}

	/**
	 * @param context
	 * @param value
	 */
	protected abstract void handleClick(Context context, String value);
}
