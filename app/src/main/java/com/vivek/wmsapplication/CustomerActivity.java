package com.vivek.wmsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vivek.wmsapplication.model.Customer;
import com.vivek.wmsapplication.model.Result;
import com.vivek.wmsapplication.remote.APIUtils;
import com.vivek.wmsapplication.remote.CustomerService;
import com.vivek.wmsapplication.utils.Charges;
import com.vivek.wmsapplication.utils.HindiWords;
import com.vivek.wmsapplication.utils.Wards;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    EditText etWardNumber;
    EditText etPhone;
    EditText etEmail;
    String etCategory;
    String etSubCategory;
    EditText etCharges;
    Integer getCharges;
    Integer CatForCharges;
    EditText etServiceActivator;
    EditText etDateOfActivation;
    EditText etDataCollector;
    Button btnCreate;
    Button btnRead;
    Button btnUpdate;
    TextView txtId;
    Spinner spCategory;
    Spinner spSubCategory;
    AutoCompleteTextView autoCompleteWardName;
    AutoCompleteTextView autoCompleteAreaName;
    ArrayList<String> wardNameList = new ArrayList<>();
    ArrayList<String> areaNameList = new ArrayList<String>();

    ArrayAdapter<String> arrayAdapter_category;
    ArrayAdapter<String> arrayAdapter_SubCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        setTitle("Customer Panel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtId = findViewById(R.id.txtId);
        etCode = findViewById(R.id.etCode);
        etCustomerName = findViewById(R.id.etCustomerName);
        etFatherOrHusbandName = findViewById(R.id.etFatherOrHusbandName);
        etHouseNumber = findViewById(R.id.etHouseNumber);
        etStreetNumber = findViewById(R.id.etStreetNumber);
        etWardNumber = findViewById(R.id.etWardNumber);
        etWardNumber.setEnabled(false);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etCharges = findViewById(R.id.etCharges);
        etCharges.setEnabled(false);
        etServiceActivator = findViewById(R.id.etServiceActivator);
        etServiceActivator.setEnabled(false);
        etDateOfActivation = findViewById(R.id.etDateOfActivation);
        etDateOfActivation.setEnabled(false);
        etDataCollector = findViewById(R.id.etDataCollector);
        etDataCollector.setEnabled(false);
        btnCreate = findViewById(R.id.btnCreate);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);

        getCharges=-1;

        spCategory = findViewById(R.id.spCategory);
        spSubCategory = findViewById(R.id.spSubCategory);

        autoCompleteWardName = findViewById(R.id.autoCompleteWardName);
        autoCompleteAreaName = findViewById(R.id.autoCompleteAreaName);

        wardNameList = Wards.getWardsName();
        areaNameList = new ArrayList<>();

        customerService = APIUtils.getCustomerService();

//      AutoCompleteTextView Starts

        ArrayAdapter<String> wardArray = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, wardNameList);
        autoCompleteWardName.setAdapter(wardArray);   //setting the adapter data into the AutoCompleteTextView
        autoCompleteWardName.setThreshold(2);       //will start working from first character

        autoCompleteWardName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                System.out.println(item.toString());
                Integer number = Wards.getWardNumber(item.toString());
                etWardNumber.setText(number.toString());
                areaNameList = Wards.getAreaName(item.toString());
                System.out.println("AREA ARRAY " + areaNameList);
                CallAutoCompleteAreaName(areaNameList);
            }
        });

//      AutoCompleteTextView Ends

//      Category Spinner Starts sc=999

        arrayAdapter_category = new ArrayAdapter<>(getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item, HindiWords.GetCategories(1, 999));
        spCategory.setAdapter(arrayAdapter_category);

//        Category Spinner Ends

//        Sub Category Spinner Starts c=888

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                arrayAdapter_SubCategory = new ArrayAdapter<>(getApplicationContext(),
                        R.layout.support_simple_spinner_dropdown_item, HindiWords.GetCategories(position, 888));
                CatForCharges = position + 1;
                int pos = position + 1;
                etCategory = Integer.toString(pos);
                spSubCategory.setAdapter(arrayAdapter_SubCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int SubCatForCharges = position + 1;
                int pos = position + 1;
                etSubCategory = Integer.toString(pos);
                System.out.println(CatForCharges);
                System.out.println(SubCatForCharges);
                getCharges = Charges.GetCharges(CatForCharges, SubCatForCharges);
                System.out.println(getCharges);
                if (getCharges != -1) {
                    etCharges.setText(getCharges.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        Sub Category Spinner Ends

        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        System.out.println("DATE++++++++" + date);

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
        String serviceActivator = extras.getString("serviceActivator");
        String dateOfActivation = extras.getString("dateOfActivation");
        String dataCollector = extras.getString("dataCollector");
        String username = extras.getString("username");

        txtId.setText(id);
        etCode.setText(code);
        etCustomerName.setText(customerName);
        etFatherOrHusbandName.setText(fatherOrHusbandName);
        etHouseNumber.setText(houseNumber);
        etStreetNumber.setText(streetNumber);
        autoCompleteAreaName.setText(place);
        autoCompleteWardName.setText(wardName);
        etPhone.setText(phone);
        etEmail.setText(email);
//        etCharges.setText(getCharges);
        etServiceActivator.setText(username);
        etDateOfActivation.setText(date);
        etDataCollector.setText(username);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = etCode.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                if (code.length() < 10 || code.length() > 10) {
                    etCode.setError("10 Characters Required");
                    etCode.requestFocus();
                } else if (phone.length() < 10 || code.length() > 10) {
                    etPhone.setError("10 Digit Required");
                    etPhone.requestFocus();
                } else {
                    Customer c = new Customer();
                    c.setCode(code);
                    c.setCustomerName(etCustomerName.getText().toString().toUpperCase());
                    c.setFatherOrHusbandName(etFatherOrHusbandName.getText().toString().toUpperCase());
                    c.setHouseNumber(etHouseNumber.getText().toString().toUpperCase());
                    c.setStreetNumber(etStreetNumber.getText().toString().toUpperCase());
                    c.setPlace(autoCompleteAreaName.getText().toString().toUpperCase());
                    c.setWardName(autoCompleteWardName.getText().toString().toUpperCase());
                    c.setWardNumber(etWardNumber.getText().toString());
                    c.setPhone(phone);
                    c.setEmail(etEmail.getText().toString().toUpperCase());
                    c.setCategory(etCategory);
                    c.setSubCategory(etSubCategory);
                    c.setCharges(etCharges.getText().toString().toUpperCase());
                    c.setServiceActivator(etServiceActivator.getText().toString().toUpperCase());
                    c.setDateOfActivation(etDateOfActivation.getText().toString().toUpperCase());
                    c.setDataCollector(etDataCollector.getText().toString().toUpperCase());
                    addCustomer(c);
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCode = findViewById(R.id.etCode);
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
                etCode = findViewById(R.id.etCode);
                String code = etCode.getText().toString().trim();
                if (code.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Code to Update", Toast.LENGTH_SHORT).show();
                } else {
                    Customer c = new Customer();
                    c.setCode(etCode.getText().toString().toUpperCase());
                    c.setCustomerName(etCustomerName.getText().toString().toUpperCase());
                    c.setFatherOrHusbandName(etFatherOrHusbandName.getText().toString().toUpperCase());
                    c.setHouseNumber(etHouseNumber.getText().toString().toUpperCase());
                    c.setStreetNumber(etStreetNumber.getText().toString().toUpperCase());
                    c.setPlace(autoCompleteAreaName.getText().toString().toUpperCase());
                    c.setWardName(autoCompleteWardName.getText().toString().toUpperCase());
                    c.setWardNumber(etWardNumber.getText().toString().toUpperCase());
                    c.setPhone(etPhone.getText().toString().toUpperCase());
                    c.setEmail(etEmail.getText().toString().toUpperCase());
                    c.setCategory(etCategory);
                    c.setSubCategory(etSubCategory);
                    c.setCharges(etCharges.getText().toString().toUpperCase());
                    c.setServiceActivator(etServiceActivator.getText().toString().toUpperCase());
                    c.setDateOfActivation(etDateOfActivation.getText().toString().toUpperCase());
                    c.setDataCollector(etDataCollector.getText().toString().toUpperCase());
                    updateCustomer(code, c);
                }
            }

        });


    }

    public void CallAutoCompleteAreaName(ArrayList<String> areaNameList) {
        System.out.println("AREA ARRAY " + areaNameList);
        ArrayAdapter<String> areaArray = new ArrayAdapter<>
                (this, android.R.layout.select_dialog_item, areaNameList);
        autoCompleteAreaName.setThreshold(2);
        autoCompleteAreaName.setAdapter(areaArray);
    }


    private void addCustomer(Customer c) {
        Call<Customer> call = customerService.addCustomer(c);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    if (response.body().getId() == 0) {
                        Toast.makeText(CustomerActivity.this, "UID already used!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CustomerActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(CustomerActivity.this, "Error at API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

    }

    public void getCustomer(String code) {
        Call<Result<Customer>> call = customerService.getCustomerResult(code);
        call.enqueue(new Callback<Result<Customer>>() {
            @Override
            public void onResponse(Call<Result<Customer>> call, Response<Result<Customer>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        // get customer
                        Customer customer = response.body().getResponse();
//                          txtId.setText(response.body().getId());
                        etCode.setText(customer.getCode());
                        etCustomerName.setText(customer.getCustomerName());
                        etFatherOrHusbandName.setText(customer.getFatherOrHusbandName());
                        etHouseNumber.setText(customer.getHouseNumber());
                        etStreetNumber.setText(customer.getStreetNumber());
                        autoCompleteAreaName.setText(customer.getPlace());
                        autoCompleteWardName.setText(customer.getWardName());
                        etWardNumber.setText(customer.getWardNumber());
                        etPhone.setText(customer.getPhone());
                        etEmail.setText(customer.getEmail());
//                          etCategory.setText(customer.getCategory());
//                          etSubCategory.setText(customer.getSubCategory());
                        etCharges.setText(customer.getCharges());
                        etServiceActivator.setText(customer.getServiceActivator());
                        etDateOfActivation.setText(customer.getDateOfActivation());
                        etDataCollector.setText(customer.getDataCollector());
                    } else {
                        Toast.makeText(getApplicationContext(), "Customer Not Found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error at API", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Result<Customer>> call, Throwable t) {
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
                    Toast.makeText(CustomerActivity.this, "Error at API", Toast.LENGTH_SHORT).show();
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




