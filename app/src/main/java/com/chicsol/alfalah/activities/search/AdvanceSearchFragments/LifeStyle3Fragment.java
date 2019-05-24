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

public class LifeStyle3Fragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private LinearLayout LinearLayoutAdvSearchRelocation, LinearLayoutAdvSearchMarrytime, LinearLayoutAdvSearchWantChildren,
            LinearLayoutAdvSearchPhysicallyChallenged, LinearLayoutAdvSearchRevert, LinearLayoutAdvSearchBeard, LinearLayoutAdvSearchKeepHalal, LinearLayoutAdvSearchSalah, LinearLayoutAdvSearchReligious;

    private ViewGenerator viewGenerator;

    private OnChildFragmentInteractionListener fragmentInteractionListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adv_search_fragment_lifestyle3,
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
        LinearLayoutAdvSearchRelocation = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchRelocation);
        LinearLayoutAdvSearchMarrytime = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchMarrytime);
        LinearLayoutAdvSearchWantChildren = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchWantChildren);

        LinearLayoutAdvSearchPhysicallyChallenged = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchPhysicallyChallenged);
        LinearLayoutAdvSearchRevert = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchRevert);
        LinearLayoutAdvSearchBeard = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchBeard);
        LinearLayoutAdvSearchKeepHalal = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchKeepHalal);

        LinearLayoutAdvSearchSalah = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchSalah);
        LinearLayoutAdvSearchReligious = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchReligious);


        Gson gsonc;
        GsonBuilder gsonBuilderc = new GsonBuilder();
        gsonc = gsonBuilderc.create();
        Type listType = new TypeToken<List<WebArd>>() {
        }.getType();


        try {
            List<WebArd> dataList0 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(29).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList0, LinearLayoutAdvSearchRelocation, "relocation");

            List<WebArd> dataList1 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(30).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList1, LinearLayoutAdvSearchMarrytime, "marrytime");

            List<WebArd> dataList2 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(31).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList2, LinearLayoutAdvSearchWantChildren, "wantchildren");

            List<WebArd> dataList3 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(32).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList3, LinearLayoutAdvSearchPhysicallyChallenged, "physicallychallenged");

            List<WebArd> dataList4 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(33).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList4, LinearLayoutAdvSearchRevert, "revert");

            List<WebArd> dataList5 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(34).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList5, LinearLayoutAdvSearchBeard, "beard");

            List<WebArd> dataList6 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(35).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList6, LinearLayoutAdvSearchKeepHalal, "keephalal");

            List<WebArd> dataList7 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(36).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList7, LinearLayoutAdvSearchSalah, "salah");

            List<WebArd> dataList8 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(37).toString(), listType);
            viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList8, LinearLayoutAdvSearchReligious, "religious");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setSelection() {

        if (defaultSelectionsObj != null) {

            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchRelocation, defaultSelectionsObj.getChoice_relocation_ids());
            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchMarrytime, defaultSelectionsObj.getChoice_marrytime_ids());
            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchWantChildren, defaultSelectionsObj.getChoice_want_children_ids());

            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchPhysicallyChallenged, defaultSelectionsObj.getChoice_physically_challenged_ids());
            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchRevert, defaultSelectionsObj.getChoice_revert_ids());
            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchBeard, defaultSelectionsObj.getChoice_beard_ids());

            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchKeepHalal, defaultSelectionsObj.getChoice_keep_halal_ids());


            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchSalah, defaultSelectionsObj.getChoice_salah_ids());
            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchReligious, defaultSelectionsObj.getChoice_religious_ids());


        }

    }

    private void setListeners() {
        {
            int childcount = LinearLayoutAdvSearchRelocation.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchRelocation.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }
        {
            int childcount = LinearLayoutAdvSearchMarrytime.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchMarrytime.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }
        {
            int childcount = LinearLayoutAdvSearchWantChildren.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchWantChildren.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }

        {
            int childcount = LinearLayoutAdvSearchPhysicallyChallenged.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchPhysicallyChallenged.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }
        {
            int childcount = LinearLayoutAdvSearchRevert.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchRevert.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }
        {
            int childcount = LinearLayoutAdvSearchBeard.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchBeard.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }
        {
            int childcount = LinearLayoutAdvSearchKeepHalal.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchKeepHalal.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }
        {
            int childcount = LinearLayoutAdvSearchSalah.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchSalah.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }
        {
            int childcount = LinearLayoutAdvSearchReligious.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchReligious.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getTag() != null) {
            //  Log.e("sibling ids",""+ defaultSelectionsObj.getChoice_raised_ids());

            if (buttonView.getTag().equals("relocation")) {
                defaultSelectionsObj.setChoice_relocation_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchRelocation));

            }
            if (buttonView.getTag().equals("marrytime")) {
                defaultSelectionsObj.setChoice_marrytime_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchMarrytime));
            }
            if (buttonView.getTag().equals("wantchildren")) {
                defaultSelectionsObj.setChoice_want_children_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchWantChildren));
            }
            if (buttonView.getTag().equals("physicallychallenged")) {
                defaultSelectionsObj.setChoice_physically_challenged_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchPhysicallyChallenged));
            }
            if (buttonView.getTag().equals("revert")) {
                defaultSelectionsObj.setChoice_revert_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchRevert));
            }
            if (buttonView.getTag().equals("beard")) {
                defaultSelectionsObj.setChoice_beard_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchBeard));
            }
            if (buttonView.getTag().equals("keephalal")) {
                defaultSelectionsObj.setChoice_keep_halal_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchKeepHalal));
            }
            if (buttonView.getTag().equals("salah")) {
                defaultSelectionsObj.setChoice_salah_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchSalah));
            }
            if (buttonView.getTag().equals("religious")) {
                defaultSelectionsObj.setChoice_religious_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchReligious));
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
