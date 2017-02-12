package co.infinum.rxpokemon.ui.list;

import co.infinum.rxpokemon.data.model.PokemonResponse;
import co.infinum.rxpokemon.data.network.Listener;
import co.infinum.rxpokemon.ui.shared.BaseMvp;

public interface ListMvp {

    interface View extends BaseMvp.View {

        void setViewState(PokemonListViewState pokemonListViewState);
    }

    interface Presenter extends BaseMvp.Presenter {

        void init();
    }

    interface Interactor extends BaseMvp.Interactor {

        void getPokemonListAndMoves(Listener<PokemonResponse> listener);
    }

}
