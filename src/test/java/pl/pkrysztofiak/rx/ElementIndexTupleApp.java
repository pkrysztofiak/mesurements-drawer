package pl.pkrysztofiak.rx;

import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class ElementIndexTupleApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		ObservableList<String> numbers = FXCollections.observableArrayList();

		Observable<ObservableList<String>> numbersObservable = JavaFxObservable.emitOnChanged(numbers);

		Observable<String> numberObservable = numbersObservable.switchMap(Observable::fromIterable);
		Observable<Integer> indexObservable = numberObservable.map(numbers::indexOf);

		Observable<Optional<Void>> zip = Observable.zip(numberObservable, indexObservable, this::onChanged);
		zip.subscribe();

		numbers.add("one");
		numbers.add("two");
		numbers.add("three");

		numbers.remove("one");
	}

	private Optional<Void> onChanged(String number, Integer index) {
		System.out.println("number=" + number + ", index=" + index);
		return Optional.empty();
	}
}