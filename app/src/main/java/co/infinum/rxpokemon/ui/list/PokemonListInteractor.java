package co.infinum.rxpokemon.ui.list;

import javax.inject.Inject;

import co.infinum.rxpokemon.data.model.Pokemon;
import co.infinum.rxpokemon.data.network.ApiService;
import io.reactivex.Observable;

public class PokemonListInteractor implements ListMvp.ListInteractor {

    private ApiService apiService;


    @Inject
    public PokemonListInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<Pokemon[]> execute(Void aVoid) {
        return apiService.getPokemons();
    }
}
