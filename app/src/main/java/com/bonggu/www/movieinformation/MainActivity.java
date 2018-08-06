package com.bonggu.www.movieinformation;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private static final String TAG = "MainActivity";

    //Fragment관리
    Fragment movieListFragment;
    BoxOfficeFragment boxOfficeFragment;

    FragmentTransaction tran;
    FragmentManager manager;

    FrameLayout searchframelayout;
    FrameLayout rankframelayout;

    RelativeLayout search;
    RelativeLayout rank;
    TextView searchtext;
    TextView ranktext;
    //

    //firebase analytics
    private FirebaseAnalytics mFirebaseAnalytics;

    //admob
    private AdView mAdView;

    //fcm
    String fcmtoken;
    String pushurl;

    //버전체크
    String device_version;
    String store_version;
    Handler handler;

    //첫설치
    SharedPreferences mShareFstExe;
    SharedPreferences.Editor fsteditor;
    private boolean mbfstexe = false;

    //리뷰창열기
    SharedPreferences openappcount;
    SharedPreferences.Editor editor;
    String count;

    //전면광고
    private InterstitialAd interstitialAd;
    int discount = 0;

    private void setFullAd() {
        interstitialAd = new InterstitialAd(this); //새 광고를 만듭니다.
        interstitialAd.setAdUnitId(getResources().getString(R.string.adID)); //이전에 String에 저장해 두었던 광고 ID를 전면 광고에 설정합니다.
        AdRequest adRequest1 = new AdRequest.Builder().build(); //새 광고요청
        interstitialAd.loadAd(adRequest1); //요청한 광고를 load 합니다.
        interstitialAd.setAdListener(new AdListener() { //전면 광고의 상태를 확인하는 리스너 등록

            @Override
            public void onAdClosed() { //전면 광고가 열린 뒤에 닫혔을 때
                AdRequest adRequest1 = new AdRequest.Builder().build();  //새 광고요청
                interstitialAd.loadAd(adRequest1); //요청한 광고를 load 합니다.
            }
        });
    }

    public void displayAD() {
        if (interstitialAd.isLoaded()) { //광고가 로드 되었을 시
            interstitialAd.show(); //보여준다
            discount = 0;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // 제목셋팅
        alertDialogBuilder.setTitle("종료 알림");
        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage("정말 종료하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 다이얼로그를 취소한다
                        dialog.cancel();
                    }
                })
                .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 프로그램을 종료한다
                        finish();
                    }
                });
        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();
        // 다이얼로그 보여주기
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BusProvider.getInstance().post(new ActivityResultEvent(requestCode, resultCode, data));
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fabric
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.main);

        //FCM 메시지 구현
        FirebaseMessaging.getInstance().subscribeToTopic("movieinformation");

        fcmtoken = FirebaseInstanceId.getInstance().getToken();
        Log.d("TAG", "Refreshed token: " + fcmtoken);

        //전면광고
        setFullAd();
        //

        //업데이트 버전체크
        new Thread() {
            public void run() {
                try {
                    store_version = MarketVersionChecker.getMarketVersion(getPackageName());
                    device_version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

                    Bundle bun = new Bundle();
                    bun.putString("store_version", store_version);
                    bun.putString("device_version", device_version);

                    Message msg = handler.obtainMessage();
                    msg.setData(bun);

                    handler.sendMessage(msg);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //첫설치
        mShareFstExe = getSharedPreferences("mShareFstExe", MODE_PRIVATE);
        fsteditor = mShareFstExe.edit();
        mbfstexe = mShareFstExe.getBoolean("mShareFstExe", false);

        if (!mbfstexe) {
            fsteditor.putBoolean("mShareFstExe", true);
            fsteditor.commit();

            //첫 설치 할 내용
            openappcount = getSharedPreferences("openappcount", MODE_PRIVATE);
            editor = openappcount.edit();
            editor.putString("opencount", "1");
            editor.commit();
        }

        //5회 실행후 리뷰작성 유도
        else {
            openappcount = getSharedPreferences("openappcount", MODE_PRIVATE);
            count = openappcount.getString("opencount", "");
            Log.d("count", count);

            switch (count) {
                case "1":
                    editor = openappcount.edit();
                    editor.putString("opencount", "2");
                    editor.commit();
                    break;
                case "2":
                    editor = openappcount.edit();
                    editor.putString("opencount", "3");
                    editor.commit();
                    break;
                case "3":
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.bonggu.www.movieinformation"));
                            startActivity(intent);
                        }
                    });
                    alert.setMessage("앱 발전을 위한 리뷰 작성 부탁드립니다");
                    alert.show();
                    editor = openappcount.edit();
                    editor.putString("opencount", "4");
                    editor.commit();
                    break;
            }
        }

        //앱업데이트 유도
        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (store_version != null) {
                    if (store_version.compareTo(device_version) > 0) {
                        // 업데이트 필요
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.bonggu.www.movieinformation"));
                                startActivity(intent);
                            }
                        });
                        alert.setMessage("최신버전으로 업데이트 후 사용을 권장해드립니다");
                        alert.show();
                    } else {
                        // 업데이트 불필요
                    }
                }
            }
        };

        search = (RelativeLayout) findViewById(R.id.main_layout_buttom_search);
        rank = (RelativeLayout) findViewById(R.id.main_layout_buttom_rank);

        searchframelayout = (FrameLayout) findViewById(R.id.main_search_fragment_container);
        rankframelayout = (FrameLayout) findViewById(R.id.main_rank_fragment_container);

        searchtext = (TextView) findViewById(R.id.main_layout_buttom_search_txt);
        ranktext = (TextView) findViewById(R.id.main_layout_buttom_rank_txt);

        search.setOnClickListener(this);
        rank.setOnClickListener(this);

        movieListFragment = new MovieListFragment();
        boxOfficeFragment = new BoxOfficeFragment();

        manager = getFragmentManager();

        tran = manager.beginTransaction();
        tran.replace(R.id.main_search_fragment_container, movieListFragment, "movieListFragment");
        tran.commit();

        //firebase analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mFirebaseAnalytics.logEvent("MAIN_START", null);

        //admob
        // Sample AdMob app ID: ca-app-pub-1782143464087714/2622839042
        MobileAds.initialize(this, "ca-app-pub-1782143464087714/2622839042");
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-1782143464087714/2622839042");
        // TODO: Add adView to your view hierarchy.

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("Ads", "onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.i("Ads", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("Ads", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                Log.i("Ads", "onAdClosed");
            }
        });

        //fcm
        if (getIntent() != null) {
            if (getIntent().getStringExtra("pushurl") != null) {
                pushurl = getIntent().getStringExtra("pushurl");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pushurl));
                startActivity(intent);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_layout_buttom_search:
                if (getSupportFragmentManager().findFragmentById(R.id.main_search_fragment_container) == null) {
                    tran = manager.beginTransaction();
                    tran.replace(searchframelayout.getId(), movieListFragment, "movieListFragment");
                    tran.commit();
                    search.setBackgroundResource(R.drawable.bi_border2);
                    searchtext.setTextColor(Color.WHITE);
                    rank.setBackgroundResource(R.drawable.bi_border);
                    ranktext.setTextColor(Color.BLACK);
                    rankframelayout.setVisibility(View.INVISIBLE);
                    searchframelayout.setVisibility(View.VISIBLE);
                } else {
                    search.setBackgroundResource(R.drawable.bi_border2);
                    searchtext.setTextColor(Color.WHITE);
                    rank.setBackgroundResource(R.drawable.bi_border);
                    ranktext.setTextColor(Color.BLACK);
                    rankframelayout.setVisibility(View.INVISIBLE);
                    searchframelayout.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.main_layout_buttom_rank:
                if (getSupportFragmentManager().findFragmentById(R.id.main_rank_fragment_container) == null) {
                    tran = manager.beginTransaction();
                    tran.replace(rankframelayout.getId(), boxOfficeFragment, "boxOfficeFragment");
                    tran.commit();
                    search.setBackgroundResource(R.drawable.bi_border);
                    searchtext.setTextColor(Color.BLACK);
                    rank.setBackgroundResource(R.drawable.bi_border2);
                    ranktext.setTextColor(Color.WHITE);
                    searchframelayout.setVisibility(View.INVISIBLE);
                    rankframelayout.setVisibility(View.VISIBLE);
                } else {
                    search.setBackgroundResource(R.drawable.bi_border);
                    searchtext.setTextColor(Color.BLACK);
                    rank.setBackgroundResource(R.drawable.bi_border2);
                    ranktext.setTextColor(Color.WHITE);
                    searchframelayout.setVisibility(View.INVISIBLE);
                    rankframelayout.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        FilterData.deleteInstance();
        super.onDestroy();
    }
}