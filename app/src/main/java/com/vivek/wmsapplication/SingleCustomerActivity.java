package com.vivek.wmsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vivek.wmsapplication.model.Customer;
import com.vivek.wmsapplication.model.Payment;
import com.vivek.wmsapplication.remote.APIUtils;
import com.vivek.wmsapplication.remote.CustomerService;
import com.vivek.wmsapplication.remote.PaymentService;
import com.vivek.wmsapplication.utils.PropertyTypes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleCustomerActivity extends AppCompatActivity {

    CustomerService customerService;
    PaymentService paymentService;

    TextView txtId;
    TextView tvCode;
    TextView tvCustomerName;
    TextView tvFatherOrHusbandName;
    TextView tvPhone;
    TextView tvEmail;
    TextView tvHouseNumber;
    TextView tvStreetNumber;
    TextView tvOtherDetails;
    TextView tvBuildingCoveredArea;
    TextView tvBuildingCarpetArea;
    TextView tvCategory;
    TextView tvSubCategory;
    TextView tvBuildingType;
    TextView tvPropertyUseBy;
    TextView tvYearOfConstruction;
    TextView tvFixedBuildingRate;
    TextView tvBuildingAnnualValue;
    TextView tvExemptedAnnualValue;
    TextView tvLandCoveredArea;
    TextView tvLandCarpetArea;
    TextView tvFixedLandRate;
    TextView tvLandAnnualValue;
    TextView tvTotalAnnualValue;
    TextView tvTaxRate;
    TextView tvHouseTax;
    TextView tvWaterTax;
    TextView tvPlace;
    TextView tvWardName;
    TextView tvWardNumber;
    TextView tvCharges;
    TextView tvServiceActivator;
    TextView tvDateOfActivation;
    TextView tvDataCollector;
    Button btnReceivePayment;
    EditText etAmount;
    EditText etMode;
    EditText etReceiveBy;
    EditText etRemarks;
    LinearLayout landFields, buildingFields;


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
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvHouseNumber = findViewById(R.id.tvHouseNumber);
        tvStreetNumber = findViewById(R.id.tvStreetNumber);
        tvOtherDetails = findViewById(R.id.tvOtherDetails);
        tvBuildingCoveredArea = findViewById(R.id.tvBuildingCoveredArea);
        tvBuildingCarpetArea = findViewById(R.id.tvBuildingCarpetArea);
        tvCategory = findViewById(R.id.tvCategory);
        tvSubCategory = findViewById(R.id.tvSubCategory);
        tvBuildingType = findViewById(R.id.tvBuildingType);
        tvPropertyUseBy = findViewById(R.id.tvPropertyUseBy);
        tvYearOfConstruction = findViewById(R.id.tvYearOfConstruction);
        tvFixedBuildingRate = findViewById(R.id.tvFixedBuildingRate);
        tvBuildingAnnualValue = findViewById(R.id.tvBuildingAnnualValue);
        tvExemptedAnnualValue = findViewById(R.id.tvExemptedAnnualValue);
        tvLandCoveredArea = findViewById(R.id.tvLandCoveredArea);
        tvLandCarpetArea = findViewById(R.id.tvLandCarpetArea);
        tvFixedLandRate = findViewById(R.id.tvFixedLandRate);
        tvLandAnnualValue = findViewById(R.id.tvLandAnnualValue);
        tvTotalAnnualValue = findViewById(R.id.tvTotalAnnualValue);
        tvTaxRate = findViewById(R.id.tvTaxRate);
        tvHouseTax = findViewById(R.id.tvHouseTax);
        tvWaterTax = findViewById(R.id.tvWaterTax);
        tvPlace = findViewById(R.id.tvPlace);
        tvWardName = findViewById(R.id.tvWardName);
        tvWardNumber = findViewById(R.id.tvWardNumber);
        tvCharges = findViewById(R.id.tvCharges);
        tvServiceActivator = findViewById(R.id.tvServiceActivator);
        tvDateOfActivation = findViewById(R.id.tvDateOfActivation);
        tvDataCollector = findViewById(R.id.tvDataCollector);
        btnReceivePayment = findViewById(R.id.btnReceivePayment);
        etAmount = findViewById(R.id.etAmount);
        etMode = findViewById(R.id.etMode);
        etReceiveBy = findViewById(R.id.etReceiveBy);
        etReceiveBy.setEnabled(false);
        etRemarks = findViewById(R.id.etRemarks);
        landFields = findViewById(R.id.landFields);
        buildingFields = findViewById(R.id.buildingFields);

        customerService = APIUtils.getCustomerService();

        paymentService = APIUtils.getPaymentService();

        Intent i = getIntent();
        Customer customer = (Customer) i.getSerializableExtra("customerObj");

        System.out.println(customer.toString());
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        String code = extras.getString("code");
        String z = extras.getString("customer_name");
        String fatherOrHusbandName = extras.getString("father_husband_name");
        String phone = extras.getString("phone");
        String email = extras.getString("email");
        String houseNumber = extras.getString("house_number");
        String streetNumber = extras.getString("street_number");
        String buildingCoveredArea = extras.getString("buildingCoveredArea");
        String buildingCarpetArea = extras.getString("buildingCarpetArea");
        String category = extras.getString("category");
        String subCategory = extras.getString("sub_category");
        String propertyType = extras.getString("propertyType");
        String openLandPlotArea = extras.getString("openLandPlotArea");
        String landCarpetArea = extras.getString("landCarpetArea");
        String buildingUseBy = extras.getString("buildingUseBy");
        String yearOfBuildingConstruction = extras.getString("yearOfBuildingConstruction");
        String buildingFixedMonthlyRentRate = extras.getString("buildingFixedMonthlyRentRate");
        String annualValueOfBuilding = extras.getString("annualValueOfBuilding");
        String annualValueAfterExemptionForOwner = extras.getString("annualValueAfterExemptionForOwner");
        String annualValueAfterExemptionForTenant = extras.getString("annualValueAfterExemptionForTenant");
        String landFixedMonthlyRentRate = extras.getString("landFixedMonthlyRentRate");
        String annualValueOfLand = extras.getString("annualValueOfLand");
        String totalAnnualValue = extras.getString("totalAnnualValue");
        String taxRate = extras.getString("taxRate");
        String taxOnTotalAnnualValue = extras.getString("taxOnTotalAnnualValue");
        String waterTax = extras.getString("waterTax");
        String dueDate = extras.getString("dueDate");
        String wardName = extras.getString("ward_name");
        String wardNumber = extras.getString("ward_number");
        String place = extras.getString("place");
        String charges = extras.getString("charges");
        String serviceActivator = extras.getString("serviceActivator");
        String dateOfActivation = extras.getString("dateOfActivation");
        String dataCollector = extras.getString("dataCollector");
        String username = extras.getString("username");

//        ArrayList categories = PropertyTypes.GetCategories(cat, subCat);

        txtId.setText(customer.getId().toString());
        tvCode.setText(customer.getCode());
        tvCustomerName.setText(customer.getCustomerName());
        tvFatherOrHusbandName.setText(customer.getFatherOrHusbandName());
        tvPhone.setText(customer.getPhone());
        tvEmail.setText(customer.getEmail());
        tvHouseNumber.setText(customer.getHouseNumber());
        tvStreetNumber.setText(customer.getStreetNumber());
        tvOtherDetails.setText(customer.getOtherDetails());
        System.out.println("vivek========"+customer.getOtherDetails());
        if (customer.getOtherDetails() == ""){
            tvOtherDetails.setVisibility(View.GONE);
        }
        tvBuildingCoveredArea.setText(customer.getBuildingCoveredArea());
        tvBuildingCarpetArea.setText(customer.getBuildingCarpetArea());
        tvCategory.setText(customer.getCategory());
        tvSubCategory.setText(customer.getSubCategory());
        tvBuildingType.setText(customer.getPropertyType());
        if (customer.getPropertyType().toUpperCase().equals("BUILDING")){
            landFields.setVisibility(View.GONE);
        }
        if (customer.getPropertyType().toUpperCase().equals("LAND")){
            buildingFields.setVisibility(View.GONE);
        }
        tvPropertyUseBy.setText(customer.getBuildingUsedBy());
        tvYearOfConstruction.setText(customer.getYearOfConstruction());
        tvFixedBuildingRate.setText(customer.getFixedMonthlyRateForBuilding());
        tvBuildingAnnualValue.setText(customer.getBuildingAnnualValue());
        tvExemptedAnnualValue.setText(customer.getBuildingAnnualValueAfterExemption());
        tvLandCoveredArea.setText(customer.getLandCoveredArea());
        tvLandCarpetArea.setText(customer.getLandCarpetArea());
        tvFixedLandRate.setText(customer.getFixedMonthlyRateForLand());
        tvLandAnnualValue.setText(customer.getLandAnnualValue());
        tvTotalAnnualValue.setText(customer.getTotalAnnualValue());
        tvTaxRate.setText(customer.getTaxRate());
        tvHouseTax.setText(customer.getTaxOnAnnualValue());
        tvWaterTax.setText(customer.getWaterTax());
        tvPlace.setText(customer.getPlace());
        tvWardName.setText(customer.getWardName());
        tvWardNumber.setText(customer.getWardNumber());
        tvServiceActivator.setText(customer.getServiceActivator());
        tvDateOfActivation.setText(customer.getDateOfActivation());
        tvDataCollector.setText(customer.getDataCollector());
        etReceiveBy.setText(username);
        etAmount.setText(customer.getTaxOnAnnualValue());
        if (!username.equals("admin")) {
            etAmount.setEnabled(false);
        }

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
                    } else {
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
