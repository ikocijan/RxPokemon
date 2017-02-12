package co.infinum.rxpokemon.data.model;

import com.squareup.moshi.Json;

import moe.banana.jsonapi2.JsonApi;
import moe.banana.jsonapi2.Resource;

@JsonApi(type = "moves")
public final class Move extends Resource {

    @Json(name = "name")
    private String name;

    @Json(name = "accuracy")
    private int accuracy;

    @Json(name = "power")
    private String power;

    public String getName() {
        return name;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public String getPower() {
        return power;
    }
}
