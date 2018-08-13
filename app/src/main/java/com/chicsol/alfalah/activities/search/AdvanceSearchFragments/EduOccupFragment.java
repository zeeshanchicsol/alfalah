package com.chicsol.alfalah.activities.search.AdvanceSearchFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.chicsol.alfalah.R;
import com.chicsol.alfalah.modal.WebArd;
import com.chicsol.alfalah.utils.ViewGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

import static com.chicsol.alfalah.utils.Constants.defaultSelectionsObj;
import static com.chicsol.alfalah.utils.Constants.jsonArraySearch;


public class EduOccupFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private LinearLayout LinearLayoutAdvSearchEducation, LinearLayoutAdvSearchOccupation;

    private ViewGenerator viewGenerator;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //item_image_slider = (Item) getArguments().getSerializable("item_image_slider");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adv_search_fragment_edu_occu,
                container, false);
        initialize(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setSelection();
        setListeners();
    }


    private void initialize(View view) {

        viewGenerator = new ViewGenerator(getContext());
        LinearLayoutAdvSearchEducation = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchEducation);
        LinearLayoutAdvSearchOccupation = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchOccupation);

        Gson gsonc;
        GsonBuilder gsonBuilderc = new GsonBuilder();
        gsonc = gsonBuilderc.create();
        Type listType = new TypeToken<List<WebArd>>() {
        }.getType();


        try {
            List<WebArd> dataList0 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(5).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList0, LinearLayoutAdvSearchEducation, "education");

            List<WebArd> dataList1 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(16).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList1, LinearLayoutAdvSearchOccupation, "occupation");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setSelection() {

        if (defaultSelectionsObj != null) {

            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchEducation, defaultSelectionsObj.get_choice_education_ids());
            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchOccupation, defaultSelectionsObj.get_choice_occupation_ids());

        }

    }

    private void setListeners() {
        {
            int childcount = LinearLayoutAdvSearchEducation.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchEducation.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }
        {
            int childcount = LinearLayoutAdvSearchOccupation.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchOccupation.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getTag() != null) {

            if (buttonView.getTag().equals("education")) {

                Log.e("in", "in edddddddddfdddddddddddd");
                defaultSelectionsObj.set_choice_education_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchEducation));
                Log.e("education ids", defaultSelectionsObj.get_choice_education_ids());
            } else if (buttonView.getTag().equals("occupation")) {

                defaultSelectionsObj.set_choice_occupation_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchOccupation));
            }

        }
    }

}
