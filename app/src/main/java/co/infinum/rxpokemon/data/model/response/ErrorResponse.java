package co.infinum.rxpokemon.data.model.response;

import com.squareup.moshi.Json;

import java.util.List;

public class ErrorResponse {

    @Json(name = "errors")
    private List<Error> errorList;

    public List<Error> getErrorList() {
        return errorList;
    }

    public static class Error {

        @Json(name = "detail")
        private String detail;

        public String getDetail() {
            return detail;
        }
    }

}
