package co.infinum.rxpokemon.ui.login;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import javax.inject.Inject;

import co.infinum.rxpokemon.data.model.param.LoginParams;
import co.infinum.rxpokemon.data.model.response.ErrorResponse;
import co.infinum.rxpokemon.data.model.response.LoginResponse;
import co.infinum.rxpokemon.data.network.ApiService;
import co.infinum.rxpokemon.data.network.Listener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginInteractor implements LoginMvp.Interactor {

    CompositeDisposable disposables = new CompositeDisposable();

    private boolean isCanceled = false;

    private ApiService apiService;

    @Inject
    public LoginInteractor(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public void loginUser(LoginParams params, final Listener<LoginResponse> listener) {

        reset();

        apiService.loginUser(params).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginResponse>() {
                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        listener.onSuccess(loginResponse);
                    }

                    @Override
                    public void onError(Throwable e) {

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

                        listener.onFailure(error);

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void cancel() {
        isCanceled = true;
    }

    @Override
    public void reset() {
        isCanceled = false;
    }

}
