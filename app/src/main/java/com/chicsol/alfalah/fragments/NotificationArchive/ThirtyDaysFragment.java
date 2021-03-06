package com.chicsol.alfalah.fragments.NotificationArchive;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.chicsol.alfalah.R;
import com.chicsol.alfalah.activities.UserProfileActivityWithSlider;
import com.chicsol.alfalah.adapters.RecyclerViewAdapterNotificationArchive;
import com.chicsol.alfalah.modal.Members;
import com.chicsol.alfalah.modal.mCommunication;
import com.chicsol.alfalah.modal.mProperties;
import com.chicsol.alfalah.preferences.SharedPreferenceManager;
import com.chicsol.alfalah.urls.Urls;
import com.chicsol.alfalah.utils.ConnectCheck;
import com.chicsol.alfalah.utils.Constants;
import com.chicsol.alfalah.utils.MySingleton;
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
 * Created by Android on 12/5/2016.
 */


public class ThirtyDaysFragment extends Fragment implements RecyclerViewAdapterNotificationArchive.OnItemClickListener {
    RecyclerView recyclerView;
    private RecyclerViewAdapterNotificationArchive recyclerAdapter;
    private List<mCommunication> items;
    private ProgressBar pDialog;
    private LinearLayout llNone;
    AppCompatTextView profileCompletionCheck;

    private String Tag="ThirtyDaysFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_noti_seven_days_old, container, false);
        initilize(rootView);
        setListenders();
        setListenders();
        return rootView;
    }

    private void initilize(View view) {
        profileCompletionCheck = (AppCompatTextView) view.findViewById(R.id.AppCompatTextViewNotificationArchiveProfileCompleteText);

        items = new ArrayList<>();
        llNone = (LinearLayout) view.findViewById(R.id.LinearLayoutNotificationArchiveNone);
        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerViewNotificationMain);
        pDialog = (ProgressBar) view.findViewById(R.id.ProgressbarNotificationArchive);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerAdapter = new RecyclerViewAdapterNotificationArchive(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(ThirtyDaysFragment.this);
        if (ConnectCheck.isConnected(getActivity().findViewById(android.R.id.content))) {
        loadArchive();}
       // Log.e("Logg Status",""+SharedPreferenceManager.getUserObject(getContext()).getMember_status());


    }

    private void setListenders() {

      /*  ll_messagedetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), DashboardMessagesDetailActivity.class);
                startActivity(in);
            }
        });*/


    }


    private void loadArchive() {


        pDialog.setVisibility(View.VISIBLE);

        JSONObject params = new JSONObject();
        try {


            params.put("path", SharedPreferenceManager.getUserObject(getContext()).getPath());
            params.put("type", "30");

        } catch (JSONException e) {
            pDialog.setVisibility(View.GONE);
            e.printStackTrace();
        }
        // Log.e("Params", Urls.loadArchive + " ==  " + params);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                Urls.loadArchive, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                      Log.e("load notificaitons", response + "");


                        try {


                            JSONArray jsonArray = response.getJSONArray("data");
                            if (jsonArray.length() == 0
                                    ) {
                                // Log.e("load notificans size 0", "00");
                                recyclerView.setVisibility(View.GONE);
                                llNone.setVisibility(View.VISIBLE);
                                if (SharedPreferenceManager.getUserObject(getContext()).getMember_status() < 3) {
                                    profileCompletionCheck.setVisibility(View.VISIBLE);
                                } else {
                                    profileCompletionCheck.setVisibility(View.INVISIBLE);
                                }

                            } else {


                                Gson gsonc;
                                GsonBuilder gsonBuilderc = new GsonBuilder();
                                gsonc = gsonBuilderc.create();
                                Type listType = new TypeToken<List<mProperties>>() {
                                }.getType();

                                List<mProperties> dataList = (List<mProperties>) gsonc.fromJson(jsonArray.getJSONArray(0).toString(), listType);
                                if (dataList.size() > 0) {
                                    llNone.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);

                                    recyclerAdapter.addAll(dataList);

                                } else {
                                    recyclerView.setVisibility(View.GONE);
                                    llNone.setVisibility(View.VISIBLE);
                                     if (SharedPreferenceManager.getUserObject(getContext()).getMember_status() < 3) {
                                        profileCompletionCheck.setVisibility(View.VISIBLE);
                                    } else {
                                        profileCompletionCheck.setVisibility(View.INVISIBLE);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            pDialog.setVisibility(View.GONE);
                            llNone.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }


                        pDialog.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                VolleyLog.e("res err", "Error: " + error);
                // Toast.makeText(RegistrationActivity.this, "Incorrect Email or Password !", Toast.LENGTH_SHORT).show();

                pDialog.setVisibility(View.GONE);
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
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjReq,Tag);

    }


    @Override
    public void onItemClick(View view, mProperties mproperties) {

        if (!mproperties.getAlias().equals("MARRYMAX.COM")) {
            Intent intent = new Intent(getContext(), UserProfileActivityWithSlider.class);

            intent.putExtra("selectedposition", "-1");
            List<Members> memberDataList = new ArrayList<Members>();
            Members members = new Members();
            members.setUserpath(mproperties.getUserpath());
            memberDataList.add(members);
            SharedPreferenceManager.setMemberDataList(getContext(), new Gson().toJson(memberDataList).toString());
            startActivity(intent);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        MySingleton.getInstance(getContext()).cancelPendingRequests(Tag);

    }

}
