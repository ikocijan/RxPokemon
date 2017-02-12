package co.infinum.rxpokemon.ui.list;

import java.util.List;

import co.infinum.rxpokemon.data.model.Pokemon;
import co.infinum.rxpokemon.ui.shared.ViewState;

public final class PokemonListViewState implements ViewState {


    private String error;

    private
    @State
    int state;

    private List<Pokemon> pokemons;

    public PokemonListViewState(int state) {
        this.state = state;
    }

    public PokemonListViewState(int state, List<Pokemon> pokemons) {
        this.state = state;
        this.pokemons = pokemons;
    }

    public PokemonListViewState(int state, String error) {
        this.state = state;
        this.error = error;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    @Override
    public
    @State
    int getState() {
        return state;
    }


    public String getError() {
        return error;
    }
}
