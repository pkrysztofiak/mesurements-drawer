package pl.pkrysztofiak.rx;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class SwitchMapListApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		ObservableList<String> numbers = FXCollections.observableArrayList();

		Observable<ObservableList<String>> numbersObservable = JavaFxObservable.emitOnChanged(numbers);
		Observable<String> firstObservable = numbersObservable.switchMap(list -> Observable.fromIterable(list).take(1));
//		Observable<String> lastObservable = numbersObservable.switchMap(list -> Observable.fromIterable(list).skip(numbers.size() - 1));
		Observable<String> lastObservable = numbersObservable.switchMap(list -> Observable.fromIterable(list).skip(numbers.size() - 1));

		firstObservable.subscribe(first -> System.out.println("first=" + first));
		lastObservable.subscribe(last -> System.out.println("last=" + last));

		numbers.add("one");
		numbers.add("two");
		numbers.add("three");
//
		numbers.remove("one");

//		numbers.remove("one");
	}

}
