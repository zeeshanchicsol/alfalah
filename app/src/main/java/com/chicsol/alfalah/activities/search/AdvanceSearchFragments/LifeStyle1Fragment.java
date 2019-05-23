package com.chicsol.alfalah.activities.search.AdvanceSearchFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class LifeStyle1Fragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private LinearLayout LinearLayoutAdvSearchRaisedWhere,LinearLayoutAdvSearchLanguage, LinearLayoutAdvSearchHijab, LinearLayoutAdvSearchFamilyValues, LinearLayoutAdvSearchLivingArrangement;

    private ViewGenerator viewGenerator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adv_search_fragment_lifestyle1,
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
        LinearLayoutAdvSearchRaisedWhere = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchRaisedWhere);
        LinearLayoutAdvSearchLanguage = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchLanguage);

        LinearLayoutAdvSearchHijab = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchHijab);
        LinearLayoutAdvSearchFamilyValues = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchFamilyValues);
        LinearLayoutAdvSearchLivingArrangement = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchLivingArrangement);


        Gson gsonc;
        GsonBuilder gsonBuilderc = new GsonBuilder();
        gsonc = gsonBuilderc.create();
        Type listType = new TypeToken<List<WebArd>>() {
        }.getType();


        try {
            List<WebArd> dataList0 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(19).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList0, LinearLayoutAdvSearchRaisedWhere, "raised");


            List<WebArd> dataList5 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(5).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList5, LinearLayoutAdvSearchLanguage, "language");

            List<WebArd> dataList1 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(12).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList1, LinearLayoutAdvSearchHijab, "hijab");


            List<WebArd> dataList2 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(9).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList2, LinearLayoutAdvSearchFamilyValues, "family");

            List<WebArd> dataList3 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(15).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList3, LinearLayoutAdvSearchLivingArrangement, "living");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setSelection() {

        if (defaultSelectionsObj != null) {

            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchRaisedWhere, defaultSelectionsObj.getChoice_raised_ids());
            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchLanguage, defaultSelectionsObj.getSpoken_language_ids());
            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchHijab, defaultSelectionsObj.getChoice_hijab_ids());
            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchFamilyValues, defaultSelectionsObj.getChoice_family_values_ids());
            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchLivingArrangement, defaultSelectionsObj.getChoice_living_arangment_ids());

        }

    }

    private void setListeners() {
        {
            int childcount = LinearLayoutAdvSearchRaisedWhere.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchRaisedWhere.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }
        {
            int childcount = LinearLayoutAdvSearchHijab.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchHijab.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }
        {
            int childcount = LinearLayoutAdvSearchFamilyValues.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchFamilyValues.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }
        {
            int childcount = LinearLayoutAdvSearchLivingArrangement.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchLivingArrangement.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }

        {
            int childcount = LinearLayoutAdvSearchLanguage.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchLanguage.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getTag() != null) {
            if (buttonView.getTag().equals("raised")) {
                defaultSelectionsObj.setChoice_raised_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchRaisedWhere));
            } else if (buttonView.getTag().equals("hijab")) {
                defaultSelectionsObj.setChoice_hijab_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchHijab));
            } else if (buttonView.getTag().equals("family")) {
                defaultSelectionsObj.setChoice_family_values_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchFamilyValues));

            } else if (buttonView.getTag().equals("living")) {
                defaultSelectionsObj.setChoice_living_arangment_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchLivingArrangement));
            }
            else if (buttonView.getTag().equals("language")) {
              //  defaultSelectionsObj.setChoice_living_arangment_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchLanguage));
            }
        }

    }
}
