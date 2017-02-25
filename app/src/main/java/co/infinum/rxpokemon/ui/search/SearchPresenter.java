package co.infinum.rxpokemon.ui.search;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import co.infinum.rxpokemon.data.model.Pokemon;
import co.infinum.rxpokemon.data.network.ErrorHandler;
import co.infinum.rxpokemon.data.network.RxDisposableObserver;
import co.infinum.rxpokemon.data.network.RxSingleDisposableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter implements SearchMvp.Presenter {

    private List<Pokemon> pokemonList = new ArrayList<>();

    private SearchMvp.View view;

    private ErrorHandler errorHandler;

    @Inject
    public SearchPresenter(SearchMvp.View view, ErrorHandler errorHandler) {
        this.view = view;
        this.errorHandler = errorHandler;
    }


    @Override
    public void init(Observable<CharSequence> searchViewObservable, List<Pokemon> pokemons) {
        this.pokemonList = pokemons;

        searchViewObservable
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(CharSequence charSequence) throws Exception {
                        return !TextUtils.isEmpty(charSequence) && charSequence.length() > 3;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxDisposableObserver<CharSequence>(errorHandler) {
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
                .subscribeWith(new RxSingleDisposableObserver<List<Pokemon>>(errorHandler) {
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

    }

}
