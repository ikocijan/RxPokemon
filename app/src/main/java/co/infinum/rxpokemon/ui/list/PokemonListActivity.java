package co.infinum.rxpokemon.ui.list;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.infinum.mjolnirrecyclerview.MjolnirRecyclerView;
import co.infinum.rxpokemon.PokemonApplication;
import co.infinum.rxpokemon.R;
import co.infinum.rxpokemon.dagger.component.AppComponent;
import co.infinum.rxpokemon.data.model.Pokemon;
import co.infinum.rxpokemon.ui.list.di.PokemonListModule;
import co.infinum.rxpokemon.ui.search.SearchPokemonActivity;
import co.infinum.rxpokemon.ui.shared.BaseActivity;
import co.infinum.rxpokemon.ui.shared.ClickListener;
import co.infinum.rxpokemon.ui.shared.PokemonListAdapter;

public class PokemonListActivity extends BaseActivity implements ListMvp.View {

    @Inject
    PokemonListPresenter presenter;

    @BindView(R.id.rv_pokemon)
    MjolnirRecyclerView rvPokemon;

    private PokemonListAdapter pokemonListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pokemonListAdapter = new PokemonListAdapter(this, Collections.<Pokemon>emptyList(), new ClickListener<Pokemon>() {
            @Override
            public void onClick(Pokemon item) {
                startActivity(SearchPokemonActivity.newIntent(PokemonListActivity.this, (ArrayList<Pokemon>) pokemonListAdapter.getAll()));
            }
        });
        rvPokemon.setAdapter(pokemonListAdapter);

        PokemonApplication.getInstance().getAppComponent().plus(new PokemonListModule(this)).inject(this);
        presenter.init();
    }

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.plus(new PokemonListModule(this)).inject(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void setViewState(PokemonListViewState pokemonListViewState) {

        switch (pokemonListViewState.getState()) {
            case State.LOADING:
                showProgress();
                break;

            case State.SHOW_POKEMON_LIST:
                hideProgress();
                pokemonListAdapter.clear();
                pokemonListAdapter.addAll(pokemonListViewState.getPokemons());
                break;

            case State.SHOW_ERROR:
                hideProgress();
                showMessage(pokemonListViewState.getError());
                break;
            default:
                break;
        }

    }
}
