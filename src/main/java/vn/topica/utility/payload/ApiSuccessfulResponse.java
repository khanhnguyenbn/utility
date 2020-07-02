package vn.topica.utility.payload;

import lombok.Data;

@Data
public class ApiSuccessfulResponse extends ApiDefaultResponse{


    public ApiSuccessfulResponse() {
        success = true;
        message = "Success";
    }

    public ApiSuccessfulResponse(Object data) {
        this();
        this.data = data;
    }
}
