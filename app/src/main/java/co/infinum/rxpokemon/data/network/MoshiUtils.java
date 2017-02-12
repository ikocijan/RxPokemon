package co.infinum.rxpokemon.data.network;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import co.infinum.rxpokemon.data.model.Pokemon;
import co.infinum.rxpokemon.data.model.param.LoginParams;
import co.infinum.rxpokemon.data.model.response.LoginResponse;
import moe.banana.jsonapi2.ResourceAdapterFactory;
import retrofit2.Converter;

public class MoshiUtils {

    private MoshiUtils() {
    }

    public static Converter.Factory getJsonApiConverter() {
        return JsonApiConverterFactory.create(getMoshi());
    }

    public static JsonAdapter.Factory getAdapterFactory() {
        return ResourceAdapterFactory.builder()
                .add(LoginResponse.class)
                .add(LoginParams.class)
                .add(Pokemon.class)
                .build();
    }

    public static Moshi getMoshi() {
        return new Moshi.Builder()
                .add(getAdapterFactory())
                .build();
    }

}
