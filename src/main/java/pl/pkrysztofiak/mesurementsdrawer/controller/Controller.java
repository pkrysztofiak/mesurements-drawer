package pl.pkrysztofiak.mesurementsdrawer.controller;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.pkrysztofiak.mesurementsdrawer.controller.panel.PanelController;
import pl.pkrysztofiak.mesurementsdrawer.controller.tool.ToolController;
import pl.pkrysztofiak.mesurementsdrawer.model.Model;
import pl.pkrysztofiak.mesurementsdrawer.view.View;

public class Controller {

    private final Behaviour behaviour = new Behaviour();
    
    private final Model model;
    private final View view;

    private final ObservableList<PanelController> panelsControllers = FXCollections.observableArrayList();
    private final Observable<PanelController> panelControllerAddedObservable = JavaFxObservable.additionsOf(panelsControllers);
    private final Observable<PanelController> panelControllerRemovedObservable = JavaFxObservable.removalsOf(panelsControllers);
    
    private final ObjectProperty<PanelController> selectedPanelControllerProperty = new SimpleObjectProperty<>();
    private final Observable<PanelController> selectedPanelControllerObservable = JavaFxObservable.valuesOf(selectedPanelControllerProperty);
    
    private final ToolController toolController;
    
    public Controller(Model model, View view) {
        super();
        this.model = model;
        this.view = view;
        toolController = new ToolController(model);
        
        initSubscriptions();
        view.show();
    }
    
    private void initSubscriptions() {
        view.panelCreatedObservable().map(PanelController::new).subscribe(panelsControllers::add);
        selectedPanelControllerObservable.subscribe(behaviour::onSelectedPanelControllerChanged);
        
        panelControllerAddedObservable.subscribe(behaviour::onPanelControllerAdded);
        panelControllerRemovedObservable.subscribe(behaviour::onPanelControllerRemoved);
    }
    
    private class Behaviour {
        
        private void onPanelControllerAdded(PanelController panelController) {
//            panelController.mouseReleasedObservable().takeUntil(panelControllerRemovedObservable.filter(panelController::equals))
//            .subscribe(toolController.mouseReleasedPublishable()::onNext);

            panelController.mouseAnyObservable().map(mouseEvent -> panelController).takeUntil(panelControllerRemovedObservable.filter(panelController::equals))
            .subscribe(selectedPanelControllerProperty::set);

            Bindings.bindContentBidirectional(panelController.getMeasurements(), model.getMeasurements());
        }
        
        private void onPanelControllerRemoved(PanelController panelController) {
            Bindings.unbindContentBidirectional(panelController.getMeasurements(), model.getMeasurements());
        }
        
        private void onSelectedPanelControllerChanged(PanelController selectedPanelController) {
            selectedPanelController.setSelected(true);
            Observable.fromIterable(panelsControllers).filter(panelController -> panelController.unequals(selectedPanelController)).subscribe(panelController -> panelController.setSelected(false));
         
            toolController.setSelectedPanelController(selectedPanelController);
        }
    }
}