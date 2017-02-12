package co.infinum.rxpokemon.ui.login;

import com.jakewharton.rxbinding.widget.RxTextView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.infinum.rxpokemon.R;
import co.infinum.rxpokemon.dagger.component.AppComponent;
import co.infinum.rxpokemon.ui.list.PokemonListActivity;
import co.infinum.rxpokemon.ui.login.di.LoginModule;
import co.infinum.rxpokemon.ui.shared.BaseActivity;
import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Flowable;

public class LoginActivity extends BaseActivity implements LoginMvp.View {

    @Inject
    protected LoginMvp.Presenter presenter;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_login)
    Button btnLogin;

    private Flowable<CharSequence> emailChangeObservable;

    private Flowable<CharSequence> passwordChangeObservable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        btnLogin.setEnabled(false);

        emailChangeObservable = RxJavaInterop.toV2Flowable(RxTextView
                .textChanges(etEmail)
                .skip(1));

        passwordChangeObservable = RxJavaInterop.toV2Flowable(RxTextView
                .textChanges(etPassword)
                .skip(1));

        presenter.init(emailChangeObservable, passwordChangeObservable);

    }

    @Override
    protected void injectDependencies(AppComponent appComponent) {
        appComponent.plus(new LoginModule(this)).inject(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        presenter.login(etEmail.getText().toString(), etPassword.getText().toString());
    }


    @Override
    public void setState(LoginViewState loginViewState) {

        switch (loginViewState.getState()) {
            case State.LOADING:
                showProgress();
                break;

            case State.LOGIN_COMPLETED:
                hideProgress();
                showPokemonList();
                break;

            case State.SHOW_MESSAGE:
                hideProgress();
                showMessage(loginViewState.getMessage());
                break;

            default:
                break;
        }

    }

    @Override
    public void enableLogin(Boolean inputValid) {
        btnLogin.setEnabled(inputValid);
    }

    private void showPokemonList() {
        startActivity(new Intent(this, PokemonListActivity.class));
    }

    @Override
    protected void onStop() {
        presenter.cancel();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
