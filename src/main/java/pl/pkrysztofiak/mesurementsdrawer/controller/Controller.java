package pl.pkrysztofiak.mesurementsdrawer.controller;

import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.pkrysztofiak.mesurementsdrawer.controller.panel.PanelController;
import pl.pkrysztofiak.mesurementsdrawer.controller.panel.image.ImagePanelController;
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

    private final ObservableList<PanelController> panelsControllers = FXCollections.observableArrayList();
    private final Observable<PanelController> panelControllerAddedObservable = JavaFxObservable.additionsOf(panelsControllers);
    private final Observable<PanelController> panelControllerRemovedObservable = JavaFxObservable.removalsOf(panelsControllers);

    private final ObjectProperty<PanelController> selectedPanelControllerProperty = new SimpleObjectProperty<>();
    private final Observable<PanelController> selectedPanelControllerObservable = JavaFxObservable.valuesOf(selectedPanelControllerProperty);

    private final ObservableList<ImagePanelController> imagesPanelsControllers = FXCollections.observableArrayList();
    private final Observable<ImagePanelController> imagePanelControllerAddedObservable = JavaFxObservable.additionsOf(imagesPanelsControllers);
    private final Observable<ImagePanelController> imagePanelControllerRemovedObservable = JavaFxObservable.removalsOf(imagesPanelsControllers);

    private final ObjectProperty<ImagePanelController> selectedImagePanelControllerProperty = new SimpleObjectProperty<>();
    private final Observable<ImagePanelController> selectedImagePanelControllerObservable = JavaFxObservable.valuesOf(selectedImagePanelControllerProperty);

    private final ToolbarController toolbarController;

    public Controller(Model model, View view) {
        super();
        this.model = model;
        this.view = view;

        ToolbarView toolbarView = new ToolbarView();
        view.setToolbarView(toolbarView);

        toolbarController = new ToolbarController(toolbarView);

        initSubscriptions();
        view.show();
    }

    private void initSubscriptions() {
//        view.panelCreatedObservable().map(ImagePanelController::new).subscribe(panelsControllers::add);
    	selectedPanelControllerObservable.subscribe(behaviour::onSelectedPanelControllerChanged);
    	panelControllerAddedObservable.subscribe(behaviour::onPanelControllerAdded);
    	view.panelViewAddedObservable().map(PanelController::new).subscribe(panelsControllers::add);


    	//old
//        selectedImagePanelControllerObservable.subscribe(behaviour::onSelectedPanelControllerChanged);
//
//        imagePanelControllerAddedObservable.subscribe(behaviour::onImagePanelControllerAdded);
//        imagePanelControllerRemovedObservable.subscribe(behaviour::onPanelControllerRemoved);
//
//        Observable.combineLatest(selectedImagePanelControllerObservable, toolbarController.selectedToolObservable(), behaviour::onChanged)
//        .subscribe();
//
//        Observable.combineLatest(selectedImagePanelControllerObservable, toolbarController.measurementCreatedObservable(), behaviour::onChanged)
//        .subscribe();
    }

    private class Behaviour {

        private void onImagePanelControllerAdded(ImagePanelController imagePanelController) {
            imagePanelController.mouseAnyObservable().map(mouseEvent -> imagePanelController).takeUntil(imagePanelControllerRemovedObservable.filter(imagePanelController::equals))
            .subscribe(selectedImagePanelControllerProperty::set);

            imagePanelController.measurementAddedObservable().flatMap(Measurement::finishedObservale).subscribe(behaviour::onMeasurementFinished);

//            Bindings.bindContentBidirectional(imagePanelController.getMeasurements(), model.getMeasurements());
        }

        private void onPanelControllerRemoved(ImagePanelController imagePanelController) {
            Bindings.unbindContentBidirectional(imagePanelController.getMeasurements(), model.getMeasurements());
        }

        private void onSelectedPanelControllerChanged(ImagePanelController selectedPanelController) {
            selectedPanelController.setSelected(true);
            Observable.fromIterable(imagesPanelsControllers).filter(panelController -> panelController.unequals(selectedPanelController)).subscribe(panelController -> panelController.setSelected(false));
        }

        private Optional<Void> onChanged(ImagePanelController selectedPanelController, Tool selectedTool) {
//        	selectedTool.setSelectedImagePanelController(selectedPanelController);
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

        //new

        private void onPanelControllerAdded(PanelController panelController) {
        	panelController.getImagePanelController().mouseAnyObservable()
        	.takeUntil(panelControllerRemovedObservable.filter(panelController::equals))
        	.subscribe(mouseEvent -> selectedPanelControllerProperty.set(panelController));
        }

        private void onSelectedPanelControllerChanged(PanelController selectedPanelController) {
        	panelsControllers.forEach(panelController -> panelController.setSelected(false));
        	selectedPanelController.setSelected(true);
        }
    }
}