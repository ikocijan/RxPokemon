package co.infinum.rxpokemon.data.network;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import co.infinum.rxpokemon.data.model.response.ErrorResponse;
import retrofit2.HttpException;

public final class Parser {


    public static String parseErrorResponse(Throwable throwable) {

        try {
            Moshi moshi = new Moshi.Builder()
                    .build();

            String errorJson = ((HttpException) throwable).response().errorBody().string();
            JsonAdapter<ErrorResponse> errorResponseJsonAdapter = moshi.adapter(ErrorResponse.class);
            ErrorResponse errorResponse = errorResponseJsonAdapter.fromJson(errorJson);
            return errorResponse.getErrorList().get(0).getDetail();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }

}
