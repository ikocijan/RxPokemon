package co.infinum.rxpokemon.ui.login.di;

import co.infinum.rxpokemon.ui.login.LoginActivity;
import dagger.Subcomponent;

@Subcomponent(modules = LoginModule.class)
public interface LoginComponent {

    void inject(LoginActivity activity);

}
