package com.bonggu.www.movieinformation;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoxOfficeFragment extends Fragment implements View.OnClickListener {
    static private final String TAG = "BoxOfficeFragment";

    // Activity 관리
    Context mContext;
    Activity activity;
    //

    // 메인 SearchTicketFragment 관리
    ViewGroup view;
    //

    public RequestManager mGlideRequestManager;

    private String htmlPageUrl = "http://ticket2.movie.daum.net/movie/movieranklist.aspx";

    ListView bolistview; // 리스트뷰
    BoxOfficeAdapter boadapter; // 어댑터

    String boimage[] = new String[9999];
    String botitle[] = new String[9999];
    String bograde[] = new String[9999];
    String boopendt[] = new String[9999];
    String boadvence[] = new String[9999];
    String bolink[] = new String[9999];

    int imagenum = 0;
    int titlenum = 0;
    int gradenum = 0;
    int opendtnum = 0;
    int advencenum = 0;
    int linknum = 0;

    ProgressDialog asyncDialog;

    TextView timeview;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public BoxOfficeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 메인 SearchTicketFragment 관리
        view = (ViewGroup) inflater.inflate(R.layout.boxoffice_fragment, container, false);
        mContext = getActivity().getApplicationContext();
        activity = getActivity();
        //

        timeview = (TextView) view.findViewById(R.id.boxoff_txt);

        mGlideRequestManager = Glide.with(this);

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();

        timeview.setText(getTime() + " 예매순위");
        return view;
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    @Override
    public void onClick(View v) {

    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            asyncDialog = new ProgressDialog(activity);

            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");
            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(htmlPageUrl).get();
                Elements links = doc.select("img[class=thumb_photo");
                Elements links2 = doc.select("div[class=raking_grade");
                Elements links3 = doc.select("dl[class=list_state");
                Elements links4 = doc.select("a[class=link_boxthumb");

                for (Element link : links) {
                    boimage[imagenum] = link.attr("abs:src");
                    imagenum++;
                }

                for (Element link2 : links) {
                    String title = link2.toString();
                    Log.d(TAG, title);
                    String[] title2 = title.split("alt=");
                    String[] title3 = title2[1].split("/");
                    String title4 = title3[0].substring(1, title3[0].length()-2);
                    botitle[titlenum] = title4;
                    titlenum++;
                }

                for (Element link3 : links2) {
                    bograde[gradenum] = link3.text();
                    gradenum++;
                }

                for (Element link4 : links3) {
                    String opendt = link4.text();
                    String[] opendt2 = opendt.split("개봉일");
                    String[] opendt3 = opendt2[1].split("현재");
                    String opendt4 = opendt3[1].replaceFirst("예매율","");
                    boopendt[opendtnum] = opendt3[0];
                    boadvence[advencenum] = opendt4;
                    opendtnum++;
                    advencenum++;
                }

                for (Element link5 : links4) {
                    String link = link5.attr("abs:href");
                    bolink[linknum] = link;
                    linknum++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bolistview = (ListView) view.findViewById(R.id.boxoffice_list);
                            boadapter = new BoxOfficeAdapter(activity, mGlideRequestManager);
                            bolistview.setAdapter(boadapter);

                            for (int b = 0; b < 20; b++) {
                                boadapter.add(boimage[b], botitle[b], bograde[b], boopendt[b], boadvence[b],b+1, bolink[b]);
                            }
                            boadapter.notifyDataSetChanged();
                            //searchTextCalendarRange.setText("18.01.08 ~ 18.01.09");
                        }
                    });
                }
            }).start();

            if (asyncDialog != null) {
                asyncDialog.dismiss();
                asyncDialog = null;
            }

            ((MainActivity) activity).displayAD();
        }
    }
    @Override
    public void onStop() {
        super.onStop();

        if (asyncDialog != null) {
            asyncDialog.dismiss();
            asyncDialog = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (asyncDialog != null) {
            asyncDialog.dismiss();
            asyncDialog = null;
        }
    }
}