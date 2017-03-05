package co.infinum.rxpokemon.ui.shared;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import co.infinum.rxpokemon.PokemonApplication;
import co.infinum.rxpokemon.R;
import co.infinum.rxpokemon.dagger.component.AppComponent;

public abstract class BaseActivity extends AppCompatActivity implements BaseMvp.View {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        injectDependencies(PokemonApplication.getInstance().getAppComponent());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

    }

    protected abstract void injectDependencies(AppComponent appComponent);

    @LayoutRes
    protected abstract int getLayoutResourceId();


    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showMessage(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name))
                .setMessage(message)
                .setPositiveButton("OK", null).create().show();

    }

}
