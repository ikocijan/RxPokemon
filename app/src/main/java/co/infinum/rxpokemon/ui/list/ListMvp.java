package co.infinum.rxpokemon.ui.list;

import co.infinum.rxpokemon.data.model.Move;
import co.infinum.rxpokemon.data.model.Pokemon;
import co.infinum.rxpokemon.ui.shared.BaseMvp;
import io.reactivex.Observable;

public interface ListMvp {

    interface View extends BaseMvp.View {

        void setViewState(PokemonListViewState pokemonListViewState);
    }

    interface Presenter extends BaseMvp.Presenter {

        void init();
    }

    interface Interactor extends BaseMvp.Interactor {

        Observable<Pokemon[]> getPokemonList();

        Observable<Move[]> getPokemonMoves();

    }

}
