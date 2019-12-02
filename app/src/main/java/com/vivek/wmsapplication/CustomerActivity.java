package com.vivek.wmsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vivek.wmsapplication.model.Customer;
import com.vivek.wmsapplication.remote.APIUtils;
import com.vivek.wmsapplication.remote.CustomerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerActivity extends AppCompatActivity {

    CustomerService customerService;
    EditText etCode;
    EditText etCustomerName;
    EditText etFatherOrHusbandName;
    EditText etHouseNumber;
    EditText etStreetNumber;
    EditText etPlace;
    EditText etWardName;
    EditText etWardNumber;
    EditText etPhone;
    EditText etEmail;
    EditText etCategory;
    EditText etSubCategory;
    EditText etCharges;
    Button btnCreate;
    Button btnRead;
    Button btnUpdate;
    Button btnDelete;
    TextView txtId;
    Customer customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        setTitle("Customers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtId = (TextView) findViewById(R.id.txtId);
        etCode = (EditText) findViewById(R.id.etCode);
        etCustomerName = (EditText) findViewById(R.id.etCustomerName);
        etFatherOrHusbandName = (EditText) findViewById(R.id.etFatherOrHusbandName);
        etHouseNumber = (EditText) findViewById(R.id.etHouseNumber);
        etStreetNumber = (EditText) findViewById(R.id.etStreetNumber);
        etPlace = (EditText) findViewById(R.id.etPlace);
        etWardName = (EditText) findViewById(R.id.etWardName);
        etWardNumber = (EditText) findViewById(R.id.etWardNumber);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etCategory = (EditText) findViewById(R.id.etCategory);
        etSubCategory = (EditText) findViewById(R.id.etSubCategory);
        etCharges = (EditText) findViewById(R.id.etCharges);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnRead = (Button) findViewById(R.id.btnRead);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
//        btnDelete = (Button) findViewById(R.id.btnDelete);

        customerService = APIUtils.getCustomerService();

        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("id");
        final String code = extras.getString("code");
        String customerName = extras.getString("customer_name");
        String fatherOrHusbandName = extras.getString("father_husband_name");
        String houseNumber = extras.getString("house_number");
        String streetNumber = extras.getString("street_number");
        String place = extras.getString("place");
        String wardName = extras.getString("ward_name");
        String wardNumber = extras.getString("ward_number");
        String phone = extras.getString("phone");
        String email = extras.getString("email");
        String category = extras.getString("category");
        String subCategory = extras.getString("sub_category");
        String charges = extras.getString("charges");

        txtId.setText(id);
        etCode.setText(code);
        etCustomerName.setText(customerName);
        etFatherOrHusbandName.setText(fatherOrHusbandName);
        etHouseNumber.setText(houseNumber);
        etStreetNumber.setText(streetNumber);
        etPlace.setText(place);
        etWardName.setText(wardName);
        etWardNumber.setText(wardNumber);
        etPhone.setText(phone);
        etEmail.setText(email);
        etCategory.setText(category);
        etSubCategory.setText(subCategory);
        etCharges.setText(charges);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Customer c = new Customer();
                c.setCode(etCode.getText().toString());
                c.setCustomerName(etCustomerName.getText().toString());
                c.setFatherOrHusbandName(etFatherOrHusbandName.getText().toString());
                c.setHouseNumber(etHouseNumber.getText().toString());
                c.setStreetNumber(etStreetNumber.getText().toString());
                c.setPlace(etPlace.getText().toString());
                c.setWardName(etWardName.getText().toString());
                c.setWardNumber(etWardNumber.getText().toString());
                c.setPhone(etPhone.getText().toString());
                c.setEmail(etEmail.getText().toString());
                c.setCategory(etCategory.getText().toString());
                c.setSubCategory(etSubCategory.getText().toString());
                c.setCharges(etCharges.getText().toString());
                addCustomer(c);
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCode = (EditText) findViewById(R.id.etCode);
                String code = etCode.getText().toString().trim();
                if (code.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Code to Search", Toast.LENGTH_SHORT).show();
                } else {
                    getCustomer(code);
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCode = (EditText) findViewById(R.id.etCode);
                String code = etCode.getText().toString().trim();
                if (code.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Code to Update", Toast.LENGTH_SHORT).show();
                } else {
                    Customer c = new Customer();
                    c.setCode(etCode.getText().toString());
                    c.setCustomerName(etCustomerName.getText().toString());
                    c.setFatherOrHusbandName(etFatherOrHusbandName.getText().toString());
                    c.setHouseNumber(etHouseNumber.getText().toString());
                    c.setStreetNumber(etStreetNumber.getText().toString());
                    c.setPlace(etPlace.getText().toString());
                    c.setWardName(etWardName.getText().toString());
                    c.setWardNumber(etWardNumber.getText().toString());
                    c.setPhone(etPhone.getText().toString());
                    c.setEmail(etEmail.getText().toString());
                    c.setCategory(etCategory.getText().toString());
                    c.setSubCategory(etSubCategory.getText().toString());
                    c.setCharges(etCharges.getText().toString());
                    updateCustomer(code, c);
                }
            }

        });


        /*btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCustomer(code);

                Intent intent = new Intent(CustomerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });*/
    }

    public void addCustomer(Customer c) {
        Call<Customer> call = customerService.addCustomer(c);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CustomerActivity.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CustomerActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

    }

    public void getCustomer(String code) {
        Call<Customer> call = customerService.getCustomer(code);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    customer = response.body();
//                    txtId.setText(response.body().getId());
                    etCode.setText(response.body().getCode());
                    etCustomerName.setText(response.body().getCustomerName());
                    etFatherOrHusbandName.setText(response.body().getFatherOrHusbandName());
                    etHouseNumber.setText(response.body().getHouseNumber());
                    etStreetNumber.setText(response.body().getStreetNumber());
                    etPlace.setText(response.body().getPlace());
                    etWardName.setText(response.body().getWardName());
                    etWardNumber.setText(response.body().getWardNumber());
                    etPhone.setText(response.body().getPhone());
                    etEmail.setText(response.body().getEmail());
                    etCategory.setText(response.body().getCategory());
                    etSubCategory.setText(response.body().getSubCategory());
                    etCharges.setText(response.body().getCharges());
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void updateCustomer(String code, Customer c) {
        Call<Customer> call = customerService.updateCustomer(code, c);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CustomerActivity.this, "User updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CustomerActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void deleteCustomer(String code) {
        Call<Customer> call = customerService.delCustomer(code);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CustomerActivity.this, "User deleted successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CustomerActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}




