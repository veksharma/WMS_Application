package com.vivek.wmsapplication.remote;


public class APIUtils {

    private APIUtils(){
    }

//    public static final String API_URL = "http://10.0.3.2:8080/wms/";     //genymotion
    public static final String API_URL = "http://13.232.197.116:8080/wms/";      //AWS vekcms
//    public static final String API_URL = "http://192.168.1.45:8080/wms/";      //Excitel
//    public static final String API_URL = "http://192.168.1.100:8080/wms/";      //Localhost for TP link wifi
//    public static final String API_URL = "http://192.168.31.102:8080/wms/";      //Localhost for mi wifi

    public static CustomerService getCustomerService(){
        return RetrofitClient.getClient(API_URL).create(CustomerService.class);
    }

    public static PaymentService getPaymentService(){
        return RetrofitClient.getClient(API_URL).create(PaymentService.class);
    }

    public static LoginResponseService getLoginResponseService(){
        return RetrofitClient.getClient(API_URL).create(LoginResponseService.class);
    }

    public static ImageUploadService getMultiInterface(){
        return RetrofitClient.getClient(API_URL).create(ImageUploadService.class);
    }

}

/*http://192.168.0.104:8080/wms/   internet ip*/
/*WMS-API Url for local testing = http://10.0.3.2:8082/wms/*/

/*http://35.154.149.37:8080/wms/*/