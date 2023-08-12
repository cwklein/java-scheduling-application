package klein.helper_controllers.interfaces;

import javafx.collections.ObservableList;

/**
 * interface setting up Lambda 'givenListAndObject', later used to determine the integer associated with a selected month in a list.
 * */
public interface ComboInterface {
    Integer givenListAndObject(ObservableList<String> monthList, String month);

}
