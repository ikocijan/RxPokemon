package co.infinum.rxpokemon.ui.login;

import javax.inject.Inject;

import co.infinum.rxpokemon.data.model.User;
import co.infinum.rxpokemon.data.model.param.LoginParams;
import co.infinum.rxpokemon.data.model.response.LoginResponse;
import co.infinum.rxpokemon.data.network.Listener;

public class LoginPresenter implements LoginMvp.Presenter {

    private LoginMvp.View view;

    private LoginMvp.Interactor interactor;


    @Inject
    public LoginPresenter(LoginMvp.View view, LoginMvp.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void init() {

    }

    @Override
    public void login(String username, String password) {

        view.setState(new LoginViewState(State.LOADING));

        LoginParams params = new LoginParams(username, password);

        interactor.loginUser(params, new Listener<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse result) {

                User.getInstance().setEmail(result.getEmail());
                User.getInstance().setAuthToken(result.getAuthToken());
                view.setState(new LoginViewState(State.LOGIN_COMPLETED));

            }

            @Override
            public void onFailure(String error) {
                view.setState(new LoginViewState(error, State.SHOW_MESSAGE));
            }
        });

    }

    @Override
    public void cancel() {
        interactor.cancel();
    }

}
