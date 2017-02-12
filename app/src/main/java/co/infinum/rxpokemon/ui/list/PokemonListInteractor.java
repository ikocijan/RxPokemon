package co.infinum.rxpokemon.ui.list;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import co.infinum.rxpokemon.data.model.Move;
import co.infinum.rxpokemon.data.model.Pokemon;
import co.infinum.rxpokemon.data.model.PokemonResponse;
import co.infinum.rxpokemon.data.network.ApiService;
import co.infinum.rxpokemon.data.network.Listener;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PokemonListInteractor implements ListMvp.Interactor {

    CompositeDisposable disposables = new CompositeDisposable();

    private ApiService apiService;


    @Inject
    public PokemonListInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void getPokemonListAndMoves(final Listener<PokemonResponse> listener) {

        Observable<List<Move>> movesObservable =
                apiService.getMoves()
                        .map(new Function<Move[], List<Move>>() {
                            @Override
                            public List<Move> apply(Move[] moves) throws Exception {
                                return Arrays.asList(moves);
                            }
                        })
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());

        Observable<List<Pokemon>> pokemonListObservable =
                apiService.getPokemons()
                        .map(new Function<Pokemon[], List<Pokemon>>() {
                            @Override
                            public List<Pokemon> apply(Pokemon[] pokemons) throws Exception {
                                return Arrays.asList(pokemons);
                            }
                        })
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());

        disposables
                .add(Observable.zip(pokemonListObservable, movesObservable, new BiFunction<List<Pokemon>, List<Move>, PokemonResponse>() {
                    @Override
                    public PokemonResponse apply(List<Pokemon> pokemons, List<Move> moves) throws Exception {
                        return new PokemonResponse(moves, pokemons);
                    }
                }).subscribe(new Consumer<PokemonResponse>() {
                    @Override
                    public void accept(PokemonResponse pokemonResponse) throws Exception {
                        listener.onSuccess(pokemonResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        listener.onFailure("Error loading moves and pokemon list");
                    }
                }));


    }

    @Override
    public void cancel() {
        disposables.clear();
    }

    @Override
    public void reset() {

    }


}
