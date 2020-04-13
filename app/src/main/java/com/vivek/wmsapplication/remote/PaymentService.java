package com.vivek.wmsapplication.remote;

import com.vivek.wmsapplication.model.Payment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PaymentService {

    @GET("payment/")
    Call<List<Payment>> getAllPayment();

    @GET("payment/{code}")
    Call<List<Payment>> getPaymentsByCode(@Path("code") String code);

    @GET("paymentreceiveby/{username}/{date}")
    Call<List<Payment>> getPaymentreceiveby(@Path("username") String username, @Path("date") String date);

    @POST("payment")
    Call<Payment> addPayment(@Body Payment payment);

    @PUT("payment/{code}")
    Call<Payment> updatePayment(@Path("code") String code, @Body Payment payment);

    @DELETE("payment/{code}")
    Call<Payment> delPayment(@Path("code") String code);
}
