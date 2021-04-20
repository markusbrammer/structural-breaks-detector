package sample;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DataFileObserver implements PropertyChangeListener {

    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("dataFile")) {
            String dataFilePath = ((Controller) event.getSource()).getDataFilePath();
            System.out.println(dataFilePath);
        }
    }

}
