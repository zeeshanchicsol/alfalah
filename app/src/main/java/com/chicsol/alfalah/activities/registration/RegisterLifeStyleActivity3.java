package com.chicsol.alfalah.activities.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.chicsol.alfalah.R;
import com.chicsol.alfalah.adapters.MySpinnerAdapter;
import com.chicsol.alfalah.dialogs.dialogMultiChoice;
import com.chicsol.alfalah.modal.Members;
import com.chicsol.alfalah.modal.WebArd;
import com.chicsol.alfalah.other.MarryMax;
import com.chicsol.alfalah.preferences.SharedPreferenceManager;
import com.chicsol.alfalah.urls.Urls;
import com.chicsol.alfalah.utils.ConnectCheck;
import com.chicsol.alfalah.utils.Constants;
import com.chicsol.alfalah.utils.MySingleton;
import com.chicsol.alfalah.utils.ViewGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegisterLifeStyleActivity3 extends BaseRegistrationActivity implements dialogMultiChoice.onMultiChoiceSaveListener {
    private Button bt_register_free, bt_back;
    private LinearLayout llcbViewBeard, llcbViewSalah;
    private RadioGroup rgBeard, rgSalah;

    private List<WebArd> beardDataList, salahDataList, marrytimeDataList, relocationDataList, wantchildrenDataList, physicallychallengedDataList, revertDataList, keephalalDataList, religiousDataList;

    private List<WebArd> myMarrytimeDataList, myRelocationDataList, myWantchildrenDataList, myPhysicallychallengedDataList, myRevertDataList, myKeephalalDataList, myReligiousDataList;

    private Members members_obj;


    private boolean updateData = true;
    private EditText etNoOfSisters, etNoOfBrothers;
    private ProgressDialog pDialog;


    private FloatingActionButton fabLifeStyle1, fabLifeStyle2, fabLifeStyle3;

    private Spinner spMyRelocation, spMyMarryTime, spMyPhysicallyChallenged, spMyWantChildren, spMyKeepHalal, spMyRevert, spMyReligious;
    private MySpinnerAdapter spAdapterMyRelocation, spAdapterMyMarryTime, spAdapterMyPhysicsallyChallenge, spAdapterMyWantChildren, spAdapterMyKeepHalal, spAdapterMyRevert, spAdapterMyReligious;

    private TextView tvMcMyChoiceRelocation, tvMcMyChoiceMarrytime, tvMcMyChoiceWantChildren, tvMcMyChoicePhysicallyChallenged, tvMcMyChoiceRevert, tvMcMyChoiceKeepHalal, tvMcMyChoiceReligious;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_lifestyle_step3);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        bt_back = (Button) findViewById(R.id.buttonBack);


        initialize();

        GetLifeStyleData();
        setListeners();


    }

    private void initialize() {

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");

        rgBeard = (RadioGroup) findViewById(R.id.RadioGroupBeard);
        llcbViewBeard = (LinearLayout) findViewById(R.id.LinearLayoutMyChoiceBeard);

        rgSalah = (RadioGroup) findViewById(R.id.RadioGroupSalah);
        llcbViewSalah = (LinearLayout) findViewById(R.id.LinearLayoutMyChoiceSalah);


        fabLifeStyle1 = (FloatingActionButton) findViewById(R.id.fabLifeStyle1);
        fabLifeStyle2 = (FloatingActionButton) findViewById(R.id.fabLifeStyle2);
        fabLifeStyle3 = (FloatingActionButton) findViewById(R.id.fabLifeStyle3);


        tvMcMyChoiceRelocation = (TextView) findViewById(R.id.MutliChoiceMyChoiceRelocation);
        tvMcMyChoiceMarrytime = (TextView) findViewById(R.id.MutliChoiceMyChoiceMarrytime);
        tvMcMyChoiceWantChildren = (TextView) findViewById(R.id.MutliChoiceMyChoiceWantChildren);
        tvMcMyChoicePhysicallyChallenged = (TextView) findViewById(R.id.MutliChoiceMyChoicePhysicallyChallenged);
        tvMcMyChoiceRevert = (TextView) findViewById(R.id.MutliChoiceMyChoiceRevert);
        tvMcMyChoiceKeepHalal = (TextView) findViewById(R.id.MutliChoiceMyChoiceKeepHalal);
        tvMcMyChoiceReligious = (TextView) findViewById(R.id.MutliChoiceMyChoiceReligious);


        beardDataList = new ArrayList<>();
        salahDataList = new ArrayList<>();

        marrytimeDataList = new ArrayList<>();
        physicallychallengedDataList = new ArrayList<>();
        relocationDataList = new ArrayList<>();
        wantchildrenDataList = new ArrayList<>();
        revertDataList = new ArrayList<>();
        religiousDataList = new ArrayList<>();
        keephalalDataList = new ArrayList<>();

        myMarrytimeDataList = new ArrayList<>();
        myPhysicallychallengedDataList = new ArrayList<>();
        myRelocationDataList = new ArrayList<>();
        myWantchildrenDataList = new ArrayList<>();
        myRevertDataList = new ArrayList<>();
        myReligiousDataList = new ArrayList<>();
        myKeephalalDataList = new ArrayList<>();


/*        spMySiblingPosition = (Spinner) findViewById(R.id.SpinnerSiblingPosition);

        spAdapterMySiblingPosition = new MySpinnerAdapter(this,
                android.R.layout.simple_spinner_item, beardDataList);
        spAdapterMySiblingPosition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMySiblingPosition.setAdapter(spAdapterMySiblingPosition);*/

        etNoOfBrothers = (EditText) findViewById(R.id.EditTextnumberOfBrothers);
        etNoOfSisters = (EditText) findViewById(R.id.EditTextnumberOfSisters);

        bt_register_free = (Button) findViewById(R.id.buttonContinueStep2);

        fabGeographic.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorRegistrationStepComplete));
        fabAppearance.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorRegistrationStepComplete));
        fabLifestyle.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorRegistrationStepOnGoing));


        tvGeogrphic.setTextColor(getResources().getColor(R.color.colorRegistrationStepComplete));
        tvAppearance.setTextColor(getResources().getColor(R.color.colorRegistrationStepComplete));
        tvLifestyle.setTextColor(getResources().getColor(R.color.colorRegistrationStepOnGoing));


        spMyRelocation = (Spinner) findViewById(R.id.spinnerMyRelocation);
        spMyMarryTime = (Spinner) findViewById(R.id.spinnerMyMarrytime);
        spMyWantChildren = (Spinner) findViewById(R.id.spinnerMyWantChildren);
        spMyPhysicallyChallenged = (Spinner) findViewById(R.id.spinnerMyPhysicallyChallenged);
        spMyRevert = (Spinner) findViewById(R.id.spinnerMyRevert);
        spMyReligious = (Spinner) findViewById(R.id.spinnerMyReligious);
        spMyKeepHalal = (Spinner) findViewById(R.id.spinnerMyKeepHalal);


        spAdapterMyRelocation = new MySpinnerAdapter(this,
                android.R.layout.simple_spinner_item, relocationDataList);
        spAdapterMyRelocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMyRelocation.setAdapter(spAdapterMyRelocation);


        spAdapterMyMarryTime = new MySpinnerAdapter(this,
                android.R.layout.simple_spinner_item, marrytimeDataList);
        spAdapterMyMarryTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMyMarryTime.setAdapter(spAdapterMyMarryTime);


        spAdapterMyWantChildren = new MySpinnerAdapter(this,
                android.R.layout.simple_spinner_item, wantchildrenDataList);
        spAdapterMyWantChildren.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMyWantChildren.setAdapter(spAdapterMyWantChildren);


        spAdapterMyPhysicsallyChallenge = new MySpinnerAdapter(this,
                android.R.layout.simple_spinner_item, myPhysicallychallengedDataList);
        spAdapterMyPhysicsallyChallenge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMyPhysicallyChallenged.setAdapter(spAdapterMyPhysicsallyChallenge);


        spAdapterMyRevert = new MySpinnerAdapter(this,
                android.R.layout.simple_spinner_item, revertDataList);
        spAdapterMyRevert.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMyRevert.setAdapter(spAdapterMyRevert);


        spAdapterMyKeepHalal = new MySpinnerAdapter(this,
                android.R.layout.simple_spinner_item, keephalalDataList);
        spAdapterMyKeepHalal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMyKeepHalal.setAdapter(spAdapterMyKeepHalal);


        spAdapterMyReligious = new MySpinnerAdapter(this,
                android.R.layout.simple_spinner_item, religiousDataList);
        spAdapterMyReligious.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMyReligious.setAdapter(spAdapterMyReligious);


        if (marryMax.getUpdateCheck(getApplicationContext())) {

            bt_register_free.setText("Update");
        }

    }

    private void setListeners() {
        tvMcMyChoiceRelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Gson gson = new Gson();
                dialogMultiChoice newFragment = dialogMultiChoice.newInstance(gson.toJson(relocationDataList), 1, "My Choice Relocation");
                newFragment.show(getSupportFragmentManager(), "dialog");


            }
        });

        tvMcMyChoiceMarrytime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Gson gson = new Gson();
                dialogMultiChoice newFragment = dialogMultiChoice.newInstance(gson.toJson(marrytimeDataList), 2, "My Choice Marrytime");
                newFragment.show(getSupportFragmentManager(), "dialog");


            }
        });


        tvMcMyChoiceWantChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Gson gson = new Gson();
                dialogMultiChoice newFragment = dialogMultiChoice.newInstance(gson.toJson(wantchildrenDataList), 3, "Want Children Choice");
                newFragment.show(getSupportFragmentManager(), "dialog");


            }
        });


        tvMcMyChoicePhysicallyChallenged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Gson gson = new Gson();
                dialogMultiChoice newFragment = dialogMultiChoice.newInstance(gson.toJson(physicallychallengedDataList), 4, "Want Physically Challenged Choice");
                newFragment.show(getSupportFragmentManager(), "dialog");


            }
        });


        tvMcMyChoiceRevert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Gson gson = new Gson();
                dialogMultiChoice newFragment = dialogMultiChoice.newInstance(gson.toJson(revertDataList), 5, "My Choice Revert");
                newFragment.show(getSupportFragmentManager(), "dialog");


            }
        });


        tvMcMyChoiceKeepHalal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Gson gson = new Gson();
                dialogMultiChoice newFragment = dialogMultiChoice.newInstance(gson.toJson(keephalalDataList), 6, "My Keep Halal");
                newFragment.show(getSupportFragmentManager(), "dialog");


            }
        });


        tvMcMyChoiceReligious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Gson gson = new Gson();
                dialogMultiChoice newFragment = dialogMultiChoice.newInstance(gson.toJson(religiousDataList), 7, "My Choice Religious");
                newFragment.show(getSupportFragmentManager(), "dialog");


            }
        });


        fabLifeStyle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class cls = RegisterLifeStyleActivity1.class;
                marryMax.getProfileProgress(cls, RegisterLifeStyleActivity3.this, getApplicationContext(), SharedPreferenceManager.getUserObject(getApplicationContext()));
            }
        });
        fabLifeStyle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class cls = RegisterLifeStyleActivity3.class;
                marryMax.getProfileProgress(cls, RegisterLifeStyleActivity3.this, getApplicationContext(), SharedPreferenceManager.getUserObject(getApplicationContext()));
            }
        });
        fabLifeStyle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class cls = RegisterLifeStyleActivity3.class;
                marryMax.getProfileProgress(cls, RegisterLifeStyleActivity3.this, getApplicationContext(), SharedPreferenceManager.getUserObject(getApplicationContext()));
            }
        });


        bt_register_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkFieldsSelection(v)) {

                    MarryMax max = new MarryMax(null);
                    ViewGenerator vg = new ViewGenerator(RegisterLifeStyleActivity3.this);


                    WebArd objRel = (WebArd) spMyRelocation.getSelectedItem();
                    String relocation_id = objRel.getId();

                    WebArd objMarry = (WebArd) spMyMarryTime.getSelectedItem();
                    String marrytime_id = objMarry.getId();

                    WebArd objWant = (WebArd) spMyWantChildren.getSelectedItem();
                    String want_children_id = objWant.getId();

                    WebArd objPhysically = (WebArd) spMyPhysicallyChallenged.getSelectedItem();
                    String physically_challenged_id = objPhysically.getId();

                    WebArd objRevert = (WebArd) spMyRevert.getSelectedItem();
                    String revert_id = objRevert.getId();

                    WebArd objKeep = (WebArd) spMyKeepHalal.getSelectedItem();
                    String religious_id = objKeep.getId();

                    WebArd objReligious = (WebArd) spMyReligious.getSelectedItem();
                    String keep_halal_id = objReligious.getId();


                    String beard_id = String.valueOf(rgBeard.getCheckedRadioButtonId());
                    String choice_beard_ids = vg.getSelectionFromCheckbox(llcbViewBeard);

                    String salah_id = String.valueOf(rgSalah.getCheckedRadioButtonId());
                    String choice_salah_ids = vg.getSelectionFromCheckbox(llcbViewSalah);

                    //==========================================================


                    StringBuilder choice_relocation_ids = new StringBuilder();
                    choice_relocation_ids = max.getSelectedIdsFromList(relocationDataList);

                    StringBuilder choice_marrytime_ids = new StringBuilder();
                    choice_marrytime_ids = max.getSelectedIdsFromList(marrytimeDataList);

                    StringBuilder choice_want_children_ids = new StringBuilder();
                    choice_want_children_ids = max.getSelectedIdsFromList(wantchildrenDataList);

                    StringBuilder choice_physically_challenged_ids = new StringBuilder();
                    choice_physically_challenged_ids = max.getSelectedIdsFromList(physicallychallengedDataList);

                    StringBuilder choice_revert_ids = new StringBuilder();
                    choice_revert_ids = max.getSelectedIdsFromList(revertDataList);

                    StringBuilder choice_keep_halal_ids = new StringBuilder();
                    choice_keep_halal_ids = max.getSelectedIdsFromList(keephalalDataList);

                    StringBuilder choice_religious_ids = new StringBuilder();
                    choice_religious_ids = max.getSelectedIdsFromList(religiousDataList);

                    JSONObject params = new JSONObject();
                    try {
                        params.put("beard_id", beard_id);
                        params.put("choice_beard_ids", choice_beard_ids);

                        params.put("salah_id", salah_id);
                        params.put("choice_salah_ids", choice_salah_ids);
                        ///================================================

                        params.put("relocation_id", relocation_id);
                        params.put("choice_relocation_ids", choice_relocation_ids.toString());

                        params.put("marrytime_id", marrytime_id);
                        params.put("choice_marrytime_ids", choice_marrytime_ids);

                        params.put("want_children_id", want_children_id);
                        params.put("choice_want_children_ids", choice_want_children_ids);


                        params.put("physically_challenged_id", physically_challenged_id);
                        params.put("choice_physically_challenged_ids", choice_physically_challenged_ids);

                        params.put("revert_id", revert_id);
                        params.put("choice_revert_ids", choice_revert_ids);


                        params.put("religious_id", religious_id);
                        params.put("choice_keep_halal_ids", choice_keep_halal_ids);

                        params.put("keep_halal_id", keep_halal_id);
                        params.put("choice_religious_ids", choice_religious_ids);


                        params.put("path", SharedPreferenceManager.getUserObject(getApplicationContext()).getPath());
                        Log.e("params", "" + params);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (ConnectCheck.isConnected(findViewById(android.R.id.content))) {
                        Log.e("Params" + Urls.updateLifestyleUrl3, "" + params);
                        updateLifestyle(params);

                    }
                }


            }
        });


        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent in = new Intent(RegisterLifeStyleActivity3.this, RegisterLifeStyleActivity1.class);
                startActivity(in);
                finish();
            }
        });

    }


    private void selectFormData(Members members_obj) {

        ViewGenerator viewGenerator = new ViewGenerator(RegisterLifeStyleActivity3.this);

        //    Log.e("getPhysically_challe  " + myPhysicallychallengedDataList.get(myPhysicallychallengedDataList.size() - 1).getName(), "" + members_obj.getPhysically_challenged_id());


        viewGenerator.selectSpinnerItemById(spMyRelocation, members_obj.getRelocation_id(), myRelocationDataList);
        viewGenerator.selectSpinnerItemById(spMyMarryTime, members_obj.getMarrytime_id(), myMarrytimeDataList);
        viewGenerator.selectSpinnerItemById(spMyWantChildren, members_obj.getWant_children_id(), myWantchildrenDataList);
        viewGenerator.selectSpinnerItemById(spMyPhysicallyChallenged, members_obj.getPhysically_challenged_id(), myPhysicallychallengedDataList);
        viewGenerator.selectSpinnerItemById(spMyRevert, members_obj.getRevert_id(), myRevertDataList);
        viewGenerator.selectSpinnerItemById(spMyReligious, members_obj.getReligious_id(), myReligiousDataList);
        viewGenerator.selectSpinnerItemById(spMyKeepHalal, members_obj.getKeep_halal_id(), myKeephalalDataList);

        viewGenerator.selectCheckRadio(rgBeard, (int) members_obj.getBeard_id(), llcbViewBeard, members_obj.getChoice_beard_ids());
        viewGenerator.selectCheckRadio(rgSalah, (int) members_obj.getSalah_id(), llcbViewSalah, members_obj.getChoice_salah_ids());

        // viewGenerator.selectCheckRadio(rgHijab, members_obj.getHijab_id(), llcbViewHijab, members_obj.getChoice_hijab_ids());


        Members member = SharedPreferenceManager.getUserObject(getApplicationContext());
        /*    if (member.getMember_status() >= 2 && member.getMember_status() < 7) {
         *//*  if (member.getMember_status() == 3 || member.getMember_status() == 4) {*//*


            viewGenerator.selectCheckRadioWithDisabledRadio(rgSmoke, members_obj.getSmoking_id(), llcbViewSmoke, members_obj.getChoice_smoking_ids());
            viewGenerator.selectCheckRadioWithDisabledRadio(rgDrink, members_obj.getDrink_id(), llcbViewDrink, members_obj.getChoice_drink_ids());
            viewGenerator.selectCheckRadioWithDisabledRadio(rgRaisedWhere, members_obj.getRaised_id(), llcbViewRaisedWhere, members_obj.getChoice_raised_ids());


        } else {

            viewGenerator.selectCheckRadio(rgSmoke, members_obj.getSmoking_id(), llcbViewSmoke, members_obj.getChoice_smoking_ids());
            viewGenerator.selectCheckRadio(rgDrink, members_obj.getDrink_id(), llcbViewDrink, members_obj.getChoice_drink_ids());
            viewGenerator.selectCheckRadio(rgRaisedWhere, members_obj.getRaised_id(), llcbViewRaisedWhere, members_obj.getChoice_raised_ids());


        }*/

      /*  etNoOfBrothers.setText(members_obj.getBrothers_count() + "");
        etNoOfSisters.setText(members_obj.getSisters_count() + "");*/


        selectChoices(members_obj.getChoice_relocation_ids(), relocationDataList, tvMcMyChoiceRelocation);
        selectChoices(members_obj.getChoice_marrytime_ids(), marrytimeDataList, tvMcMyChoiceMarrytime);
        selectChoices(members_obj.getChoice_want_children_ids(), wantchildrenDataList, tvMcMyChoiceWantChildren);
        selectChoices(members_obj.getChoice_physically_challenged_ids(), physicallychallengedDataList, tvMcMyChoicePhysicallyChallenged);
        selectChoices(members_obj.getChoice_revert_ids(), revertDataList, tvMcMyChoiceRevert);
        selectChoices(members_obj.getChoice_keep_halal_ids(), keephalalDataList, tvMcMyChoiceKeepHalal);
        selectChoices(members_obj.getChoice_religious_ids(), religiousDataList, tvMcMyChoiceReligious);


    }


    private void selectChoices(String choiceIds, List<WebArd> dataList, TextView tvChoice) {

        List<String> selectedIdsDataList = new ArrayList();
        {
            Log.e("choice Education", choiceIds + "===");
            String[] cids = choiceIds.split(",");
            Log.e("choices length", cids.length + "===");
            //multi choice selection

            for (int i = 0; i < cids.length; i++) {
                selectedIdsDataList.add((cids[i]));
            }


            if (!selectedIdsDataList.isEmpty()) {

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < selectedIdsDataList.size(); i++) {
                    int id = Integer.parseInt(selectedIdsDataList.get(i).toString());

                    for (int j = 0; j < dataList.size(); j++) {

                        if (Integer.parseInt(dataList.get(j).getId()) == id) {
                            dataList.get(j).setSelected(true);
                            stringBuilder.append(dataList.get(j).getName());
                            Log.e("choices name", dataList.get(j).getName() + "====");
                            if (i != selectedIdsDataList.size() - 1) {
                                stringBuilder.append(",");
                            }


                        }

                    }
                }

                Log.e("choices stringBuilder", stringBuilder.toString() + "===");
                tvChoice.setText(stringBuilder);


            }

        }

    }

    private boolean checkFieldsSelection(View v) {
        boolean ck = false;


        if (spMyRelocation.getSelectedItemId() == 0) {
            TextView errorText = (TextView) spMyRelocation.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(getResources().getColor(R.color.colorTextRed));//just to highlight that this is an error
            errorText.setText("Please select Language");

         /*   Snackbar.make(v, "Please select Education", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
*/
            ck = true;
        }
        if (spMyMarryTime.getSelectedItemId() == 0) {
            TextView errorText = (TextView) spMyMarryTime.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(getResources().getColor(R.color.colorTextRed));//just to highlight that this is an error
            errorText.setText("Please select Language");

         /*   Snackbar.make(v, "Please select Education", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
*/
            ck = true;
        }
        if (spMyWantChildren.getSelectedItemId() == 0) {
            TextView errorText = (TextView) spMyWantChildren.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(getResources().getColor(R.color.colorTextRed));//just to highlight that this is an error
            errorText.setText("Please select Language");

         /*   Snackbar.make(v, "Please select Education", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
*/
            ck = true;
        }
        if (spMyPhysicallyChallenged.getSelectedItemId() == 0) {
            TextView errorText = (TextView) spMyPhysicallyChallenged.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(getResources().getColor(R.color.colorTextRed));//just to highlight that this is an error
            errorText.setText("Please select Physically Challenged");

         /*   Snackbar.make(v, "Please select Education", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
*/
            ck = true;
        }
        if (spMyRevert.getSelectedItemId() == 0) {
            TextView errorText = (TextView) spMyRevert.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(getResources().getColor(R.color.colorTextRed));//just to highlight that this is an error
            errorText.setText("Please select Revert");

         /*   Snackbar.make(v, "Please select Education", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
*/
            ck = true;
        }
        if (spMyReligious.getSelectedItemId() == 0) {
            TextView errorText = (TextView) spMyReligious.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(getResources().getColor(R.color.colorTextRed));//just to highlight that this is an error
            errorText.setText("Please select Religious");

         /*   Snackbar.make(v, "Please select Education", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
*/
            ck = true;
        }
        if (spMyKeepHalal.getSelectedItemId() == 0) {
            TextView errorText = (TextView) spMyKeepHalal.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(getResources().getColor(R.color.colorTextRed));//just to highlight that this is an error
            errorText.setText("Please select Keep Halal");

         /*   Snackbar.make(v, "Please select Education", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
*/
            ck = true;
        }


        if (rgBeard.getCheckedRadioButtonId() == -1) {
            Snackbar.make(v, "Please select Beard", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            ck = true;
        } else if (rgSalah.getCheckedRadioButtonId() == -1) {
            Snackbar.make(v, "Please select Salah", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            ck = true;
        }







        /*else if (rgRaisedWhere.getCheckedRadioButtonId() == -1) {
            Snackbar.make(v, "Please select Raised Where", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            ck = true;
        } else if (rgHijab.getCheckedRadioButtonId() == -1) {
            Snackbar.make(v, "Please select  Hijab", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            ck = true;
        } else if (rgSmoke.getCheckedRadioButtonId() == -1) {
            Snackbar.make(v, "Please select Smoke", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            ck = true;
        } else if (rgDrink.getCheckedRadioButtonId() == -1) {
            Snackbar.make(v, "Please select Drink", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            ck = true;
        }*/
        return ck;
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registration_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item_image_slider clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //   NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/


    private void GetLifeStyleData() {
        Log.e("GetLifeStyleData par", "" + Urls.RegGetLifeStyle1Url3 + SharedPreferenceManager.getUserObject(getApplicationContext()).getPath());

        pDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Urls.RegGetLifeStyle1Url3 + SharedPreferenceManager.getUserObject(getApplicationContext()).getPath(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("res mainnnnnnnnnnn", response + "");
                        try {

                            JSONArray jsonArrayBeard = response.getJSONArray("beard");
                            JSONArray jsonArraySalah = response.getJSONArray("salah");

                            JSONArray jsonArrayRelocation = response.getJSONArray("relocation");
                            JSONArray jsonArrayMarrytime = response.getJSONArray("marrytime");
                            JSONArray jsonArrayWantchildren = response.getJSONArray("wantchildren");
                            JSONArray jsonArrayPhysicallychallenged = response.getJSONArray("physicallychallenged");
                            JSONArray jsonArrayRevert = response.getJSONArray("revert");
                            JSONArray jsonArrayKeephalal = response.getJSONArray("keephalal");

                            JSONArray jsonArrayReligious = response.getJSONArray("religious");


                            Gson gsonc;

                            GsonBuilder gsonBuilderc = new GsonBuilder();

                            gsonc = gsonBuilderc.create();
                            Type listType = new TypeToken<List<WebArd>>() {
                            }.getType();


                            beardDataList = (List<WebArd>) gsonc.fromJson(jsonArrayBeard.toString(), listType);
                            salahDataList = (List<WebArd>) gsonc.fromJson(jsonArraySalah.toString(), listType);

                            relocationDataList = (List<WebArd>) gsonc.fromJson(jsonArrayRelocation.toString(), listType);
                            marrytimeDataList = (List<WebArd>) gsonc.fromJson(jsonArrayMarrytime.toString(), listType);
                            wantchildrenDataList = (List<WebArd>) gsonc.fromJson(jsonArrayWantchildren.toString(), listType);
                            physicallychallengedDataList = (List<WebArd>) gsonc.fromJson(jsonArrayPhysicallychallenged.toString(), listType);
                            revertDataList = (List<WebArd>) gsonc.fromJson(jsonArrayRevert.toString(), listType);
                            keephalalDataList = (List<WebArd>) gsonc.fromJson(jsonArrayKeephalal.toString(), listType);
                            religiousDataList = (List<WebArd>) gsonc.fromJson(jsonArrayReligious.toString(), listType);


                            myRelocationDataList = (List<WebArd>) gsonc.fromJson(jsonArrayRelocation.toString(), listType);
                            myMarrytimeDataList = (List<WebArd>) gsonc.fromJson(jsonArrayMarrytime.toString(), listType);
                            myWantchildrenDataList = (List<WebArd>) gsonc.fromJson(jsonArrayWantchildren.toString(), listType);
                            myPhysicallychallengedDataList = (List<WebArd>) gsonc.fromJson(jsonArrayPhysicallychallenged.toString(), listType);
                            myRevertDataList = (List<WebArd>) gsonc.fromJson(jsonArrayRevert.toString(), listType);
                            myKeephalalDataList = (List<WebArd>) gsonc.fromJson(jsonArrayKeephalal.toString(), listType);
                            myReligiousDataList = (List<WebArd>) gsonc.fromJson(jsonArrayReligious.toString(), listType);


                            myRelocationDataList.add(0, new WebArd("-1", "Please select"));
                            myMarrytimeDataList.add(0, new WebArd("-1", "Please select"));
                            myWantchildrenDataList.add(0, new WebArd("-1", "Please select"));
                            myPhysicallychallengedDataList.add(0, new WebArd("-1", "Please select"));
                            myRevertDataList.add(0, new WebArd("-1", "Please select"));
                            myKeephalalDataList.add(0, new WebArd("-1", "Please select"));
                            myReligiousDataList.add(0, new WebArd("-1", "Please select"));


                            myRelocationDataList.remove(myRelocationDataList.size() - 1);
                            myMarrytimeDataList.remove(myMarrytimeDataList.size() - 1);
                            myWantchildrenDataList.remove(myWantchildrenDataList.size() - 1);
                            myPhysicallychallengedDataList.remove(myPhysicallychallengedDataList.size() - 1);
                            myRevertDataList.remove(myRevertDataList.size() - 1);
                            myKeephalalDataList.remove(myKeephalalDataList.size() - 1);
                            myReligiousDataList.remove(myReligiousDataList.size() - 1);


                            //   revertDataList.add(0, new WebArd("-1", "Please select"));

                            //   spAdapterMySiblingPosition.updateDataList(revertDataList);


                            spAdapterMyRelocation.updateDataList(myRelocationDataList);
                            spAdapterMyMarryTime.updateDataList(myMarrytimeDataList);
                            spAdapterMyWantChildren.updateDataList(myWantchildrenDataList);
                            spAdapterMyPhysicsallyChallenge.updateDataList(myPhysicallychallengedDataList);
                            spAdapterMyRevert.updateDataList(myRevertDataList);
                            spAdapterMyKeepHalal.updateDataList(myKeephalalDataList);
                            spAdapterMyReligious.updateDataList(myReligiousDataList);


                            //=================================parsing object
                            Gson gson;
                            JSONArray jsonGrography = response.getJSONArray("lifestyle3");
                            GsonBuilder gsonBuilder = new GsonBuilder();

                            gson = gsonBuilder.create();
                            //  Log.e("Aliaaaaaaaasss", jsonGrography.get(0).toString());
                            members_obj = gson.fromJson(jsonGrography.get(0).toString(), Members.class);
                            //  Log.e("Aliaaaaaaaasss", members_obj.getBeard_id() + "");


                        } catch (JSONException e) {
                            pDialog.dismiss();
                            e.printStackTrace();
                        }

                        //==============DATA SETTING==============================================

                        ViewGenerator viewGenerator = new ViewGenerator(RegisterLifeStyleActivity3.this);


                        Log.e("members_obj", "" + members_obj.getBeard_id());
                        if (members_obj.getBeard_id() == 0) {
                            updateData = false;

                        } else {
                            updateData = true;

                        }

                        if (updateData) {
                            viewGenerator.addDynamicCheckRdioButtons(beardDataList, rgBeard, llcbViewBeard);
                            viewGenerator.addDynamicCheckRdioButtons(salahDataList, rgSalah, llcbViewSalah);
                          /*  viewGenerator.addDynamicCheckRdioButtons(marrytimeDataList, rgRaisedWhere, llcbViewRaisedWhere);
                            viewGenerator.addDynamicCheckRdioButtons(relocationDataList, rgHijab, llcbViewHijab);
                            viewGenerator.addDynamicCheckRdioButtons(wantchildrenDataList, rgSmoke, llcbViewSmoke);
                            viewGenerator.addDynamicCheckRdioButtons(physicallychallengedDataList, rgDrink, llcbViewDrink);*/

                            selectFormData(members_obj);

                        } else {
                            viewGenerator.addDynamicCheckRdioButtons(beardDataList, rgBeard, llcbViewBeard);
                            viewGenerator.addDynamicCheckRdioButtons(salahDataList, rgSalah, llcbViewSalah);
                          /*  viewGenerator.addDynamicCheckRdioButtons(marrytimeDataList, rgRaisedWhere, llcbViewRaisedWhere);
                            viewGenerator.addDynamicCheckRdioButtons(relocationDataList, rgHijab, llcbViewHijab);
                            viewGenerator.addDynamicCheckRdioButtons(wantchildrenDataList, rgSmoke, llcbViewSmoke);
                            viewGenerator.addDynamicCheckRdioButtons(physicallychallengedDataList, rgDrink, llcbViewDrink);*/
                        }

                        pDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                VolleyLog.e("res err", "Error: " + error);
                // Toast.makeText(RegistrationActivity.this, "Incorrect Email or Password !", Toast.LENGTH_SHORT).show();

                pDialog.dismiss();
            }


        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Constants.getHashMap();
            }
        };




/*        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
        MySingleton.getInstance(this).addToRequestQueue(jsonObjReq);

    }


    private void updateLifestyle(JSONObject params) {


        pDialog.show();


        Log.e("Params" + Urls.updateLifestyleUrl3, "" + params);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                Urls.updateLifestyleUrl3, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("re  update appearance", response + "");
                        try {
                            int responseid = response.getInt("id");


                            if (responseid == 1) {

                                //updating status
                                Members member = SharedPreferenceManager.getUserObject(getApplication());


                                if (member.getMember_status() == 0) {
                                    member.setMember_status(1);
                                    SharedPreferenceManager.setUserObject(getApplicationContext(), member);
                                }


                                //auto updating status
                           /*     Members member = SharedPreferenceManager.getUserObject(getApplication());


                                if (member.getMember_status() == 0) {
                                    int status = response.getInt("name");
                                    member.setMember_status(status);
                                    SharedPreferenceManager.setUserObject(getApplicationContext(), member);
                                }
*/


                                if (!marryMax.getUpdateCheck(getApplicationContext())) {
                                    Intent in = new Intent(RegisterLifeStyleActivity3.this, RegisterInterest.class);
                                    startActivity(in);
                                    finish();

                                } else {

                                    Toast.makeText(RegisterLifeStyleActivity3.this, "Updated", Toast.LENGTH_SHORT).show();
                                }


                            }

                        } catch (JSONException e) {
                            pDialog.dismiss();
                            e.printStackTrace();
                        }
                        pDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                VolleyLog.e("res err", "Error: " + error);
                // Toast.makeText(RegistrationActivity.this, "Incorrect Email or Password !", Toast.LENGTH_SHORT).show();

                pDialog.dismiss();
            }


        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Constants.getHashMap();
            }
        };
// Adding request to request queue
        ///   rq.add(jsonObjReq);
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this).addToRequestQueue(jsonObjReq);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.cancel();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }


    @Override
    public void onMultiChoiceSave(List<WebArd> s, int which) {
        if (which == 1) {

            relocationDataList.clear();
            relocationDataList.addAll(s);


            MarryMax max = new MarryMax(null);

            tvMcMyChoiceRelocation.setText(max.getSelectedTextFromList(relocationDataList, "My Choice Relocation"));
            // Log.e("selected id is", max.getSelectedIdsFromList(educationDataList) + "");
        } else if (which == 2) {

            marrytimeDataList.clear();
            marrytimeDataList.addAll(s);


            MarryMax max = new MarryMax(null);

            tvMcMyChoiceMarrytime.setText(max.getSelectedTextFromList(marrytimeDataList, "My Choice Marrytime"));
            //  Log.e("selected id is", max.getSelectedIdsFromList(occupationDataList) + "");
        } else if (which == 3) {

            wantchildrenDataList.clear();
            wantchildrenDataList.addAll(s);


            MarryMax max = new MarryMax(null);

            tvMcMyChoiceWantChildren.setText(max.getSelectedTextFromList(wantchildrenDataList, "My Choice Want Children"));
            //  Log.e("selected id is", max.getSelectedIdsFromList(occupationDataList) + "");
        } else if (which == 4) {

            physicallychallengedDataList.clear();
            physicallychallengedDataList.addAll(s);


            MarryMax max = new MarryMax(null);

            tvMcMyChoicePhysicallyChallenged.setText(max.getSelectedTextFromList(physicallychallengedDataList, "My Choice Physically Challenged"));
            //  Log.e("selected id is", max.getSelectedIdsFromList(occupationDataList) + "");
        } else if (which == 5) {

            revertDataList.clear();
            revertDataList.addAll(s);


            MarryMax max = new MarryMax(null);

            tvMcMyChoiceRevert.setText(max.getSelectedTextFromList(revertDataList, "My Choice Revert"));
            //  Log.e("selected id is", max.getSelectedIdsFromList(occupationDataList) + "");
        } else if (which == 6) {

            keephalalDataList.clear();
            keephalalDataList.addAll(s);


            MarryMax max = new MarryMax(null);

            tvMcMyChoiceKeepHalal.setText(max.getSelectedTextFromList(keephalalDataList, "My Choice Keep Halal"));
            //  Log.e("selected id is", max.getSelectedIdsFromList(occupationDataList) + "");
        } else if (which == 7) {

            religiousDataList.clear();
            religiousDataList.addAll(s);


            MarryMax max = new MarryMax(null);

            tvMcMyChoiceReligious.setText(max.getSelectedTextFromList(religiousDataList, "My Choice Religious"));
            //  Log.e("selected id is", max.getSelectedIdsFromList(occupationDataList) + "");
        }
    }
}
