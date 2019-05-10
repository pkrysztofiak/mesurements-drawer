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
import pl.pkrysztofiak.mesurementsdrawer.controller.tool.Tool;
import pl.pkrysztofiak.mesurementsdrawer.controller.toolbar.ToolbarController;
import pl.pkrysztofiak.mesurementsdrawer.model.Model;
import pl.pkrysztofiak.mesurementsdrawer.view.View;
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
        view.panelCreatedObservable().map(PanelController::new).subscribe(panelsControllers::add);
        selectedPanelControllerObservable.subscribe(behaviour::onSelectedPanelControllerChanged);

        panelControllerAddedObservable.subscribe(behaviour::onPanelControllerAdded);
        panelControllerRemovedObservable.subscribe(behaviour::onPanelControllerRemoved);

        Observable.combineLatest(selectedPanelControllerObservable, toolbarController.selectedToolObservable(), behaviour::onChanged)
        .subscribe();
    }

    private class Behaviour {

        private void onPanelControllerAdded(PanelController panelController) {
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
        }

        private Optional<Void> onChanged(PanelController selectedPanelController, Tool selectedTool) {
        	selectedTool.setSelectedPanelController(selectedPanelController);
        	selectedPanelController.setEventsReceiver(selectedTool);
        	return Optional.empty();
        }
    }
}