package co.infinum.rxpokemon.ui.login.di;

import co.infinum.rxpokemon.data.network.ErrorHandler;
import co.infinum.rxpokemon.data.network.ErrorHandlerDelegate;
import co.infinum.rxpokemon.shared.interfaces.StringProvider;
import co.infinum.rxpokemon.ui.login.LoginInteractor;
import co.infinum.rxpokemon.ui.login.LoginMvp;
import co.infinum.rxpokemon.ui.login.LoginPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    private LoginMvp.View view;

    public LoginModule(LoginMvp.View view) {
        this.view = view;
    }

    @Provides
    public LoginMvp.View provideView() {
        return view;
    }

    @Provides
    public LoginMvp.Presenter providePresenter(LoginPresenter presenter) {
        return presenter;
    }

    @Provides
    public LoginMvp.Interactor provideInteractor(LoginInteractor interactor) {
        return interactor;
    }

    @Provides
    public ErrorHandler provideErrorHandler(StringProvider stringProvider) {
        return new ErrorHandlerDelegate(view, stringProvider);
    }

}
