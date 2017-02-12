package co.infinum.rxpokemon.dagger.module;

import javax.inject.Named;
import javax.inject.Singleton;

import co.infinum.rxpokemon.BuildConfig;
import co.infinum.rxpokemon.shared.Constants;
import dagger.Module;
import dagger.Provides;

@Module
public class HostModule {

    public static final int NETWORK_TIMEOUT_SECONDS = 30;

    @Provides
    @Singleton
    public Integer provideNetworkTimeout() {
        return NETWORK_TIMEOUT_SECONDS;
    }

    @Provides
    @Named(Constants.API_URL)
    public String provideBaseUrl() {
        return BuildConfig.API_URL;
    }

}
