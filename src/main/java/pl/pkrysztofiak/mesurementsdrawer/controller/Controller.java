package pl.pkrysztofiak.mesurementsdrawer.controller;

import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.pkrysztofiak.mesurementsdrawer.controller.panel.ImagePanelController;
import pl.pkrysztofiak.mesurementsdrawer.controller.tool.Tool;
import pl.pkrysztofiak.mesurementsdrawer.controller.toolbar.ToolbarController;
import pl.pkrysztofiak.mesurementsdrawer.model.Model;
import pl.pkrysztofiak.mesurementsdrawer.view.View;
import pl.pkrysztofiak.mesurementsdrawer.view.measurements.Measurement;
import pl.pkrysztofiak.mesurementsdrawer.view.toolbar.ToolbarView;

public class Controller {

    private final Behaviour behaviour = new Behaviour();

    private final Model model;
    private final View view;

    private final ObservableList<ImagePanelController> panelsControllers = FXCollections.observableArrayList();
    private final Observable<ImagePanelController> panelControllerAddedObservable = JavaFxObservable.additionsOf(panelsControllers);
    private final Observable<ImagePanelController> panelControllerRemovedObservable = JavaFxObservable.removalsOf(panelsControllers);

    private final ObjectProperty<ImagePanelController> selectedPanelControllerProperty = new SimpleObjectProperty<>();
    private final Observable<ImagePanelController> selectedPanelControllerObservable = JavaFxObservable.valuesOf(selectedPanelControllerProperty);

    private final ToolbarController toolbarController;

    public Controller(Model model, View view) {
        super();
        this.model = model;
        this.view = view;

        ToolbarView toolbarView = new ToolbarView();
        view.setToolbarView(toolbarView);

        toolbarController = new ToolbarController(toolbarView, model);

        initSubscriptions();
        view.show();
    }

    private void initSubscriptions() {
        view.panelCreatedObservable().map(ImagePanelController::new).subscribe(panelsControllers::add);
        selectedPanelControllerObservable.subscribe(behaviour::onSelectedPanelControllerChanged);

        panelControllerAddedObservable.subscribe(behaviour::onPanelControllerAdded);
        panelControllerRemovedObservable.subscribe(behaviour::onPanelControllerRemoved);

        Observable.combineLatest(selectedPanelControllerObservable, toolbarController.selectedToolObservable(), behaviour::onChanged)
        .subscribe();

        Observable.combineLatest(selectedPanelControllerObservable, toolbarController.measurementCreatedObservable(), behaviour::onChanged)
        .subscribe();
    }

    private class Behaviour {

        private void onPanelControllerAdded(ImagePanelController imagePanelController) {
            imagePanelController.mouseAnyObservable().map(mouseEvent -> imagePanelController).takeUntil(panelControllerRemovedObservable.filter(imagePanelController::equals))
            .subscribe(selectedPanelControllerProperty::set);

            imagePanelController.measurementAddedObservable().flatMap(Measurement::finishedObservale).subscribe(behaviour::onMeasurementFinished);

            Bindings.bindContentBidirectional(imagePanelController.getMeasurements(), model.getMeasurements());
        }

        private void onPanelControllerRemoved(ImagePanelController imagePanelController) {
            Bindings.unbindContentBidirectional(imagePanelController.getMeasurements(), model.getMeasurements());
        }

        private void onSelectedPanelControllerChanged(ImagePanelController selectedPanelController) {
            selectedPanelController.setSelected(true);
            Observable.fromIterable(panelsControllers).filter(panelController -> panelController.unequals(selectedPanelController)).subscribe(panelController -> panelController.setSelected(false));
        }

        private Optional<Void> onChanged(ImagePanelController selectedPanelController, Tool selectedTool) {
        	selectedTool.setSelectedPanelController(selectedPanelController);
        	selectedPanelController.setEventsReceiver(selectedTool);
        	return Optional.empty();
        }

        private Optional<Void> onChanged(ImagePanelController selectedPanelController, Measurement createdMeasurement) {
        	selectedPanelController.addMeasurement(createdMeasurement);
        	selectedPanelController.setEventsReceiver(createdMeasurement);
        	return Optional.empty();
        }

        private void onMeasurementFinished(Measurement measurement) {

        }
    }
}