package com.chicsol.alfalah.adapters;

/**
 * Created by Android on 2/2/2017.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.chicsol.alfalah.R;
import com.chicsol.alfalah.dialogs.dialogBlock;
import com.chicsol.alfalah.dialogs.dialogDeclineInterest;
import com.chicsol.alfalah.dialogs.dialogMatchAid;
import com.chicsol.alfalah.dialogs.dialogMatchAidUnderProcess;
import com.chicsol.alfalah.dialogs.dialogReplyOnAcceptInterest;
import com.chicsol.alfalah.dialogs.dialogReportConcern;
import com.chicsol.alfalah.dialogs.dialogShowInterest;
import com.chicsol.alfalah.fragments.userprofilefragments.BasicInfoFragment;
import com.chicsol.alfalah.fragments.userprofilefragments.PicturesFragment;
import com.chicsol.alfalah.modal.Members;
import com.chicsol.alfalah.other.MarryMax;
import com.chicsol.alfalah.preferences.SharedPreferenceManager;
import com.chicsol.alfalah.urls.Urls;
import com.chicsol.alfalah.utils.Constants;
import com.chicsol.alfalah.utils.MySingleton;
import com.chicsol.alfalah.utils.functions;
import com.chicsol.alfalah.utils.userProfileConstants;
import com.chicsol.alfalah.widgets.faTextView;
import com.chicsol.alfalah.widgets.mTextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileSliderPagerAdapter extends PagerAdapter {

    Context context;
    List<Members> membersList;
    LayoutInflater layoutInflater;
    Activity activity;
    FragmentManager fragmentManager;


    //===OLD=================================
    public ImageLoader imageLoader;
    //slider
    MarryMax marryMax;
    private int lastSelectedPage = 0;
    Members member;
    private ImageView ivZodiacSign, ivCountrySign, ivPhoneVerified;
    private PopupMenu popupUp;
    //slider
    private ViewPager viewPagerSlider;
    private List<String> sliderImagesDataList;
    private ImageSliderPagerAdapter myImagesPagerAdapter;
    private ImageButton ibSwipeRight, ibSwipeLeft;
    private faTextView faUserDropdown, faAddToFavourites, faInterestIcon;
    // private Toolbar toolbar;
    private TabLayout tabLayout1;
    private ViewPager viewPager1;
    private mTextView tvShowInterestButtonText, tvMatchAid, tvImagesCount, tvInterest, tvAlias, tvAge, tvLocation, tvProfileFor, tvReligion, tvEducation, tvOccupation, tvMaritalStatus, tvLastLoginDate;
    private DisplayImageOptions options;
    private LayoutInflater inflater;
    private LinearLayout llshowInterest, llBottomshowInterest, llBottomSendMessage, llUPSendMessage, llImagesCount, LineaLayoutUserProfileInterestMessage, LineaLayoutUserProfileTopBar;
    private JSONArray responsArray;
    private String userpath;
    private ProgressDialog pDialog;
    //
    BasicAndPictureFragmentAdapter adapter;

    public ProfileSliderPagerAdapter(Context context, Activity activity, FragmentManager fragmentManager, List<Members> membersList) {

        this.fragmentManager = fragmentManager;
        this.context = context;
        this.activity = activity;
        this.membersList = membersList;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return membersList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    /*   private int getItem(int i) {
           return getCurrentItem() + i;
       }*/


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = layoutInflater.inflate(R.layout.item_userprofile_slider, container, false);

        //   ImageView imageView = (ImageView) view.findViewById(R.id.imageViewUserProfileSlider);
        //  ImageButton ibLeft = (ImageButton) view.findViewById(R.id.imageButtonUPArrowLeft);


        //  imageView.setImageResource(images.get(position));

        Log.e("initializing", "initializing");

        initialize(view);
        setListenders();
        JSONObject params = new JSONObject();
        try {
            params.put("userpath", membersList.get(position).getUserpath());
            params.put("member_status", SharedPreferenceManager.getUserObject(context).getMember_status());
            params.put("gender", SharedPreferenceManager.getUserObject(context).getGender());
            params.put("path", SharedPreferenceManager.getUserObject(context).getPath());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Params", params.toString() + "");
        getLifestyle(params);


        container.addView(view);

        //listening to image click
/*        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });*/

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


    private void initialize(View view) {
        tabLayout1 = (TabLayout) view.findViewById(R.id.tabs1);
        adapter = new BasicAndPictureFragmentAdapter(fragmentManager);
        viewPager1 = (ViewPager) view.findViewById(R.id.viewpager1);
        marryMax = new MarryMax(activity);
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Loading...");

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        faUserDropdown = (faTextView) view.findViewById(R.id.faTextViewUserDetailDropdown);
        faAddToFavourites = (faTextView) view.findViewById(R.id.faTextViewAddToFavouriteMember);
        faInterestIcon = (faTextView) view.findViewById(R.id.faTextViewUserProfileInterestIcon);

        tvImagesCount = (mTextView) view.findViewById(R.id.TextViewImagesCount);
        // iv_profile = (ImageView) view.findViewById(R.id.ImageViewUPImage);

        tvAlias = (mTextView) view.findViewById(R.id.TextViewUPAlias);
        tvAge = (mTextView) view.findViewById(R.id.TextViewUPAge);
        tvLocation = (mTextView) view.findViewById(R.id.TextViewUPLocation);
        tvProfileFor = (mTextView) view.findViewById(R.id.TextViewUPProfileFor);
        tvReligion = (mTextView) view.findViewById(R.id.TextViewUPReligion);
        tvEducation = (mTextView) view.findViewById(R.id.TextViewUPEducation);
        tvOccupation = (mTextView) view.findViewById(R.id.TextViewUPOccupation);
        tvMaritalStatus = (mTextView) view.findViewById(R.id.TextViewUPMaritalStatus);
        tvLastLoginDate = (mTextView) view.findViewById(R.id.TextViewUPLastLoginDate);
        tvMatchAid = (mTextView) view.findViewById(R.id.TextViewMatchAid);
        llshowInterest = (LinearLayout) view.findViewById(R.id.LinearLayoutShowInterest);

        tvShowInterestButtonText = (mTextView) view.findViewById(R.id.mTextViewLinearLayoutUserProfileShowInterestText);


        llBottomSendMessage = (LinearLayout) view.findViewById(R.id.LinearLayoutUserProfileSendMessage);
        llBottomshowInterest = (LinearLayout) view.findViewById(R.id.LinearLayoutUserProfileShowInterest);

        llUPSendMessage = (LinearLayout) view.findViewById(R.id.LinearLayoutUPSendMessage);
        llImagesCount = (LinearLayout) view.findViewById(R.id.LinearLayoutImagesCount);

        tvInterest = (mTextView) view.findViewById(R.id.TextViewInterestId);

        ibSwipeRight = (ImageButton) view.findViewById(R.id.imageButtonUPArrowRight);
        ibSwipeLeft = (ImageButton) view.findViewById(R.id.imageButtonUPArrowLeft);

        ivZodiacSign = (ImageView) view.findViewById(R.id.ImageViewUPZodiacSign);
        ivCountrySign = (ImageView) view.findViewById(R.id.ImageViewUPCountrySign);
        ivPhoneVerified = (ImageView) view.findViewById(R.id.ImageViewUPPhoneVerified);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        inflater = LayoutInflater.from(context);

        options = new DisplayImageOptions.Builder()
                //   .showImageOnLoading(resize(R.drawable.loading_sm))
                // .showImageOnLoading(resize(R.drawable.loading))
                // .showImageForEmptyUri(resize(R.drawable.oops_sm))
                // .showImageForEmptyUri(resize(R.drawable.no_image))
                //.showImageOnFail(resize(R.drawable.oops_sm))
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)

                .postProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bmp) {

                        Bitmap bmp_sticker;
                        Display display = activity.getWindowManager().getDefaultDisplay();
                        DisplayMetrics metrics = new DisplayMetrics();

                        display.getMetrics(metrics);

                        int widthScreen = metrics.widthPixels;
                        int heightScreen = metrics.heightPixels;
                        if (widthScreen > heightScreen) {
                            int h = (int) (heightScreen * 0.046);//it set the height of image 10% of your screen
                            //     iv.getLayoutParams().width = (int) (widthScreen * 0.10);
                            Log.e("wid " + widthScreen + "  " + heightScreen, "");
                            bmp_sticker = resizeImage(bmp, h);
                        } else {
                            int h = (int) (heightScreen * 0.027);//it set the height of image 10% of your screen
                            //   iv.getLayoutParams().width = (int) (widthScreen * 0.15);
                            bmp_sticker = resizeImage(bmp, h);
                            Log.e("wid " + widthScreen + "  " + heightScreen, "");
                        }

                        return bmp_sticker;
                    }
                }).build();

//=====================ENTER MAIN USER PATH HERE==============================================================================================
        //   userpath = getIntent().getExtras().getString("userpath");
        popupUp = new PopupMenu(activity, faUserDropdown);

        popupUp.getMenuInflater()
                .inflate(R.menu.menu_user_profile, popupUp.getMenu());


    }


    private void postSetListener() {
        ivPhoneVerified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                marryMax.statusBaseChecks(member, context, 5, fragmentManager, null, v, null, null,null,null);

            }
        });

    }


    private void setListenders() {

        llBottomSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v);
            }
        });
        llBottomshowInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterest(v);
            }
        });
        viewPager1.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                lastSelectedPage = position;

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tvMatchAid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean bcheck3 = marryMax.statusBaseChecks(member, context, 7, fragmentManager, null, v, null, null,null,null);
                if (bcheck3) {
                    matchAid();
                }

            }
        });
        faAddToFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Toast.makeText(UserProfileActivity.context, "Clicked", Toast.LENGTH_SHORT).show();
                //    Log.e("Saved Member", member.getSaved_member() + " ");

                boolean bcheck3 = marryMax.statusBaseChecks(member, context, 7, fragmentManager, null, v, null, null,null,null);
                if (bcheck3) {
                    if (member.getSaved_member() == 1) {
                        JSONObject params = new JSONObject();
                        try {
                            params.put("id", "1");
                            params.put("userpath", userpath);
                            params.put("path", SharedPreferenceManager.getUserObject(context).getPath());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        addToFavourites(params);
                    } else {
                        JSONObject params = new JSONObject();
                        try {
                            params.put("id", "0");
                            params.put("userpath", userpath);
                            params.put("path", SharedPreferenceManager.getUserObject(context).getPath());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        addToFavourites(params);
                    }
                }


            }
        });
        llUPSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessage(v);


            }
        });


        llshowInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showInterest(v);


            }
        });
      /*  ibSwipeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerSlider.setCurrentItem(getItem(+1), true);
            }
        });
        ibSwipeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerSlider.setCurrentItem(getItem(-1), true);
            }
        });*/

        faUserDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                //  addRemoveBlockMenu=popup;
                //Inflating the Popup using xml file


                //registering popup with OnMenuItemClickListener
                popupUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                         /*   case R.id.menu_up_request:

                                break;*/
                            case R.id.menu_up_block:

                                boolean bcheck = marryMax.statusBaseChecks(member, context, 7, fragmentManager, null, v, null, null,null,null);
                                if (bcheck) {
                                    blockUser();
                                }
                                break;
                            case R.id.menu_up_remove:


                                boolean checkStatus = marryMax.statusBaseChecks(member, context, 3, fragmentManager, null, v, null, null,null,null);

                                if (checkStatus) {
                                    JSONObject params = new JSONObject();
                                    try {
                                        params.put("id", member.getRemoved_member());
                                        params.put("userpath", userpath);
                                        params.put("path", SharedPreferenceManager.getUserObject(context).getPath());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    removeMember(params, item);
                                }

                                break;

                            case R.id.menu_up_report_concern:
                                boolean bcheck3 = marryMax.statusBaseChecks(member, context, 7, fragmentManager, null, v, null, null,null,null);
                                if (bcheck3) {
                                    reportConcern();
                                }

                                break;

                        }


                        return true;
                    }

                });
                popupUp.show(); //showing popup menu


            }
        });


    }

    private void sendMessage(View v) {

        marryMax.statusBaseChecks(member, context, 6, fragmentManager, null, v, null, null,null,null);

    }


    private void showInterest(View v) {

        JSONObject params = new JSONObject();
        try {
            params.put("userpath", userpath);
            params.put("path", SharedPreferenceManager.getUserObject(context).getPath());
            /*  params.put("userpath", "jX0GywjuTMhXATJ3f56FIg==");
                    params.put("path", "G2vOHlGTQOrBjneguNPQuA==");*/
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Members sessionObj = SharedPreferenceManager.getUserObject(context);
        Log.e(functions.checkProfileCompleteStatus(member) + "" + member.getMember_status(), "checcccccccccccccccccccc");


        boolean checkStatus = marryMax.statusBaseChecks(member, context, 2, fragmentManager, null, v, null, null,null,null);

        if (checkStatus) {
            if (functions.checkProfileCompleteStatus(sessionObj)) {
                if (member.getInterested_id() == 0) {
                    showInterest(params, false);
                      /*  tvInterest.setText("Show Interest");
                        llshowInterest.setBackgroundColor(getResources().getColor(R.color.colorUserProfileTextGreen));*/

                } else if (member.getInterested_id() != 0) {

                    if (member.getInterest_received() == 0) {
                           /* tvInterest.setText("Withdraw Interest");
                            llshowInterest.setBackgroundColor(getResources().getColor(R.color.colorGrey));*/
                        try {
                            params.put("interested_id", member.getInterested_id());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String desc = "Are you sure to withdraw your interest for  <font color=#216917>" + member.getAlias() + "</font>";
                        marryMax.withdrawInterest(params, "Withdraw Interest", desc, null, fragmentManager, "5");

                    } else if (member.getInterest_received() == 1) {
                        replyOnInterest(v);
/*
                            tvInterest.setText("Reply On Interest");
                            llshowInterest.setBackgroundColor(getResources().getColor(R.color.colorDefaultGreen));*/


                    } else if (member.getInterest_received() == 3) {
                          /*  tvInterest.setText("Interest Accepted");
                            llshowInterest.setBackgroundColor(getResources().getColor(R.color.colorUserProfileTextGreen));*/
                    }
                }

            } else {

                Toast.makeText(activity, "Please Complete Your Profile", Toast.LENGTH_SHORT).show();
                //comp profile  when click on context
            }

        }
    }


    private void replyOnInterest(View v) {


        PopupMenu popup = new PopupMenu(activity, v);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.menu_yes_no, popup.getMenu());


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_up_yes:
                        JSONObject params = new JSONObject();
                        try {
                            params.put("userpath", userpath);
                            params.put("path", SharedPreferenceManager.getUserObject(context).getPath());
            /*  params.put("userpath", "jX0GywjuTMhXATJ3f56FIg==");
                    params.put("path", "G2vOHlGTQOrBjneguNPQuA==");*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        replyOnAcceptInterest(params, true);

                        break;

                    case R.id.menu_up_no:


                        dialogDeclineInterest newFragment = dialogDeclineInterest.newInstance(member, userpath);
                        newFragment.show(fragmentManager, "dialog");


                        break;


                }


                return true;
            }

        });
        popup.show(); //showing popup menu


    }

    private void blockUser() {

        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(Urls.getBlockReasonData,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response", response.toString());


                        try {
                            JSONArray responseJSONArray = response.getJSONArray(0);

                            dialogBlock newFragment = dialogBlock.newInstance(responseJSONArray, userpath, member);
                            newFragment.show(fragmentManager, "dialog");


                        } catch (JSONException e) {
                            pDialog.dismiss();
                            e.printStackTrace();
                        }

                        pDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Err", "Error: " + error.getMessage());
                pDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Constants.getHashMap();
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(req);
    }


    private void reportConcern() {

        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(Urls.getReportConcernData,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response", response.toString() + "  ==   " + Urls.getReportConcernData);


                        try {
                            JSONArray responseJSONArray = response.getJSONArray(0);

                            dialogReportConcern newFragment = dialogReportConcern.newInstance(responseJSONArray, userpath, member);
                            newFragment.show(fragmentManager, "dialog");


                        } catch (JSONException e) {
                            pDialog.dismiss();
                            e.printStackTrace();
                        }

                        pDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Err", "Error: " + error.getMessage());
                pDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Constants.getHashMap();
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(req);
    }

    private void matchAid() {

        pDialog.show();
        Log.e("url", Urls.getAssistance + SharedPreferenceManager.getUserObject(context).getPath());
        JsonArrayRequest req = new JsonArrayRequest(Urls.getAssistance + SharedPreferenceManager.getUserObject(context).getPath(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Response", response.toString() + "  ==   ");

                        try {
                            int res = response.getJSONArray(1).getJSONObject(0).getInt("id");
                            Log.e("ressss", "" + res + "");
                            if (res == 0) {
                                dialogMatchAid newFragment = dialogMatchAid.newInstance(response, userpath, SharedPreferenceManager.getUserObject(context).getMember_status());
                                newFragment.show(fragmentManager, "dialog");
                            } else {
                                dialogMatchAidUnderProcess newFragment = dialogMatchAidUnderProcess.newInstance(response, userpath);
                                newFragment.show(fragmentManager, "dialog");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        pDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Err", "Error: " + error.getMessage());
                pDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Constants.getHashMap();
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(req);
    }


    private void replyOnAcceptInterest(JSONObject params, final boolean replyCheck) {


        pDialog.show();
        Log.e("params", params.toString());
        Log.e("profile path", Urls.interestProvisions);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                Urls.interestProvisions, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Res  interest ", response + "");

                        try {
                            JSONObject responseObject = response.getJSONArray("data").getJSONArray(0).getJSONObject(0);


                            Gson gson;
                            GsonBuilder gsonBuilder = new GsonBuilder();

                            gson = gsonBuilder.create();
                            Type type = new TypeToken<Members>() {
                            }.getType();
                            Members member2 = (Members) gson.fromJson(responseObject.toString(), type);
                            Log.e("interested id", "" + member.getAlias() + "====================");

                            dialogReplyOnAcceptInterest newFragment = dialogReplyOnAcceptInterest.newInstance(member, userpath, replyCheck, member2);
                            newFragment.show(fragmentManager, "dialog");


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
                // Toast.makeText(RegistrationActivity.context, "Incorrect Email or Password !", Toast.LENGTH_SHORT).show();

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
        MySingleton.getInstance(context).addToRequestQueue(jsonObjReq);
    }

    private void showInterest(JSONObject params, final boolean replyCheck) {


        pDialog.show();
        Log.e("params", params.toString());
        Log.e("profile path", Urls.interestProvisions);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                Urls.interestProvisions, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Res  interest ", response + "");

                        try {
                            JSONObject responseObject = response.getJSONArray("data").getJSONArray(0).getJSONObject(0);


                            Gson gson;
                            GsonBuilder gsonBuilder = new GsonBuilder();

                            gson = gsonBuilder.create();
                            Type type = new TypeToken<Members>() {
                            }.getType();
                            Members member2 = (Members) gson.fromJson(responseObject.toString(), type);
                            Log.e("interested id", "" + member.getAlias() + "====================");

                            dialogShowInterest newFragment = dialogShowInterest.newInstance(member, userpath, replyCheck, member2);
                            newFragment.show(fragmentManager, "dialog");


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
                // Toast.makeText(RegistrationActivity.context, "Incorrect Email or Password !", Toast.LENGTH_SHORT).show();

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
        MySingleton.getInstance(context).addToRequestQueue(jsonObjReq);
    }

    private void removeMember(JSONObject params, final MenuItem menuItem) {


        pDialog.show();
        Log.e("params", params.toString());
        //      Log.e("profile path", Urls.interestProvisions);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                Urls.removeMember, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Res  ", response + "");

                        try {
                            int responseid = response.getInt("id");
                            if (responseid == 1) {

                                member.setRemoved_member(responseid);
                                Toast.makeText(activity, "User has been Removed successfully ", Toast.LENGTH_SHORT).show();

                                /*  MenuItem menuItem = addRemoveBlockMenu.getMenu().findItem(R.id.menu_up_remove);
                                 */
                                menuItem.setTitle("Unremove");
                            } else {
                                member.setRemoved_member(responseid);
                                Toast.makeText(activity, "User has been unremoved successfully ", Toast.LENGTH_SHORT).show();
                                /*    MenuItem menuItem = addRemoveBlockMenu.getMenu().findItem(R.id.menu_up_remove);
                                 */
                                menuItem.setTitle("Remove");
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
                // Toast.makeText(RegistrationActivity.context, "Incorrect Email or Password !", Toast.LENGTH_SHORT).show();

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
        MySingleton.getInstance(context).addToRequestQueue(jsonObjReq);
    }

    private void addToFavourites(JSONObject params) {


        pDialog.show();
        Log.e("params", params.toString());
        //      Log.e("profile path", Urls.interestProvisions);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                Urls.addRemoveFavorites, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Res  ", response + "");

                        try {
                            int responseid = response.getInt("id");
                            if (responseid == 1) {
                                faAddToFavourites.setPressed(true);
                                Toast.makeText(activity, "Favourite Updated successfully ", Toast.LENGTH_SHORT).show();

                            } else {
                                faAddToFavourites.setPressed(false);

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
                // Toast.makeText(RegistrationActivity.context, "Incorrect Email or Password !", Toast.LENGTH_SHORT).show();

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
        MySingleton.getInstance(context).addToRequestQueue(jsonObjReq);
    }

    private void getLifestyle(JSONObject params) {


        // pDialog.show();
        Log.e("params", params.toString());
        Log.e("profile path", Urls.getProfileDetail);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                Urls.getProfileDetail, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("re  update lifestyle", response + "");

                        try {
                            responsArray = response.getJSONArray("jdata");

                            JSONObject firstJsonObj = responsArray.getJSONArray(0).getJSONObject(0);


                            Gson gson;
                            GsonBuilder gsonBuilder = new GsonBuilder();

                            gson = gsonBuilder.create();
                            Type type = new TypeToken<Members>() {
                            }.getType();
                            member = (Members) gson.fromJson(firstJsonObj.toString(), type);
                            // getSupportActionBar().setTitle(member.getAlias());
                           /* if (SharedPreferenceManager.getUserObject(context).getPath() != null && member.getPath() != null) {
                                Log.e("=========path ========", SharedPreferenceManager.getUserObject(context).getPath() + "======" + member.getUserpath());
                                if (SharedPreferenceManager.getUserObject(context).getPath().equals((member.getUserpath()))) {
                                    LineaLayoutUserProfileInterestMessage = (LinearLayout) findViewById(R.id.LineaLayoutUserProfileInterestMessage);
                                    LineaLayoutUserProfileTopBar = (LinearLayout) findViewById(R.id.LineaLayoutUserProfileTopBar);
                                    LineaLayoutUserProfileInterestMessage.setVisibility(View.GONE);
                                    LineaLayoutUserProfileTopBar.setVisibility(View.GONE);
                                }
                            }*/


                            setInterestButtonText();
                            settHeader();
                            //  loadSlider(member.getDefault_image());

                            setupViewPager(viewPager1, responsArray.toString());
                            // Log.e("Member checkedTextView", "" + member.getAlias());

                        } catch (JSONException e) {
                            //  pDialog.dismiss();
                            e.printStackTrace();
                        }


                        // pDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                VolleyLog.e("res err", "Error: " + error);
                // Toast.makeText(RegistrationActivity.context, "Incorrect Email or Password !", Toast.LENGTH_SHORT).show();

                //  pDialog.dismiss();
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
        MySingleton.getInstance(context).addToRequestQueue(jsonObjReq);
    }


    private Bitmap resizeImage(final Bitmap image, int maxHeight) {
        Bitmap resizedImage = null;
        if (image != null) {
            //  int maxHeight = 80; //actual image height coming from internet
            int maxWidth = 300; //actual image width coming from internet

            int imageHeight = image.getHeight();
            if (imageHeight > maxHeight)
                imageHeight = maxHeight;
            int imageWidth = (imageHeight * image.getWidth()) / image.getHeight();
            if (imageWidth > maxWidth) {
                imageWidth = maxWidth;
                imageHeight = (imageWidth * image.getHeight()) / image.getWidth();
            }
            resizedImage = Bitmap.createScaledBitmap(image, imageWidth, imageHeight, true);
        }
        return resizedImage;
    }


    class BasicAndPictureFragmentAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public BasicAndPictureFragmentAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }


        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        public void clearFragments() {
            mFragmentList.clear();
            mFragmentTitleList.clear();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    // =====================


    private void setupViewPager(ViewPager viewPager, String jsonArryaResponse1) {


        Bundle args = new Bundle();
        args.putString("json", jsonArryaResponse1);
        BasicInfoFragment basicInfoFragment = new BasicInfoFragment();
        //MessageHistoryFragment messageHistoryFragment = new MessageHistoryFragment();
        PicturesFragment picturesFragment = new PicturesFragment();
        picturesFragment.jsonData = jsonArryaResponse1;
        userProfileConstants.jsonArryaResponse = jsonArryaResponse1;
        basicInfoFragment.setArguments(args);
        // messageHistoryFragment.setArguments(args);
        // picturesFragment.setArguments(args);
        // picturesFragment.jsonData = jsonArryaResponse;

        adapter.clearFragments();
        adapter.addFragment(basicInfoFragment, "Basic");
        ///adapter.addFragment(messageHistoryFragment, "Message History");
        adapter.addFragment(picturesFragment, "Pictures");

        Bundle picfrg = new Bundle();
        picfrg.putBoolean("myprofilecheck", false);
        picturesFragment.setArguments(picfrg);


        //   viewPager.setAdapter(null);
        viewPager.setAdapter(adapter);
        tabLayout1.setupWithViewPager(viewPager1);
        adapter.notifyDataSetChanged();

        for (int i = tabLayout1.getTabCount() - 1; i >= 0; i--) {
            TabLayout.Tab tab = tabLayout1.getTabAt(i);
            LinearLayout relativeLayout = (LinearLayout)
                    LayoutInflater.from(context).inflate(R.layout.custom_user_tab_item, tabLayout1, false);
            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title1);
            tabTextView.setText(tab.getText());

            if (i == tabLayout1.getTabCount() - 1) {
                View view1 = (View) relativeLayout.findViewById(R.id.tab_view_separator1);
                view1.setVisibility(View.INVISIBLE);
            }
            tab.setCustomView(relativeLayout);
            tab.select();
        }
        {
            TabLayout.Tab tabs = tabLayout1.getTabAt(lastSelectedPage);
            tabs.select();
        }

        // picturesFragment.loadData(jsonArryaResponse);
    }

    private void setInterestButtonText() {
        Members sessionObj = SharedPreferenceManager.getUserObject(context);
        Log.e(functions.checkProfileCompleteStatus(member) + "" + member.getMember_status(), "checcccccccccccccccccccc");
        if (functions.checkProfileCompleteStatus(sessionObj)) {
       /*     if (sessionObj.getMember_status() < 3) {

                tvInterest.setText("Show Interest");
                llshowInterest.setBackgroundColor(R.color.colorUserProfileTextGreen);

            }*/

            Log.e("interested id", member.getInterested_id() + "");
            Log.e("interested receieved", member.getInterest_received() + "");
            if (member.getInterested_id() == 0) {
                tvInterest.setText("Show Interest");
                llshowInterest.setBackgroundColor(context.getResources().getColor(R.color.colorUserProfileTextGreen));

            } else if (member.getInterested_id() != 0) {

                if (member.getInterest_received() == 0) {
                    tvInterest.setText("Withdraw Interest");
                    tvShowInterestButtonText.setText("Withdraw Interest");
                    llshowInterest.setBackgroundColor(context.getResources().getColor(R.color.colorGrey));
                } else if (member.getInterest_received() == 1) {

                    tvInterest.setText("Reply On Interest");
                    tvShowInterestButtonText.setText("Reply On Interest");
                    llshowInterest.setBackgroundColor(context.getResources().getColor(R.color.colorDefaultGreen));


                } else if (member.getInterest_received() == 3) {
                    tvInterest.setText("Interest Accepted");
                    tvShowInterestButtonText.setText("Interest Accepted");
                    llshowInterest.setBackgroundColor(context.getResources().getColor(R.color.colorUserProfileTextGreen));
                }
            }
            /*else if (member.getInterested_id() == 0 && member.getInterest_received() == 0) {

                tvInterest.setText("Withdraw Interest");
                llshowInterest.setBackgroundColor(R.color.colorGrey);

            } else if (member.getInterested_id() == 0 && member.getInterest_received() == 3) {
                tvInterest.setText("Interest Accepted");
                llshowInterest.setBackgroundColor(R.color.colorUserProfileTextGreen);
            }*/
        } else {
            Toast.makeText(context, "copmp profile", Toast.LENGTH_SHORT).show();
            //comp profile  when click on this
        }
    }




/*
    @Override
    public void onComplete(String s) {


        JSONObject params = new JSONObject();
        try {
            params.put("userpath", userpath);
            params.put("member_status", SharedPreferenceManager.getUserObject(context).getMember_notes_id());
            params.put("gender", SharedPreferenceManager.getUserObject(context).getGender());
            params.put("path", SharedPreferenceManager.getUserObject(context).getPath());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Params", params.toString() + "");
        getLifestyle(params);
    }



    @Override
    public void onComplete(int s) {

    }
*/

    private void settHeader() {
        //    tvAlias,tvDesc,tvLocation,tvProfileFor,tvReligion,tvEducation,tvOccupation,tvMaritalStatus;
        tvAlias.setText(member.getAlias());
        tvAge.setText("( " + member.getAge() + " Years )");
        String location = "";
        if (!(member.getCity_name().equals(""))) {
            location = member.getCity_name() + ", ";
        }
        if (!member.getState_name().equals("")) {

            location = location + member.getState_name() + ", ";
        }
        tvLocation.setText(location + member.getCountry_name() + ", (" + member.getVisa_status_types() + ")");
        // Log.e("Location", member.getCity_name());
        tvProfileFor.setText(member.getProfile_owner());
        tvReligion.setText(member.getReligious_sec_type());
        tvEducation.setText(member.getEducation_types());
        tvOccupation.setText(member.getOccupation_types());
        tvMaritalStatus.setText(member.getMarital_status_types());
        tvLastLoginDate.setText(member.getLast_login_date());


        if (member.getImage_count() > 0) {
            llImagesCount.setVisibility(View.VISIBLE);
            ibSwipeLeft.setVisibility(View.VISIBLE);
            ibSwipeRight.setVisibility(View.VISIBLE);
        } else {
            llImagesCount.setVisibility(View.GONE);
            ibSwipeLeft.setVisibility(View.GONE);
            ibSwipeRight.setVisibility(View.GONE);
        }

        tvImagesCount.setText(Long.toString(member.getImage_count()));

        //  Log.e("zodaic", "" + member.getSign_name());
        //  Log.e("country flag", "" + member.getCountry_flag());
        imageLoader.displayImage(Urls.baseUrl + "/images/zodiac/" + member.getSign_name() + ".png", ivZodiacSign, options);
        imageLoader.displayImage(Urls.baseUrl + "/images/flags/" + member.getCountry_flag() + ".gif", ivCountrySign, options);

        if (member.getPhone_verified() == 2) {
            if (member.getHide_phone() == 0 || member.getAllow_phone_view() > 0) {
                ivPhoneVerified.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_num_verified_icon_60));
                //greeen
            } else {
                ivPhoneVerified.setImageDrawable(context.getResources().getDrawable(R.drawable.num_not_verified_icon_60));
                //    orange
            }

        } else {
            ivPhoneVerified.setImageDrawable(context.getResources().getDrawable(R.drawable.no_number_icon_60));
            //default
        }

      /*imageLoader.displayImage(Urls.baseUrl + "/" + member.getDefault_image(),
              ivZodiacSign, options,
                new SimpleImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        //  holder.progressBar.setVisibility(View.VISIBLE);
                        // holder.RLprogress1.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view,
                                                FailReason failReason) {
                        // holder.RLprogress1.setVisibility(View.GONE);
                        //   holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri,
                                                  View view, Bitmap loadedImage) {
                        // holder.progressBar.setVisibility(View.GONE);
                        // holder.RLprogress1.setVisibility(View.GONE);
                        //   holder.progressBar.setVisibility(View.GONE);
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri,
                                                 View view, int current, int total) {
                        // holder.RLprogress1.setProgress(Math.round(100.0f
                        // * current / total));
                    }
                });
*/


        if (member.getRemoved_member() == 1) {
            MenuItem menuItem = popupUp.getMenu().findItem(R.id.menu_up_remove);

            menuItem.setTitle("Unremove");
        } else {
            MenuItem menuItem = popupUp.getMenu().findItem(R.id.menu_up_remove);
            menuItem.setTitle("Remove");

        }
       /* if (member.get_is == 1) {
            MenuItem menuItem = popupUp.getMenu().findItem(R.id.menu_up_remove);

            menuItem.setTitle("Unremove");
        } else {
            MenuItem menuItem = popupUp.getMenu().findItem(R.id.menu_up_remove);
            menuItem.setTitle("Remove");

        }*/

        Log.e("Saved Member", member.getSaved_member() + " ");

        if (member.getSaved_member() == 1) {

            faAddToFavourites.setPressed(true);
        }

        //MenuItem menuItem1 = popupUp.getMenu().findItem(R.id.menu_up_request);

      /*  Log.e("Vall Member", member.getHide_profile() + " " + member.getHide_photo());

        if (member.getHide_profile() == 1) {
            menuItem1.setTitle("Request Profile View");

        }

        if (member.getHide_photo() == 1) {
            menuItem1.setTitle("Request Photo View");

        }*/

        postSetListener();

    }


}