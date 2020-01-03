package com.vivek.wmsapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.vivek.wmsapplication.model.Customer;

import java.util.List;

public class CustomerAdapter extends ArrayAdapter {

    private Context context;
    private List<Customer> customers;

    public CustomerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Customer> objects) {
        super(context, resource, objects);
        this.context = context;
        this.customers = objects;

    }
    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_customer, parent, false);

        TextView txtId = (TextView) rowView.findViewById(R.id.txtId);
        TextView txtCode = (TextView) rowView.findViewById(R.id.txtCode);
        TextView txtCustomerName = (TextView) rowView.findViewById(R.id.txtCustomerName);
        TextView txtFatherOrHusbandName = (TextView) rowView.findViewById(R.id.txtFatherOrHusbandName);
        TextView txtHouseNumber = (TextView) rowView.findViewById(R.id.txtHouseNumber);
        TextView txtStreetNumber = (TextView) rowView.findViewById(R.id.txtStreetNumber);
        TextView txtPlace = (TextView) rowView.findViewById(R.id.txtPlace);
        TextView txtWardName = (TextView) rowView.findViewById(R.id.txtWardName);
        TextView txtWardNumber = (TextView) rowView.findViewById(R.id.txtWardNumber);
        TextView txtPhone = (TextView) rowView.findViewById(R.id.txtPhone);
        TextView txtEmail = (TextView) rowView.findViewById(R.id.txtEmail);
        TextView txtCategory = (TextView) rowView.findViewById(R.id.txtCategory);
        TextView txtSubCategory = (TextView) rowView.findViewById(R.id.txtSubCategory);
        TextView txtCharges = (TextView) rowView.findViewById(R.id.txtCharges);
        TextView txtServiceActivator = (TextView) rowView.findViewById(R.id.txtServiceActivator);
        TextView txtDateOfActivation = (TextView) rowView.findViewById(R.id.txtDateOfActivation);
        TextView txtDataCollector = (TextView) rowView.findViewById(R.id.txtDataCollector);

        txtId.setText(String.format("#ID: %d", customers.get(pos).getId()));
        txtCode.setText(String.format("Customer Code: %s", customers.get(pos).getCode()));
        txtCustomerName.setText(String.format("Customer Name: %s", customers.get(pos).getCustomerName()));
        txtFatherOrHusbandName.setText(String.format("Customer: %s", customers.get(pos).getFatherOrHusbandName()));
        txtHouseNumber.setText(String.format("Customer: %s", customers.get(pos).getHouseNumber()));
        txtStreetNumber.setText(String.format("Customer: %s", customers.get(pos).getId()));
        txtPlace.setText(String.format("Customer: %s", customers.get(pos).getId()));
        txtWardName.setText(String.format("Customer: %s", customers.get(pos).getId()));
        txtWardNumber.setText(String.format("Customer: %s", customers.get(pos).getId()));
        txtPhone.setText(String.format("Customer: %s", customers.get(pos).getId()));
        txtEmail.setText(String.format("Customer: %s", customers.get(pos).getId()));
        txtCategory.setText(String.format("Customer: %s", customers.get(pos).getId()));
        txtSubCategory.setText(String.format("Customer: %s", customers.get(pos).getId()));
        txtCharges.setText(String.format("Customer: %s", customers.get(pos).getId()));
        txtServiceActivator.setText(String.format("Customer: %s", customers.get(pos).getId()));
        txtDateOfActivation.setText(String.format("Customer: %s", customers.get(pos).getId()));
        txtDataCollector.setText(String.format("Customer: %s", customers.get(pos).getId()));
        txtId.setText(String.format("Customer: %s", customers.get(pos).getId()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Activity User Form
                Intent intent = new Intent(context, CustomerActivity.class);
                intent.putExtra("id", String.valueOf(customers.get(pos).getId()));
                intent.putExtra("code", customers.get(pos).getCode());
                intent.putExtra("customer_name", customers.get(pos).getCustomerName());

                context.startActivity(intent);
            }
        });
        return rowView;
    }

}
