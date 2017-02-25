package co.infinum.rxpokemon.ui.login;


import javax.inject.Inject;

import co.infinum.rxpokemon.data.model.param.LoginParams;
import co.infinum.rxpokemon.data.model.response.LoginResponse;
import co.infinum.rxpokemon.data.network.ApiService;
import io.reactivex.Observable;

public class LoginInteractor implements LoginMvp.Interactor {

    private ApiService apiService;

    @Inject
    public LoginInteractor(ApiService apiService) {
        this.apiService = apiService;
    }


    @Override
    public Observable<LoginResponse> execute(LoginParams params) {
        return apiService.loginUser(params);

    }
}
