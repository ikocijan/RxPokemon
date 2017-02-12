package co.infinum.rxpokemon.ui.list.di;

import co.infinum.rxpokemon.ui.list.ListMvp;
import co.infinum.rxpokemon.ui.list.PokemonListInteractor;
import co.infinum.rxpokemon.ui.list.PokemonListPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class PokemonListModule {

    private ListMvp.View view;

    public PokemonListModule(ListMvp.View view) {
        this.view = view;
    }

    @Provides
    public ListMvp.View provideView() {
        return view;
    }

    @Provides
    public ListMvp.Presenter providePresenter(PokemonListPresenter presenter) {
        return presenter;
    }

    @Provides
    public ListMvp.Interactor provideInteractor(PokemonListInteractor interactor) {
        return interactor;
    }

}
