package com.chicsol.alfalah.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.chicsol.alfalah.R;
import com.chicsol.alfalah.other.ConnectionDetector;
import com.chicsol.alfalah.urls.Urls;
import com.chicsol.alfalah.utils.ConnectCheck;
import com.chicsol.alfalah.utils.Constants;
import com.chicsol.alfalah.utils.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by muneeb on 6/1/17.
 */

public class ActivityForgetPassword extends AppCompatActivity {

    Button buttonHomePage, ButtonSubmitForgetPassword;
    EditText etEmail;
    private ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);

        initialize();
        setListeners();
    }

    private void initialize() {

        buttonHomePage = (Button) findViewById(R.id.buttonHomePage);
        ButtonSubmitForgetPassword = (Button) findViewById(R.id.ButtonSubmitForgetPassword);
        etEmail = (EditText) findViewById(R.id.EditTextForgetEmail);
        connectionDetector = new ConnectionDetector(getApplicationContext());
    }

    private void setListeners() {
        ButtonSubmitForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectCheck.isConnected(findViewById(android.R.id.content))) {

                    CheckData();

                }
            }
        });


        etEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //etEmail.setFocusable(true);
                    CheckData();
                }
                return false;
            }
        });


        buttonHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void CheckData(){

        View focusView = null;

        // Reset errors.
        etEmail.setError(null);

        String email = etEmail.getText().toString();


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Please Enter Email");
            focusView = etEmail;

            focusView.requestFocus();
        } else if (!isEmailValid(email)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            focusView = etEmail;

            focusView.requestFocus();
        } else {


            if (connectionDetector.isConnectingToInternet()) {



                LoginUser(email);

            }
        }


    }




    private void LoginUser(String email) {

        final ProgressDialog pDialog = new ProgressDialog(ActivityForgetPassword.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        JSONObject params = new JSONObject();
        try {
            params.put("email", email);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                Urls.sendPassCode, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //  Log.d("res", response.toString());
                        try {
                            int responseid = response.getInt("id");
                            if (responseid == 1) {

                                Toast.makeText(getApplicationContext(), "Please check your email. We have sent you an email that will allow you to reset your password.", Toast.LENGTH_LONG).show();
                                finish();
                            } else {

                                Toast.makeText(getApplicationContext(), "We couldn't find a MarryMax account with the email address you entered. Please try again or contact MarryMax support.", Toast.LENGTH_LONG).show();

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


                //VolleyLog.e("res err", "Error: " +networkResponse);
                Toast.makeText(ActivityForgetPassword.this, "Incorrect Email or Password !", Toast.LENGTH_SHORT).show();

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
        MySingleton.getInstance(this).addToRequestQueue(jsonObjReq);

    }

    private boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }
}
