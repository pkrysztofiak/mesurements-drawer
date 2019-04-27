package pl.pkrysztofiak.mesurementsdrawer.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.Measurement;

public class Model {

    private final ObservableList<Measurement> measurements = FXCollections.observableArrayList();
    
    public ObservableList<Measurement> getMeasurements() {
        return measurements;
    }
}