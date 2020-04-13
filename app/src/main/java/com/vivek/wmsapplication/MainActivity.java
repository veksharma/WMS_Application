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
import com.vivek.wmsapplication.model.Result;
import com.vivek.wmsapplication.remote.APIUtils;
import com.vivek.wmsapplication.remote.CustomerService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnAddCustomer;
    Button btnPaymentHistory;
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

        btnAddCustomer = findViewById(R.id.btnAddCustomer);
        btnPaymentHistory = findViewById(R.id.btnPaymentHistory);
        btnGetCustomer = findViewById(R.id.btnGetCustomer);
        listView = findViewById(R.id.listView);
        customerService = APIUtils.getCustomerService();

        Bundle extras = getIntent().getExtras();
        final String username = extras.getString("username");
        System.out.println("===========>>>" + username);


        btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                intent.putExtra("customer_name", "");
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        btnGetCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = findViewById(R.id.etSearchCustomer);
                String code = editText.getText().toString();
                if (code.length() < 10 || code.length() > 10) {
                    editText.setError("10 Characters Required");
                    editText.requestFocus();
                } else {
                    getCustomer(code, username);
                }
            }
        });

        btnPaymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PaymentHistoryActivity.class);
                intent.putExtra("customer_name", "");
                intent.putExtra("username", username);
                startActivity(intent);
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

    public void getCustomer(String code, final String username) {
        Call<Result<Customer>> call = customerService.getCustomerResult(code);
        call.enqueue(new Callback<Result<Customer>>() {
            @Override
            public void onResponse(Call<Result<Customer>> call, Response<Result<Customer>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        // get customer
                        Customer customer = response.body().getResponse();
                        //start Show CustomerActivity
                        Intent intent = new Intent(MainActivity.this, SingleCustomerActivity.class);
                        intent.putExtra("id", String.valueOf(customer.getId()));
                        intent.putExtra("code", customer.getCode());
                        intent.putExtra("customer_name", customer.getCustomerName());
                        intent.putExtra("father_husband_name", customer.getFatherOrHusbandName());
                        intent.putExtra("house_number", customer.getHouseNumber());
                        intent.putExtra("street_number", customer.getStreetNumber());
                        intent.putExtra("place", customer.getPlace());
                        intent.putExtra("ward_name", customer.getWardName());
                        intent.putExtra("ward_number", customer.getWardNumber());
                        intent.putExtra("phone", customer.getPhone());
                        intent.putExtra("email", customer.getEmail());
                        intent.putExtra("category", customer.getCategory());
                        intent.putExtra("sub_category", customer.getSubCategory());
                        intent.putExtra("charges", customer.getCharges());
                        intent.putExtra("serviceActivator", customer.getServiceActivator());
                        intent.putExtra("dateOfActivation", customer.getDateOfActivation());
                        intent.putExtra("dataCollector", customer.getDataCollector());
                        intent.putExtra("username", username);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Customer Not Found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Check customer Code", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result<Customer>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}
