package com.chicsol.alfalah.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.chicsol.alfalah.R;
import com.chicsol.alfalah.adapters.NotificationSpinnerAdapter;
import com.chicsol.alfalah.modal.Members;
import com.chicsol.alfalah.modal.mProperties;
import com.chicsol.alfalah.other.MarryMax;
import com.chicsol.alfalah.other.UserSessionManager;
import com.chicsol.alfalah.preferences.SharedPreferenceManager;
import com.chicsol.alfalah.urls.Urls;
import com.chicsol.alfalah.utils.ConnectCheck;
import com.chicsol.alfalah.utils.Constants;
import com.chicsol.alfalah.utils.MySingleton;
import com.chicsol.alfalah.widgets.mTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Android on 12/7/2016.
 */

public class
DrawerActivity extends AppCompatActivity {

    public LinearLayout LinearLayoutDrawerMyProfile;
    public static Members rawSearchObj = null;
    /*   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onCreateDrawer();
    }*/
    public ImageLoader imageLoader;
    DrawerLayout drawer;
    int count = 0;
    private NotificationSpinnerAdapter m_adapter;
    private ArrayList<mProperties> m_NotificationDataList = new ArrayList<mProperties>();
    private mTextView tcUserName;
    private Members member;
    private ImageView iv_profile;
    private DisplayImageOptions options;

    // private ProgressDialog pDialog;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        onCreateDrawer();
    }

    private void initialize() {

        LinearLayoutDrawerMyProfile = (LinearLayout) findViewById(R.id.LinearLayoutDrawerMyProfile);


        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));


        options = new DisplayImageOptions.Builder()
                //   .showImageOnLoading(resize(R.drawable.loading_sm))
                // .showImageOnLoading(resize(R.drawable.loading))
                // .showImageForEmptyUri(resize(R.drawable.oops_sm))
                // .showImageForEmptyUri(resize(R.drawable.no_image))
                //.showImageOnFail(resize(R.drawable.oops_sm))
                .cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)

             /*   .postProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bmp) {

                        Bitmap bmp_sticker;
                        Display display =getContext().getWindowManager().getDefaultDisplay();
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
                })*/.build();


        tcUserName = (mTextView) findViewById(R.id.TextViewNavUserName);
        member = SharedPreferenceManager.getUserObject(getApplicationContext());


        m_adapter = new NotificationSpinnerAdapter(this, R.layout.item_spinner_notifications, m_NotificationDataList);
        tcUserName.setText(member.getPersonal_name());


        iv_profile = (ImageView) findViewById(R.id.imageViewNavDefaultImage);
        imageLoader.displayImage(Urls.baseUrl + "/" + SharedPreferenceManager.getUserObject(getApplicationContext()).getDefault_image(),
                iv_profile, options,
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
        getSearchListData();
        // getRawData();


        LinearLayoutDrawerMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProfile();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNotificationCount();
    }

    public void onCreateDrawer() {
        initialize();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


    }


    public void onDashboardClick(View view) {

        Intent in = new Intent(DrawerActivity.this, DashboarMainActivityWithBottomNav.class);
        startActivity(in);
        finish();

    }


    public void onEditProfile(View view) {
        drawer.closeDrawers();

Log.e("drawer","drawer");
    }


    private void viewProfile() {

    /*    if (ConnectCheck.isConnected(findViewById(android.R.id.content))) {
         if (SharedPreferenceManager.getUserObject(getApplicationContext()).getMember_status() != 0) {
                if (SharedPreferenceManager.getUserObject(getApplicationContext()).getMember_status() != 0 || SharedPreferenceManager.getUserObject(getApplicationContext()).getMember_status() != 7) {

                    if (drawer != null) {
                        drawer.closeDrawers();
                    }
                    if (SharedPreferenceManager.getUserObject(getApplicationContext()).getPath() != null && SharedPreferenceManager.getUserObject(getApplicationContext()).getPath() != "") {
                        Intent intent = new Intent(DrawerActivity.this, MyProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("userpath", SharedPreferenceManager.getUserObject(getApplicationContext()).getPath());

                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error ! ", Toast.LENGTH_SHORT).show();

                    }
                } else {

                    Toast.makeText(this, "Please Complete your profile first", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please Complete your profile first", Toast.LENGTH_SHORT).show();
            }*/




        if (ConnectCheck.isConnected(findViewById(android.R.id.content))) {
        if (drawer != null) {
                drawer.closeDrawers();
            }

         /*   MarryMax marryMax = new MarryMax(DrawerActivity.this);
            boolean acheck = marryMax.checkUserStatusLogin(member);
            if (acheck) {
                Intent intent = new Intent(DrawerActivity.this, MyProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("userpath", SharedPreferenceManager.getUserObject(getApplicationContext()).getPath());

                startActivity(intent);
            }*/



       if(     member.getMember_status() == 0 || member.getMember_status() >= 7){


           MarryMax marryMax = new MarryMax(DrawerActivity.this);
           marryMax.getProfileProgress(getApplicationContext(), member, DrawerActivity.this);
       }
       else {
           Intent intent = new Intent(DrawerActivity.this, MyProfileActivity.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           intent.putExtra("userpath", SharedPreferenceManager.getUserObject(getApplicationContext()).getPath());

           startActivity(intent);
       }



        }
    }


    public void onAccountClick(View view) {
        drawer.closeDrawers();
        //   Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        Intent in = new Intent(DrawerActivity.this, AccountSettingActivity.class);
        startActivity(in);
    }

    public void onFAQClick(View view) {
        drawer.closeDrawers();
        Intent in = new Intent(DrawerActivity.this, FaqActivity.class);
        startActivity(in);
        // onLogoutClick(view);
    }

    public void onLogoutClick(View view) {
        //   Toast.makeText(this, "Clicked ", Toast.LENGTH_SHORT).show();

        UserSessionManager sessionManager = new UserSessionManager(getApplicationContext());
        sessionManager.logoutUser();
    }

    public void onUploadPhotoClick(View view) {
        drawer.closeDrawers();
        //   Toast.makeText(this, "Clicked ", Toast.LENGTH_SHORT).show();
        MarryMax marryMax = new MarryMax(DrawerActivity.this);

        if (marryMax.uploadPhotoCheck(getApplicationContext())) {
            Intent in = new Intent(DrawerActivity.this, PhotoUpload.class);
            startActivity(in);
        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //========adv search=====================================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_notifications);
        menuItem.setIcon(buildCounterDrawable(count, R.drawable.ic_bell));


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_notifications) {

            if (ConnectCheck.isConnected(findViewById(android.R.id.content))) {
                Intent intent = new Intent(DrawerActivity.this, NotificationsActivity.class);
                startActivity(intent);
            }
            return true;
        } else if (id == R.id.action_search) {

            if (ConnectCheck.isConnected(findViewById(android.R.id.content))) {
                DrawerActivity.rawSearchObj = new Members();
                MarryMax max = new MarryMax(DrawerActivity.this);
                max.onSearchClicked(getApplicationContext(), 0);
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.counter_menuitem_layout, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    private void settNotificationCount(String c) {
        count = Integer.parseInt(c);
        invalidateOptionsMenu();
    }
    //......................................


    public void getNotificationCount() {


        Log.e(" Notification url", Urls.getNotificationCount + SharedPreferenceManager.getUserObject(getApplicationContext()).getPath());
        StringRequest req = new StringRequest(Urls.getNotificationCount + SharedPreferenceManager.getUserObject(getApplicationContext()).getPath(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Notification Count==", "=======================  " + response);
                        if (!response.equals(null) && !response.equals("")) {
                            settNotificationCount(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Err", "Error: " + error.getMessage());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Constants.getHashMap();
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(req);
    }


//==================================================SEARCH===========================================================


    private void getSearchListData() {

        Log.e("getSearchLists", Urls.getSearchLists + SharedPreferenceManager.getUserObject(getApplicationContext()).getPath());
        JsonArrayRequest req = new JsonArrayRequest(Urls.getSearchLists + SharedPreferenceManager.getUserObject(getApplicationContext()).getPath(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Constants.jsonArraySearch = response;
                        //   pDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Err", "Error: " + error.getMessage());
                //  pDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Constants.getHashMap();
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(req);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
      /*  if (pDialog != null && pDialog.isShowing()) {
            pDialog.cancel();
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();


    }


}
