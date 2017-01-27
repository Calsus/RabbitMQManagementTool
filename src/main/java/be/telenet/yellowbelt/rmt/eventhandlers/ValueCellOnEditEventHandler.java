package be.telenet.yellowbelt.rmt.eventhandlers;

import be.telenet.yellowbelt.rmt.models.Header;

/**
 * EventHandler to handling the OnEditEvent of the Value Cell
 * <p>
 * Created by Jerry-Lee on 25/01/2017.
 */
public class ValueCellOnEditEventHandler extends CellOnEditEventHandler {
	/**
	 * Sets the property value of the given header to the newValue
	 *
	 * @param header   {@link Header}
	 * @param newValue the new value give by the user.
	 */
	@Override
	public void setNewValue(Header header, String newValue) {
		header.setValue(newValue);
	}
}
