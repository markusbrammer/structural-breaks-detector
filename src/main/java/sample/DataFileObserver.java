package sample;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DataFileObserver implements PropertyChangeListener {

    public void propertyChange(PropertyChangeEvent event) {
        System.out.println(((Controller) event.getSource()).getDataFilePath());
    }

}
