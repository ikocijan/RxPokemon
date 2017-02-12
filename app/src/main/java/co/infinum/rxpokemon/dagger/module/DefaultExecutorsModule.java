package co.infinum.rxpokemon.dagger.module;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import co.infinum.rxpokemon.shared.Constants;
import dagger.Module;
import dagger.Provides;
import okhttp3.Dispatcher;

@Module
public class DefaultExecutorsModule {

    private static final int NUMBER_OF_THREADS = 3;

    @Provides
    @Singleton
    @Named(Constants.HTTP_EXECUTOR)
    public Executor provideHttpExecutor() {
        return Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }

    @Provides
    @Singleton
    @Named(Constants.CALLBACK_EXECUTOR)
    public Executor provideCallbackExecutor() {
        return new MainThreadExecutor();
    }

    @Provides
    @Singleton
    public Dispatcher provideDispatcher() {
        return new Dispatcher();
    }

    private static class MainThreadExecutor implements Executor {

        private final Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable r) {
            handler.post(r);
        }

        @Override
        public String toString() {
            return "MainThreadExecutor";
        }
    }

}
