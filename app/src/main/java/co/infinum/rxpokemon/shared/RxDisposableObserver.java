package co.infinum.rxpokemon.shared;


import android.support.annotation.Nullable;

import java.net.HttpURLConnection;
import java.net.UnknownHostException;

import co.infinum.rxpokemon.data.network.ErrorHandler;
import co.infinum.rxpokemon.data.network.Parser;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

public abstract class RxDisposableObserver<T> extends DisposableObserver<T> {

    private final ErrorHandler errorHandler;

    @Nullable
    private final CompositeDisposable compositeDisposable;

    public RxDisposableObserver(ErrorHandler errorHandler) {
        this(errorHandler, null);
    }

    public RxDisposableObserver(ErrorHandler errorHandler, @Nullable CompositeDisposable compositeDisposable) {
        this.errorHandler = errorHandler;
        this.compositeDisposable = compositeDisposable;
        if (this.compositeDisposable != null) {
            this.compositeDisposable.add(this);
        }
    }


    @Override
    public void onError(Throwable e) {

        if (e instanceof HttpException && (((HttpException) e).code()) == HttpURLConnection.HTTP_UNAUTHORIZED) {
            onUnauthorized(e);
        } else if (e instanceof UnknownHostException) {
            onNetworkError(e);
        } else {
            onUnknownError(e);
        }

    }

    /**
     * Override for different behaviour
     */
    protected void onUnknownError(Throwable e) {
        errorHandler.onUnknownError();
    }

    /**
     * Override for different behaviour
     */
    protected void onNetworkError(Throwable e) {
        errorHandler.onNetworkError();
    }

    /**
     * /**
     * Override for different behaviour
     */
    protected void onUnauthorized(Throwable e) {
        String error = Parser.parseErrorResponse(e);
        errorHandler.onError(error);
    }

}
