package co.infinum.rxpokemon;

import com.jakewharton.threetenabp.AndroidThreeTen;

import android.app.Application;

import co.infinum.rxpokemon.dagger.component.AppComponent;
import co.infinum.rxpokemon.dagger.component.DaggerAppComponent;
import timber.log.Timber;

public class PokemonApplication extends Application{

    private static PokemonApplication instance;

    protected AppComponent appComponent;

    public static PokemonApplication getInstance() {
        return instance;
    }

    public static void setInstance(PokemonApplication instance) {
        PokemonApplication.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        AndroidThreeTen.init(this);
        injectDependencies();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

    }

    private void injectDependencies() {
        appComponent = DaggerAppComponent.create();
        appComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
