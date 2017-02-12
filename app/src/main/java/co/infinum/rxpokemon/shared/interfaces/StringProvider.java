package co.infinum.rxpokemon.shared.interfaces;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * Defines a way of receiving a String over
 * a resource ID. It holds no platform specific
 * implementations, which makes it safe for
 * CLEAN architecture and plain unit tests.
 */
public interface StringProvider {

    /**
     * @return a {@link String} value from a given
     * {@code id}, or {@code null} if no value exists
     * for a given id.
     */
    @Nullable
    String getString(@StringRes int id);

    /**
     * @return The string data associated with the resource, formatted and
     * stripped of styled text information.
     */
    @Nullable
    String getString(@StringRes int id, Object... formatArgs);

}
