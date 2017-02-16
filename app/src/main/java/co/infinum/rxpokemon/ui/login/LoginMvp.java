package co.infinum.rxpokemon.ui.login;

import co.infinum.rxpokemon.data.model.param.LoginParams;
import co.infinum.rxpokemon.data.model.response.LoginResponse;
import co.infinum.rxpokemon.data.network.Listener;
import co.infinum.rxpokemon.ui.shared.BaseMvp;

public interface LoginMvp {

    interface View extends BaseMvp.View {

        void setState(LoginViewState loginViewState);

    }

    interface Presenter extends BaseMvp.Presenter {

        void init();

        void login(String username, String password);

    }

    interface Interactor extends BaseMvp.Interactor {

        void loginUser(LoginParams params, Listener<LoginResponse> listener);

    }


}
