package com.mesozi.app.buidafrique.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.CommonUtils;
import com.mesozi.app.buidafrique.Utils.FragmentModel;
import com.mesozi.app.buidafrique.activity.SignUp;

import java.util.Objects;

/**
 * Created by ekirapa on 7/8/18 .
 */
public class CreateAcountFrag extends Fragment {
    private View v;
    private static final int POSITION = 0;
    private EditText name, email, phone, password;
    CountryCodePicker country_code;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_signup, container, false);
        setViews();
        return v;
    }

    private void setViews() {
        v.findViewById(R.id.btn_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    String number = country_code.getSelectedCountryCodeWithPlus() + CommonUtils.sanitizeNumber(phone.getText().toString());
                    FragmentModel.getInstance().addNewFrag(name.getText().toString(), email.getText().toString(), number, password.getText().toString());
                }
            }
        });

        email = v.findViewById(R.id.et_email);
        password = v.findViewById(R.id.et_password);
        name = v.findViewById(R.id.et_name);
        phone = v.findViewById(R.id.et_phone);
        country_code = v.findViewById(R.id.country_code);
    }

    private boolean check() {
        if (name.getText().toString().isEmpty()) {
            name.requestFocus();
            name.setError(getString(R.string.error_required));
            return false;
        } else if (email.getText().toString().isEmpty()) {
            email.requestFocus();
            email.setError(getString(R.string.error_required));
            return false;
        } else if (phone.getText().toString().isEmpty()) {
            phone.requestFocus();
            phone.setError(getString(R.string.error_required));
            return false;
        } else if (password.getText().toString().isEmpty()) {
            password.requestFocus();
            password.setError(getString(R.string.error_required));
            return false;
        }
        return true;
    }
}
