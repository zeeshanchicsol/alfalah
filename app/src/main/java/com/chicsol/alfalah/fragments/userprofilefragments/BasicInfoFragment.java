package com.chicsol.alfalah.fragments.userprofilefragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chicsol.alfalah.R;
import com.chicsol.alfalah.modal.Members;
import com.chicsol.alfalah.widgets.mTextView;
import com.crashlytics.android.Crashlytics;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;


public class BasicInfoFragment extends Fragment {
    private JSONArray interestJsonArray, describePersonalityJsonArray;
    private Members member, memberChoice;
    private mTextView tvDesc, tvMostThankful, tvWhatIdoFor, tvMyStrengths, tvABoutMyChoice, tvAge, tvHeight, tvPhysique, tvComplexion, tvEyeColor, tvHairColor, tvChoiceAge,
            tvChoiceHeight, tvChoicePhysique, tvChoiceComplexion, tvChoiceEyeColor, tvChoiceHairColor;
    private mTextView tvMyEducation, tvMyEducationField, tvGraduated, tvOccupation, tvEconomy, tvIncome, tvRaised, tvFamilyValues, tvHijab, tvLiving, tvRelocation, tvMarryTime, tvWantChildren, tvKeepHalal, tvReligious, tvMaritalStatus, tvChildren, tvChildrenDetail, tvEthnicity, tvReligiousSect, tvBrothers, tvSisters, tvSiblingPosiiton, tvSmoke, tvDrink,
            tvLanguage, tvRevert, tvPhysicallyChallenged, tvBeard, tvSalah, tvChoiceMyEducation, tvChoiceOccupation, tvChoiceEconomy, tvChoiceRaised, tvChoiceFamilyValues, tvChoiceHijab, tvChoiceLiving, tvChoiceMaritalStatus, tvChoiceChildren, tvChoiceEthnicity, tvChoiceReligiousSect, tvChoiceCountry, tvChoiceVisaStatus, tvChoiceSmoke, tvChoiceDrink;

    private TextView pref1, pref2, pref3, pref4;
 //   tvCastSurname
    private mTextView tvDescribePersonality;

    private mTextView tvCountryOfOrigin, tvCountryOfLiving, tvVisaStatus, tvChoiceCountryOfOrigin, tvChoiceCountryOfLiving;
    private FlexboxLayout flexboxLayoutInterest;
    private LinearLayout llMTO, llWIDFF, llMS, llAMC;
    public String jsona = "";


    public BasicInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        Log.e("Basic Info Fragment  ", getArguments().getString("json"));
        String json = getArguments().getString("json");

        try {
            JSONArray jsonArray = new JSONArray(json);

            JSONObject firstJsonObj = jsonArray.getJSONArray(0).getJSONObject(0);
            JSONObject secondJsonObj = jsonArray.getJSONArray(1).getJSONObject(0);

            interestJsonArray = jsonArray.getJSONArray(3);
            describePersonalityJsonArray = jsonArray.getJSONArray(2);

/*
            <com.chicsol.marrymax.widgets.mTextView
                    android:layout_margin="5dp"
            android:background="@drawable/border_dash_userprofile"
            android:padding="5dp"
            android:text="Movies/ Theator/ Television"
            android:textColor="@color/colorBlack" />*/


            Gson gson;
            GsonBuilder gsonBuilder = new GsonBuilder();

            gson = gsonBuilder.create();
            Type type = new TypeToken<Members>() {
            }.getType();
            member = (Members) gson.fromJson(firstJsonObj.toString(), type);
            memberChoice = (Members) gson.fromJson(secondJsonObj.toString(), type);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_userprpfile_basic_info, container, false);
        initilize(rootView);
        setListener();
        setValuee();
        setInterestValues();
        setPreferencesValues();
        setTvDescribePersonality();
        return rootView;
    }

    private void initilize(View view) {
        tvDesc = (mTextView) view.findViewById(R.id.TextViewUPDescription);
        tvMostThankful = (mTextView) view.findViewById(R.id.TextViewUPMostThankful);
        tvWhatIdoFor = (mTextView) view.findViewById(R.id.TextViewUPWhatIdo);
        tvMyStrengths = (mTextView) view.findViewById(R.id.TextViewUPMyStrengths);
        tvABoutMyChoice = (mTextView) view.findViewById(R.id.TextViewUPAboutMyChoice);


        tvCountryOfOrigin = (mTextView) view.findViewById(R.id.TextViewUPCountryofOrigin);
        tvCountryOfLiving = (mTextView) view.findViewById(R.id.TextViewUPCountryofLiving);
        tvVisaStatus = (mTextView) view.findViewById(R.id.TextViewUPVisaStatus);

        tvChoiceCountryOfOrigin = (mTextView) view.findViewById(R.id.TextViewUPChoiceCountryofOrigin);
        tvChoiceCountryOfLiving = (mTextView) view.findViewById(R.id.TextViewUPChoiceCountryofLiving);
        tvChoiceVisaStatus = (mTextView) view.findViewById(R.id.TextViewUPChoiceVisaStatus);


        tvAge = (mTextView) view.findViewById(R.id.TextViewUPAge);
        tvHeight = (mTextView) view.findViewById(R.id.TextViewUPHeight);
        tvPhysique = (mTextView) view.findViewById(R.id.TextViewUPPhysique);
        tvComplexion = (mTextView) view.findViewById(R.id.TextViewUPComplexion);

        tvEyeColor = (mTextView) view.findViewById(R.id.TextViewUPEyeColor);
        tvHairColor = (mTextView) view.findViewById(R.id.TextViewUPHairColor);


        tvChoiceAge = (mTextView) view.findViewById(R.id.TextViewUPChoiceAge);
        tvChoiceHeight = (mTextView) view.findViewById(R.id.TextViewUPChoiceHeight);
        tvChoiceHairColor = (mTextView) view.findViewById(R.id.TextViewUPChoiceHairColor);
        tvChoiceEyeColor = (mTextView) view.findViewById(R.id.TextViewUPChoiceEyeColor);
        tvChoicePhysique = (mTextView) view.findViewById(R.id.TextViewUPChoicePhysique);
        tvChoiceComplexion = (mTextView) view.findViewById(R.id.TextViewUPChoiceComplexion);


        tvMyEducation = (mTextView) view.findViewById(R.id.TextViewUPEducationDetail);
        tvMyEducationField = (mTextView) view.findViewById(R.id.TextViewUPEducationalField);
        tvGraduated = (mTextView) view.findViewById(R.id.TextViewUPGraduatedFrom);
        tvOccupation = (mTextView) view.findViewById(R.id.TextViewUPOccupationDetail);
        tvEconomy = (mTextView) view.findViewById(R.id.TextViewUPEconomy);
        //   tvIncome = (mTextView) view.findViewById(R.id.TextViewUPIncome);
      //  tvCastSurname = (mTextView) view.findViewById(R.id.TextViewUPCastSurname);


        tvRaised = (mTextView) view.findViewById(R.id.TextViewUPRaised);
        tvFamilyValues = (mTextView) view.findViewById(R.id.TextViewUPFamilyValues);
        tvHijab = (mTextView) view.findViewById(R.id.TextViewUPHijaab);
        tvLiving = (mTextView) view.findViewById(R.id.TextViewUPLivingArrangements);

        tvRelocation = (mTextView) view.findViewById(R.id.TextViewUPRelocationType);
        tvMarryTime = (mTextView) view.findViewById(R.id.TextViewUPMarrytimeType);
        tvWantChildren = (mTextView) view.findViewById(R.id.TextViewUPWantChildrenType);
        tvKeepHalal = (mTextView) view.findViewById(R.id.TextViewUPKeepHalalType);
        tvReligious = (mTextView) view.findViewById(R.id.TextViewUPReligiousType);


        tvMaritalStatus = (mTextView) view.findViewById(R.id.TextViewUPMaritalStatus);
        tvChildren = (mTextView) view.findViewById(R.id.TextViewUPChildren);
        // tvChildrenDetail = (mTextView) view.findViewById(R.id.TextViewUPChildrenDetail);
        tvEthnicity = (mTextView) view.findViewById(R.id.TextViewUPEthnicity);
        tvReligiousSect = (mTextView) view.findViewById(R.id.TextViewUPReligiousSect);
        tvBrothers = (mTextView) view.findViewById(R.id.TextViewUPBrothers);
        tvSisters = (mTextView) view.findViewById(R.id.TextViewUPSisters);
        tvSiblingPosiiton = (mTextView) view.findViewById(R.id.TextViewUPSiblingPosition);
        tvSmoke = (mTextView) view.findViewById(R.id.TextViewUPSmoke);
        tvDrink = (mTextView) view.findViewById(R.id.TextViewUPDrink);

        tvLanguage = (mTextView) view.findViewById(R.id.TextViewUPLanguage);
        tvRevert = (mTextView) view.findViewById(R.id.TextViewUPRevertType);
        tvPhysicallyChallenged = (mTextView) view.findViewById(R.id.TextViewUPPhysicallyChallengeType);
        tvBeard = (mTextView) view.findViewById(R.id.TextViewUPBeardType);
        tvSalah = (mTextView) view.findViewById(R.id.TextViewUPSalahType);


        //choice
        tvChoiceMyEducation = (mTextView) view.findViewById(R.id.TextViewUPChoiceEducationDetail);

        tvChoiceOccupation = (mTextView) view.findViewById(R.id.TextViewUPChoiceOccupationDetail);
        tvChoiceEconomy = (mTextView) view.findViewById(R.id.TextViewUPChoiceEconomy);
        tvChoiceRaised = (mTextView) view.findViewById(R.id.TextViewUPChoiceRaised);
        tvChoiceFamilyValues = (mTextView) view.findViewById(R.id.TextViewUPChoiceChoiceFamilyValues);
        tvChoiceHijab = (mTextView) view.findViewById(R.id.TextViewUPChoiceHijab);
        tvChoiceLiving = (mTextView) view.findViewById(R.id.TextViewUPChoiceLivingArrangements);
        tvChoiceMaritalStatus = (mTextView) view.findViewById(R.id.TextViewUPChoiceMaritalStatus);
        tvChoiceChildren = (mTextView) view.findViewById(R.id.TextViewUPChoiceChildren);
        tvChoiceEthnicity = (mTextView) view.findViewById(R.id.TextViewUPChoiceEthnicity);
        tvChoiceReligiousSect = (mTextView) view.findViewById(R.id.TextViewUPChoiceReligiousSect);

        tvChoiceCountry = (mTextView) view.findViewById(R.id.TextViewUPChoiceCountry);


        tvChoiceSmoke = (mTextView) view.findViewById(R.id.TextViewUPChoiceSmoke);
        tvChoiceDrink = (mTextView) view.findViewById(R.id.TextViewUPChoiceDrink);


        //==========Layouts

        flexboxLayoutInterest = (FlexboxLayout) view.findViewById(R.id.FlexboxLaoutInterests);

//==========
        pref1 = (TextView) view.findViewById(R.id.PrefChoice1);
        pref2 = (TextView) view.findViewById(R.id.PrefChoice2);
        pref3 = (TextView) view.findViewById(R.id.PrefChoice3);
        pref4 = (TextView) view.findViewById(R.id.PrefChoice4);
//==============
        tvDescribePersonality = (mTextView) view.findViewById(R.id.TextViewDescribePersonality);


        //=====================================
        llMS = (LinearLayout) view.findViewById(R.id.LinearLayoutUserProfileMS);
        llWIDFF = (LinearLayout) view.findViewById(R.id.LinearLayoutUserProfileWIDFF);
        llMTO = (LinearLayout) view.findViewById(R.id.LinearLayoutUserProfileMTO);
        llAMC = (LinearLayout) view.findViewById(R.id.LinearLayoutUserProfileAMC);

    }

    private void setListener() {

    }


    private void setTvDescribePersonality() {

        if (describePersonalityJsonArray != null) {

            StringBuilder stringBuilder = new StringBuilder();
            //  Log.e("describePersonali", describePersonalityJsonArray.toString()+"");
            for (int i = 0; i < describePersonalityJsonArray.length(); i++) {

                if (i != describePersonalityJsonArray.length() - 1) {
                    try {
                        stringBuilder.append(describePersonalityJsonArray.getJSONObject(i).get("type") + ","
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        stringBuilder.append(describePersonalityJsonArray.getJSONObject(i).get("type")
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            tvDescribePersonality.setText(stringBuilder.toString());

        }
    }


    private void setInterestValues() {
        try {


            for (int i = 0; i < interestJsonArray.length(); i++) {
                TextView mTextView = new TextView(getActivity());
                mTextView.setBackground(getResources().getDrawable(R.drawable.shape_chip_drawable));
                mTextView.setTextColor(getResources().getColor(R.color.colorBlack));
                FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(5, 5, 5, 5);

                mTextView.setLayoutParams(params);

                //mTextView.setPadding(5, 3, 5, 3);
                mTextView.setGravity(Gravity.CENTER);
                try {
                    mTextView.setText("  " + interestJsonArray.getJSONObject(i).get("type").toString() + "  ");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Crashlytics.logException(e);
                }
                flexboxLayoutInterest.addView(mTextView);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "There is some error. Please try again", Toast.LENGTH_SHORT).show();
        }

    }

    private void setPreferencesValues() {
        try {

            pref1.setText(" " + memberChoice.getPref1() + " ");
            pref2.setText(" " + memberChoice.getPref2() + " ");
            pref3.setText(" " + memberChoice.getPref3() + " ");
            pref4.setText(" " + memberChoice.getPref4() + " ");

        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    private void setValuee() {

        try {


            tvDesc.setText(Html.fromHtml(member.getOther_info()));
            Log.e("what i dooo", "==" + member.getFor_fun());
            Log.e("what i dooo", "==" + member.getFor_fun());
            if (member.getFor_fun() != null && member.getFor_fun() != "") {
                tvWhatIdoFor.setText(Html.fromHtml(member.getFor_fun()));
            } else {
                llWIDFF.setVisibility(View.GONE);

            }
            if (member.getGood_quality() != null && member.getGood_quality() != "") {
                tvMyStrengths.setText(Html.fromHtml(member.getGood_quality()));
            } else {
                llMS.setVisibility(View.GONE);

            }

            if (member.getMost_thankfull() != null && member.getMost_thankfull() != "") {
                tvMostThankful.setText(Html.fromHtml(member.getMost_thankfull()));
            } else {
                llMTO.setVisibility(View.GONE);

            }

            if (member.getAbout_my_choice() != null && member.getAbout_my_choice() != "") {
                tvABoutMyChoice.setText(Html.fromHtml(member.getAbout_my_choice()));
            } else {
                llAMC.setVisibility(View.GONE);

            }





            tvCountryOfOrigin.setText(member.getCountry_name());
            tvCountryOfLiving.setText( (member.getCity_name() == null ? "" : member.getCity_name()+",")+    (member.getState_name() == null ? "" : member.getState_name())+ (member.getOrigan_country_name() == null ? "" : ",")  + (member.getOrigan_country_name() == null ? "" : member.getOrigan_country_name()));


        /*    <%# Eval("city_name") %><%# Eval("city_name") != "" ? "," : "" %> <%# Eval("state_name") %><%# Eval("state_name") != "" ? "," : "" %> <%# Eval("origan_country_name") %>
      */      // origan_country_name
            tvVisaStatus.setText(member.getVisa_status_types());

            tvChoiceCountryOfLiving.setText(memberChoice.getChoice_country_names());

            tvChoiceCountryOfOrigin.setText(memberChoice.getChoice_origin_country_ids());
             Log.e("memberChoice", memberChoice.getChoice_country_names() +"" + memberChoice.getChoice_origin_country_ids());




            tvAge.setText(member.getAge());
            tvHeight.setText(member.getHeight_description());
            tvPhysique.setText(member.getBody_types());
            tvComplexion.setText(member.getComplexion_types());

            tvEyeColor.setText(member.getEye_color_types());
            tvHairColor.setText(member.getHair_color_types());

            //choice
            tvChoiceAge.setText(memberChoice.getChoice_age_from() + " Year To " + memberChoice.getChoice_age_upto() + " Years");

            tvChoicePhysique.setText(memberChoice.getChoice_body());
            tvChoiceComplexion.setText(memberChoice.getChoice_complexion());
            tvChoiceEyeColor.setText(memberChoice.getChoice_eye_color());
            tvChoiceHairColor.setText(memberChoice.getChoice_hair_color());
            tvChoiceHeight.setText(memberChoice.getChoice_height_from() + " To " + memberChoice.getChoice_height_to());


            tvMyEducation.setText(member.getEducation_types());
            tvMyEducationField.setText(member.getEducation_field_types());

            String in = "";
            if (!member.getAdmin_notes().equals("")) {

                in = " in " + member.getAdmin_notes();

            }
            tvGraduated.setText(member.getNotes() + in);
            tvOccupation.setText(member.getOccupation_types());
            tvEconomy.setText(member.getAbout_type());
            // tvIncome.setText(member.getIncome_level());
        //    tvCastSurname.setText(member.getCaste_name());


            tvRaised.setText(member.getRaised_types());
            tvFamilyValues.setText(member.getFamily_values_types());
            tvHijab.setText(member.getHijab_types());
            tvLiving.setText(member.getLiving_arrabgements_types());

            tvRelocation.setText(member.getRelocation_type());
            tvMarryTime.setText(member.getMarrytime_type());
            tvWantChildren.setText(member.getWant_children_type());
            tvKeepHalal.setText(member.getKeep_halal_type());
            tvReligious.setText(member.getReligious_type());


            tvMaritalStatus.setText(member.getMarital_status_types());
            tvChildren.setText(member.getChildren_types());
            //  tvChildrenDetail.setText(member.getChoice_children());
            tvEthnicity.setText(member.getEthnic_background_types());
            tvReligiousSect.setText(member.getReligious_sec_type());
            tvBrothers.setText(member.getBrothers_count());
            tvSisters.setText(member.getSisters_count());
            tvSiblingPosiiton.setText(member.getSibling_types());
            tvSmoke.setText(member.getSmoking_types());
            tvDrink.setText(member.getDrinks_types());

            tvLanguage.setText(member.getLanguage());
            tvRevert.setText(member.getRevert_type());
            tvPhysicallyChallenged.setText(member.getPhysically_challenged_type());
            tvBeard.setText(member.getBeard_type());
            tvSalah.setText(member.getSalah_type());


            tvChoiceMyEducation.setText(memberChoice.getChoice_education());

            tvChoiceOccupation.setText(memberChoice.getChoice_occupation());
            tvChoiceEconomy.setText(memberChoice.getChoice_economy_ids());
            tvChoiceRaised.setText(memberChoice.getChoice_raised());
            tvChoiceFamilyValues.setText(memberChoice.getChoice_family_values());
            tvChoiceHijab.setText(memberChoice.getChoice_hijab());
            tvChoiceLiving.setText(memberChoice.getChoice_living_arrangements());
            tvChoiceMaritalStatus.setText(memberChoice.getChoice_marital_status());
            tvChoiceChildren.setText(memberChoice.getChoice_children());
            tvChoiceEthnicity.setText(memberChoice.getChoice_ethnic_background());
            tvChoiceReligiousSect.setText(memberChoice.getChoice_religious_sec());

            tvChoiceCountry.setText(memberChoice.getChoice_country_names());
            tvChoiceVisaStatus.setText(memberChoice.getChoice_visa_status());
            tvChoiceSmoke.setText(memberChoice.getChoice_smoking());
            tvChoiceDrink.setText(memberChoice.getChoice_drinks());
        } catch (Exception e) {
            getActivity().finish();
        }
    }
}
