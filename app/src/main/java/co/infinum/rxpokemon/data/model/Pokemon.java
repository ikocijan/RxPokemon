package co.infinum.rxpokemon.data.model;

import com.squareup.moshi.Json;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import moe.banana.jsonapi2.HasMany;
import moe.banana.jsonapi2.JsonApi;
import moe.banana.jsonapi2.Resource;

@JsonApi(type = "pokemons")
public final class Pokemon extends Resource implements Parcelable {

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel source) {
            return new Pokemon(source);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    private String name;

    @Json(name = "moves")
    private HasMany<Move> moves;

    public Pokemon() {
    }

    public Pokemon(String name) {
        this.name = name;
    }

    protected Pokemon(Parcel in) {
        this.name = in.readString();
    }

    public String getName() {
        return name;
    }

    public List<Move> getMoves() {
        return moves.get(getContext());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }
}
