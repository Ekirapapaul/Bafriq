package com.mesozi.app.buidafrique.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.activity.MainActivity;

/**
 * Created by ekirapa on 7/8/18 .
 */
public class SignUpFrag extends Fragment {
    private View v;
    private EditText location, occupation;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_create_account, container, false);
        registerViews();
        return v;
    }

    private void registerViews() {
        v.findViewById(R.id.btn_create_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        location = v.findViewById(R.id.et_location);
        occupation = v.findViewById(R.id.et_occupation);
    }

    private boolean check() {
        if (location.getText().toString().isEmpty()) {
            location.requestFocus();
            location.setError(getString(R.string.error_required));
            return false;
        } else if (occupation.getText().toString().isEmpty()) {
            occupation.requestFocus();
            occupation.setError(getString(R.string.error_required));
            return false;
        }
        return true;
    }
}
