package co.infinum.rxpokemon.ui.search.di;

import co.infinum.rxpokemon.data.network.ErrorHandler;
import co.infinum.rxpokemon.data.network.ErrorHandlerDelegate;
import co.infinum.rxpokemon.shared.interfaces.StringProvider;
import co.infinum.rxpokemon.ui.search.SearchMvp;
import co.infinum.rxpokemon.ui.search.SearchPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    private SearchMvp.View view;

    public SearchModule(SearchMvp.View view) {
        this.view = view;
    }

    @Provides
    public SearchMvp.View provideView() {
        return view;
    }

    @Provides
    public SearchMvp.Presenter providePresenter(SearchPresenter presenter) {
        return presenter;
    }

    @Provides
    public ErrorHandler provideErrorHandler(StringProvider provider) {
        return new ErrorHandlerDelegate(view, provider);
    }


}
