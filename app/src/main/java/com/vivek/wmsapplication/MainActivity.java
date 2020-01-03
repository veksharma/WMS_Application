package com.vivek.wmsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.vivek.wmsapplication.model.Customer;
import com.vivek.wmsapplication.remote.APIUtils;
import com.vivek.wmsapplication.remote.CustomerService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnAddCustomer;
    Button btnGetCustomersList;
    Button btnGetCustomer;
    ListView listView;
    EditText editText;

    private Context context;
    private List<Customer> customers;

    CustomerService customerService;
    List<Customer> list = new ArrayList<Customer>();
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("WMS Application");

        btnAddCustomer = (Button) findViewById(R.id.btnAddCustomer);
//        btnGetCustomersList = (Button) findViewById(R.id.btnGetCustomersList);
        btnGetCustomer = (Button) findViewById(R.id.btnGetCustomer);
        listView = (ListView) findViewById(R.id.listView);
        customerService = APIUtils.getCustomerService();


        btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                intent.putExtra("customer_name", "");
                startActivity(intent);
            }
        });

        /*btnGetCustomersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get users list
                getCustomersList();
            }
        });*/

        btnGetCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = (EditText) findViewById(R.id.etSearchCustomer);
                String code = editText.getText().toString();
                if (code.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter customer Code", Toast.LENGTH_SHORT).show();
                } else {
                    getCustomer(code);
                }
            }
        });

    }

    /*public void getCustomersList() {
        Call<List<Customer>> call = customerService.getAllCustomers();
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    listView.setAdapter(new CustomerAdapter(MainActivity.this, R.layout.list_customer, list));
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }*/

    public void getCustomer(String code) {
        Call<Customer> call = customerService.getCustomer(code);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                       // customer = response.body();
                        //start Show CustomerActivity
                        Intent intent = new Intent(MainActivity.this, SingleCustomerActivity.class);
                        intent.putExtra("id", String.valueOf(response.body().getId()));
                        intent.putExtra("code", response.body().getCode());
                        intent.putExtra("customer_name", response.body().getCustomerName());
                        intent.putExtra("father_husband_name", response.body().getFatherOrHusbandName());
                        intent.putExtra("house_number", response.body().getHouseNumber());
                        intent.putExtra("street_number", response.body().getStreetNumber());
                        intent.putExtra("place", response.body().getPlace());
                        intent.putExtra("ward_name", response.body().getWardName());
                        intent.putExtra("ward_number", response.body().getWardNumber());
                        intent.putExtra("phone", response.body().getPhone());
                        intent.putExtra("email", response.body().getEmail());
                        intent.putExtra("category", response.body().getCategory());
                        intent.putExtra("sub_category", response.body().getSubCategory());
                        intent.putExtra("charges", response.body().getCharges());
                        intent.putExtra("serviceActivator", response.body().getServiceActivator());
                        intent.putExtra("dateOfActivation", response.body().getDateOfActivation());
                        intent.putExtra("dataCollector", response.body().getDataCollector());
                        startActivity(intent);
                    }
                 else {
                    Toast.makeText(getApplicationContext(), "Check customer Code", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}
