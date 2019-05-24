package com.chicsol.alfalah.activities.search.AdvanceSearchFragments;

import android.content.Context;
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

public class EthnicBackgroundFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private LinearLayout LinearLayoutAdvSearchEthnicBackground, LinearLayoutAdvSearchReligiousSect;
   // LinearLayoutAdvSearchCaste

    private ViewGenerator viewGenerator;
    private OnChildFragmentInteractionListener fragmentInteractionListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adv_search_fragment_ethnic_background,
                container, false);
        initialize(view);


        return view;
    }

    private void initialize(View view) {

        viewGenerator = new ViewGenerator(getContext());
        LinearLayoutAdvSearchEthnicBackground = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchEthnicBackground);
        LinearLayoutAdvSearchReligiousSect = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchReligiousSect);
        //LinearLayoutAdvSearchCaste = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchCaste);

        Gson gsonc;
        GsonBuilder gsonBuilderc = new GsonBuilder();
        gsonc = gsonBuilderc.create();
        Type listType = new TypeToken<List<WebArd>>() {
        }.getType();


        try {
            List<WebArd> dataList0 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(7).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList0, LinearLayoutAdvSearchEthnicBackground, "ethnic");

            List<WebArd> dataList1 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(20).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList1, LinearLayoutAdvSearchReligiousSect, "religious");


         //   List<WebArd> dataList2 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(23).toString(), listType);
        //    viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList2, LinearLayoutAdvSearchCaste, "caste");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setSelection();
        setListeners();
    }

    private void setSelection() {

        if (defaultSelectionsObj != null) {

            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchEthnicBackground, defaultSelectionsObj.getChoice_ethnic_bground_ids());
            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchReligiousSect, defaultSelectionsObj.getChoice_religious_sect_ids());
        //    viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchCaste, defaultSelectionsObj.getChoice_caste_ids());
        }

    }

    private void setListeners() {
        {
            int childcount = LinearLayoutAdvSearchEthnicBackground.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchEthnicBackground.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }
        {
            int childcount = LinearLayoutAdvSearchReligiousSect.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchReligiousSect.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }

   /*     {
            int childcount = LinearLayoutAdvSearchCaste.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchCaste.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }*/
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView != null) {

            if (buttonView.getTag() != null) {

                if (buttonView.getTag().equals("ethnic")) {
                    defaultSelectionsObj.setChoice_ethnic_bground_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchEthnicBackground));
                }
                if (buttonView.getTag().equals("religious")) {
                    defaultSelectionsObj.setChoice_religious_sect_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchReligiousSect));
                }

            /*    if (buttonView.getTag().equals("caste")) {
                    defaultSelectionsObj.setChoice_caste_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchCaste));
                }*/
            }
        }

        updateDot();
    }
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {

            if (getTargetFragment() != null) {
                fragmentInteractionListener = (OnChildFragmentInteractionListener) getTargetFragment();
            } else {
                fragmentInteractionListener = (OnChildFragmentInteractionListener) activity;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement OnCompleteListener");
        }
    }
    public interface OnChildFragmentInteractionListener {
        void messageFromChildToParent();
    }

    private void updateDot() {
        fragmentInteractionListener.messageFromChildToParent();

    }
}
