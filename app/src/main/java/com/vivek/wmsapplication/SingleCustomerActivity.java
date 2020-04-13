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
import com.vivek.wmsapplication.utils.HindiWords;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleCustomerActivity extends AppCompatActivity {

    CustomerService customerService;
    PaymentService paymentService;
    TextView tvCode;
    TextView tvCustomerName;
    TextView tvFatherOrHusbandName;
    TextView tvHouseNumber;
    TextView tvStreetNumber;
    TextView tvPlace;
    TextView tvWardName;
    TextView tvWardNumber;
    TextView tvPhone;
    TextView tvEmail;
    TextView tvCategory;
    TextView tvSubCategory;
    TextView tvCharges;
    TextView tvServiceActivator;
    TextView tvDateOfActivation;
    TextView tvDataCollector;
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

        txtId = findViewById(R.id.txtId);
        tvCode = findViewById(R.id.tvCode);
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvFatherOrHusbandName = findViewById(R.id.tvFatherOrHusbandName);
        tvHouseNumber = findViewById(R.id.tvHouseNumber);
        tvStreetNumber = findViewById(R.id.tvStreetNumber);
        tvPlace = findViewById(R.id.tvPlace);
        tvWardName = findViewById(R.id.tvWardName);
        tvWardNumber = findViewById(R.id.tvWardNumber);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvCategory = findViewById(R.id.tvCategory);
        tvSubCategory = findViewById(R.id.tvSubCategory);
        tvCharges = findViewById(R.id.tvCharges);
        tvServiceActivator = findViewById(R.id.tvServiceActivator);
        tvDateOfActivation = findViewById(R.id.tvDateOfActivation);
        tvDataCollector = findViewById(R.id.tvDataCollector);
        btnReceivePayment = findViewById(R.id.btnReceivePayment);
        etAmount = findViewById(R.id.etAmount);
        etAmount.setEnabled(false);
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
        int cat = Integer.parseInt(category);
        String subCategory = extras.getString("sub_category");
        int subCat = Integer.parseInt(subCategory);
        String charges = extras.getString("charges");
        String serviceActivator = extras.getString("serviceActivator");
        String dateOfActivation = extras.getString("dateOfActivation");
        String dataCollector = extras.getString("dataCollector");
        String username = extras.getString("username");

        ArrayList categories = HindiWords.MyCategories(cat, subCat);

        txtId.setText(id);
        tvCode.setText(code);
        tvCustomerName.setText(customerName);
        tvFatherOrHusbandName.setText(fatherOrHusbandName);
        tvHouseNumber.setText(houseNumber);
        tvStreetNumber.setText(streetNumber);
        tvPlace.setText(place);
        tvWardName.setText(wardName);
        tvWardNumber.setText(wardNumber);
        tvPhone.setText(phone);
        tvEmail.setText(email);
        tvCategory.setText(categories.get(0).toString());
        tvSubCategory.setText(categories.get(1).toString());
        tvCharges.setText(charges);
        tvServiceActivator.setText(serviceActivator);
        tvDateOfActivation.setText(dateOfActivation);
        tvDataCollector.setText(dataCollector);
        etReceiveBy.setText(username);
        etAmount.setText(charges);

        btnReceivePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = tvCode.getText().toString().trim();
                String amount = etAmount.getText().toString().trim();
                String mode = etMode.getText().toString().trim();
                String receiveBy = etReceiveBy.getText().toString().trim();
                Payment payment = new Payment();
                payment.setCode(code);
                payment.setAmount(etAmount.getText().toString());
                payment.setMode(etMode.getText().toString());
                payment.setReceiveBy(etReceiveBy.getText().toString());
                payment.setExtra(etRemarks.getText().toString());
                if (amount.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter amount", Toast.LENGTH_SHORT).show();
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
                    if (response.body().getId() == 0) {
                        Toast.makeText(SingleCustomerActivity.this, "Payment Already Done Today",
                                Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(SingleCustomerActivity.this, "Payment saved successfully!",
                                Toast.LENGTH_LONG).show();
                    }
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
