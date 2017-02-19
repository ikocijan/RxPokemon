package co.infinum.rxpokemon.data.model.response;

import com.squareup.moshi.Json;

import moe.banana.jsonapi2.JsonApi;
import moe.banana.jsonapi2.Resource;

@JsonApi(type = "users")
public final class LoginResponse extends Resource{

    @Json(name = "email")
    private String email;

    @Json(name = "password")
    private String password;

    @Json(name = "auth-token")
    private String authToken;

    public LoginResponse(String email, String password, String authToken) {
        this.email = email;
        this.password = password;
        this.authToken = authToken;
    }

    public LoginResponse() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthToken() {
        return authToken;
    }
}
