package com.vivek.wmsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vivek.wmsapplication.model.Payment;
import com.vivek.wmsapplication.remote.APIUtils;
import com.vivek.wmsapplication.remote.CustomerService;
import com.vivek.wmsapplication.remote.PaymentService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleCustomerActivity extends AppCompatActivity {

    CustomerService customerService;
    PaymentService paymentService;
    TextView etCode;
    TextView etCustomerName;
    TextView etFatherOrHusbandName;
    TextView etHouseNumber;
    TextView etStreetNumber;
    TextView etPlace;
    TextView etWardName;
    TextView etWardNumber;
    TextView etPhone;
    TextView etEmail;
    TextView etCategory;
    TextView etSubCategory;
    TextView etCharges;
    TextView etServiceActivator;
    TextView etDateOfActivation;
    TextView etDataCollector;
    Button btnReceivePayment;
    TextView txtId;
    EditText etAmount;
    EditText etMode;
    EditText etReceiveBy;
    EditText etRemarks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_customer);

        setTitle("Receive Payment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtId = (TextView) findViewById(R.id.txtId);
        etCode = (TextView) findViewById(R.id.etCode);
        etCustomerName = (TextView) findViewById(R.id.etCustomerName);
        etFatherOrHusbandName = (TextView) findViewById(R.id.etFatherOrHusbandName);
        etHouseNumber = (TextView) findViewById(R.id.etHouseNumber);
        etStreetNumber = (TextView) findViewById(R.id.etStreetNumber);
        etPlace = (TextView) findViewById(R.id.etPlace);
        etWardName = (TextView) findViewById(R.id.etWardName);
        etWardNumber = (TextView) findViewById(R.id.etWardNumber);
        etPhone = (TextView) findViewById(R.id.etPhone);
        etEmail = (TextView) findViewById(R.id.etEmail);
        etCategory = (TextView) findViewById(R.id.etCategory);
        etSubCategory = (TextView) findViewById(R.id.etSubCategory);
        etCharges = (TextView) findViewById(R.id.etCharges);
        etServiceActivator = (TextView) findViewById(R.id.etServiceActivator);
        etDateOfActivation = (TextView) findViewById(R.id.etDateOfActivation);
        etDataCollector = (TextView) findViewById(R.id.etDataCollector);
        btnReceivePayment = findViewById(R.id.btnReceivePayment);
        etAmount = findViewById(R.id.etAmount);
        etMode = findViewById(R.id.etMode);
        etReceiveBy = findViewById(R.id.etReceiveBy);
        etReceiveBy.setEnabled(false);
        etRemarks = findViewById(R.id.etRemarks);


        customerService = APIUtils.getCustomerService();

        paymentService = APIUtils.getPaymentService();

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        String code = extras.getString("code");
        String customerName = extras.getString("customer_name");
        String fatherOrHusbandName = extras.getString("father_husband_name");
        String houseNumber = extras.getString("house_number");
        String streetNumber = extras.getString("street_number");
        String place = extras.getString("place");
        String wardName = extras.getString("ward_name");
        String wardNumber = extras.getString("ward_number");
        String phone = extras.getString("phone");
        final String email = extras.getString("email");
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
        etPlace.setText(place);
        etWardName.setText(wardName);
        etWardNumber.setText(wardNumber);
        etPhone.setText(phone);
        etEmail.setText(email);
        etCategory.setText(category);
        etSubCategory.setText(subCategory);
        etCharges.setText(charges);
        etServiceActivator.setText(serviceActivator);
        etDateOfActivation.setText(dateOfActivation);
        etDataCollector.setText(dataCollector);
        etReceiveBy.setText(username);

        btnReceivePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCode = (TextView) findViewById(R.id.etCode);
                String code = etCode.getText().toString().trim();
                etAmount = (EditText) findViewById(R.id.etAmount);
                String amount = etAmount.getText().toString().trim();
                etMode = (EditText) findViewById(R.id.etMode);
                String mode = etMode.getText().toString().trim();
                etReceiveBy = (EditText) findViewById(R.id.etReceiveBy);
                String receiveBy = etReceiveBy.getText().toString().trim();
                Payment payment = new Payment();
                payment.setCode(code);
                payment.setAmount(etAmount.getText().toString());
                payment.setMode(etMode.getText().toString());
                payment.setReceiveBy(etReceiveBy.getText().toString());
                payment.setExtra(etRemarks.getText().toString());
                if (amount.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter amount", Toast.LENGTH_SHORT).show();
                } else if (mode.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter mode", Toast.LENGTH_SHORT).show();
                } else if (receiveBy.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter receive by name", Toast.LENGTH_SHORT).show();
                } else {
                    savePayment(payment);
                    finish();
                }
            }
        });
    }

    public void savePayment(Payment payment) {
        Call<Payment> call = paymentService.addPayment(payment);
        call.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SingleCustomerActivity.this, "Payment saved successfully!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SingleCustomerActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
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
