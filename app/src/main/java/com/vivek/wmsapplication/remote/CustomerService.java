package com.vivek.wmsapplication.remote;

import com.vivek.wmsapplication.model.Customer;
import com.vivek.wmsapplication.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CustomerService {

    @GET("customer/")
    Call<List<Customer>> getAllCustomers();

    @GET("customer/{code}")
    Call<Customer> getCustomer(@Path("code") String code);

    @GET("customer/find/{code}")
    Call<Result<Customer>> getCustomerResult(@Path("code") String code);

    @POST("customer")
    Call<Customer> addCustomer(@Body Customer customer);

    @PUT("customer/{code}")
    Call<Customer> updateCustomer(@Path("code") String code, @Body Customer customer);

    @DELETE("customer/{code}")
    Call<Customer> delCustomer(@Path("code") String code);
}
