package klein.helper_controllers.interfaces;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import klein.helper_controllers.AppointmentObject;

/**
 * interface setting up Lambda 'generateResultView', later used to populate the result tableView and result count with information from the result list.
 * */
public interface ResultInterface {
    void generateResultView(TableView<AppointmentObject> tableToSet, Label labelToSet, ObservableList<AppointmentObject> resultList);
}
