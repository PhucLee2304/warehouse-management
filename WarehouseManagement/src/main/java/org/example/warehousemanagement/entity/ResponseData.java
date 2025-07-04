package org.example.warehousemanagement.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseData {
    int status = 200;
    boolean success;
    String message;
    Object data;

    public static ResponseData success(String message, Object data) {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setMessage(message);
        responseData.setData(data);
        return responseData;
    }

    public static ResponseData error(String message) {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setMessage(message);
        return responseData;
    }
}
