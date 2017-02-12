package co.infinum.rxpokemon.ui.search;

import com.jakewharton.rxbinding.widget.RxSearchView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.infinum.mjolnirrecyclerview.MjolnirRecyclerView;
import co.infinum.rxpokemon.R;
import co.infinum.rxpokemon.dagger.component.AppComponent;
import co.infinum.rxpokemon.data.model.Pokemon;
import co.infinum.rxpokemon.ui.search.di.SearchModule;
import co.infinum.rxpokemon.ui.shared.BaseActivity;
import co.infinum.rxpokemon.ui.shared.PokemonListAdapter;
import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Observable;

public class SearchPokemonActivity extends BaseActivity implements SearchMvp.View {

    private static final String EXTRA_POKEMON_LIST = "EXTRA_POKEMON_LIST";

    @Inject
    protected SearchMvp.Presenter presenter;

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.rv_pokemon)
    MjolnirRecyclerView rvPokemon;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private PokemonListAdapter pokemonListAdapter;

    public static Intent newIntent(Context context, List<Pokemon> pokemonList) {

        Intent intent = new Intent(context, SearchPokemonActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_POKEMON_LIST, (ArrayList<? extends Parcelable>) pokemonList);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        pokemonListAdapter = new PokemonListAdapter(this, Collections.<Pokemon>emptyList(), null);
        rvPokemon.setAdapter(pokemonListAdapter);

        Observable<CharSequence> searchViewObservable =
                RxJavaInterop.toV2Observable(RxSearchView.queryTextChanges(searchView));

        List<Pokemon> pokemons = getIntent().getParcelableArrayListExtra(EXTRA_POKEMON_LIST);

        presenter.init(searchViewObservable, pokemons);

    }

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.plus(new SearchModule(this)).inject(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_search_pokemon;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setViewState(PokemonSearchViewState pokemonSearchViewState) {

        switch (pokemonSearchViewState.getState()) {
            case State.LOADING:
                rvPokemon.setVisibility(View.GONE);
                showProgress();
                break;

            case State.SHOW_POKEMON_LIST:
                hideProgress();
                rvPokemon.setVisibility(View.VISIBLE);
                pokemonListAdapter.clear();
                pokemonListAdapter.addAll(pokemonSearchViewState.getPokemons());
                break;
        }

    }
}
