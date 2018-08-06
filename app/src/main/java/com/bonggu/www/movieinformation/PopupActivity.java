package com.bonggu.www.movieinformation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SoundEffectConstants;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class PopupActivity extends Activity {
    private static final String TAG = "PopupActivity";

    WebView mWebview;

    String linkurl;

    //admob
    private AdView popmAdView;

    // 핸들러, 플래그
    private Handler mHandler;
    private boolean mFlag = false;

    @Override
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup);

        // 핸들러 객체 설정 (onCreate() 메소드 안에 구현!!!)
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    mFlag = false;
                }
            }
        };

        if (getIntent() != null) {
            if (getIntent().getStringExtra("linkurl") != null) {
                linkurl = getIntent().getStringExtra("linkurl");
            }
        }

        mWebview = (WebView) findViewById(R.id.popup_webview);

        mWebview.getSettings().setTextZoom(100);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setSupportMultipleWindows(true);
        mWebview.setSoundEffectsEnabled(true);
        mWebview.playSoundEffect(SoundEffectConstants.CLICK);
        mWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebview.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        mWebview.getSettings().setSupportZoom(true);//돋보기버튼 설정
        mWebview.getSettings().setUseWideViewPort(false);// 자동개행
        mWebview.getSettings().setJavaScriptEnabled(true);//자바스크립트 가능 설정
        mWebview.getSettings().setAppCacheEnabled(true);
        mWebview.getSettings().setBuiltInZoomControls(true);// 확대축소
        mWebview.setWebViewClient(new WebClient());//정의한 클래스 사용
        mWebview.getSettings().setSupportMultipleWindows(true);


        //롤리팝 이후 지원
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        if (Build.VERSION.SDK_INT >= 19) {
            mWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        mWebview.loadUrl(linkurl);

        //admob
        // Sample AdMob app ID: ca-app-pub-1782143464087714/2622839042
        MobileAds.initialize(this, "ca-app-pub-1782143464087714/2622839042");
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-1782143464087714/2622839042");
        // TODO: Add adView to your view hierarchy.

        popmAdView = (AdView) findViewById(R.id.popup_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        popmAdView.loadAd(adRequest);

        popmAdView.setAdListener(new AdListener() {
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

        if (Build.VERSION.SDK_INT >= 11) {
            getWindow().addFlags(16777216);
        }
    }

    public class WebClient extends WebViewClient {
        /*
            * 웹뷰 내 링크 터치 시 새로운 창이 뜨지 않고
            * 해당 웹뷰 안에서 새로운 페이지가 로딩되도록 함
               */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http://tv.kakao.com/v/")) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(url);
                i.setDataAndType(uri, "video/mp4");
                startActivity(i);
            } else {
                view.loadUrl(url);
                Log.d(TAG, mWebview.getUrl().toString());
            }
            return true;
        }
    }

    public class MyChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) { /*Javascript에서 alert()함수가 사용될 때 자동으로 호출되는 콜백 메소드 * 안드로이드의 AlertDialog를 작성하여 보여주면 된다 */
            new AlertDialog.Builder(PopupActivity.this).setTitle("JavaScript Alert !").setMessage(message).setPositiveButton(android.R.string.ok,/*대신 CharSequence도 가능*/ new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            }).create().show();
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        stopMediaPlayer();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        stopMediaPlayer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        stopMediaPlayer();
        super.onResume();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 백 키를 터치한 경우
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 이전 페이지를 볼 수 있다면 이전 페이지를 보여줌
            if (mWebview.getUrl().toString().contains("https://m.search.naver.com/search.naver?")) {
                AlertDialog.Builder d = new AlertDialog.Builder(this);
                d.setMessage("메인으로 돌아가시겠습니까?");
                d.setPositiveButton("아니오", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // process전체 종료
                        dialog.cancel();
                    }
                });
                d.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                d.show();
            } else {
                if (mWebview.canGoBack()) {
                    mWebview.goBack();
                    return false;
                }

                // 이전 페이지를 볼 수 없다면 백키를 한번 더 터치해서 종료
                else {
                    AlertDialog.Builder d = new AlertDialog.Builder(this);
                    d.setMessage("메인으로 돌아가시겠습니까?");
                    d.setPositiveButton("아니오", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // process전체 종료
                            dialog.cancel();
                        }
                    });
                    d.setNegativeButton("예", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    d.show();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void stopMediaPlayer() {
        MediaPlayer player = new MediaPlayer();

        if (player.isPlaying()) {
            player.stop();
        }
    }
}