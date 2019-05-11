package pl.pkrysztofiak.mesurementsdrawer.controller;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.pkrysztofiak.mesurementsdrawer.controller.panel.PanelController;
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

    public Controller(Model model, View view) {
        super();
        this.model = model;
        this.view = view;

        //TODO main toolbar should be removed
        ToolbarView toolbarView = new ToolbarView();
        view.setToolbarView(toolbarView);

        initSubscriptions();
        view.show();
    }

    private void initSubscriptions() {
    	selectedPanelControllerObservable.subscribe(behaviour::onSelectedPanelControllerChanged);
    	panelControllerAddedObservable.subscribe(behaviour::onPanelControllerAdded);
    	view.panelViewAddedObservable().map(PanelController::new).subscribe(panelsControllers::add);
    }

    private class Behaviour {

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