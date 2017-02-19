package co.infinum.rxpokemon.ui.list.di;

import co.infinum.rxpokemon.data.network.ErrorHandler;
import co.infinum.rxpokemon.data.network.RxErrorHandlerDelegate;
import co.infinum.rxpokemon.shared.interfaces.StringProvider;
import co.infinum.rxpokemon.ui.list.ListMvp;
import co.infinum.rxpokemon.ui.list.PokemonListInteractor;
import co.infinum.rxpokemon.ui.list.PokemonListPresenter;
import co.infinum.rxpokemon.ui.list.PokemonMovesInteractor;
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
    public ListMvp.ListInteractor provideListInteractor(PokemonListInteractor interactor) {
        return interactor;
    }

    @Provides
    public ListMvp.MovesInteractor provideMovesInteractor(PokemonMovesInteractor interactor) {
        return interactor;
    }

    @Provides
    public ErrorHandler provideErrorHandler(StringProvider stringProvider) {
        return new RxErrorHandlerDelegate(view, stringProvider);
    }
}
