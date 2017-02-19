package co.infinum.rxpokemon.ui.list;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import co.infinum.rxpokemon.data.model.Move;
import co.infinum.rxpokemon.data.model.Pokemon;
import co.infinum.rxpokemon.data.model.PokemonResponse;
import co.infinum.rxpokemon.data.network.ErrorHandler;
import co.infinum.rxpokemon.data.network.RxDisposableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PokemonListPresenter implements ListMvp.Presenter {

    private Disposable rxDisposable;

    private ListMvp.View view;

    private ListMvp.ListInteractor listInteractor;

    private ListMvp.MovesInteractor movesInteractor;

    private ErrorHandler errorHandler;

    @Inject
    public PokemonListPresenter(ListMvp.View view, ListMvp.ListInteractor listInteractor, ListMvp.MovesInteractor movesInteractor,
            ErrorHandler errorHandler) {
        this.view = view;
        this.listInteractor = listInteractor;
        this.movesInteractor = movesInteractor;
        this.errorHandler = errorHandler;
    }

    @Override
    public void init() {

        view.setViewState(new PokemonListViewState(State.LOADING));
        loadPokemonListAndMoves();

    }

    private void loadPokemonListAndMoves() {

        Observable<List<Move>> movesObservable = movesInteractor.execute(null)
                .map(new Function<Move[], List<Move>>() {
                    @Override
                    public List<Move> apply(Move[] moves) throws Exception {
                        return Arrays.asList(moves);
                    }
                }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

        Observable<List<Pokemon>> pokemonListObservable =
                listInteractor.execute(null)
                        .map(new Function<Pokemon[], List<Pokemon>>() {
                            @Override
                            public List<Pokemon> apply(Pokemon[] pokemons) throws Exception {
                                return Arrays.asList(pokemons);
                            }
                        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

        rxDisposable = Observable.zip(pokemonListObservable, movesObservable, new BiFunction<List<Pokemon>, List<Move>, PokemonResponse>() {
            @Override
            public PokemonResponse apply(List<Pokemon> pokemons, List<Move> moves) throws Exception {
                return new PokemonResponse(moves, pokemons);
            }
        }).subscribeWith(new RxDisposableObserver<PokemonResponse>(errorHandler) {

            @Override
            public void onNext(PokemonResponse pokemonResponse) {
                Timber.d("Pokemon list: " + pokemonResponse.getPokemons().size());
                Timber.d("Pokemon moves: " + pokemonResponse.getMoves().size());

                view.setViewState(new PokemonListViewState(State.SHOW_POKEMON_LIST, pokemonResponse.getPokemons()));
            }

            @Override
            public void onComplete() {

            }
        });


    }

    @Override
    public void cancel() {

        if (rxDisposable != null && !rxDisposable.isDisposed()) {
            rxDisposable.dispose();
        }

    }
}
