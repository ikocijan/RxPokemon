package co.infinum.rxpokemon.dagger.module;

import javax.inject.Singleton;

import co.infinum.rxpokemon.dagger.annotations.Computation;
import co.infinum.rxpokemon.dagger.annotations.IO;
import co.infinum.rxpokemon.dagger.annotations.MainThread;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class SchedulersModule {

    @Provides
    @Singleton
    @Computation
    public Scheduler provideComputationScheduler() {
        return Schedulers.computation();
    }

    @Provides
    @Singleton
    @IO
    public Scheduler provideIoScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @MainThread
    public Scheduler provideMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }


}
