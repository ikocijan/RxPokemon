package co.infinum.rxpokemon.data.network;


import java.net.HttpURLConnection;
import java.net.UnknownHostException;

import io.reactivex.SingleObserver;
import retrofit2.HttpException;

public abstract class RxSingleDisposableObserver<T> implements SingleObserver<T> {

    private ErrorHandler errorHandler;

    protected RxSingleDisposableObserver(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
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
