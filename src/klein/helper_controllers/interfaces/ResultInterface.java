package klein.helper_controllers.interfaces;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import klein.helper_controllers.AppointmentObject;

public interface ResultInterface {
    void generateResultView(TableView<AppointmentObject> tableToSet, Label labelToSet, ObservableList<AppointmentObject> resultList);
}
