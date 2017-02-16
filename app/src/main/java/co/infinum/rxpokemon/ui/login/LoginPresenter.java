package co.infinum.rxpokemon.ui.login;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import javax.inject.Inject;

import co.infinum.rxpokemon.data.model.User;
import co.infinum.rxpokemon.data.model.param.LoginParams;
import co.infinum.rxpokemon.data.model.response.ErrorResponse;
import co.infinum.rxpokemon.data.model.response.LoginResponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements LoginMvp.Presenter {

    public static final int NUMBER_OF_RETRIES = 1;

    private LoginMvp.View view;

    private LoginMvp.Interactor interactor;

    private Disposable loginDisposable;


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

        cancel();
        view.setState(new LoginViewState(State.LOADING));

        LoginParams params = new LoginParams(username, password);

        loginDisposable = interactor.loginUser(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(NUMBER_OF_RETRIES)
                .subscribeWith(new DisposableObserver<LoginResponse>() {
                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        User.getInstance().setEmail(loginResponse.getEmail());
                        User.getInstance().setAuthToken(loginResponse.getAuthToken());
                        view.setState(new LoginViewState(State.LOGIN_COMPLETED));
                    }

                    @Override
                    public void onError(Throwable e) {

                        //TODO will be fixed
                        String error = "Login error";

                        if (e instanceof HttpException) {

                            try {

                                Moshi moshi = new Moshi.Builder()
                                        .build();

                                String errorJson = ((HttpException) e).response().errorBody().string();
                                JsonAdapter<ErrorResponse> errorResponseJsonAdapter = moshi.adapter(ErrorResponse.class);
                                ErrorResponse errorResponse = errorResponseJsonAdapter.fromJson(errorJson);

                                error = errorResponse.getErrorList().get(0).getDetail();

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }

                        view.setState(new LoginViewState(error, State.SHOW_MESSAGE));
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void cancel() {

        if (loginDisposable != null && !loginDisposable.isDisposed()) {
            loginDisposable.dispose();
        }

    }

}
