package com.harrydrippin.hitchhiker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapView;

/**
 * Created by harryhong on 2016. 11. 18..
 */
public class MainActivity extends NMapActivity implements View.OnClickListener {
    Toolbar mToolbar;
    NMapView mMapView;
    NMapController mMapController;
    RelativeLayout solo, multi;
    RelativeLayout and;
    LinearLayout mainRelative;
    TextView alert;
    ImageView imgSolo, imgMulti, imgPerson;
    boolean isSolo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainRelative = (LinearLayout) findViewById(R.id.main_relative);
        alert = (TextView)findViewById(R.id.text_alert);

        imgSolo = (ImageView)findViewById(R.id.img_solo);
        imgMulti = (ImageView)findViewById(R.id.img_multi);
        imgPerson = (ImageView)findViewById(R.id.img_person);
        imgPerson.setVisibility(View.INVISIBLE);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.BLACK);
        mToolbar.setLogo(android.R.drawable.ic_lock_lock);
        mToolbar.inflateMenu(R.menu.menu_main);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.menu_log) {
                    Toast.makeText(getApplicationContext(), "최근 사용 기록", Toast.LENGTH_LONG).show();
                } else if(id == R.id.menu_pref) {
                    Toast.makeText(getApplicationContext(), "설정", Toast.LENGTH_LONG).show();
                }

                return true;
            }
        });

        mMapView = (NMapView)findViewById(R.id.mapView);
        mMapView.setClientId("cFBymAhXbjRYdnDenpHI");
        mMapView.setClickable(true);
//        mMapView.setOnMapStateChangeListener(new NMapView.OnMapStateChangeListener() {
//
//            @Override
//            public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
//                if (nMapError == null) { // success
//                    mMapController.setMapCenter(new NGeoPoint(126.978371, 37.5666091), 11);
//                } else { // fail
//                    Log.e("NMAP", "onMapInitHandler: error=" + nMapError.toString());
//                }
//            }
//
//            @Override
//            public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {
//
//            }
//
//            @Override
//            public void onMapCenterChangeFine(NMapView nMapView) {
//
//            }
//
//            @Override
//            public void onZoomLevelChange(NMapView nMapView, int i) {
//
//            }
//
//            @Override
//            public void onAnimationStateChange(NMapView nMapView, int i, int i1) {
//
//            }
//        });

        solo = (RelativeLayout) findViewById(R.id.button_solo);
        multi = (RelativeLayout) findViewById(R.id.button_multi);
        and = (RelativeLayout) findViewById(R.id.layout_and);

        ViewGroup.LayoutParams params = solo.getLayoutParams();
        params.width = (1440 - 60) / 3;
        params.height = 450;
        solo.setLayoutParams(params);
        multi.setLayoutParams(params);
        and.setLayoutParams(params);

        solo.setOnClickListener(this);
        multi.setOnClickListener(this);
        and.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_log) {
            Toast.makeText(getApplicationContext(), "최근 사용 기록", Toast.LENGTH_LONG).show();
        } else if(id == R.id.menu_pref) {
            Toast.makeText(getApplicationContext(), "설정", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_solo:
                alert.setText("혼자 또는 친구와");
                alert.setTextColor(Color.parseColor("#ECB0B0"));
                imgSolo.setImageDrawable(getDrawable(R.drawable.solo));
                imgMulti.setImageDrawable(getDrawable(R.drawable.multi_un));
                imgPerson.setImageDrawable(getDrawable(R.drawable.person_four));
                imgPerson.setVisibility(View.VISIBLE);
                isSolo = true;
                break;
            case R.id.button_multi:
                alert.setText("다른 사람들과");
                alert.setTextColor(Color.parseColor("#A2D2CF"));
                imgSolo.setImageDrawable(getDrawable(R.drawable.solo_un));
                imgMulti.setImageDrawable(getDrawable(R.drawable.multi));
                imgPerson.setImageDrawable(getDrawable(R.drawable.person_three));
                imgPerson.setVisibility(View.VISIBLE);
                isSolo = false;
                break;
            case R.id.layout_and:
                if(alert.getText().toString().equals("선택해주세요!")) {
                    Toast.makeText(getApplicationContext(), "매칭 방식이 선정되지 않았습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_down);
                    mainRelative.startAnimation(animation);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), ValueSetActivity.class);
                            intent.putExtra("isSolo", isSolo);
                            startActivity(intent);
                        }

                    }, 1000); // Setting time for waiting
                }
        }

    }
}
