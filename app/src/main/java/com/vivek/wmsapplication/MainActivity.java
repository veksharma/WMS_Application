package com.vivek.wmsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
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

    Button btnAddResidentialCustomer, btnAddCommercialCustomer;
    Button btnPaymentHistory;
    Button btnGetCustomer;
    //    Button btnPhotoUpload;
//    Button btnQRcode;
    ListView listView;
    EditText editText;

    LocationManager locationManager;

    private Context context;
    private List<Customer> customers;

    CustomerService customerService;
    List<Customer> list = new ArrayList<Customer>();
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.app_name);

        btnAddResidentialCustomer = findViewById(R.id.btnAddResidentialCustomer);
//        btnAddCommercialCustomer = findViewById(R.id.btnAddCommercialCustomer);
        btnPaymentHistory = findViewById(R.id.btnPaymentHistory);
        btnGetCustomer = findViewById(R.id.btnGetCustomer);
//        btnPhotoUpload = findViewById(R.id.btnPhotoUpload);
//        btnQRcode = findViewById(R.id.btnQRcode);
        listView = findViewById(R.id.listView);
        customerService = APIUtils.getCustomerService();

        Bundle extras = getIntent().getExtras();
        final String username = extras.getString("username");
        System.out.println("===========>>>" + username);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        }

        btnAddResidentialCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QRCodeActivity.class);
                intent.putExtra("property", "residential");
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

//        btnAddCommercialCustomer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, QRCodeActivity.class);
//                intent.putExtra("username", username);
//                intent.putExtra("property", "commercial");
//                startActivity(intent);
//            }
//        });

        btnGetCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = findViewById(R.id.etSearchCustomer);
                String code = editText.getText().toString();
                if (code.length() < 13 || code.length() > 13) {
                    editText.setError("13 Characters Required");
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

//        btnPhotoUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
//                intent.putExtra("username", username);
//                startActivity(intent);
//            }
//        });

//        btnQRcode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, QRCodeActivity.class);
//                intent.putExtra("username", username);
//                startActivity(intent);
//            }
//        });


    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS Required").setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
                        intent.putExtra("customerObj", customer);
                        intent.putExtra("id", String.valueOf(customer.getId()));
                        intent.putExtra("code", customer.getCode());
                        intent.putExtra("customer_name", customer.getCustomerName());
                        intent.putExtra("father_husband_name", customer.getFatherOrHusbandName());
                        intent.putExtra("phone", customer.getPhone());
                        intent.putExtra("email", customer.getEmail());
                        intent.putExtra("house_number", customer.getHouseNumber());
                        intent.putExtra("street_number", customer.getStreetNumber());
                        intent.putExtra("buildingCoveredArea", customer.getBuildingCoveredArea());
                        intent.putExtra("buildingCarpetArea", customer.getBuildingCarpetArea());
                        intent.putExtra("category", customer.getCategory());
                        intent.putExtra("sub_category", customer.getSubCategory());
                        intent.putExtra("propertyType", customer.getPropertyType());
                        intent.putExtra("openLandPlotArea", customer.getLandCoveredArea());
                        intent.putExtra("landCarpetArea", customer.getLandCarpetArea());
                        intent.putExtra("buildingUseBy", customer.getBuildingUsedBy());
                        intent.putExtra("yearOfBuildingConstruction", customer.getYearOfConstruction());
                        intent.putExtra("buildingFixedMonthlyRentRate", customer.getFixedMonthlyRateForBuilding());
                        intent.putExtra("annualValueOfBuilding", customer.getBuildingAnnualValue());
                        intent.putExtra("annualValueAfterExemptionForOwner", customer.getBuildingAnnualValueAfterExemption());
                        intent.putExtra("annualValueAfterExemptionForTenant", customer.getBuildingAnnualValueAfterExemption());
                        intent.putExtra("landFixedMonthlyRentRate", customer.getFixedMonthlyRateForLand());
                        intent.putExtra("annualValueOfLand", customer.getLandAnnualValue());
                        intent.putExtra("totalAnnualValue", customer.getTotalAnnualValue());
                        intent.putExtra("taxRate", customer.getTaxRate());
                        intent.putExtra("taxOnTotalAnnualValue", customer.getTaxOnAnnualValue());
                        intent.putExtra("waterTax", customer.getWaterTax());
                        intent.putExtra("dueDate", customer.getDueDate());
                        intent.putExtra("ward_number", customer.getWardNumber());
                        intent.putExtra("ward_name", customer.getWardName());
                        intent.putExtra("place", customer.getPlace());
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
