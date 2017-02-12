package co.infinum.rxpokemon.shared;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import co.infinum.rxpokemon.shared.interfaces.StringProvider;

public class AndroidStringProvider implements StringProvider {

    private final Context appContext;

    public AndroidStringProvider(@NonNull Context appContext) {
        this.appContext = appContext.getApplicationContext();
    }

    @Nullable
    @Override
    public String getString(@StringRes int id) {
        return appContext.getString(id);
    }

    @Nullable
    @Override
    public String getString(@StringRes int id, Object... formatArgs) {
        return appContext.getString(id, formatArgs);
    }

}
