package com.vivek.wmsapplication.remote;


public class APIUtils {

    private APIUtils(){
    }

    public static final String API_URL = "http://10.0.3.2:8080/wms/";     //genymotion
//    public static final String API_URL = "http://35.154.149.37:8080/wms/";      //AWS

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
/*WMS-API Url for local testing = http://10.0.3.2:8082/wms/*/

/*http://35.154.149.37:8080/wms/*/