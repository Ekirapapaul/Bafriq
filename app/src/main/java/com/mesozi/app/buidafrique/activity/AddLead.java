package com.mesozi.app.buidafrique.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.mesozi.app.buidafrique.Models.Customer_Table;
import com.mesozi.app.buidafrique.adapters.CustomSpinnerAdapter;
import com.mesozi.app.buidafrique.adapters.CustomSpinnerEmailAdapter;
import com.mesozi.app.buidafrique.adapters.CustomSpinnerNumberAdapter;
import com.mesozi.app.buidafrique.adapters.CustomerAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import com.mesozi.app.buidafrique.Models.Customer;
import com.mesozi.app.buidafrique.R;

import java.util.List;

/**
 * Created by ekirapa on 9/25/18 .
 */
public class AddLead extends AppCompatActivity {
    private Customer customer;
    private EditText etName, etDescription, etCustName, etMobile, etEmail;
    SearchableSpinner searchableSpinner, spinnerNumber, spinnerEmail;
    List<Customer> customersEmail;
    List<Customer> customersPhone;
    List<Customer> customers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lead);

        if (getIntent().getParcelableExtra("parcel_data") != null) {
            customer = getIntent().getParcelableExtra("parcel_data");
        }
        setViews();
    }

    private void setViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        etName = findViewById(R.id.et_name);
        etDescription = findViewById(R.id.et_description);
        etCustName = findViewById(R.id.et_cust_name);
        etMobile = findViewById(R.id.et_cust_mobile);
        etEmail = findViewById(R.id.et_cust_email);

        searchableSpinner = findViewById(R.id.spinner_name);
        searchableSpinner.setTitle("Select Customer");
        customers = SQLite.select().from(Customer.class).queryList();
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddLead.this, android.R.layout.simple_spinner_item, customers);
        searchableSpinner.setAdapter(adapter);
        searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < customers.size()) {
                    customer = customers.get(i);
                    setCustomer(customer);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (customer != null) {
            if (customer.getName() != null && !customer.getName().isEmpty()) {
                etCustName.setText(customer.getName());
                int position = customers.indexOf(customer);
                searchableSpinner.setSelection(position);
            }
            if (customer.getMobile() != null && !customer.getMobile().isEmpty()) {
                etMobile.setText(customer.getMobile());
            }
            if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
                etEmail.setText(customer.getEmail());
            }
        }

        spinnerNumber = findViewById(R.id.spinner_mobile);
        spinnerNumber.setTitle("Select Customer");
        customersPhone = SQLite.select().from(Customer.class).where(Customer_Table.phone.notEq("false")).queryList();
        CustomSpinnerNumberAdapter customSpinnerNumberAdapter = new CustomSpinnerNumberAdapter(AddLead.this, android.R.layout.simple_spinner_item, customersPhone);
        spinnerNumber.setAdapter(customSpinnerNumberAdapter);
        spinnerNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < customersPhone.size()) {
                    customer = customersPhone.get(i);
                    setCustomer(customer);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerEmail = findViewById(R.id.spinner_email);
        spinnerEmail.setTitle("Select Customer");
        customersEmail = SQLite.select().from(Customer.class).where(Customer_Table.email.notEq("false")).queryList();
        CustomSpinnerEmailAdapter customSpinnerEmailAdapter = new CustomSpinnerEmailAdapter(AddLead.this, android.R.layout.simple_spinner_item, customersEmail);
        spinnerEmail.setAdapter(customSpinnerEmailAdapter);
        spinnerEmail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < customersEmail.size()) {
                    customer = customersEmail.get(i);
                    setCustomer(customer);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setCustomer(Customer customer) {
        if (customersEmail.contains(customer))
            spinnerEmail.setSelection(customersEmail.indexOf(customer));
        if (customers.contains(customer))
            searchableSpinner.setSelection(customers.indexOf(customer));
        if (customersPhone.contains(customer))
            spinnerNumber.setSelection(customersPhone.indexOf(customer));

    }
}
