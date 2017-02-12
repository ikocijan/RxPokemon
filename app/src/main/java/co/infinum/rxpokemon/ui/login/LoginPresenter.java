package co.infinum.rxpokemon.ui.login;

import android.text.TextUtils;
import android.util.Patterns;

import javax.inject.Inject;

import co.infinum.rxpokemon.data.model.User;
import co.infinum.rxpokemon.data.model.param.LoginParams;
import co.infinum.rxpokemon.data.model.response.LoginResponse;
import co.infinum.rxpokemon.data.network.Listener;
import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.subscribers.DisposableSubscriber;

public class LoginPresenter implements LoginMvp.Presenter {

    private LoginMvp.View view;

    private LoginMvp.Interactor interactor;

    private DisposableSubscriber<Boolean> disposableObserver = null;

    @Inject
    public LoginPresenter(LoginMvp.View view, LoginMvp.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }


    @Override
    public void init(Flowable<CharSequence> emailChangeObservable, Flowable<CharSequence> passwordChangeObservable) {

        disposableObserver = new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean inputValid) {
                view.enableLogin(inputValid);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };

        Flowable.combineLatest(emailChangeObservable, passwordChangeObservable, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence email, CharSequence password) throws Exception {

                boolean isEmailValid = !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
                boolean isPasswordValid = !TextUtils.isEmpty(password);

                return isEmailValid && isPasswordValid;
            }
        }).subscribe(disposableObserver);

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
    public void onDestroy() {
        disposableObserver.dispose();
    }


    @Override
    public void cancel() {
        interactor.cancel();
    }

}
