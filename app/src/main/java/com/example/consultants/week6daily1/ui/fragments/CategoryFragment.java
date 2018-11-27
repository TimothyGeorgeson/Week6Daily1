package com.example.consultants.week6daily1.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.consultants.week6daily1.R;
import com.example.consultants.week6daily1.ui.main.MainActivity;

public class CategoryFragment extends DialogFragment {
    public static final String TAG = CategoryFragment.class.getSimpleName() + "_TAG";

    RadioGroup categoryGroup;
    RadioButton categoryButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.category_fragment, container, false);

        categoryGroup = view.findViewById(R.id.radioCategory);

        //ok button to dismiss detailed info fragment
        Button btnOk = view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // find the radiobutton by id
                int selectedId = categoryGroup.getCheckedRadioButtonId();
                categoryButton = view.findViewById(selectedId);

                if (categoryButton != null)
                    Log.d(TAG, "onClick: " + categoryButton.getText());

                ((MainActivity)getActivity()).getLocationAndPlaces(categoryButton.getText().toString().toLowerCase());
                getDialog().dismiss();
            }
        });

        return view;
    }
}
