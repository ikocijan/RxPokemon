package co.infinum.rxpokemon.ui.list;

import javax.inject.Inject;

import co.infinum.rxpokemon.data.model.Move;
import co.infinum.rxpokemon.data.model.Pokemon;
import co.infinum.rxpokemon.data.network.ApiService;
import io.reactivex.Observable;

public class PokemonListInteractor implements ListMvp.Interactor {

    private ApiService apiService;


    @Inject
    public PokemonListInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<Pokemon[]> getPokemonList() {
        return apiService.getPokemons();


    }

    @Override
    public Observable<Move[]> getPokemonMoves() {
        return apiService.getMoves();
    }

    @Override
    public void cancel() {
    }

    @Override
    public void reset() {

    }


}
