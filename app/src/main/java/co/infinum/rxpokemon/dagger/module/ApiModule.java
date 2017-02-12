package co.infinum.rxpokemon.dagger.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.Executor;

import javax.inject.Named;

import co.infinum.rxpokemon.data.network.ApiService;
import co.infinum.rxpokemon.shared.Constants;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    @Provides
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    public Retrofit provideRetrofit(OkHttpClient client, @Named(Constants.API_URL) String baseUrl,
            @Named(Constants.CALLBACK_EXECUTOR) Executor callbackExecutor,
            @Named(Constants.JSON_API_CONVERTER) Converter.Factory jsonApiConverter) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(jsonApiConverter)
                .client(client);

        if (callbackExecutor != null) {
            builder.callbackExecutor(callbackExecutor);
        }
        return builder.build();
    }

}
