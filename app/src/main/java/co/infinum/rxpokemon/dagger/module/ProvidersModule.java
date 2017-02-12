package co.infinum.rxpokemon.dagger.module;

import android.content.Context;

import javax.inject.Singleton;

import co.infinum.rxpokemon.shared.AndroidStringProvider;
import co.infinum.rxpokemon.shared.interfaces.StringProvider;
import dagger.Module;
import dagger.Provides;

@Module
public class ProvidersModule {

    @Provides
    @Singleton
    public StringProvider provideStringProvider(Context context) {
        return new AndroidStringProvider(context);
    }

}
