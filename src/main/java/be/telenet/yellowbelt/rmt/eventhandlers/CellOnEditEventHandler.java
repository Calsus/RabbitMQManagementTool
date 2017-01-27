package be.telenet.yellowbelt.rmt.eventhandlers;

import be.telenet.yellowbelt.rmt.models.Header;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;

/**
 * EventHandler that handle the CellEditEvent.
 * Created by Jerry-Lee on 25/01/2017.
 */
public abstract class CellOnEditEventHandler implements EventHandler<TableColumn.CellEditEvent<Header, String>> {
	/**
	 * It will get the row in which we are editing and then it will set the value to the given value.
	 *
	 * @param event {@inheritDoc}
	 */
	@Override
	public void handle(TableColumn.CellEditEvent<Header, String> event) {
		Header header = event.getTableView().getItems().get(event.getTablePosition().getRow());
		setNewValue(header, event.getNewValue());
	}

	public abstract void setNewValue(Header header, String newValue);
}
