package co.infinum.rxpokemon.dagger.module;

import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import co.infinum.rxpokemon.PokemonApplication;
import dagger.Module;
import dagger.Provides;

@Module
public class AppContextModule {

    @Provides
    @Singleton
    public Context provideAppContext() {
        return PokemonApplication.getInstance();
    }

    @Provides
    @Singleton
    public Resources provideResources(Context context) {
        return context.getResources();
    }

}
