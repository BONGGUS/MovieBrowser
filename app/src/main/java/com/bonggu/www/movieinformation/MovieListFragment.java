package com.bonggu.www.movieinformation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import static android.app.Activity.RESULT_OK;

public class MovieListFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MovieListFragment";
    public static final int REQUEST_CODE_SHOW_FILTER = 2018;

    // Activity 관리
    Context mContext;
    Activity activity;
    //

    // 메인 SearchTicketFragment 관리
    ViewGroup view;
    //

    ImageView mltopbtn;

    public RequestManager mGlideRequestManager;

    ListView mllistview; // 리스트뷰
    MovieListAdapter mladapter; // 어댑터

    NestedScrollView mlnestedScrollView; //스크롤뷰

    private boolean mllastItemVisibleFlag = false;    // 리스트 스크롤이 마지막 셀(맨 바닥)로 이동했는지 체크할 변수
    private boolean mlmLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수
    private boolean mltouchvalue = true; //터치여부

    EditText mlsearchedittxt;
    Button mlsearchbtn;
    String mlsearchname;

    String mlimage[] = new String[9999];
    String mltitle[] = new String[9999];
    String mlpubdate[] = new String[9999];
    String mldirector[] = new String[9999];
    String mlactor[] = new String[9999];
    String mllink[] = new String[9999];
    String mlsubtitle[] = new String[9999];
    int mluserrating[] = new int[9999];

    int imagenum = 0;
    int titlenum = 0;
    int directornum = 0;
    int pubdatenum = 0;
    int actornum = 0;
    int linknum = 0;
    int userratingnum = 0;
    int datanum = 0;

    ProgressDialog asyncDialog;

    Apimovielist apimovielist;
    Boolean apioption = false;

    int start = 0;
    int displaystart = 1;
    int display = 50;
    int start2 = 0;
    int total = 0;

    boolean fullad = false;
    RelativeLayout filterbtn;

    public MovieListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.movielist_fragment, container, false);
        mContext = getActivity().getApplicationContext();
        activity = getActivity();
        //

        mGlideRequestManager = Glide.with(this);

        mlsearchbtn = (Button) view.findViewById(R.id.movielist_search_btn);
        mlsearchbtn.setOnClickListener(this);
        mlsearchedittxt = (EditText) view.findViewById(R.id.movielist_search_edittxt);

        mlnestedScrollView = (NestedScrollView) view.findViewById(R.id.movielist_scroll);

        mltopbtn = (ImageView) view.findViewById(R.id.movielist_topbtn);

        mltopbtn.setOnClickListener(this);

        filterbtn = (RelativeLayout) view.findViewById(R.id.movielist_search_filter_btn);
        filterbtn.setOnClickListener(this);

        return view;
    }

    public void scrolltop() {
        mlnestedScrollView.post(new Runnable() {
            @Override
            public void run() {
                mllistview.setSelection(0);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mlsearchbtn) {
            if (mlsearchedittxt.getText().toString().equals("")) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alert.setMessage("원하시는 검색어를 입력해주세요");
                        alert.show();
                    }
                });
            } else {
                datanum = 0;
                imagenum = 0;
                titlenum = 0;
                directornum = 0;
                pubdatenum = 0;
                actornum = 0;
                linknum = 0;
                userratingnum = 0;
                start = 0;
                displaystart = 1;
                start2 = 0;
                total = 0;
                mlsearchname = mlsearchedittxt.getText().toString();

                if (apioption == true) {
                    apimovielist.cancel(true);
                }

                apimovielist = new Apimovielist();
                apimovielist.execute(0);
                apioption = true;

                if (fullad == false) {
                    ((MainActivity) activity).displayAD();
                    fullad = true;
                }
            }
        }

        if (v == mltopbtn) {
            if (mllistview != null) {
                scrolltop();
            }
        }

        if (v == filterbtn) {
            Intent i = new Intent(activity, FilterActivity.class);
            getActivity().startActivityForResult(i, REQUEST_CODE_SHOW_FILTER);
        }
    }

    public void APIMovieSearch() {
        String clientId = "3e6CBuNux7wRO09oT8Eb";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "_vkHbPoo0o";//애플리케이션 클라이언트 시크릿값";

        try {
            String text = URLEncoder.encode(mlsearchname, "UTF-8");
            String apiURL = null;
            //String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text; // json 결과
            if (FilterData.getInstance() != null) {
                if (!FilterData.getInstance().country.equals("null")) {
                    if (!FilterData.getInstance().genre.equals("null")) {
                        apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text + "&start=" + displaystart + "&display=" + display + "&country=" + FilterData.getInstance().country + "&genre=" + FilterData.getInstance().genre; // xml 결과
                    } else {
                        apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text + "&start=" + displaystart + "&display=" + display + "&country=" + FilterData.getInstance().country; // xml 결과
                    }
                } else if(!FilterData.getInstance().genre.equals("null")) {
                    apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text + "&start=" + displaystart + "&display=" + display + "&genre=" + FilterData.getInstance().genre; // xml 결과
                } else {
                    apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text + "&start=" + displaystart + "&display=" + display; // xml 결과
                }
            } else {
                apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text + "&start=" + displaystart + "&display=" + display; // xml 결과
            }
            URL url = new URL(apiURL);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); //openConnection 해당 요청에 대해서 쓸 수 있는 connection 객체

            urlConn.setRequestMethod("GET");
            urlConn.setRequestProperty("X-Naver-Client-ID", clientId);
            urlConn.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            String data = "";
            String msg = null;

            while ((msg = br.readLine()) != null) {
                data += msg;
            }
            Log.d(TAG, data);

            //읽어들인 JSON포맷의 데이터를 JSON객체로 변환
            JSONObject json = new JSONObject(data);

            total = json.getInt("total");

            JSONArray jobj = json.getJSONArray("items");

            datanum = jobj.length();

            for (int i = 0; i < datanum; i++) {
                JSONObject jsonObject = jobj.getJSONObject(i);
                mltitle[start + i] = jsonObject.getString("title");
                mllink[start + i] = jsonObject.getString("link");
                mlimage[start + i] = jsonObject.getString("image");
                mlsubtitle[start + i] = jsonObject.getString("subtitle");
                mlpubdate[start + i] = jsonObject.getString("pubDate");
                mldirector[start + i] = jsonObject.getString("director");
                mlactor[start + i] = jsonObject.getString("actor");
                mluserrating[start + i] = jsonObject.getInt("userRating");
            }

            start = start + datanum;
            Log.d(TAG, "start=" + start);

            if (datanum > 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (start2 == 0) {
                                    mllistview = (ListView) view.findViewById(R.id.movielist_list);
                                    mladapter = new MovieListAdapter(activity, mGlideRequestManager);
                                    mllistview.setAdapter(mladapter);

                                    mllistview.setOnScrollListener(new AbsListView.OnScrollListener() {
                                        @Override
                                        public void onScrollStateChanged(AbsListView view, int scrollState) {
                                            // 1. OnScrollListener.SCROLL_STATE_IDLE : 스크롤이 이동하지 않을때의 이벤트(즉 스크롤이 멈추었을때).
                                            // 2. lastItemVisibleFlag : 리스트뷰의 마지막 셀의 끝에 스크롤이 이동했을때.
                                            // 3. mLockListView == false : 데이터 리스트에 다음 데이터를 불러오는 작업이 끝났을때.
                                            // 1, 2, 3 모두가 true일때 다음 데이터를 불러온다.
                                            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mllastItemVisibleFlag && mlmLockListView == false) {
                                                // 화면이 바닦에 닿을때 처리
                                                // 로딩중을 알리는 프로그레스바를 보인다.
                                                // 다음 데이터를 불러온다.
                                                if (mltouchvalue == true) {
                                                    mltouchvalue = false;
                                                    if (total > start + display) {
                                                        apimovielist.cancel(true);
                                                        apimovielist = new Apimovielist();
                                                        apimovielist.execute(0);
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                            // firstVisibleItem : 화면에 보이는 첫번째 리스트의 아이템 번호.
                                            // visibleItemCount : 화면에 보이는 리스트 아이템의 갯수
                                            // totalItemCount : 리스트 전체의 총 갯수
                                            // 리스트의 갯수가 0개 이상이고, 화면에 보이는 맨 하단까지의 아이템 갯수가 총 갯수보다 크거나 같을때.. 즉 리스트의 끝일때. true
                                            mllastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount - 20);
                                        }
                                    });
                                }

                                for (int b = start2; b < start; b++) {
                                    mladapter.add(mltitle[b], mlimage[b], mllink[b], mlactor[b], mldirector[b], mlpubdate[b], mluserrating[b]);
                                }
                                Log.d(TAG, "start=" + start);

                                mladapter.notifyDataSetChanged();
                                start2 = start;
                                displaystart = start + 1;
                                mlmLockListView = false;
                                mltouchvalue = true;
                            }
                        });
                    }
                }).start();
            } else {
            }
        } catch (
                Exception e)

        {
        }
    }

    public class Apimovielist extends AsyncTask<Integer, Integer, Integer> {
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
        protected Integer doInBackground(Integer... integers) {
            APIMovieSearch();

            return 0;
        }

        @Override
        protected void onProgressUpdate(Integer... params) {

        }

        @Override
        protected void onPostExecute(Integer result) {
            if (asyncDialog != null) {
                asyncDialog.dismiss();
                asyncDialog = null;
            }

            if (total == 0) {
                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                    }
                });
                alert.setMessage("검색값이 없습니다");
                alert.show();
            }
            super.onPostExecute(result);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onDestroyView() {
        BusProvider.getInstance().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void onActivityResult(ActivityResultEvent activityResultEvent) {
        if (activityResultEvent.getRequestCode() == REQUEST_CODE_SHOW_FILTER) {
            if(activityResultEvent.getResultCode() == RESULT_OK) {
                // 필터 적용 후 코드
                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                    }
                });
                alert.setMessage("필터가 새로 적용되었습니다. 검색을 시작해주세요.");
                alert.show();
                //
            }
        }
        onActivityResult(activityResultEvent.getRequestCode(), activityResultEvent.getResultCode(), activityResultEvent.getData());
    }
}