package com.chicsol.alfalah.fragments.inbox.interests;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.chicsol.alfalah.R;
import com.chicsol.alfalah.adapters.RecyclerViewAdapterMyInterestsRequests;
import com.chicsol.alfalah.dialogs.dialogDeclineInterestInbox;
import com.chicsol.alfalah.dialogs.dialogReplyOnAcceptInterestInbox;
import com.chicsol.alfalah.dialogs.dialogRequestPhone;
import com.chicsol.alfalah.dialogs.dialogShowInterest;
import com.chicsol.alfalah.modal.mComCount;
import com.chicsol.alfalah.modal.mCommunication;
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
 * Created by Android on 11/3/2016.
 */

public class DashboardInterestsFragment extends Fragment implements RecyclerViewAdapterMyInterestsRequests.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, dialogShowInterest.onCompleteListener, dialogRequestPhone.onCompleteListener, dialogReplyOnAcceptInterestInbox.onCompleteListener, dialogDeclineInterestInbox.onCompleteListener, RecyclerViewAdapterMyInterestsRequests.OnUpdateListener {
    public static int result = 0;
    // LinearLayout LinearLayoutMMMatchesNotFound;
    //private Button bt_loadmore;
    private
    RecyclerView recyclerView;
    private int lastPage = 1;
    private List<mCommunication> membersDataList;
    private int totalPages = 0;
    // private int currentPage=1;
    private Fragment fragment;
    private RecyclerViewAdapterMyInterestsRequests
            recyclerAdapter;
    private ProgressBar pDialog;
    private SwipeRefreshLayout swipeRefresh;
    private String params;
    private static boolean m_iAmVisible;
    Context activity;
    boolean withdrawCheck;

    private ImageView ivEmptyState;
    private LinearLayout llEmptyState;
    private LinearLayout llEmptySubItems;

    private TextView tvInterestRequestEmptyState;

    private String type = "";
    private Context context;

    private int getInterested_members_count = 0;

    ViewGenerator viewGenerator;

    private String Tag = "DashboardInterestsFragment";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    /*   @Override
       public void fragmentBecameVisible() {

           // You can do your animation here because we are visible! (make sure onViewCreated has been called too and the Layout has been laid. Source for another question but you get the idea.
       }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard_interests, container, false);

        initilize(rootView);


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();


        if (ConnectCheck.isConnected(getActivity().findViewById(android.R.id.content))) {
            getCommunicationCount();
            JSONObject params = new JSONObject();
            try {
                params.put("path", SharedPreferenceManager.getUserObject(getContext()).getPath());
                params.put("page_no", 1);
                params.put("type", type);

                loadData(params.toString(), false);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }


    private void initilize(View view) {


        if (getArguments() != null) {
            type = getArguments().getString("type");
        }

        if (getArguments() != null) {
            withdrawCheck = getArguments().getBoolean("withdrawcheck");
        }


        fragment = DashboardInterestsFragment.this;

        viewGenerator = new ViewGenerator(context);


        ivEmptyState = (ImageView) view.findViewById(R.id.ImageViewInterestRequestEmptyState);
        ivEmptyState.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_empty_state_new_interest_received));
        tvInterestRequestEmptyState = (TextView) view.findViewById(R.id.TextViewInterestRequestEmptyState);
        //  tvInterestRequestEmptyState.setText("You have 0 interests");

        llEmptyState = (LinearLayout) view.findViewById(R.id.LinearLayoutInterestsRequestsEmptyState);

        llEmptySubItems = (LinearLayout) view.findViewById(R.id.LinearLayoutEmptySubItems);

        membersDataList = new ArrayList<>();
        pDialog = (ProgressBar) view.findViewById(R.id.ProgressbarMyInterests);
        //   pDialog.setMessage("Loading...");
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshMyInterests);
        // LinearLayoutMMMatchesNotFound = (LinearLayout) view.findViewById(R.id.LinearLayoutMMMatchesNotFound);
        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerViewInboxListInterests);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerAdapter = new RecyclerViewAdapterMyInterestsRequests(getContext(), getFragmentManager(), this, fragment, true, this, withdrawCheck);


        recyclerAdapter.setLinearLayoutManager(mLayoutManager);

        recyclerAdapter.setRecyclerView(recyclerView);

        recyclerView.setAdapter(recyclerAdapter);
        swipeRefresh.setOnRefreshListener(this);


    }


    @Override
    public void onLoadMore() {

        if (lastPage != totalPages && lastPage < totalPages) {
            lastPage = lastPage + 1;

            Log.e("", "las p: " + lastPage + " Total Pages:" + totalPages);


            JSONObject params = new JSONObject();
            try {
                params.put("path", SharedPreferenceManager.getUserObject(getContext()).getPath());
                params.put("page_no", lastPage);
                params.put("type", type);

                // loadData(params.toString(), false);
                loadMoreData(params.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


    @Override
    public void onRefresh() {

        if (ConnectCheck.isConnected(getActivity().findViewById(android.R.id.content))) {

            getCommunicationCount();

            JSONObject params = new JSONObject();
            try {
                params.put("path", SharedPreferenceManager.getUserObject(getContext()).getPath());
                params.put("page_no", 1);
                params.put("type", type);

                loadData(params.toString(), false);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    /*private void loadData() {
        itemList.clear();
        for (int i = 1; i <= 20; i++) {
            itemList.add(new Item("Item " + i));
        }
        mAdapter.addAll(itemList);
    }*/


    @Override
    public void onComplete(String s) {
        onRefresh();
    }


    private void loadData(String paramsString, final boolean refresh) {
        getCommunicationCount();

        pDialog.setVisibility(View.VISIBLE);
        //   RequestQueue rq = Volley.newRequestQueue(getActivity().getApplicationContext());

        JSONObject params = null;
        try {
            params = new JSONObject(paramsString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(type + "Params search" + " " + Urls.interestRequestType, "" + params);

        //  Log.e("Params search" + " " + Urls.searchProfiles, "");
        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                Urls.interestRequestType, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("re interestRequestpe " + type, response + "");
                        try {
                            llEmptySubItems.removeAllViews();
                            StringBuilder htmlDescriptionText = new StringBuilder();


                            JSONArray jsonArray = response.getJSONArray("data");

                            /* if (jsonArray.length() > 1) {*/

                            Log.e("Length", jsonArray.getJSONArray(0).length() + "");
                            JSONArray jsonarrayData = jsonArray.getJSONArray(0);
                            JSONArray jsonarrayTotalPages = jsonArray.getJSONArray(1);
                            Log.e("json array data", jsonarrayTotalPages + "");

                            //   if (jsonarrayData.length() == 0) {

                            recyclerAdapter.clear();
                            swipeRefresh.setRefreshing(false);


                            Gson gson;
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            gson = gsonBuilder.create();


                            Type memberlis = new TypeToken<List<mCommunication>>() {
                            }.getType();
                            membersDataList = (List<mCommunication>) gson.fromJson(jsonarrayData.toString(), memberlis);


                            Type membert = new TypeToken<mCommunication>() {
                            }.getType();

                            mCommunication memberC = (mCommunication) gson.fromJson(jsonarrayTotalPages.getJSONObject(0).toString(), membert);
                            //memberC.setInterested_members_count(getInterested_members_count);

                            if (type.equals("interestsent")) {
                                if (SharedPreferenceManager.getUserObject(context).getMember_status() < 3 || SharedPreferenceManager.getUserObject(context).getMember_status() > 4) {

                                    recyclerView.setVisibility(View.GONE);
                                    llEmptyState.setVisibility(View.VISIBLE);
                                    tvInterestRequestEmptyState.setText("You haven’t shown interest in anyone yet!. " +
                                            "\n Find your matches and start communicating.");

                                } else {

                                    if (SharedPreferenceManager.getUserObject(context).getMember_status() == 3 || SharedPreferenceManager.getUserObject(context).getMember_status() == 4) {
                                        if (membersDataList.size() == 0) {
                                            htmlDescriptionText.append(" You haven’t shown interest in anyone yet! \n");

                                            if (SharedPreferenceManager.getUserObject(context).getMember_status() == 3) {

                                                htmlDescriptionText.append(" Subscribe now to enjoy following benefits.");



                                                viewGenerator.generateTextViewWithIcon(llEmptySubItems, "Priority Profile Listing.");
                                                viewGenerator.generateTextViewWithIcon(llEmptySubItems, "Maximum interaction & quick connect with other members.");
                                                viewGenerator.generateTextViewWithIcon(llEmptySubItems, "More Privacy options.");
                                                viewGenerator.generateTextViewWithIcon(llEmptySubItems, "Personalized service from MarryMax when need. ");



                                            }

                                            recyclerView.setVisibility(View.GONE);
                                            llEmptyState.setVisibility(View.VISIBLE);
                                            swipeRefresh.setRefreshing(false);


                                            tvInterestRequestEmptyState.setText(htmlDescriptionText.toString());


                                        } else if (membersDataList.size() > 0) {   //   records with load more
                                            //    LinearLayoutMMMatchesNotFound.setVisibility(View.GONE);
                                            recyclerAdapter.addAll(membersDataList);
                                            recyclerView.setVisibility(View.VISIBLE);
                                            llEmptyState.setVisibility(View.GONE);

                                            recyclerAdapter.addAll(membersDataList);
                                            Gson gsont;
                                            GsonBuilder gsonBuildert = new GsonBuilder();
                                            gsont = gsonBuildert.create();
                                            Type membersa = new TypeToken<mCommunication>() {
                                            }.getType();
                                            mCommunication memberTotalPages = (mCommunication) gson.fromJson(jsonarrayTotalPages.getJSONObject(0).toString(), membersa);


                                            totalPages = (int) memberTotalPages.getInterested_members_count();
                                            lastPage = 1;
                                                 Log.e("total pages interests", "" + totalPages);
                                            swipeRefresh.setRefreshing(false);
                                        }
                                    }


                                }
                            }

                            //interest receieved
                            else {


                                if (SharedPreferenceManager.getUserObject(context).getMember_status() < 3 || SharedPreferenceManager.getUserObject(context).getMember_status() > 4) {

                                    if (memberC.getInterested_members_count() == 0) {
                                        recyclerView.setVisibility(View.GONE);
                                        llEmptyState.setVisibility(View.VISIBLE);
                                        tvInterestRequestEmptyState.setText("There are " + getInterested_members_count + " interests." +
                                                "\nPlease complete & verify your profile to view the interests," +
                                                "\n shown in you.");
                                    } else if (memberC.getInterested_members_count() > 0) {
                                        recyclerView.setVisibility(View.GONE);
                                        llEmptyState.setVisibility(View.VISIBLE);
                                        tvInterestRequestEmptyState.setText("There are " + getInterested_members_count + " interests, waiting for you to respond." +
                                                "\nPlease complete & verify your profile to view the interests," +
                                                "\n shown in you.");

                                    }

                                } else {

                                    if (SharedPreferenceManager.getUserObject(context).getMember_status() == 3 || SharedPreferenceManager.getUserObject(context).getMember_status() == 4) {
                                        if (membersDataList.size() == 0) {
                                            htmlDescriptionText.append(" You have  " + getInterested_members_count + "  interests.\n");

                                            if (SharedPreferenceManager.getUserObject(context).getMember_status() == 3) {
                                                //    htmlDescriptionText.append("&#8226; \n");
                                                htmlDescriptionText.append(" Subscribe now to enjoy following benefits ");



                                               /* htmlDescriptionText.append(" Priority Profile Listing. \n");
                                                htmlDescriptionText.append(" Maximum interaction & quick connect with other members.\n");
                                                htmlDescriptionText.append(" More Privacy options.\n");
                                                htmlDescriptionText.append(" Personalized service from MarryMax when need.\n");
                                           */

                                                viewGenerator.generateTextViewWithIcon(llEmptySubItems, "Priority Profile Listing.");
                                                viewGenerator.generateTextViewWithIcon(llEmptySubItems, "Maximum interaction & quick connect with other members.");
                                                viewGenerator.generateTextViewWithIcon(llEmptySubItems, "More Privacy options.");
                                                viewGenerator.generateTextViewWithIcon(llEmptySubItems, "Personalized service from MarryMax when need.");

                                                //     htmlDescriptionText.append(" Find your matches and start communicating.\n");


                                            }
                                            recyclerView.setVisibility(View.GONE);
                                            llEmptyState.setVisibility(View.VISIBLE);
                                            swipeRefresh.setRefreshing(false);


                                            tvInterestRequestEmptyState.setText(htmlDescriptionText.toString());


                                        } else if (membersDataList.size() > 0) {
                                            // records with load more

                                            //    LinearLayoutMMMatchesNotFound.setVisibility(View.GONE);

                                            recyclerView.setVisibility(View.VISIBLE);
                                            llEmptyState.setVisibility(View.GONE);

                                            Log.e("membersDataList size", membersDataList.size() + "");

                                            recyclerAdapter.addAll(membersDataList);
                                            Gson gsont;
                                            GsonBuilder gsonBuildert = new GsonBuilder();
                                            gsont = gsonBuildert.create();
                                            Type membersa = new TypeToken<mCommunication>() {
                                            }.getType();
                                            mCommunication memberTotalPages = (mCommunication) gson.fromJson(jsonarrayTotalPages.getJSONObject(0).toString(), membersa);


                                            totalPages = (int) memberTotalPages.getInterested_members_count();
                                            lastPage = 1;
                                            //      Log.e("total pages", "" + totalPages);
                                            swipeRefresh.setRefreshing(false);


                                        }
                                    }


                                }


                            }


                          /*      } else {


                                    Gson gson;
                                    GsonBuilder gsonBuilder = new GsonBuilder();
                                    gson = gsonBuilder.create();
                                    Type member = new TypeToken<List<mCommunication>>() {
                                    }.getType();
                                    membersDataList = (List<mCommunication>) gson.fromJson(jsonarrayData.toString(), member);
                                    Log.e("membersDataList", "" + membersDataList.size());
                                    if (membersDataList.size() > 0) {
                                    } else {
                                        recyclerAdapter.clear();
                                        swipeRefresh.setRefreshing(false);
                                        //LinearLayoutMMMatchesNotFound.setVisibility(View.VISIBLE);
                                    }
                                }*/

                           /* } else {
                                recyclerAdapter.clear();
                                //no data
                                swipeRefresh.setRefreshing(false);
                                //  LinearLayoutMMMatchesNotFound.setVisibility(View.VISIBLE);
                            }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (!refresh) {
                            // pDialog.dismiss();
                            pDialog.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                VolleyLog.e("res err", "Error: " + error);
                if (!refresh) {
                    //pDialog.dismiss();
                    pDialog.setVisibility(View.GONE);
                }
                // LinearLayoutMMMatchesNotFound.setVisibility(View.VISIBLE);
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

    private void loadMoreData(String paramsString) {
        recyclerAdapter.setProgressMore(true);
        JSONObject params = null;
        try {
            params = new JSONObject(paramsString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
         Log.e("Params loadM request" + " " + Urls.interestRequestType, "" + params);

        //  Log.e("Params search" + " " + Urls.searchProfiles, "");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                Urls.interestRequestType, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //  Log.e("re  update appearance", response + "");
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            if (jsonArray.length() > 1) {

                                Log.e("Length", jsonArray.getJSONArray(0).length() + "");
                                JSONArray jsonarrayData = jsonArray.getJSONArray(0);
                                JSONArray jsonarrayTotalPages = jsonArray.getJSONArray(1);

                                Gson gson;
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                gson = gsonBuilder.create();
                                Type member = new TypeToken<List<mCommunication>>() {
                                }.getType();
                                recyclerAdapter.setProgressMore(false);
                                // membersDataList.clear();
                                membersDataList = (List<mCommunication>) gson.fromJson(jsonarrayData.toString(), member);

                                // Log.e("Length 56", membersDataList.size() + "  ");
                                recyclerAdapter.addItemMore(membersDataList);
                                recyclerAdapter.setMoreLoading(false);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        recyclerAdapter.setMoreLoading(false);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                VolleyLog.e("res err", "Error: " + error);
                // Toast.makeText(RegistrationActivity.this, "Incorrect Email or Password !", Toast.LENGTH_SHORT).show();

                recyclerAdapter.setMoreLoading(false);

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
    public void onUpdate(String msg) {
        if (ConnectCheck.isConnected(getActivity().findViewById(android.R.id.content))) {

            JSONObject params = new JSONObject();
            try {
                params.put("path", SharedPreferenceManager.getUserObject(getContext()).getPath());
                params.put("page_no", 1);
                params.put("type", type);

                loadData(params.toString(), false);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    private void getCommunicationCount() {
       /* final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();*/
        Log.e("url", Urls.getCommunicationCount + SharedPreferenceManager.getUserObject(context).getPath());
        JsonArrayRequest req = new JsonArrayRequest(Urls.getCommunicationCount + SharedPreferenceManager.getUserObject(context).getPath(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("getSavedList ", response.toString() + "  ==   ");
                        Gson gsonc;
                        GsonBuilder gsonBuilderc = new GsonBuilder();
                        gsonc = gsonBuilderc.create();
                        Type listType = new TypeToken<mComCount>() {
                        }.getType();
                        try {


                            mComCount comCount = (mComCount) gsonc.fromJson(response.getJSONArray(0).getJSONObject(0).toString(), listType);

                            Log.e("ressssss", comCount.getNew_interests_count() + "");
                            getInterested_members_count = (int) comCount.getNew_interests_count();

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        //   pDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Err", "Error: " + error.getMessage());

                //     pDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Constants.getHashMap();
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(req,Tag);
    }

    @Override
    public void onStop() {
        super.onStop();
        MySingleton.getInstance(getContext()).cancelPendingRequests(Tag);

    }
}
