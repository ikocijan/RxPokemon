package co.infinum.rxpokemon.ui.search;

import java.util.List;

import co.infinum.rxpokemon.data.model.Pokemon;
import co.infinum.rxpokemon.ui.shared.ViewState;


public final class PokemonSearchViewState implements ViewState {

    private
    @State
    int state;

    private List<Pokemon> pokemons;

    public PokemonSearchViewState(int state) {
        this.state = state;
    }

    public PokemonSearchViewState(int state, List<Pokemon> pokemons) {
        this.state = state;
        this.pokemons = pokemons;
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
}
