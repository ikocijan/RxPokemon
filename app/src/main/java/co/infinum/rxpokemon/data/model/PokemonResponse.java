package co.infinum.rxpokemon.data.model;

import java.util.List;

public final class PokemonResponse {

    private List<Move> moves;

    private List<Pokemon> pokemons;

    public PokemonResponse(List<Move> moves, List<Pokemon> pokemons) {
        this.moves = moves;
        this.pokemons = pokemons;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }
}
