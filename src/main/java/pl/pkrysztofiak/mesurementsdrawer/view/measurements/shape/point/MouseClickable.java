package pl.pkrysztofiak.mesurementsdrawer.view.measurements.shape.point;

import io.reactivex.Observable;

public interface MouseClickable<T> {

	Observable<T> mouseClickedObservable();
}