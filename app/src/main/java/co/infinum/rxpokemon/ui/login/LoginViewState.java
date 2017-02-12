package co.infinum.rxpokemon.ui.login;

import co.infinum.rxpokemon.ui.shared.ViewState;

final class LoginViewState implements ViewState {

    private String message;

    private
    @State
    int state;

    public LoginViewState(int state) {
        this.state = state;
    }

    public LoginViewState(String message, int state) {
        this.message = message;
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int getState() {
        return state;
    }

}
