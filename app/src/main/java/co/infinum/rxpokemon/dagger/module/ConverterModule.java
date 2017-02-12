package co.infinum.rxpokemon.dagger.module;

import javax.inject.Named;
import javax.inject.Singleton;

import co.infinum.rxpokemon.data.network.MoshiUtils;
import co.infinum.rxpokemon.shared.Constants;
import dagger.Module;
import dagger.Provides;
import retrofit2.Converter;

@Module
public class ConverterModule {

    @Provides
    @Singleton
    @Named(Constants.JSON_API_CONVERTER)
    public Converter.Factory provideJsonApiConverter() {

        return MoshiUtils.getJsonApiConverter();
    }

}