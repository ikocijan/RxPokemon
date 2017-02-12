package co.infinum.rxpokemon.ui.list;

import javax.inject.Inject;

import co.infinum.rxpokemon.data.model.PokemonResponse;
import co.infinum.rxpokemon.data.network.Listener;
import timber.log.Timber;

public class PokemonListPresenter implements ListMvp.Presenter {

    private ListMvp.View view;

    private ListMvp.Interactor interactor;

    @Inject
    public PokemonListPresenter(ListMvp.View view, ListMvp.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void init() {

        view.setViewState(new PokemonListViewState(State.LOADING));

        interactor.getPokemonListAndMoves(new Listener<PokemonResponse>() {
            @Override
            public void onSuccess(PokemonResponse result) {

                Timber.d("Pokemon list: " + result.getPokemons().size());
                Timber.d("Pokemon moves: " + result.getMoves().size());

                view.setViewState(new PokemonListViewState(State.SHOW_POKEMON_LIST, result.getPokemons()));


            }

            @Override
            public void onFailure(String error) {
                view.setViewState(new PokemonListViewState(State.SHOW_ERROR, error));

            }
        });

    }

    @Override
    public void cancel() {
        interactor.cancel();
    }
}
