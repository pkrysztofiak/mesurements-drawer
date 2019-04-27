package pl.pkrysztofiak.mesurementsdrawer.controller.tool;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.pkrysztofiak.mesurementsdrawer.controller.panel.PanelController;
import pl.pkrysztofiak.mesurementsdrawer.model.Model;

public class ToolController {
    
    private final Behaviour behaviour = new Behaviour();
    
    private final Model model;
    
    private final ObjectProperty<Tool> selectedToolProperty = new SimpleObjectProperty<>();
    private final Observable<Tool> selectedToolObservable = JavaFxObservable.valuesOf(selectedToolProperty);
    
//    private final PublishSubject<MouseEvent> mouseReleasedPublishable = PublishSubject.create();
    
    private final ObjectProperty<PanelController> activePanelControllerProperty = new SimpleObjectProperty<>();
    private final Observable<PanelController> activePanelControllerObservable = JavaFxObservable.valuesOf(activePanelControllerProperty);
    
    public ToolController(Model model) {
        this.model = model;
        selectedToolProperty.set(new PolygonTool(model));
        initSubscriptions();
    }
    
    private void initSubscriptions() {
//        BiFunction<MouseEvent, Tool, Optional<Void>> mouseReleasedToolHandler = (mouseEvent, tool) -> {
//            tool.mouseReleasedPubslishable().onNext(mouseEvent);
//            return Optional.empty();
//        };
        
//        mouseReleasedPublishable.withLatestFrom(selectedToolObservable, mouseReleasedToolHandler::apply).subscribe();
        activePanelControllerObservable.subscribe(behaviour::onSelectedPanelControllerChanged);
        
        selectedToolObservable.switchMap(selectedTool -> 
            activePanelControllerObservable.doOnNext(selectedTool::setActivePanelController))
        .subscribe();
    }
    
    public void setSelectedPanelController(PanelController panelController) {
        activePanelControllerProperty.set(panelController);
    }
    
    public final Observable<Tool> selectedToolObservable() {
        return selectedToolObservable;
    }
    
//    public PublishSubject<MouseEvent> mouseReleasedPublishable() {
//        return mouseReleasedPublishable;
//    }
    
    private class Behaviour {
        
        private void onSelectedPanelControllerChanged(PanelController selectedPanelController) {
            selectedPanelController.setEventsReceiver(selectedToolProperty.get());
            
//            Optional.ofNullable(selectedToolProperty.get()).ifPresent(selectedTool -> selectedTool.setActivePanelController(selectedPanelController));
        }
    }
}