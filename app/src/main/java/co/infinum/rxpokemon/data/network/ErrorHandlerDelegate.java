package co.infinum.rxpokemon.data.network;

import co.infinum.rxpokemon.R;
import co.infinum.rxpokemon.shared.interfaces.StringProvider;
import co.infinum.rxpokemon.ui.shared.BaseMvp;

public class ErrorHandlerDelegate implements ErrorHandler {

    private final BaseMvp.View view;

    private final StringProvider stringProvider;

    public ErrorHandlerDelegate(BaseMvp.View view, StringProvider stringProvider) {
        this.view = view;
        this.stringProvider = stringProvider;
    }

    @Override
    public void onUnknownError() {
        view.hideProgress();
        view.showMessage(stringProvider.getString(R.string.unknown_error));
    }


    @Override
    public void onNetworkError() {
        view.hideProgress();
        view.showMessage(stringProvider.getString(R.string.check_internet_connection));
    }

    @Override
    public void onUnauthorized(String message) {
        view.hideProgress();
        view.showMessage(message);
    }
}
