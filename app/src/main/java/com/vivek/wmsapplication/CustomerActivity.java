package com.vivek.wmsapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.vivek.wmsapplication.model.Customer;
import com.vivek.wmsapplication.model.Result;
import com.vivek.wmsapplication.remote.APIUtils;
import com.vivek.wmsapplication.remote.CustomerService;
import com.vivek.wmsapplication.utils.Charges;
import com.vivek.wmsapplication.utils.PropertyTypes;
import com.vivek.wmsapplication.utils.Wards;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerActivity extends AppCompatActivity {

    CustomerService customerService;
    TextView txtId, textView269, textView270;
    EditText etCode, etCustomerName, etFatherOrHusbandName, etCaste, etPhone, etEmail, etHouseNumber, etResidentialAddress, etOtherDetails,
            etBuildingCoveredArea, etBuildingCarpetArea, etOpenLandPlotArea, etLandCarpetArea, etYearOfBuildingConstruction,
            etBuildingFixedMonthlyRentRate, etAnnualValueOfBuilding, etAnnualValueAfterExemptionForOwner,
            etAnnualValueAfterExemptionForTenant, etBuildingTaxRate, etBuildingTaxOnAnnualValue, etBuildingWaterTax,
            etLandFixedMonthlyRentRate, etAnnualValueOfLand, etLandTaxRate, etLandTaxOnAnnualValue, etLandWaterTax,
            etDueDate, etWardNumber, etCharges, etServiceActivator, etDateOfActivation, etDataCollector;
    String etCategory, etSubCategory, etRoadInFront, waterConnection;
    Integer getCharges, CatForCharges;
    Spinner spCategory, spSubCategory, spRoadInFront;
    AutoCompleteTextView autoCompleteWardName, autoCompleteAreaName;
    ArrayList<String> wardNameList = new ArrayList<>();
    ArrayList<String> areaNameList = new ArrayList<>();
    ArrayList<String> roadSize = new ArrayList<>();
    Button btnCreate, btnRead, btnUpdate;
    private Dialog loadingDialog;
    RadioButton building, land, owner, rented, waterYes, waterNo;
    LinearLayout building_fields, land_fields, building_annual_value, land_annual_value;

    ArrayAdapter<String> arrayAdapter_category, arrayAdapter_SubCategory, arrayAdapter_RoadInFront;

    /////////////////////// Signature
    private int STORAGE_PERMISSION_CODE = 1;
    static String ConvertedBitmap;
    String currentSignaturePath;
    Button btnGetSign, btnClear, btnSaveSign, btnCancel;
    ImageView img_sig;
    Dialog dialog;
    LinearLayout mContent;
    View view;
    CustomerActivity.signature mSignature;
    Bitmap bitmap;
    AlertDialog.Builder builder;
    LinearLayout layout;
    /////////////////////// Signature

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        setTitle("Registration Form");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StoragePermission();

        txtId = findViewById(R.id.txtId);
//        textView269 = findViewById(R.id.textView269);
//        textView270 = findViewById(R.id.textView270);
        etCode = findViewById(R.id.etCode);
        etCode.setEnabled(false);
        etCustomerName = findViewById(R.id.etCustomerName);
        etFatherOrHusbandName = findViewById(R.id.etFatherOrHusbandName);
        etCaste = findViewById(R.id.etCaste);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etHouseNumber = findViewById(R.id.etHouseNumber);
        etResidentialAddress = findViewById(R.id.etResidentialAddress);
        etOtherDetails = findViewById(R.id.etOtherDetails);

        waterYes = findViewById(R.id.water_yes);
        waterNo = findViewById(R.id.water_no);

        etBuildingCoveredArea = findViewById(R.id.etBuildingCoveredArea);
//        etBuildingCarpetArea = findViewById(R.id.etBuildingCarpetArea);
//        etBuildingCarpetArea.setEnabled(false);
        etOpenLandPlotArea = findViewById(R.id.etOpenLandPlotArea);
//        etLandCarpetArea = findViewById(R.id.etLandCarpetArea);
//        etLandCarpetArea.setEnabled(false);
//        etYearOfBuildingConstruction = findViewById(R.id.etYearOfBuildingConstruction);
//        etBuildingFixedMonthlyRentRate = findViewById(R.id.etBuildingFixedMonthlyRentRate);
//        etAnnualValueOfBuilding = findViewById(R.id.etAnnualValueOfBuilding);
//        etAnnualValueOfBuilding.setEnabled(false);
//        etAnnualValueAfterExemptionForOwner = findViewById(R.id.etAnnualValueAfterExemptionForOwner);
//        etAnnualValueAfterExemptionForOwner.setEnabled(false);
//        etAnnualValueAfterExemptionForTenant = findViewById(R.id.etAnnualValueAfterExemptionForTenant);
//        etAnnualValueAfterExemptionForTenant.setEnabled(false);
        etBuildingTaxRate = findViewById(R.id.etBuildingTaxRate);
        etBuildingTaxOnAnnualValue = findViewById(R.id.etBuildingTaxOnAnnualValue);
        etBuildingTaxOnAnnualValue.setEnabled(false);
//        etBuildingWaterTax = findViewById(R.id.etBuildingWaterTax);
//        etBuildingWaterTax.setEnabled(false);
//        etLandFixedMonthlyRentRate = findViewById(R.id.etLandFixedMonthlyRentRate);
//        etAnnualValueOfLand = findViewById(R.id.etAnnualValueOfLand);
//        etAnnualValueOfLand.setEnabled(false);
        etLandTaxRate = findViewById(R.id.etLandTaxRate);
        etLandTaxOnAnnualValue = findViewById(R.id.etLandTaxOnAnnualValue);
        etLandTaxOnAnnualValue.setEnabled(false);
//        etLandWaterTax = findViewById(R.id.etLandWaterTax);
//        etLandWaterTax.setEnabled(false);

        etDueDate = findViewById(R.id.etDueDate);
        String patternDueDate = "yyyy-MM-dd";
        SimpleDateFormat DueDateFormat = new SimpleDateFormat(patternDueDate);
        String dateDueDate = DueDateFormat.format(new Date());
        etDueDate.setText(dateDueDate);

        etWardNumber = findViewById(R.id.etWardNumber);
        etWardNumber.setEnabled(false);

        etCharges = findViewById(R.id.etCharges);
        etServiceActivator = findViewById(R.id.etServiceActivator);
        etServiceActivator.setEnabled(false);
        etDateOfActivation = findViewById(R.id.etDateOfActivation);
        etDateOfActivation.setEnabled(false);
        etDataCollector = findViewById(R.id.etDataCollector);
        etDataCollector.setEnabled(false);

        btnCreate = findViewById(R.id.btnCreate);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);

        building = findViewById(R.id.radio_building);
        land = findViewById(R.id.radio_land);
        owner = findViewById(R.id.radio_owner);
        rented = findViewById(R.id.radio_rented);

        building_fields = findViewById(R.id.building_fields);
        building_fields.setVisibility(View.GONE);
        land_fields = findViewById(R.id.land_fields);
        land_fields.setVisibility(View.GONE);
        building_annual_value = findViewById(R.id.building_annual_value);
        building_annual_value.setVisibility(View.GONE);
        land_annual_value = findViewById(R.id.land_annual_value);
        land_annual_value.setVisibility(View.GONE);

        building.setOnCheckedChangeListener(new Radio_check());
        land.setOnCheckedChangeListener(new Radio_check());
        owner.setOnCheckedChangeListener(new Owner_check());
        rented.setOnCheckedChangeListener(new Owner_check());
        waterYes.setOnCheckedChangeListener(new water_check());
        waterNo.setOnCheckedChangeListener(new water_check());

        /////////////loading dialog
        loadingDialog = new Dialog(CustomerActivity.this);
        loadingDialog.setContentView(R.layout.loading_progess_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.slider_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        /////////////loading dialog


        /////////////////////// Signature
        img_sig = findViewById(R.id.img_sig);
        layout = findViewById(R.id.layout);
        btnGetSign = findViewById(R.id.btn_get_sig);
        dialog = new Dialog(CustomerActivity.this);
        // Removing the features of Normal Dialogs
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_signature);
        dialog.setCancelable(true);

        btnGetSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConvertedBitmap = "";
                dialog_action();
            }
        });
        /////////////////////// Signature

        getCharges = -1;

        spCategory = findViewById(R.id.spCategory);
        spSubCategory = findViewById(R.id.spSubCategory);
        spRoadInFront = findViewById(R.id.spRoadInFront);

        autoCompleteWardName = findViewById(R.id.autoCompleteWardName);
        autoCompleteAreaName = findViewById(R.id.autoCompleteAreaName);

        wardNameList = Wards.getWardsName();
        areaNameList = new ArrayList<>();
        roadSize = new ArrayList<>();

        customerService = APIUtils.getCustomerService();

//      AutoCompleteTextView Starts

        ArrayAdapter<String> wardArray = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, wardNameList);
        autoCompleteWardName.setAdapter(wardArray);   //setting the adapter data into the AutoCompleteTextView
        autoCompleteWardName.setThreshold(2);       //will start working from first character

        autoCompleteWardName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (wardNameList.indexOf(autoCompleteWardName.getText().toString().trim()) == -1) {
                        autoCompleteWardName.getText().clear();
                    }
                    ;
                }
            }
        });

        autoCompleteWardName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                System.out.println(item.toString());
                if (item != null) {
                    Integer number = Wards.getWardNumber(item.toString());
                    System.out.println(number);
                    if (number != null) {
                        etWardNumber.setText(number.toString());
                    }
                    areaNameList = Wards.getAreaName(item.toString());
                    System.out.println("AREA ARRAY " + areaNameList);
                    CallAutoCompleteAreaName(areaNameList);
                }
            }
        });

//      AutoCompleteTextView Ends

        //      Road Spinner Starts sc=999

        roadSize.add("20 feet se adhik");
        roadSize.add("8 feet say 20 feet");
        roadSize.add("4 feet say 7 feet");
        roadSize.add("4 feet feet say kam");

        arrayAdapter_RoadInFront = new ArrayAdapter<>(getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item, roadSize);
        spRoadInFront.setAdapter(arrayAdapter_RoadInFront);

        spRoadInFront.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                etRoadInFront = roadSize.get(position).toUpperCase();
                Log.d("etRoadInFront", etRoadInFront);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        Road Spinner Ends

//      Category Spinner Starts sc=999

        arrayAdapter_category = new ArrayAdapter<>(getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item, PropertyTypes.GetCategories(1, 999));
        spCategory.setAdapter(arrayAdapter_category);

//        Category Spinner Ends

//        Sub Category Spinner Starts c=888

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                arrayAdapter_SubCategory = new ArrayAdapter<>(getApplicationContext(),
                        R.layout.support_simple_spinner_dropdown_item, PropertyTypes.GetCategories(position, 888));
                CatForCharges = position + 1;
                int pos = position + 1;
                ArrayList categories = PropertyTypes.GetCategories(position, 999);
                etCategory = categories.get(position).toString();
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
                ArrayList subCategories = PropertyTypes.GetCategories(position, 888);
                etSubCategory = subCategories.get(position).toString();
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

//        etYearOfBuildingConstruction.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                String BuildingFixedMonthlyRentRate = etBuildingFixedMonthlyRentRate.getText().toString();
//                String YearOfBuildingConstruction = etYearOfBuildingConstruction.getText().toString();
//                if (TextUtils.isEmpty(YearOfBuildingConstruction)) {
//                    etYearOfBuildingConstruction.setError("Required");
//                } else if (!TextUtils.isEmpty(BuildingFixedMonthlyRentRate)) {
//                    getExemptedValue();
//                }
//            }
//        });

        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        System.out.println("DATE++++++++" + date);

        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("id");
        final String code = extras.getString("code");
        String customerName = extras.getString("customer_name");
        String fatherOrHusbandName = extras.getString("father_husband_name");
        String caste = extras.getString("caste");
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
        String uniqueId = extras.getString("uniqueId");
        final String QRCodePath = extras.getString("QRCodePath");
        System.out.println("In Add Customer activity ===========>>> " + username);
        System.out.println("uniqueId ===========>>> " + uniqueId);
        System.out.println("QRCodePath ===========>>> " + QRCodePath);


        txtId.setText(id);
        etCode.setText(uniqueId);
        etCustomerName.setText(customerName);
        etFatherOrHusbandName.setText(fatherOrHusbandName);
        etCaste.setText(caste);
        etHouseNumber.setText(houseNumber);
        etResidentialAddress.setText(streetNumber);
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
                String wardName = autoCompleteWardName.getText().toString().trim();
                String areaName = autoCompleteAreaName.getText().toString().trim();
                if (code.length() < 13 || code.length() > 13) {
                    etCode.setError("13 Characters Required");
                    etCode.requestFocus();
                } else if (phone.length() < 10 || phone.length() > 10) {
                    etPhone.setError("10 Digit Required");
                    etPhone.requestFocus();
                } else if (wardName.length() == 0) {
                    autoCompleteWardName.setError("WardName Required");
                    autoCompleteWardName.requestFocus();
                } else if (areaName.length() == 0) {
                    autoCompleteAreaName.setError("AreaName Required");
                    autoCompleteAreaName.requestFocus();
                } else if (currentSignaturePath == null) {
                    Toast.makeText(CustomerActivity.this, "Take Signature First", Toast.LENGTH_SHORT).show();
                } else {
                    Customer c = new Customer();
                    c.setCode(code);
                    c.setCustomerName(etCustomerName.getText().toString().toUpperCase());
                    c.setFatherOrHusbandName(etFatherOrHusbandName.getText().toString().toUpperCase());
                    c.setCaste(etCaste.getText().toString().toUpperCase());
                    c.setPhone(phone);
                    c.setEmail(etEmail.getText().toString().toUpperCase());
                    c.setHouseNumber(etHouseNumber.getText().toString().toUpperCase());
                    c.setStreetNumber(etResidentialAddress.getText().toString().toUpperCase());
                    c.setOtherDetails(etOtherDetails.getText().toString().toUpperCase());
                    c.setWaterConnection(waterConnection);
                    c.setRoadInFront(etRoadInFront);
                    if (building.isChecked()) {
                        c.setPropertyType("Building");
                        c.setTaxRate(etBuildingTaxRate.getText().toString());
                        c.setTaxOnAnnualValue(etBuildingTaxOnAnnualValue.getText().toString());
                        if (waterYes.isChecked()) {
                            c.setWaterTax("demo");
                        } else if (waterNo.isChecked()) {
                            c.setWaterTax("0");
                        }
                    } else if (land.isChecked()) {
                        c.setPropertyType("Land");
                        c.setTaxRate("demo");
                        c.setTaxOnAnnualValue(etLandTaxOnAnnualValue.getText().toString());
                        if (waterYes.isChecked()) {
                            c.setWaterTax(etLandWaterTax.getText().toString());
                        } else if (waterNo.isChecked()) {
                            c.setWaterTax("0");
                        }
                    }
                    c.setBuildingCoveredArea(etBuildingCoveredArea.getText().toString());
                    c.setLandCoveredArea(etOpenLandPlotArea.getText().toString());
                    c.setBuildingCarpetArea("demo");
                    c.setLandCarpetArea("demo");
                    c.setCategory(etCategory);
                    c.setSubCategory(etSubCategory);
                    if (owner.isChecked()) {
                        c.setBuildingUsedBy("Owner");
                        c.setBuildingAnnualValueAfterExemption("demo");
                    } else if (rented.isChecked()) {
                        c.setBuildingUsedBy("Rented");
                        c.setBuildingAnnualValueAfterExemption("demo");
                    }
                    c.setYearOfConstruction("demo");
                    c.setFixedMonthlyRateForBuilding("demo");
                    c.setFixedMonthlyRateForLand("demo");
                    c.setBuildingAnnualValue("demo");
                    c.setLandAnnualValue("demo");
                    c.setDueDate(etDueDate.getText().toString());
                    c.setPlace(autoCompleteAreaName.getText().toString().toUpperCase());
                    c.setWardName(autoCompleteWardName.getText().toString().toUpperCase());
                    c.setWardNumber(etWardNumber.getText().toString());
                    if (building.isChecked()) {
                        c.setCharges(etBuildingTaxOnAnnualValue.getText().toString().toUpperCase());
                    } else if (land.isChecked()) {
                        c.setCharges(etLandTaxOnAnnualValue.getText().toString().toUpperCase());
                    }
                    c.setServiceActivator(etServiceActivator.getText().toString().toUpperCase());
                    c.setDateOfActivation(etDateOfActivation.getText().toString().toUpperCase());
                    c.setDataCollector(etDataCollector.getText().toString().toUpperCase());
                    addCustomer(c, currentSignaturePath, QRCodePath);
                    loadingDialog.show();
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
                    loadingDialog.show();
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
                    c.setCaste(etCaste.getText().toString().toUpperCase());
                    c.setHouseNumber(etHouseNumber.getText().toString().toUpperCase());
                    c.setStreetNumber(etResidentialAddress.getText().toString().toUpperCase());
                    c.setOtherDetails(etOtherDetails.getText().toString().toUpperCase());
                    c.setRoadInFront(etRoadInFront);
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
                    loadingDialog.show();
                }
            }

        });


    }

    //storage permission
    public void StoragePermission() {

        if (ContextCompat.checkSelfPermission(CustomerActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            //Toast.makeText(MainActivity.this, "storage permission granted",Toast.LENGTH_SHORT).show();
        } else {

            requestStoragePermission();
        }

    }


    public void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this).setTitle("Storage Permission needed!").setMessage("This permission is needed")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(CustomerActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);


                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create().show();


        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }

    }

    public void dialog_action() {

        mContent = dialog.findViewById(R.id.linearLayout);
        mSignature = new CustomerActivity.signature(getApplicationContext(), null);
        mSignature.setBackgroundColor(Color.WHITE);
        // Dynamically generating Layout through java code
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        btnClear = dialog.findViewById(R.id.clear);
        btnSaveSign = dialog.findViewById(R.id.save_sign);
        btnSaveSign.setEnabled(false);
        btnCancel = dialog.findViewById(R.id.cancel);
        view = mContent;

        btnClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Log.v("log_tag", "Panel Cleared");

                mSignature.clear();
                bitmap = null;
                img_sig.setImageDrawable(null);
            }
        });

        btnSaveSign.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // Creating Separate Directory for saving Generated Images
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String pic_name = "SIGN_" + timeStamp;
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                String dirPath = storageDir.getPath();
                String StoredPath = dirPath + "/" + pic_name + ".png";
                Log.d("StoredPath", StoredPath);
                //save to static string
                ConvertedBitmap = StoredPath;
                // Method to create Directory, if the Directory doesn't exists
                File file = new File(dirPath);
                if (!file.exists()) {
                    file.mkdir();
                    /*Toast.makeText(getApplicationContext(), "Folder created", Toast.LENGTH_SHORT).show();*/
                    Snackbar snackbar = Snackbar.make(layout, "Folder created successfully!", Snackbar.LENGTH_LONG);
                    snackbar.show();

                }

                Log.v("log_tag", "Panel Saved");
                view.setDrawingCacheEnabled(true);

                mSignature.save(view, StoredPath);
                dialog.dismiss();


                if (img_sig.equals("")) {

                    builder.setTitle("Reminder!");
                    builder.setMessage("Please make sure all required fields are not empty. Before getting the driver's Signature");
                    //builder.setIcon(R.drawable.ic_priority_high_black_24dp);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });
                    builder.show();

                } else {
                    Snackbar snackbar = Snackbar.make(layout, "Signature saved successfully!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    //selectImage1();
                    File imgFile = new File(ConvertedBitmap);
                    String absoluteFile = imgFile.getAbsolutePath();
                    Log.d("Signature saved path", absoluteFile);

                    if (imgFile.exists()) {

                        // save image path to upload when save
                        currentSignaturePath = imgFile.getAbsolutePath();

                        bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        ImageView myImage = findViewById(R.id.img_sig);

                        myImage.setImageBitmap(bitmap);


                    }
                }


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("log_tag", "Panel Canceled");
                if (img_sig.equals(null)) {
                    mSignature.clear();
                    dialog.dismiss();
                } else {
                    dialog.dismiss();

                }
            }
        });
        dialog.show();
    }

    static Canvas canvas;

    public class signature extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();


        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath) {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
            }
            canvas = new Canvas(bitmap);
            try {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();
            mContent.removeAllViews();
            mContent = (LinearLayout) dialog.findViewById(R.id.linearLayout);
            mSignature = new CustomerActivity.signature(getApplicationContext(), null);
            mSignature.setBackgroundColor(Color.WHITE);
            // Dynamically generating Layout through java code
            mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            btnSaveSign.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }


    }

    public void CallAutoCompleteAreaName(final ArrayList<String> areaNameList) {
        System.out.println("AREA ARRAY " + areaNameList);
        ArrayAdapter<String> areaArray = new ArrayAdapter<>
                (this, android.R.layout.select_dialog_item, areaNameList);
        autoCompleteAreaName.setThreshold(2);
        autoCompleteAreaName.setAdapter(areaArray);

        autoCompleteAreaName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String wardName = autoCompleteWardName.getText().toString().trim();
                    if (!wardName.equals("Others")) {
                        if (areaNameList.indexOf(autoCompleteAreaName.getText().toString().trim()) == -1) {
                            autoCompleteAreaName.getText().clear();
                        }
                    }
                }
            }
        });
    }


    private void addCustomer(final Customer c, String currentSignaturePath, String QRCodePath) {

        File Signature = new File(currentSignaturePath);
        RequestBody signRequestBody = RequestBody.create(MediaType.parse("*/*"), Signature);
        MultipartBody.Part signaturePart = MultipartBody.Part.createFormData("signature", Signature.getName(), signRequestBody);

        File QRCode = new File(QRCodePath);
        RequestBody QrRequestBody = RequestBody.create(MediaType.parse("*/*"), QRCode);
        MultipartBody.Part QRCodePart = MultipartBody.Part.createFormData("qrcode", Signature.getName(), QrRequestBody);

        Call<Customer> call = customerService.addCustomer(c, signaturePart, QRCodePart);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    if (response.body().getId() == 0) {
                        Toast.makeText(CustomerActivity.this, "UID already used!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CustomerActivity.this, "Customer created successfully", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                        Log.d("response", "Successful");
                        Intent intent = new Intent(CustomerActivity.this, PhotoActivity.class);
                        intent.putExtra("uniqueId", c.getCode());
                        startActivity(intent);
                        finish();
                    }
                } else {
                    loadingDialog.dismiss();
                    Log.d("response", "Failed");
                    Toast.makeText(CustomerActivity.this, "Error at API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                loadingDialog.dismiss();
                Log.d("call", "onFailure");
                Toast.makeText(CustomerActivity.this, "onFailure", Toast.LENGTH_SHORT).show();
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
                        etCaste.setText(customer.getCaste());
                        etHouseNumber.setText(customer.getHouseNumber());
                        etResidentialAddress.setText(customer.getStreetNumber());
                        etOtherDetails.setText(customer.getOtherDetails());
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
                        loadingDialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Customer Not Found", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error at API", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
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
                    loadingDialog.dismiss();
                    Toast.makeText(CustomerActivity.this, "User updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    loadingDialog.dismiss();
                    Toast.makeText(CustomerActivity.this, "Error at API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                loadingDialog.dismiss();
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


    private class Radio_check implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (building.isChecked()) {
                building_fields.setVisibility(View.VISIBLE);
                building_annual_value.setVisibility(View.VISIBLE);
                land_fields.setVisibility(View.GONE);
                land_annual_value.setVisibility(View.GONE);

                etBuildingCoveredArea.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        String A1 = etBuildingCoveredArea.getText().toString();
                        if (TextUtils.isEmpty(A1)) {
                            etBuildingCoveredArea.setError("Required");
                        } else {
//                            float carpetArea = (Float.parseFloat(A1) * 80) / 100;
//                            Log.d("carpetArea", String.valueOf(carpetArea));
//                            etBuildingCarpetArea.setText(String.valueOf(carpetArea));
//                            setAnnualValueOfBuilding();
//                            setBuildingTaxOnAnnualValue();
                        }
                    }
                });

//                etBuildingFixedMonthlyRentRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        String fixedMonthlyRentRate = etBuildingFixedMonthlyRentRate.getText().toString();
//                        if (TextUtils.isEmpty(fixedMonthlyRentRate)) {
//                            etBuildingFixedMonthlyRentRate.setError("Required");
//                        } else {
////                            setAnnualValueOfBuilding();
////                            setBuildingTaxOnAnnualValue();
//                        }
//                    }
//                });

                etBuildingTaxRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        String BuildingTaxRate = etBuildingTaxRate.getText().toString();
                        if (TextUtils.isEmpty(BuildingTaxRate)) {
                            etBuildingTaxRate.setError("Required");
                        } else {
                            setBuildingTaxOnAnnualValue();
                        }
                    }
                });

            } else if (land.isChecked()) {
                land_fields.setVisibility(View.VISIBLE);
                land_annual_value.setVisibility(View.VISIBLE);
                building_fields.setVisibility(View.GONE);
                building_annual_value.setVisibility(View.GONE);

                etOpenLandPlotArea.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        String s = etOpenLandPlotArea.getText().toString();
                        if (TextUtils.isEmpty(s)) {
                            etOpenLandPlotArea.setError("Required");
                        } else {
//                            double landCarpetArea = Float.parseFloat(s) * .80;
//                            etLandCarpetArea.setText(String.valueOf(landCarpetArea));
//                            setAnnualValueOfLand();
//                            setLandTaxOnAnnualValue();
                        }
                    }
                });

//                etLandFixedMonthlyRentRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        String LandFixedMonthlyRentRate = etLandFixedMonthlyRentRate.getText().toString();
//                        if (TextUtils.isEmpty(LandFixedMonthlyRentRate)) {
//                            etLandFixedMonthlyRentRate.setError("Required");
//                        } else {
////                            setAnnualValueOfLand();
////                            setLandTaxOnAnnualValue();
//                        }
//                    }
//                });

                etLandTaxRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        String LandTaxRate = etLandTaxRate.getText().toString();
                        if (TextUtils.isEmpty(LandTaxRate)) {
                            etBuildingTaxRate.setError("Required");
                        } else {
                            setLandTaxOnAnnualValue();
                        }
                    }
                });

            }
        }

    }

    private void setAnnualValueOfBuilding() {
//        String BuildingCarpetArea = etBuildingCarpetArea.getText().toString();
//        String yearTxt = etYearOfBuildingConstruction.getText().toString();
//        String fixedMonthlyRentRate = etBuildingFixedMonthlyRentRate.getText().toString();
//        if (!TextUtils.isEmpty(fixedMonthlyRentRate)) {
//            if (!TextUtils.isEmpty(BuildingCarpetArea)) {
//                float annualValue = 12 * Float.parseFloat(fixedMonthlyRentRate) * Float.parseFloat(BuildingCarpetArea);
//                Log.d("AnnualValueOfBuilding", String.valueOf(annualValue));
//                etAnnualValueOfBuilding.setText(String.valueOf(annualValue));
//                if (!TextUtils.isEmpty(yearTxt)) {
//                    getExemptedValue();
//                }
//            }
//        }
    }

    private void setBuildingTaxOnAnnualValue() {
        String BuildingTaxRate = etBuildingTaxRate.getText().toString();
        if (!TextUtils.isEmpty(BuildingTaxRate)) {
            int taxOnAnnualValue = (int)((Float.parseFloat(BuildingTaxRate) * 0.18) + Float.parseFloat(BuildingTaxRate));
            Log.d("taxOnAnnualValue", String.valueOf(taxOnAnnualValue));
            etBuildingTaxOnAnnualValue.setText(String.valueOf(taxOnAnnualValue));
        }
//        String AnnualValueAfterExemptionForOwner = etAnnualValueAfterExemptionForOwner.getText().toString();
//        String AnnualValueAfterExemptionForTenant = etAnnualValueAfterExemptionForTenant.getText().toString();
//        String BuildingTaxRate = etBuildingTaxRate.getText().toString();
//        if (!TextUtils.isEmpty(BuildingTaxRate)) {
//            if (owner.isChecked()) {
//                if (!TextUtils.isEmpty(AnnualValueAfterExemptionForOwner)) {
//                    float taxOnAnnualValue = (Float.parseFloat(AnnualValueAfterExemptionForOwner) * Float.parseFloat(BuildingTaxRate) / 100);
//                    Log.d("taxOnAnnualValue", String.valueOf(taxOnAnnualValue));
//                    etBuildingTaxOnAnnualValue.setText(String.valueOf(taxOnAnnualValue));
//                    if (waterYes.isChecked()) {
//                        etBuildingWaterTax.setText(String.valueOf(taxOnAnnualValue));
//                    }else if (waterNo.isChecked()){
//                        etBuildingWaterTax.setText("0");
//                    }
//                }
//            } else if (rented.isChecked()) {
//                if (!TextUtils.isEmpty(AnnualValueAfterExemptionForTenant)) {
//                    float taxOnAnnualValue = (Float.parseFloat(AnnualValueAfterExemptionForTenant) * Float.parseFloat(BuildingTaxRate) / 100);
//                    Log.d("taxOnAnnualValue", String.valueOf(taxOnAnnualValue));
//                    etBuildingTaxOnAnnualValue.setText(String.valueOf(taxOnAnnualValue));
//                    if (waterYes.isChecked()) {
//                        etBuildingWaterTax.setText(String.valueOf(taxOnAnnualValue));
//                    }else if (waterNo.isChecked()){
//                        etBuildingWaterTax.setText("0");
//                    }
//                }
//            }
//        }
    }

    private void setAnnualValueOfLand() {
//        String LandFixedMonthlyRentRate = etLandFixedMonthlyRentRate.getText().toString();
//        if (!TextUtils.isEmpty(LandFixedMonthlyRentRate)) {
//            String LandCarpetArea = etLandCarpetArea.getText().toString();
//            if (!TextUtils.isEmpty(LandCarpetArea)) {
//                float annualValue = 12 * Float.parseFloat(LandFixedMonthlyRentRate) * Float.parseFloat(LandCarpetArea);
//                Log.d("AnnualValueOfLand", String.valueOf(annualValue));
//                etAnnualValueOfLand.setText(String.valueOf(annualValue));
//            }
//        }

    }

    private void setLandTaxOnAnnualValue() {
        String LandTaxRate = etLandTaxRate.getText().toString();
        if (!TextUtils.isEmpty(LandTaxRate)) {
            double taxOnAnnualValue = ((Float.parseFloat(LandTaxRate) * 0.18) + Float.parseFloat(LandTaxRate));
            Log.d("taxOnAnnualValue", String.valueOf(taxOnAnnualValue));
            etLandTaxOnAnnualValue.setText(String.valueOf(taxOnAnnualValue));
        }
//        if (!TextUtils.isEmpty(LandTaxRate)) {
//            String AnnualValueOfLand = etAnnualValueOfLand.getText().toString();
//            if (!TextUtils.isEmpty(AnnualValueOfLand)) {
//                float taxOnAnnualValue = (Float.parseFloat(AnnualValueOfLand) * Float.parseFloat(LandTaxRate) / 100);
//                Log.d("taxOnAnnualValue", String.valueOf(taxOnAnnualValue));
//                etLandTaxOnAnnualValue.setText(String.valueOf(taxOnAnnualValue));
//                if (waterYes.isChecked()){
//                    etLandWaterTax.setText(String.valueOf(taxOnAnnualValue));
//                }else if (waterNo.isChecked()){
//                    etLandWaterTax.setText("0");
//                }
//            }
//        }
    }

    private void getExemptedValue() {
//        String s = etAnnualValueOfBuilding.getText().toString();
//        if (!TextUtils.isEmpty(s)) {
//            float annualValue = Float.parseFloat(s);
//            String yearTxt = etYearOfBuildingConstruction.getText().toString();
//            int year = Integer.parseInt(yearTxt);
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(new Date());
//            int presentYear = cal.get(Calendar.YEAR);
//            int age = presentYear - year;
//            if (owner.isChecked()) {
//                if (1 <= age && age <= 10) {
//                    double AnnualValueAfterExemptionForOwner = annualValue * .75;
//                    etAnnualValueAfterExemptionForOwner.setText(String.valueOf(AnnualValueAfterExemptionForOwner));
//                }
//                if (11 <= age && age <= 20) {
//                    double AnnualValueAfterExemptionForOwner = annualValue * .675;
//                    etAnnualValueAfterExemptionForOwner.setText(String.valueOf(AnnualValueAfterExemptionForOwner));
//                }
//                if (20 < age) {
//                    double AnnualValueAfterExemptionForOwner = annualValue * .60;
//                    etAnnualValueAfterExemptionForOwner.setText(String.valueOf(AnnualValueAfterExemptionForOwner));
//                }
//            } else if (rented.isChecked()) {
//                if (1 <= age && age <= 10) {
//                    double AnnualValueAfterExemptionForTenant = annualValue * 1.25;
//                    etAnnualValueAfterExemptionForTenant.setText(String.valueOf(AnnualValueAfterExemptionForTenant));
//                }
//                if (11 <= age && age <= 20) {
//                    double AnnualValueAfterExemptionForTenant = annualValue * 1.125;
//                    etAnnualValueAfterExemptionForTenant.setText(String.valueOf(AnnualValueAfterExemptionForTenant));
//                }
//                if (20 < age) {
//                    double AnnualValueAfterExemptionForTenant = annualValue;
//                    etAnnualValueAfterExemptionForTenant.setText(String.valueOf(AnnualValueAfterExemptionForTenant));
//                }
//            }
//        }

    }

    private class Owner_check implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            String yearTxt = etYearOfBuildingConstruction.getText().toString();
            if (owner.isChecked()) {
//                textView269.setVisibility(View.VISIBLE);
//                etAnnualValueAfterExemptionForOwner.setVisibility(View.VISIBLE);
//                textView270.setVisibility(View.GONE);
//                etAnnualValueAfterExemptionForTenant.setVisibility(View.GONE);
//                setLandTaxOnAnnualValue();
//                if (!TextUtils.isEmpty(yearTxt)) {
//                    getExemptedValue();
//                }
//                setBuildingTaxOnAnnualValue();

            } else if (rented.isChecked()) {
//                textView269.setVisibility(View.GONE);
//                etAnnualValueAfterExemptionForOwner.setVisibility(View.GONE);
//                textView270.setVisibility(View.VISIBLE);
//                etAnnualValueAfterExemptionForTenant.setVisibility(View.VISIBLE);
//                setLandTaxOnAnnualValue();
//                if (!TextUtils.isEmpty(yearTxt)) {
//                    getExemptedValue();
//                }
//                setBuildingTaxOnAnnualValue();
            }
        }

    }

    private class water_check implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (waterYes.isChecked()) {
//                etBuildingWaterTax.setText(etBuildingTaxOnAnnualValue.getText());
//                etLandWaterTax.setText(etLandWaterTax.getText());
                waterConnection = "YES";
            } else if (waterNo.isChecked()) {
//                etBuildingWaterTax.setText("0");
//                etLandWaterTax.setText("0");
                waterConnection = "NO";

            }
        }
    }
}




