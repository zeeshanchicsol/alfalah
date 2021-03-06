package com.chicsol.alfalah.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.chicsol.alfalah.R;
import com.chicsol.alfalah.modal.Members;
import com.chicsol.alfalah.preferences.SharedPreferenceManager;
import com.chicsol.alfalah.urls.Urls;
import com.chicsol.alfalah.utils.Constants;
import com.chicsol.alfalah.utils.MySingleton;
import com.chicsol.alfalah.widgets.faTextView;
import com.chicsol.alfalah.widgets.mRadioButton;
import com.chicsol.alfalah.widgets.mTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by zeedr on 24/10/2016.
 */

public class dialogDeclineInterest extends DialogFragment {
    String interested_id, image_view, phone_view, my_id;
    mRadioButton cbDenyInterest, cbDenyInterestBlockMember;
    mTextView tvDesc;
    String userpath, blocked_member = "0", alias;
    private onCompleteListener mCompleteListener;

    // private ListView lv_mycontacts;
    public static dialogDeclineInterest newInstance(Members member, String userpath) {

        dialogDeclineInterest frag = new dialogDeclineInterest();
        Bundle args = new Bundle();

        args.putString("name", String.valueOf(member.getInterested_id()));

        //    args.putString("param", String.valueOf(member.getPhone_view()));
        //   args.putString("my_id", String.valueOf(member.getMy_id()));
        args.putString("alias", member.getAlias());
        args.putString("userpath", userpath);


        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle mArgs = getArguments();
        interested_id = mArgs.getString("name");

        //  desc = mArgs.getString("desc");
        // param = mArgs.getString("param");
        alias = mArgs.getString("alias");
        userpath = mArgs.getString("userpath");


        // Log.e("name Fragment", name);


    }
/*    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_profilefor, null))


          *//*      // Add action buttons
                .setPositiveButton("Submut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialogDosDonts.this.getDialog().cancel();
                    }
                })*//*;


        return builder.create();

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.dialog_decline, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        cbDenyInterest = (mRadioButton) rootView.findViewById(R.id.CheckBoxDeclineInterestAllowPhone);
        cbDenyInterestBlockMember = (mRadioButton) rootView.findViewById(R.id.CheckBoxDeclineInterestAllowPics);
        tvDesc = (mTextView) rootView.findViewById(R.id.TextViewDialogDeclineInterestDetails);


        String txt = "<font color='#9a0606'>" + alias + "</font>";
        tvDesc.setText(Html.fromHtml("Yes, I am not interested in" + txt.toUpperCase()));









    /*    lv_mycontacts=(ListView)rootView.findViewById(R.id.ListViewMySavedList);
        List<String> dataList=new ArrayList<>();
        dataList.add("asdsadsa");
        dataList.add("1212321");
        MyContactsListAdapter myContactsListAdapter=new MyContactsListAdapter(getActivity(),R.layout.item_list_my_saved_list,dataList);
        lv_mycontacts.setAdapter(myContactsListAdapter);*/

   /*     Bundle mArgs = getArguments();
        String myValue = mArgs.getString("jsondata");
        try {
            JSONArray jsonArray = new JSONArray(myValue);
            JSONArray dosDataList = jsonArray.getJSONArray(0);
            JSONArray dontsDataList = jsonArray.getJSONArray(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/





     /*   RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.RadioGroupProfileFor);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //  radioGroup.ad
        for (int i = 0; i < DataList.size(); i++) {
            mRadioButton mradiobutton = new mRadioButton(getContext());
            mradiobutton.setText(DataList.get(i));
            mradiobutton.setLayoutParams(layoutParams);

            radioGroup.addView(mradiobutton);
        }*/

        /*//do's
        for (int i=0;i<jsonArray1.length();i++){
            JSONObject jsonObject= jsonArray1.getJSONObject(i);
            Log.e("value",""+jsonObject.get("name").toString());
        }

        //dont's
        for (int i=0;i<jsonArray2.length();i++){
            JSONObject jsonObject= jsonArray2.getJSONObject(i);
            Log.e("value 2",""+jsonObject.get("name").toString());
        }*/


        Button mOkButton = (Button) rootView.findViewById(R.id.mButtonExpressWithDrawInterest);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {



                JSONObject params = new JSONObject();
                try {

                    params.put("request_response_id", "4");

                    if (cbDenyInterest.isChecked()) {
                        blocked_member = "0";

                    }
                    if (cbDenyInterestBlockMember.isChecked()) {
                        blocked_member = "1";

                    }
                    params.put("param", "0");
                    params.put("desc", "0");
                    params.put("blocked_member", blocked_member);
                    params.put("request_id", interested_id);
                    params.put("type", "4");
                    params.put("userpath", userpath);
                    params.put("path", SharedPreferenceManager.getUserObject(getContext()).getPath());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("params", "" + params);

                query(params);


            }
        });

        faTextView cancelButton = (faTextView) rootView.findViewById(R.id.dismissBtnGeoInfo);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                //Toast.makeText(getActivity().getApplicationContext(), "clcieck", Toast.LENGTH_SHORT).show();
                dialogDeclineInterest.this.getDialog().cancel();
            }
        });


        return rootView;
    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {


            if (getTargetFragment() != null) {
                mCompleteListener = (onCompleteListener) getTargetFragment();
            } else {
                mCompleteListener = (onCompleteListener) activity;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement OnCompleteListener");
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void query(JSONObject params) {

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                Urls.responseInterest, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e("re  update interest", response + "");
                        dialogDeclineInterest.this.getDialog().cancel();
                        try {
                            int responseid = response.getInt("id");


                            if (responseid == 1) {


                            }


                        } catch (JSONException e) {
                            pDialog.dismiss();
                            dialogDeclineInterest.this.getDialog().cancel();
                            e.printStackTrace();
                        }

                        dialogDeclineInterest.this.getDialog().cancel();
                        mCompleteListener.onComplete("");
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
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjReq);

    }

    public static interface onCompleteListener {
        public abstract void onComplete(String s);
    }
}


