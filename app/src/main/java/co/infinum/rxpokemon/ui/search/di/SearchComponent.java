package co.infinum.rxpokemon.ui.search.di;

import co.infinum.rxpokemon.ui.search.SearchPokemonActivity;
import dagger.Subcomponent;

@Subcomponent(modules = SearchModule.class)
public interface SearchComponent {

    void inject(SearchPokemonActivity activity);

}
