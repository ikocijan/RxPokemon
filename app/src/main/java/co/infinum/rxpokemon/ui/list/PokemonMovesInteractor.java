package co.infinum.rxpokemon.ui.list;

import javax.inject.Inject;

import co.infinum.rxpokemon.data.model.Move;
import co.infinum.rxpokemon.data.network.ApiService;
import io.reactivex.Observable;

public class PokemonMovesInteractor implements ListMvp.MovesInteractor {

    private ApiService apiService;

    @Inject
    PokemonMovesInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<Move[]> execute(Void aVoid) {
        return apiService.getMoves();
    }
}
