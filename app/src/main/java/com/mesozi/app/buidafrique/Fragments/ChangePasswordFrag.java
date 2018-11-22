package com.mesozi.app.buidafrique.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.FragmentModel;
import com.mesozi.app.buidafrique.activity.AccountRecovery;

import java.util.Objects;

/**
 * Created by ekirapa on 11/13/18 .
 */
public class ChangePasswordFrag extends Fragment {
    EditText password, confirm;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_change_password, container, false);
        Objects.requireNonNull(((AccountRecovery) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("Change Password");
        setViews();
        return v;
    }

    private void setViews() {
        v.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    ((AccountRecovery) Objects.requireNonNull(getActivity())).setPassword(password.getText().toString());
                    FragmentModel.getInstance().addNewFrag(3);
                }
            }
        });

        password = v.findViewById(R.id.et_password);
        confirm = v.findViewById(R.id.et_confirm_password);
    }

    private boolean check() {
        if (password.getText().toString().isEmpty()) {
            password.requestFocus();
            password.setError(getString(R.string.error_required));
            return false;
        } else if (confirm.getText().toString().isEmpty()) {
            confirm.requestFocus();
            confirm.setError(getString(R.string.error_required));
            return false;
        } else if (!password.getText().toString().equals(confirm.getText().toString())) {
            confirm.requestFocus();
            confirm.setError("Passwords do not match");
            return false;
        }
        return true;
    }
}
