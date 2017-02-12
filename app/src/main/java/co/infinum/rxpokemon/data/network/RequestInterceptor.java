package co.infinum.rxpokemon.data.network;

import android.text.TextUtils;

import java.io.IOException;

import co.infinum.rxpokemon.data.model.User;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class RequestInterceptor implements Interceptor {

    private static final String HEADER_AUTHORIZATION = "Authorization";

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        if (!TextUtils.isEmpty(User.getInstance().getAuthToken())) {

            String authHeader = String.format("Token token=%s, email=%s", User.getInstance().getAuthToken(), User.getInstance().getEmail());

            Timber.tag("koc").d(authHeader);

            builder.addHeader(HEADER_AUTHORIZATION, authHeader);
        }

        return chain.proceed(builder.build());
    }
}
