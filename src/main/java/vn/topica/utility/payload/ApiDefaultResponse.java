package vn.topica.utility.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiDefaultResponse {
    protected boolean success;
    protected String message;

    @JsonProperty("error_code")
    protected String errorCode;
    protected Object data;

    public ApiDefaultResponse() {
    }

    public ApiDefaultResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ApiDefaultResponse(boolean success, String message, String errorCode, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errorCode =  errorCode;
    }
}
