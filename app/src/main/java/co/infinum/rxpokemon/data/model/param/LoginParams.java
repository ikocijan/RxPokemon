package co.infinum.rxpokemon.data.model.param;

import com.squareup.moshi.Json;

import moe.banana.jsonapi2.JsonApi;
import moe.banana.jsonapi2.Resource;

@JsonApi(type = "session")
public final class LoginParams extends Resource{

    @Json(name = "email")
    private String email;

    @Json(name = "password")
    private String password;

    public LoginParams() {
    }

    public LoginParams(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
