package co.infinum.rxpokemon.dagger.module;

import javax.inject.Singleton;

import co.infinum.rxpokemon.dagger.annotations.JsonApiConverter;
import co.infinum.rxpokemon.data.network.MoshiUtils;
import dagger.Module;
import dagger.Provides;
import retrofit2.Converter;

@Module
public class ConverterModule {

    @Provides
    @Singleton
    @JsonApiConverter
    public Converter.Factory provideJsonApiConverter() {

        return MoshiUtils.getJsonApiConverter();
    }

}