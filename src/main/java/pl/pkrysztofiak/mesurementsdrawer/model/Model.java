package pl.pkrysztofiak.mesurementsdrawer.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.MeasurementView;

public class Model {

    private final ObservableList<MeasurementView> measurementViews = FXCollections.observableArrayList();
    
    public ObservableList<MeasurementView> getMeasurements() {
        return measurementViews;
    }
}