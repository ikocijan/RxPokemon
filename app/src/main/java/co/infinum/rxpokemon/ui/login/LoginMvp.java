package co.infinum.rxpokemon.ui.login;

import co.infinum.rxpokemon.data.model.param.LoginParams;
import co.infinum.rxpokemon.data.model.response.LoginResponse;
import co.infinum.rxpokemon.data.network.Listener;
import co.infinum.rxpokemon.ui.shared.BaseMvp;
import io.reactivex.Flowable;

public interface LoginMvp {

    interface View extends BaseMvp.View {

        void setState(LoginViewState loginViewState);

        void enableLogin(Boolean inputValid);
    }

    interface Presenter extends BaseMvp.Presenter {

        void init(Flowable<CharSequence> emailChangeObserver, Flowable<CharSequence> passwordChangeObserver);

        void login(String username, String password);

        void onDestroy();
    }

    interface Interactor extends BaseMvp.Interactor {

        void loginUser(LoginParams params, Listener<LoginResponse> listener);

    }


}
