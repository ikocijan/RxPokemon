package co.infinum.rxpokemon.ui.shared;

import io.reactivex.Observable;

public interface BaseMvp {

    interface View {

        void showProgress();

        void hideProgress();

        void showMessage(String message);

    }

    interface Presenter {

        void cancel();
    }

    interface Interactor<RequestParams, Response extends Observable> {

        Response execute(RequestParams requestParams);

    }
}