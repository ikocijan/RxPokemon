package co.infinum.rxpokemon.ui.search;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import co.infinum.rxpokemon.data.model.Pokemon;
import co.infinum.rxpokemon.data.network.ErrorHandler;
import co.infinum.rxpokemon.shared.RxDisposableObserver;
import co.infinum.rxpokemon.shared.RxSingleDisposableObserver;
import co.infinum.rxpokemon.shared.SimpleObservable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter implements SearchMvp.Presenter {

    private List<Pokemon> pokemonList = new ArrayList<>();

    private SearchMvp.View view;

    private ErrorHandler errorHandler;

    private SimpleObservable<String> searchObservable = new SimpleObservable<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public SearchPresenter(SearchMvp.View view, ErrorHandler errorHandler) {
        this.view = view;
        this.errorHandler = errorHandler;
    }


    @Override
    public void init(List<Pokemon> pokemons) {
        this.pokemonList = pokemons;

        searchObservable
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        return !TextUtils.isEmpty(charSequence) && charSequence.length() > 3;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxDisposableObserver<CharSequence>(errorHandler, compositeDisposable) {
                    @Override
                    public void onNext(CharSequence charSequence) {
                        search(charSequence.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onSearchChanged(String newText) {
        searchObservable.emit(newText);
    }

    public void search(final String query) {

        Observable
                .fromIterable(pokemonList)
                .filter(new Predicate<Pokemon>() {
                    @Override
                    public boolean test(Pokemon pokemon) throws Exception {
                        return pokemon.getName().toLowerCase().contains(query.toLowerCase());
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribeWith(new RxSingleDisposableObserver<List<Pokemon>>(errorHandler, compositeDisposable) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        view.setViewState(new PokemonSearchViewState(State.LOADING));
                    }

                    @Override
                    public void onSuccess(List<Pokemon> pokemons) {
                        view.setViewState(new PokemonSearchViewState(State.SHOW_POKEMON_LIST, pokemons));
                    }
                });

    }


    @Override
    public void cancel() {
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

}
