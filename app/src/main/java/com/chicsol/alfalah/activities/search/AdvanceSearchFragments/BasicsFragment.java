package com.chicsol.alfalah.activities.search.AdvanceSearchFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.chicsol.alfalah.R;
import com.chicsol.alfalah.adapters.MySpinnerAdapter;
import com.chicsol.alfalah.modal.Members;
import com.chicsol.alfalah.modal.WebArd;
import com.chicsol.alfalah.utils.ViewGenerator;
import com.chicsol.alfalah.widgets.mCheckBox;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.chicsol.alfalah.utils.Constants.defaultSelectionsObj;
import static com.chicsol.alfalah.utils.Constants.jsonArraySearch;


public class BasicsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    List<WebArd> dataListRegWithin;
    private LinearLayout LinearLayoutAdvSearchProfileCreatedFor, LinearLayoutAdvSearchZodiacSign;
    private ViewGenerator viewGenerator;
    private Spinner spRegisterWithIn, spLastLoginDate;
    private MySpinnerAdapter spinnerAdapterRegisteredWithIn, spinnerAdapterLastLogin;
    private mCheckBox checkboxItemAdvSearchBasicsPicOnly, checkboxItemAdvSearchBasicsOpenToPub;
    private OnChildFragmentInteractionListener fragmentInteractionListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //item_image_slider = (Item) getArguments().getSerializable("item_image_slider");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adv_search_fragment_baiscs,
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

        checkboxItemAdvSearchBasicsPicOnly = (mCheckBox) view.findViewById(R.id.checkboxItemAdvSearchBasicsPicOnly);
        checkboxItemAdvSearchBasicsOpenToPub = (mCheckBox) view.findViewById(R.id.checkboxItemAdvSearchBasicsOpenToPub);

        LinearLayoutAdvSearchProfileCreatedFor = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchProfileCreatedFor);
        LinearLayoutAdvSearchZodiacSign = (LinearLayout) view.findViewById(R.id.LinearLayoutAdvSearchZodiacSign);

        dataListRegWithin = new ArrayList<>();
        dataListRegWithin.add(new WebArd("0", "Select"));
     //   dataListRegWithin.add(new WebArd("1", "Today"));
        dataListRegWithin.add(new WebArd("7", "Last 7 Days"));
        dataListRegWithin.add(new WebArd("15", "Last 15 Days"));
        dataListRegWithin.add(new WebArd("30", "Last 30 Days"));

        spRegisterWithIn = (Spinner) view.findViewById(R.id.SpinnerAdvSearchRegisteredWith);
        spLastLoginDate = (Spinner) view.findViewById(R.id.SpinnerAdvSearchLastLoginDate);
        spinnerAdapterRegisteredWithIn = new MySpinnerAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, dataListRegWithin);
        spinnerAdapterLastLogin = new MySpinnerAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, dataListRegWithin);


        Members members = defaultSelectionsObj;

        spRegisterWithIn.setAdapter(spinnerAdapterRegisteredWithIn);

        spLastLoginDate.setAdapter(spinnerAdapterLastLogin);


        Gson gsonc;
        GsonBuilder gsonBuilderc = new GsonBuilder();
        gsonc = gsonBuilderc.create();
        Type listType = new TypeToken<List<WebArd>>() {
        }.getType();

        if (jsonArraySearch != null && members != null) {
            try {
                List<WebArd> dataList0 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(17).toString(), listType);
                viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList0, LinearLayoutAdvSearchProfileCreatedFor, "profilefor");

                List<WebArd> dataList1 = (List<WebArd>) gsonc.fromJson(jsonArraySearch.getJSONArray(14).toString(), listType);
                viewGenerator.generateDynamicCheckBoxesLLWithTag(dataList1, LinearLayoutAdvSearchZodiacSign, "zodiacsign");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    private void setListeners() {
        spRegisterWithIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                WebArd selectedItem = (WebArd) spRegisterWithIn.getSelectedItem();
                defaultSelectionsObj.setRegistration_within_id(Long.parseLong(selectedItem.getId()));

                updateDot();
                //    Log.e("set_reg_within_id",""+defaultSelectionsObj.getRegistration_within_id());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spLastLoginDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                WebArd selectedItem = (WebArd) spLastLoginDate.getSelectedItem();

                defaultSelectionsObj.setLast_login_date_id(Long.parseLong(selectedItem.getId()));
                //  Log.e("set_reg_within_id",""+defaultSelectionsObj.getLast_login_date_id());
                updateDot();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        {
            int childcount = LinearLayoutAdvSearchProfileCreatedFor.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchProfileCreatedFor.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }

        {
            int childcount = LinearLayoutAdvSearchZodiacSign.getChildCount();
            for (int i = 0; i < childcount; i++) {
                View sv = LinearLayoutAdvSearchZodiacSign.getChildAt(i);
                if (sv instanceof CheckBox) {
                    ((CheckBox) sv).setOnCheckedChangeListener(this);
                }
            }
        }
        checkboxItemAdvSearchBasicsPicOnly.setOnCheckedChangeListener(this);
        checkboxItemAdvSearchBasicsOpenToPub.setOnCheckedChangeListener(this);

    }

    private void setSelection() {

        if (defaultSelectionsObj != null) {

            // Log.e("Choice profile ower ids", "" + defaultSelectionsObj.getChoice_profile_owner_Ids());
            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchProfileCreatedFor, defaultSelectionsObj.getChoice_profile_owner_Ids());
            viewGenerator.selectCheckBoxes(LinearLayoutAdvSearchZodiacSign, defaultSelectionsObj.getChoice_zodiac_sign_ids());


            if (defaultSelectionsObj.getPictureonly() == 1) {
                checkboxItemAdvSearchBasicsPicOnly.setChecked(true);
            } else {
                checkboxItemAdvSearchBasicsPicOnly.setChecked(false);
            }

            if (defaultSelectionsObj.getOpentopublic() == 1) {
                checkboxItemAdvSearchBasicsOpenToPub.setChecked(true);
            } else {
                checkboxItemAdvSearchBasicsOpenToPub.setChecked(false);
            }


            if (defaultSelectionsObj.getRegistration_within_id() == 0) {
                spRegisterWithIn.setSelection(0);
            } else if (defaultSelectionsObj.getRegistration_within_id() == 7) {
                spRegisterWithIn.setSelection(1);
            } else if (defaultSelectionsObj.getRegistration_within_id() == 15) {
                spRegisterWithIn.setSelection(2);
            } else if (defaultSelectionsObj.getRegistration_within_id() == 30) {
                spRegisterWithIn.setSelection(3);
            } else {
                spRegisterWithIn.setSelection(0);
            }

            if (defaultSelectionsObj.getLast_login_date_id() == 0) {
                spLastLoginDate.setSelection(0);
            } else if (defaultSelectionsObj.getLast_login_date_id() == 7) {
                spLastLoginDate.setSelection(1);
            } else if (defaultSelectionsObj.getLast_login_date_id() == 15) {
                spLastLoginDate.setSelection(2);
            } else if (defaultSelectionsObj.getLast_login_date_id() == 30) {
                spLastLoginDate.setSelection(3);
            } else {

                spLastLoginDate.setSelection(0);
            }


        }


    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //   Log.e("pic only", isChecked + "" + buttonView.getTag());

        if (buttonView == checkboxItemAdvSearchBasicsPicOnly) {
            if (isChecked) {

                defaultSelectionsObj.setPictureonly(1);

                /// Log.e("pic only", ListViewAdvSearchFragment.defaultSelectionsObj.getPictureonly() + "");
            } else {
                defaultSelectionsObj.setPictureonly(0);
                //  Log.e("pic only", ListViewAdvSearchFragment.defaultSelectionsObj.getPictureonly() + "");
            }
        }

        if (buttonView == checkboxItemAdvSearchBasicsOpenToPub) {
            if (isChecked) {
                defaultSelectionsObj.setOpentopublic(1);
            } else {
                defaultSelectionsObj.setOpentopublic(0);
            }
        }
        if (buttonView.getTag() != null) {
            if (buttonView.getTag().equals("profilefor")) {
                defaultSelectionsObj.setChoice_profile_owner_Ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchProfileCreatedFor));

            }
            if (buttonView.getTag().equals("zodiacsign")) {
                defaultSelectionsObj.setChoice_zodiac_sign_ids(viewGenerator.getSelectionFromCheckbox(LinearLayoutAdvSearchZodiacSign));
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

/*    public void resetSearch() {
        Log.e("Dome","Dddddddddddddddddddddddddddddd");
     }*/
}
