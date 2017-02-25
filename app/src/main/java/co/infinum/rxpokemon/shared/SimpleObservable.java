package co.infinum.rxpokemon.shared;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SimpleObservable<T> extends Observable<T> {

    private List<Observer<? super T>> observers = new ArrayList<>();

    @Override
    protected void subscribeActual(final Observer<? super T> observer) {

        observers.add(observer);
        observer.onSubscribe(new Disposable() {
            @Override
            public void dispose() {
                observers.remove(observer);
            }

            @Override
            public boolean isDisposed() {
                return observers.contains(observer);
            }
        });
    }

    public void emit(T type) {
        for (Observer<? super T> observer : observers) {
            observer.onNext(type);
        }
    }

}
