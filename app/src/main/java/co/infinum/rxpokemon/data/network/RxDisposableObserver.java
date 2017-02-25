package co.infinum.rxpokemon.data.network;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.net.HttpURLConnection;
import java.net.UnknownHostException;

import co.infinum.rxpokemon.data.model.response.ErrorResponse;
import io.reactivex.observers.DisposableObserver;

public abstract class RxDisposableObserver<T> extends DisposableObserver<T> {

    private ErrorHandler errorHandler;

    public RxDisposableObserver(ErrorHandler errorHandler) {
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

        String error = "Login error";

        try {

            Moshi moshi = new Moshi.Builder()
                    .build();

            String errorJson = ((HttpException) e).response().errorBody().string();
            JsonAdapter<ErrorResponse> errorResponseJsonAdapter = moshi.adapter(ErrorResponse.class);
            ErrorResponse errorResponse = errorResponseJsonAdapter.fromJson(errorJson);

            error = errorResponse.getErrorList().get(0).getDetail();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        errorHandler.onError(error);


    }

}
