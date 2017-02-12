package co.infinum.rxpokemon.data.model;

public final class User {

    private static User user;

    private String email;

    private String authToken;

    public static User getInstance() {

        if (user == null) {
            user = new User();
        }

        return user;

    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        User.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
