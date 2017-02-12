package co.infinum.rxpokemon.data.network;

public interface Listener<T> {

    void onSuccess(T result);

    void onFailure(String error);

}
