package com.chicsol.alfalah.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import com.chicsol.alfalah.activities.registration.RegistrationActivity;
import com.chicsol.alfalah.activities.searchyourbestmatch.SearchYourBestMatchActivity;
import com.chicsol.alfalah.modal.Members;
import com.chicsol.alfalah.other.ConnectionDetector;
import com.chicsol.alfalah.other.MarryMax;
import com.chicsol.alfalah.other.UserSessionManager;
import com.chicsol.alfalah.preferences.SharedPreferenceManager;
import com.chicsol.alfalah.urls.Urls;
import com.chicsol.alfalah.utils.ConnectCheck;
import com.chicsol.alfalah.utils.Constants;
import com.chicsol.alfalah.utils.MySingleton;
import com.chicsol.alfalah.utils.functions;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.fabric.sdk.android.Fabric;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityLogin extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private EditText etPassword;
    private AutoCompleteTextView etEmail;
    private Button btLogin, btRegister, btSearchBMatch, forgetpassword;
    private TextView tvDont;
    private ConnectionDetector connectionDetector;
    private UserSessionManager session;

    private ProgressDialog pDialog;

    private FirebaseAnalytics mFirebaseAnalytics;
    private ArrayAdapter acAdapter;


    ArrayList emailSuggestionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_login);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        UserSessionManager session = new UserSessionManager(
                getApplicationContext());
        boolean isUserLoggegIn = session.isUserLoggedIn();


        new functions().getAccessToken();

        if (isUserLoggegIn == true) {
            Members member = SharedPreferenceManager.getUserObject(getApplication());
            checkUserStatus(member);
        }


        initialize();
        setListeners();


    }


    private void initialize() {
        pDialog = new ProgressDialog(ActivityLogin.this);
        pDialog.setMessage("Loading...");

        //  Typeface tf = Typeface.createFromAsset(getAssets(), Constants.font_centurygothic);
        etEmail = (AutoCompleteTextView) findViewById(R.id.EditTextEmailLogin);
        // etEmail.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);


        etPassword = (EditText) findViewById(R.id.EditTextPasswordLogin);

        tvDont = (TextView) findViewById(R.id.textViewDont);

        btLogin = (Button) findViewById(R.id.email_sign_in_button);
        btRegister = (Button) findViewById(R.id.ButtonRegister);
        btSearchBMatch = (Button) findViewById(R.id.buttonSearchYourBestMatch);

        //  etEmail.setTransformationMethod(new PasswordTransformationMethod());
        etPassword.setTransformationMethod(new PasswordTransformationMethod());
        connectionDetector = new ConnectionDetector(getApplicationContext());

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        session = new UserSessionManager(getApplicationContext());

        forgetpassword = (Button) findViewById(R.id.forgetpassword);

        emailSuggestionList = new ArrayList();


        if (SharedPreferenceManager.getEmailSuggestionList(getApplicationContext()) == null) {
            Log.e("array null", "null");
        } else {
            emailSuggestionList.addAll(SharedPreferenceManager.getEmailSuggestionList(getApplicationContext()));
            // mcList.add("zeeshan40@gmail.com");
            // mcList.add("zeeshan33@gmail.com");
            if (emailSuggestionList.size() > 0) {
                acAdapter = new ArrayAdapter<String>(ActivityLogin.this, android.R.layout.simple_list_item_1, emailSuggestionList);
                etEmail.setAdapter(acAdapter);
            }
        }
    }

    private void setListeners() {
       /* settingsIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, Settings.class));
            }
        });
*/

        etEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    etPassword.setFocusable(true);
                }
                return false;
            }
        });
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    getUserCredentils();
                }
                return false;
            }
        });


        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectCheck.isConnected(findViewById(android.R.id.content))) {
                    Intent intent = new Intent(ActivityLogin.this, ActivityForgetPassword.class);
                    startActivity(intent);
                }
            }
        });


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getUserCredentils();

            }
        });


        btRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ConnectCheck.isConnected(findViewById(android.R.id.content))) {
                    Intent intent = new Intent(ActivityLogin.this, RegistrationActivity.class);
                    intent.putExtra("updateData", false);
                    startActivity(intent);
                }

            }
        });


        btSearchBMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectCheck.isConnected(findViewById(android.R.id.content))) {
                    Intent in = new Intent(ActivityLogin.this, SearchYourBestMatchActivity.class);
                    startActivity(in);
                }
            }
        });

    }


    private void getUserCredentils() {

        View focusView = null;
        // Reset errors.
        etEmail.setError(null);
        etPassword.setError(null);
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Please Enter Email");
            focusView = etEmail;

            focusView.requestFocus();
        } else if (!isEmailValid(email)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            focusView = etEmail;

            focusView.requestFocus();
        }
        // Check for a valid password, if the user entered one.
        else if (TextUtils.isEmpty(password)) {
            etPassword.setError("Please Enter Password");
            focusView = etPassword;

            focusView.requestFocus();
        } else if (!isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;

            focusView.requestFocus();
        } else {

            if (connectionDetector.isConnectingToInternet()) {
                LoginUser(email, password);

            }
                    /*if (ConnectivityReceiver.isConnected()) {
                        LoginUser(email, password);

                    }*/
            else {

                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });

                // Changing message text color

                snackbar.setActionTextColor(Color.RED);

                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);

                snackbar.show();

            }



                    /*  showProgress(true);
                    mAuthTask = new UserLoginTask(email, password);
                    mAuthTask.execute((Void) null);*/
        }
    }


    private void LoginUser(final String email, final String password) {


        pDialog.show();
        //   RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());

        JSONObject params = new JSONObject();
        try {
            params.put("email", email);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("Log params ", Urls.LoginUrl + "   " + params);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                Urls.LoginUrl, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("res", response.toString());
                        try {


                            if (response.get("status").equals("success")) {


                                if (emailSuggestionList.size() > 0) {
                                    if (!emailSuggestionList.contains(email)) {

                                        emailSuggestionList.add(email);
                                        SharedPreferenceManager.setEmailSuggestionList(getApplicationContext(), emailSuggestionList);
                                    }
                                } else {
                                    emailSuggestionList.add(email);
                                    SharedPreferenceManager.setEmailSuggestionList(getApplicationContext(), emailSuggestionList);

                                }


                                SharedPreferenceManager.setUserObject(getApplicationContext(), response.getJSONObject("data"));
                                session.createUserLoginSession(response.getJSONObject("data").get("alias").toString(), response.getJSONObject("data").getString("path") + "", response.getJSONObject("data").getInt("member_status") + "");

                                //  Log.d("Member id", response.getLong("memberid") + "");
                                //  Log.d("Alias", response.get("alias").toString());
                                //   Log.d("member type", response.getInt("member_status") + "");
                                Members member = SharedPreferenceManager.getUserObject(getApplication());

                                member.setPassword(password);

                                SharedPreferenceManager.setUserObject(getApplicationContext(), member);


                                checkUserStatus(member);
                                //   Log.e("Reggggggggggg type",member.getRegistration_within_id() + "");

                            } else {

                                Toast.makeText(ActivityLogin.this, "Invalid Email or Password !", Toast.LENGTH_SHORT).show();
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

                VolleyLog.e("res err", "Error: " + error.getMessage());
                Toast.makeText(ActivityLogin.this, "Invalid Email or Password !", Toast.LENGTH_SHORT).show();

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

    private void checkUserStatus(Members member) {

        MarryMax marryMax = new MarryMax(ActivityLogin.this);

        boolean acheck = marryMax.checkUserStatusLogin(member);
        if (acheck) {
            Intent intent = new Intent(ActivityLogin.this, DashboarMainActivityWithBottomNav.class);
            startActivity(intent);
            finish();
        }

    /*    if (member.getRegistration_within_id() != 5 && member.getMember_status() == 0) {
            if (member.getRegistration_within_id() == -1) {
                Intent intent = new Intent(ActivityLogin.this, RegisterGeographicActivity.class);
                startActivity(intent);
                finish();
                //  HttpContext.Current.Response.Redirect("~/1_Geographic", false);
            } else if (member.getRegistration_within_id() == 0) {
                Intent intent = new Intent(ActivityLogin.this, RegisterAppearanceActivity.class);
                startActivity(intent);
                finish();
                // HttpContext.Current.Response.Redirect("~/2_Appearance", false);
            } else if (member.getRegistration_within_id() == 1) {
                Intent intent = new Intent(ActivityLogin.this, RegisterLifeStyleActivity1.class);
                startActivity(intent);
                finish();
                //   HttpContext.Current.Response.Redirect("~/3_LifeStyle", false);
            } else if (member.getRegistration_within_id() == 2) {
                Intent intent = new Intent(ActivityLogin.this, RegisterLifeStyleActivity2.class);
                startActivity(intent);
                finish();
                //  HttpContext.Current.Response.Redirect("~/3_LifeStyle2", false);
            } else if (member.getRegistration_within_id() == 3) {
                Intent intent = new Intent(ActivityLogin.this, RegisterInterest.class);
                startActivity(intent);
                finish();
                // HttpContext.Current.Response.Redirect("~/4_Interest", false);
            } else if (member.getRegistration_within_id() == 4) {
                Intent intent = new Intent(ActivityLogin.this, RegisterPersonalityActivity.class);
                startActivity(intent);
                finish();
                //  HttpContext.Current.Response.Redirect("~/5_Personality", false);
            }


        } else {
            Intent intent = new Intent(ActivityLogin.this, DashboarMainActivityWithBottomNav.class);
            startActivity(intent);
            finish();


        }*/


    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return (password.length() >= 6 && password.length() <= 15);
    }


    private boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

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

    public Date fromDotNetTicks(long ticks) {
        // Rebase to Jan 1st 1970, the Unix epoch
        ticks -= 621355968000000000L;
        long millis = ticks / 10000;
        return new Date(millis);
    }

}
