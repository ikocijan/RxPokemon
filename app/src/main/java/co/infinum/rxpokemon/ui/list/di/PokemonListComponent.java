package co.infinum.rxpokemon.ui.list.di;

import co.infinum.rxpokemon.ui.list.PokemonListActivity;
import dagger.Subcomponent;

@Subcomponent(modules = PokemonListModule.class)
public interface PokemonListComponent {

    void inject(PokemonListActivity activity);

}
