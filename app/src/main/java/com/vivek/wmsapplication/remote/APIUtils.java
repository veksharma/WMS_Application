package com.vivek.wmsapplication.remote;

import com.vivek.wmsapplication.model.LoginResponse;

public class APIUtils {

    private APIUtils(){
    }

    public static final String API_URL = "http://13.233.142.192:8080/wms/";

    public static CustomerService getCustomerService(){
        return RetrofitClient.getClient(API_URL).create(CustomerService.class);
    }

    public static PaymentService getPaymentService(){
        return RetrofitClient.getClient(API_URL).create(PaymentService.class);
    }

    public static LoginResponseService getLoginResponseService(){
        return RetrofitClient.getClient(API_URL).create(LoginResponseService.class);
    }

}

/*http://10.0.3.2:8080/wms/*/

/*http://192.168.31.102:8080/wms/*/