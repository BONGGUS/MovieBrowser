package com.bonggu.www.movieinformation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class MovieListAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<Movie> mItems;
    Activity activity;
    RequestManager mRequestManager;

    // ListViewAdapter의 생성자
    public MovieListAdapter(Activity act, RequestManager requestManager) {
        mItems = new ArrayList<Movie>();
        activity = act;
        mRequestManager = requestManager;
    }
    // 현재 아이템의 수를 리턴
    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int postion) {
        return mItems.get(postion);
    }

    @Override
    public long getItemId(int postion) {
        return postion;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_아이템" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.movie_list_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        Button mibutton = (Button) convertView.findViewById(R.id.mi_movie_btn);
        ImageView miimage = (ImageView) convertView.findViewById(R.id.mi_movie_image);
        TextView mititle = (TextView) convertView.findViewById(R.id.mi_movie_title);
        TextView migrade = (TextView) convertView.findViewById(R.id.mi_movie_grade);
        TextView miopendt = (TextView) convertView.findViewById(R.id.mi_movie_opendt);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final Movie movie = mItems.get(pos);

        // 아이템 내 각 위젯에 데이터 반영
        if (activity.isFinishing()) {

        } else {
            mRequestManager.load(movie.getImage()).into(miimage);

            if(movie.getImage().equals("")) {
                miimage.setImageResource(R.drawable.no_image);
            }
        }

        String movietitle = movie.getTitle().replace("<b>","");
        String movietitle2 = movietitle.replace("</b>","");

        mititle.setText(movietitle2);

        if(movie.getUserRating()==0) {
            migrade.setText("평점 없음");
        } else {
            migrade.setText("평점 " + String.valueOf(movie.getUserRating()));
        }

        miopendt.setText(movie.getPubDate() + "년 개봉영화");

        mibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, PopupActivity.class);
                i.putExtra("linkurl", movie.getLink());
                activity.startActivity(i);
            }
        });

        return convertView;
    }

    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void add(String title, String image, String link, String actor, String director, String pubdate, int userrating) {
        Movie item = new Movie();

        item.setLink(link);
        item.setImage(image);
        item.setActor(actor);
        item.setDirector(director);
        item.setPubDate(pubdate);
        item.setTitle(title);
        item.setUserRating(userrating);
        mItems.add(item);
    }
}