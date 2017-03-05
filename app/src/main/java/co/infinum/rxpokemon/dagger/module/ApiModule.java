package co.infinum.rxpokemon.dagger.module;

import java.util.concurrent.Executor;

import co.infinum.rxpokemon.dagger.annotations.ApiUrl;
import co.infinum.rxpokemon.dagger.annotations.CallbackExecutor;
import co.infinum.rxpokemon.dagger.annotations.JsonApiConverter;
import co.infinum.rxpokemon.data.network.ApiService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@Module
public class ApiModule {

    @Provides
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    public Retrofit provideRetrofit(OkHttpClient client, @ApiUrl String baseUrl,
            @CallbackExecutor Executor callbackExecutor,
            @JsonApiConverter Converter.Factory jsonApiConverter) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .addConverterFactory(jsonApiConverter)
                .client(client);

        if (callbackExecutor != null) {
            builder.callbackExecutor(callbackExecutor);
        }
        return builder.build();
    }

}
