package pl.pkrysztofiak.mesurementsdrawer.view.measurements.graphics;

import io.reactivex.functions.Consumer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleBehaviour {

    private final Behaviour behaviour = new Behaviour();
    
    private final Circle circle;

    public CircleBehaviour(Circle circle) {
        this.circle = circle;
    }
    
    public Consumer<Boolean> blueOnHover() {
        return behaviour.blueOnHover(circle);
    }
    
    
    
    private class Behaviour {
        
        private Consumer<Boolean> blueOnHover(Circle circle) {
            return hover -> {
                if (hover) {
                    circle.setFill(Color.BLUE);
                }
            };
        }
        
        
        
    }
}
