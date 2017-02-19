package co.infinum.rxpokemon.ui.login;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@IntDef({})
public @interface State {

    int LOADING = 0;
    int SHOW_MESSAGE = 1;
    int LOGIN_COMPLETED = 2;
    int INVALID_INPUT = 3;

}
