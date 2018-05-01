package com.ciuciu.footballhighlight.data.network;

import retrofit2.Response;

public class ServiceApiException extends ServiceException {

    public ServiceApiException(Response response) {
        super(response.message());

    }

    /*public ServiceApiException(Response response) {
        this(response, readApiError(response), DEFAULT_ERROR_CODE);
    }

    ServiceApiException(Response response, ApiError apiError, int apiErrorCode) {
        super(createExceptionMessage(apiErrorCode));
        this.apiError = apiError;
        this.code = apiErrorCode;
        this.response = response;
    }*/

}

