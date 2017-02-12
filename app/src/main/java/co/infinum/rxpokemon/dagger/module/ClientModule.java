package co.infinum.rxpokemon.dagger.module;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import co.infinum.rxpokemon.BuildConfig;
import co.infinum.rxpokemon.data.network.RequestInterceptor;
import dagger.Module;
import dagger.Provides;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

@Module
public class ClientModule {

    private HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Timber.tag("api_tag").d(message);
        }
    });

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Integer networkTimeoutSeconds, Dispatcher dispatcher) {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        clientBuilder.connectTimeout(networkTimeoutSeconds, TimeUnit.SECONDS);
        clientBuilder.readTimeout(networkTimeoutSeconds, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(networkTimeoutSeconds, TimeUnit.SECONDS);
        clientBuilder.dispatcher(dispatcher);
        clientBuilder.addNetworkInterceptor(new RequestInterceptor());


        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(httpLoggingInterceptor);
        }
        return clientBuilder.build();
    }

}
