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
public class CodeFrag extends Fragment {
    EditText code;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_enter_code, container, false);
        Objects.requireNonNull(((AccountRecovery) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("Account Recovery");
        setViews();
        return v;
    }

    private void setViews() {
        v.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    ((AccountRecovery) Objects.requireNonNull(getActivity())).setCode(code.getText().toString());
                    FragmentModel.getInstance().addNewFrag(2);
                }
            }
        });

        code = v.findViewById(R.id.et_code);
    }

    private boolean check() {
        if (code.getText().toString().isEmpty()) {
            code.requestFocus();
            code.setError(getString(R.string.error_required));
            return false;
        }
        return true;
    }
}
