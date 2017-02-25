package co.infinum.rxpokemon.data.network;


public interface ErrorHandler {

    void onUnknownError();

    void onNetworkError();

    void onError(String message);
}