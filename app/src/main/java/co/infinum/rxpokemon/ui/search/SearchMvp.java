package co.infinum.rxpokemon.ui.search;

import java.util.List;

import co.infinum.rxpokemon.data.model.Pokemon;
import co.infinum.rxpokemon.ui.shared.BaseMvp;
import io.reactivex.Observable;

public interface SearchMvp {

    interface View extends BaseMvp.View {

        void setViewState(PokemonSearchViewState pokemonSearchViewState);
    }

    interface Presenter extends BaseMvp.Presenter {

        void search(String query);

        void init(Observable<CharSequence> searchViewObservable, List<Pokemon> pokemons);
    }


}
