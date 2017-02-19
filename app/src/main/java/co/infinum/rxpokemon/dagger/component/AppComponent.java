package co.infinum.rxpokemon.dagger.component;

import javax.inject.Singleton;

import co.infinum.rxpokemon.PokemonApplication;
import co.infinum.rxpokemon.dagger.module.ApiModule;
import co.infinum.rxpokemon.dagger.module.AppContextModule;
import co.infinum.rxpokemon.dagger.module.ClientModule;
import co.infinum.rxpokemon.dagger.module.ConverterModule;
import co.infinum.rxpokemon.dagger.module.DefaultExecutorsModule;
import co.infinum.rxpokemon.dagger.module.HostModule;
import co.infinum.rxpokemon.dagger.module.ProvidersModule;
import co.infinum.rxpokemon.dagger.module.SchedulersModule;
import co.infinum.rxpokemon.ui.list.di.PokemonListComponent;
import co.infinum.rxpokemon.ui.list.di.PokemonListModule;
import co.infinum.rxpokemon.ui.login.di.LoginComponent;
import co.infinum.rxpokemon.ui.login.di.LoginModule;
import co.infinum.rxpokemon.ui.search.di.SearchComponent;
import co.infinum.rxpokemon.ui.search.di.SearchModule;
import dagger.Component;

@Component(modules = {
        HostModule.class,
        DefaultExecutorsModule.class,
        ConverterModule.class,
        AppContextModule.class,
        ApiModule.class,
        ClientModule.class,
        ProvidersModule.class,
        SchedulersModule.class
})
@Singleton
public interface AppComponent {

    void inject(PokemonApplication application);

    PokemonListComponent plus(PokemonListModule module);

    LoginComponent plus(LoginModule module);

    SearchComponent plus(SearchModule searchModule);

}
