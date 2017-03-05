package co.infinum.rxpokemon.ui.login;

import javax.inject.Inject;

import co.infinum.rxpokemon.dagger.annotations.IO;
import co.infinum.rxpokemon.dagger.annotations.MainThread;
import co.infinum.rxpokemon.data.model.User;
import co.infinum.rxpokemon.data.model.param.LoginParams;
import co.infinum.rxpokemon.data.model.response.LoginResponse;
import co.infinum.rxpokemon.data.network.ErrorHandler;
import co.infinum.rxpokemon.shared.RxDisposableObserver;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenter implements LoginMvp.Presenter {

    public static final int NUMBER_OF_RETRIES = 1;

    private LoginMvp.View view;

    private LoginMvp.Interactor interactor;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Scheduler ioScheduler;

    private Scheduler mainThreadScheduler;

    private ErrorHandler errorHandler;


    @Inject
    public LoginPresenter(LoginMvp.View view, LoginMvp.Interactor interactor, @IO Scheduler ioScheduler,
            @MainThread Scheduler mainThreadScheduler, ErrorHandler errorHandler) {
        this.view = view;
        this.interactor = interactor;
        this.ioScheduler = ioScheduler;
        this.mainThreadScheduler = mainThreadScheduler;
        this.errorHandler = errorHandler;
    }

    @Override
    public void init() {

    }

    @Override
    public void login(String username, String password) {

        view.setState(new LoginViewState(State.LOADING));

        final LoginParams params = new LoginParams(username, password);

        interactor.execute(params)
                .subscribeOn(ioScheduler)
                .observeOn(mainThreadScheduler)
                .retry(NUMBER_OF_RETRIES)
                .subscribeWith(new RxDisposableObserver<LoginResponse>(errorHandler, compositeDisposable) {

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        User.getInstance().setEmail(loginResponse.getEmail());
                        User.getInstance().setAuthToken(loginResponse.getAuthToken());
                        view.setState(new LoginViewState(State.LOGIN_COMPLETED));
                    }


                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void cancel() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }

    }

}
