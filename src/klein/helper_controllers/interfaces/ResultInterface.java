package klein.helper_controllers.interfaces;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import klein.helper_controllers.AppointmentObj;

public interface ResultInterface {
    void generateResultView(TableView<AppointmentObj> tableToSet, Label labelToSet, ObservableList<AppointmentObj> resultList);
}
