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

public class BoxOfficeAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<BoxOffice> mItems;
    Activity activity;
    RequestManager mRequestManager;

    // ListViewAdapter의 생성자
    public BoxOfficeAdapter(Activity act, RequestManager requestManager) {
        mItems = new ArrayList<BoxOffice>();
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
            convertView = inflater.inflate(R.layout.boxoffice_list_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView biimage = (ImageView) convertView.findViewById(R.id.bi_movie_image);
        TextView bititle = (TextView) convertView.findViewById(R.id.bi_movie_title);
        TextView bigrade = (TextView) convertView.findViewById(R.id.bi_movie_grade);
        TextView biopendt = (TextView) convertView.findViewById(R.id.bi_movie_opendt);
        TextView biadvence = (TextView) convertView.findViewById(R.id.bi_movie_advance);
        TextView birank = (TextView) convertView.findViewById(R.id.bi_movie_rank);

        Button bibutton = (Button) convertView.findViewById(R.id.bi_movie_btn);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final BoxOffice boxOffice = mItems.get(pos);

        // 아이템 내 각 위젯에 데이터 반영
        if (activity.isFinishing()) {

        } else {
            mRequestManager.load(boxOffice.getImage()).into(biimage);
        }

        bititle.setText(boxOffice.getTitle());
        biadvence.setText(boxOffice.getAdvence());
        bigrade.setText(boxOffice.getGrade());
        biopendt.setText(boxOffice.getOpendt());
        birank.setText(boxOffice.getRank() + "위");

        bibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, PopupActivity.class);
                i.putExtra("linkurl", boxOffice.getLink());
                activity.startActivity(i);
            }
        });

        return convertView;
    }

    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void add(String image, String title, String grade, String opendt, String advence, int rank, String link) {
        BoxOffice item = new BoxOffice();

        item.setAdvence(advence);
        item.setGrade(grade);
        item.setImage(image);
        item.setOpendt(opendt);
        item.setTitle(title);
        item.setRank(rank);
        item.setLink(link);

        mItems.add(item);
    }
}