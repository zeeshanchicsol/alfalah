package com.chicsol.alfalah.activities.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.chicsol.alfalah.R;
import com.chicsol.alfalah.modal.Members;
import com.chicsol.alfalah.modal.WebArd;
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

/**
 * Created by Redz on 10/21/2016.
 */


public class RegisterInterest extends BaseRegistrationActivity {
    private Button bt_register_free, bt_back;
    private GridLayout gridLayout;
    private List<WebArd> interestDataList;
    private boolean updateData = true;
    private Members members_obj;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_interest);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initialize();

        GetInterestData();
        setListeners();

    }

    public void initialize() {


        interestDataList = new ArrayList<>();


        gridLayout = (GridLayout) findViewById(R.id.GridlayoutInterest);

        bt_register_free = (Button) findViewById(R.id.buttonContinueStep2);

        bt_back = (Button) findViewById(R.id.buttonBack);


        fabGeographic.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorRegistrationStepComplete));
        fabAppearance.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorRegistrationStepComplete));
        fabLifestyle.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorRegistrationStepComplete));
        fabInterest.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorRegistrationStepOnGoing));


        tvGeogrphic.setTextColor(getResources().getColor(R.color.colorRegistrationStepComplete));
        tvAppearance.setTextColor(getResources().getColor(R.color.colorRegistrationStepComplete));
        tvLifestyle.setTextColor(getResources().getColor(R.color.colorRegistrationStepComplete));
        tvInterest.setTextColor(getResources().getColor(R.color.colorRegistrationStepOnGoing));
    }

    public void setListeners() {
        bt_register_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGenerator vg = new ViewGenerator(RegisterInterest.this);


                Log.e("selecccccc", "44" + vg.getSelectionFromCheckbox(gridLayout));
                if (vg.getSelectionFromCheckbox(gridLayout) != "") {


                    //
                    String interest_ids = vg.getSelectionFromCheckbox(gridLayout);

                    JSONObject params = new JSONObject();
                    try {


                        params.put("interest_ids", interest_ids);
                        params.put("member_status", SharedPreferenceManager.getUserObject(getApplicationContext()).get_member_status());

                        params.put("path", SharedPreferenceManager.getUserObject(getApplicationContext()).get_path());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (ConnectCheck.isConnected(findViewById(android.R.id.content))) {
                        updateInterest(params);
                    }
                } else {
                    //  Toast.makeText(RegisterInterest.this, "Please select Interests", Toast.LENGTH_SHORT).show();

                    Snackbar.make(v, "Please select Interests", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();

                }

            }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterInterest.this, RegisterLifeStyleActivity2.class);
                startActivity(intent);
                finish();
            }
        });

        if (marryMax.getUpdateCheck(getApplicationContext())) {

            bt_register_free.setText("Update");
        }

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

       *//*     case R.id.action_aboutus:
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
                return true;*//*

            //noinspection SimplifiableIfStatement
 *//*       if (id == R.id.action_aboutus) {

     *//**//* Intent intent = new Intent(Main.this, activity_change_password.class);
            startActivity(intent);*//**//*
            return true;
        }
*//*
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    private void selectFormData(Members members_obj) {

        ViewGenerator viewGenerator = new ViewGenerator(RegisterInterest.this);


        viewGenerator.selectCheckRadioFromGridLayout(gridLayout, members_obj.get_interest_ids());
    }


    private void GetInterestData() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Urls.regGetInterestUrl + SharedPreferenceManager.getUserObject(getApplicationContext()).get_path(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("res mainnnnnnnnnnn", response + "");
                        try {

                            pDialog.dismiss();
                            JSONArray jsonArrayInterest = response.getJSONArray("interest");


                            Gson gsonc;

                            GsonBuilder gsonBuilderc = new GsonBuilder();

                            gsonc = gsonBuilderc.create();
                            Type listType = new TypeToken<List<WebArd>>() {
                            }.getType();


                            interestDataList = (List<WebArd>) gsonc.fromJson(jsonArrayInterest.toString(), listType);


                            //=================================parsing object
                            Gson gson;
                            JSONArray jsonGrography = response.getJSONArray("interests");
                            GsonBuilder gsonBuilder = new GsonBuilder();

                            gson = gsonBuilder.create();
                            //   Log.e("Aliaaaaaaaasss", jsonGrography.get(0).toString());
                            members_obj = gson.fromJson(jsonGrography.get(0).toString(), Members.class);
                            // Log.e("Aliaaaaaaaasss", members_obj.get_country_id() + "");


                        } catch (JSONException e) {
                            pDialog.dismiss();
                            e.printStackTrace();
                        }

                        //==============DATA SETTING==============================================

                        ViewGenerator viewGenerator = new ViewGenerator(RegisterInterest.this);

                        Log.e("Perssssssssss", members_obj.get_interest_ids() + "h");
                        if (members_obj.get_interest_ids().equals("")) {
                            updateData = false;

                        } else {
                            updateData = true;

                        }

                        if (updateData) {

                            Point size = new Point();
                            getWindowManager().getDefaultDisplay().getSize(size);
                            viewGenerator.generateCheckBoxesInGridLayout(interestDataList, gridLayout, size.x - 30);

                            selectFormData(members_obj);

                        } else {


                            Point size = new Point();
                            getWindowManager().getDefaultDisplay().getSize(size);
                            viewGenerator.generateCheckBoxesInGridLayout(interestDataList, gridLayout, size.x - 30);
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


        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(this).addToRequestQueue(jsonObjReq);

    }

    private void updateInterest(JSONObject params) {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();


        Log.e("Params", "" + params);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                Urls.updateInterestUrl, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("re  update appearance", response + "");
                        try {
                            int responseid = response.getInt("id");

                            if (responseid == 1) {


                                if (!marryMax.getUpdateCheck(getApplicationContext())) {

                                    Intent in = new Intent(RegisterInterest.this, RegisterPersonalityActivity.class);
                                    startActivity(in);
                                } else {

                                    Toast.makeText(RegisterInterest.this, "Updated", Toast.LENGTH_SHORT).show();
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


}
