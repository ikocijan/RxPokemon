package co.infinum.rxpokemon.data.network;

import co.infinum.rxpokemon.data.model.Move;
import co.infinum.rxpokemon.data.model.Pokemon;
import co.infinum.rxpokemon.data.model.param.LoginParams;
import co.infinum.rxpokemon.data.model.response.LoginResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("api/v1/pokemons")
    Observable<Pokemon[]> getPokemons();

    @GET("/api/v1/moves")
    Observable<Move[]> getMoves();

    @POST("api/v1/users/login")
    Observable<LoginResponse> loginUser(@Body LoginParams params);

}
