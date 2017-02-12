package co.infinum.rxpokemon.ui.shared;

public interface BaseMvp {

    interface View {

        void showProgress();

        void hideProgress();

        void showMessage(String message);

    }

    interface Presenter {

        void cancel();
    }

    interface Interactor {

        void cancel();

        void reset();

    }
}