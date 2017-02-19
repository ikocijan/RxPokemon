package co.infinum.rxpokemon.ui.login;

import co.infinum.rxpokemon.data.model.param.LoginParams;
import co.infinum.rxpokemon.data.model.response.LoginResponse;
import co.infinum.rxpokemon.ui.shared.BaseMvp;
import io.reactivex.Observable;

public interface LoginMvp {

    interface View extends BaseMvp.View {

        void setState(LoginViewState loginViewState);

    }

    interface Presenter extends BaseMvp.Presenter {

        void init();

        void login(String username, String password);

    }

    interface Interactor extends BaseMvp.Interactor {

        Observable<LoginResponse> loginUser(LoginParams params);

    }


}
