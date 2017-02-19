package co.infinum.rxpokemon.ui.login;

import co.infinum.rxpokemon.ui.shared.ViewState;

public final class LoginViewState implements ViewState {

    private String message;

    private
    @State
    int state;

    public LoginViewState(@State int state) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoginViewState that = (LoginViewState) o;

        if (state != that.state) {
            return false;
        }
        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + state;
        return result;
    }
}
