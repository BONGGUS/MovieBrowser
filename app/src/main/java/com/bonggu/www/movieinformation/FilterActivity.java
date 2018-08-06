package com.bonggu.www.movieinformation;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FilterActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "FilterActivity";

    CheckBox genre1;
    CheckBox genre2;
    CheckBox genre3;
    CheckBox genre4;
    CheckBox genre5;
    CheckBox genre6;
    CheckBox genre7;
    CheckBox genre8;
    CheckBox genre9;
    CheckBox genre10;
    CheckBox genre11;
    CheckBox genre12;
    CheckBox genre13;
    CheckBox genre14;
    CheckBox genre15;
    CheckBox genre16;
    CheckBox genre17;
    CheckBox genre18;
    CheckBox genre19;
    CheckBox genre20;
    CheckBox genre21;
    CheckBox genre22;
    CheckBox genre23;
    CheckBox genre24;
    CheckBox genre25;
    CheckBox genre26;
    CheckBox genre27;
    CheckBox genre28;

    CheckBox countrykr;
    CheckBox countryjp;
    CheckBox countryus;
    CheckBox countryhk;
    CheckBox countrygb;
    CheckBox countryfr;
    CheckBox countryetc;

    ImageView backbtn;

    RelativeLayout clearbtn;
    RelativeLayout setbtn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.movie_filter);

        backbtn = (ImageView) findViewById(R.id.movie_filter_back_btn);

        clearbtn = (RelativeLayout) findViewById(R.id.movie_filter_clear_btn);
        setbtn = (RelativeLayout) findViewById(R.id.movie_filter_set_btn);

        genre1 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_1);
        genre2 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_2);
        genre3 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_3);
        genre4 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_4);
        genre5 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_5);
        genre6 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_6);
        genre7 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_7);
        genre8 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_8);
        genre9 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_9);
        genre10 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_10);
        genre11 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_11);
        genre12 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_12);
        genre13 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_13);
        genre14 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_14);
        genre15 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_15);
        genre16 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_16);
        genre17 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_17);
        genre18 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_18);
        genre19 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_19);
        genre20 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_20);
        genre21 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_21);
        genre22 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_22);
        genre23 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_23);
        genre24 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_24);
        genre25 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_25);
        genre26 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_26);
        genre27 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_27);
        genre28 = (CheckBox) findViewById(R.id.movie_filter_sort_checkbox_28);

        countrykr = (CheckBox) findViewById(R.id.movie_filter_sort2_checkbox_kr);
        countryjp = (CheckBox) findViewById(R.id.movie_filter_sort2_checkbox_jp);
        countryfr = (CheckBox) findViewById(R.id.movie_filter_sort2_checkbox_fr);
        countrygb = (CheckBox) findViewById(R.id.movie_filter_sort2_checkbox_gb);
        countryhk = (CheckBox) findViewById(R.id.movie_filter_sort2_checkbox_hk);
        countryus = (CheckBox) findViewById(R.id.movie_filter_sort2_checkbox_us);
        countryetc = (CheckBox) findViewById(R.id.movie_filter_sort2_checkbox_etc);

        FilterData.initInstance();

        Load();

        if (FilterData.getInstance().start == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(FilterActivity.this);
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();     //닫기
                }
            });
            alert.setMessage("필터 선택 안할 시 전체가 기본 필터입니다.");
            alert.show();

            FilterData.getInstance().start = 1;
        }


        setbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set();
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clear();
            }
        });

        genre1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(1);
            }
        });
        genre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(2);
            }
        });

        genre3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(3);
            }
        });

        genre4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(4);
            }
        });

        genre5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(5);
            }
        });

        genre6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(6);
            }
        });

        genre7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(7);
            }
        });

        genre8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(8);
            }
        });

        genre9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(9);
            }
        });

        genre10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(10);
            }
        });

        genre11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(11);
            }
        });

        genre12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(12);
            }
        });

        genre13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(13);
            }
        });

        genre14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(14);
            }
        });
        genre15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(15);
            }
        });

        genre16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(16);
            }
        });
        genre17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(17);
            }
        });
        genre18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(18);
            }
        });
        genre19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(19);
            }
        });
        genre20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(20);
            }
        });

        genre21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(21);
            }
        });
        genre22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(22);
            }
        });
        genre23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(23);
            }
        });
        genre24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(24);
            }
        });
        genre25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(25);
            }
        });
        genre26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(26);
            }
        });
        genre27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(27);
            }
        });
        genre28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genrecheck(28);
            }
        });

        countrykr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryjp.setChecked(false);
                countryfr.setChecked(false);
                countrygb.setChecked(false);
                countryhk.setChecked(false);
                countryus.setChecked(false);
                countryetc.setChecked(false);
            }
        });

        countryjp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countrykr.setChecked(false);
                countryfr.setChecked(false);
                countrygb.setChecked(false);
                countryhk.setChecked(false);
                countryus.setChecked(false);
                countryetc.setChecked(false);
            }
        });

        countryfr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countrykr.setChecked(false);
                countryjp.setChecked(false);
                countrygb.setChecked(false);
                countryhk.setChecked(false);
                countryus.setChecked(false);
                countryetc.setChecked(false);
            }
        });

        countrygb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countrykr.setChecked(false);
                countryjp.setChecked(false);
                countryfr.setChecked(false);
                countryhk.setChecked(false);
                countryus.setChecked(false);
                countryetc.setChecked(false);
            }
        });

        countryhk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countrykr.setChecked(false);
                countryjp.setChecked(false);
                countryfr.setChecked(false);
                countrygb.setChecked(false);
                countryus.setChecked(false);
                countryetc.setChecked(false);
            }
        });

        countryus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countrykr.setChecked(false);
                countryjp.setChecked(false);
                countryfr.setChecked(false);
                countrygb.setChecked(false);
                countryhk.setChecked(false);
                countryetc.setChecked(false);
            }
        });

        countryetc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countrykr.setChecked(false);
                countryjp.setChecked(false);
                countryfr.setChecked(false);
                countrygb.setChecked(false);
                countryhk.setChecked(false);
                countryus.setChecked(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    public void genrecheck(int num) {
        switch (num) {
            case 1:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 2:
                genre1.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 3:
                genre2.setChecked(false);
                genre1.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 4:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre1.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 5:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre1.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 6:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre1.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 7:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre1.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 8:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre1.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 9:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre1.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 10:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre1.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 11:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre1.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 12:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre1.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 13:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre1.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 14:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre1.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 15:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre1.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 16:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre1.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 17:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre1.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 18:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre1.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 19:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre1.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 20:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre1.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 21:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre1.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 22:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre1.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 23:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre1.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 24:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre1.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 25:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre1.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 26:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre1.setChecked(false);
                genre27.setChecked(false);
                genre28.setChecked(false);
                break;
            case 27:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre1.setChecked(false);
                genre28.setChecked(false);
                break;
            case 28:
                genre2.setChecked(false);
                genre3.setChecked(false);
                genre4.setChecked(false);
                genre5.setChecked(false);
                genre6.setChecked(false);
                genre7.setChecked(false);
                genre8.setChecked(false);
                genre9.setChecked(false);
                genre10.setChecked(false);
                genre11.setChecked(false);
                genre12.setChecked(false);
                genre13.setChecked(false);
                genre14.setChecked(false);
                genre15.setChecked(false);
                genre16.setChecked(false);
                genre17.setChecked(false);
                genre18.setChecked(false);
                genre19.setChecked(false);
                genre20.setChecked(false);
                genre21.setChecked(false);
                genre22.setChecked(false);
                genre23.setChecked(false);
                genre24.setChecked(false);
                genre25.setChecked(false);
                genre26.setChecked(false);
                genre27.setChecked(false);
                genre1.setChecked(false);
                break;
        }
    }

    public void Clear() {
        genre1.setChecked(false);
        genre2.setChecked(false);
        genre3.setChecked(false);
        genre4.setChecked(false);
        genre5.setChecked(false);
        genre6.setChecked(false);
        genre7.setChecked(false);
        genre8.setChecked(false);
        genre9.setChecked(false);
        genre10.setChecked(false);
        genre11.setChecked(false);
        genre12.setChecked(false);
        genre13.setChecked(false);
        genre14.setChecked(false);
        genre15.setChecked(false);
        genre16.setChecked(false);
        genre17.setChecked(false);
        genre18.setChecked(false);
        genre19.setChecked(false);
        genre20.setChecked(false);
        genre21.setChecked(false);
        genre22.setChecked(false);
        genre23.setChecked(false);
        genre24.setChecked(false);
        genre25.setChecked(false);
        genre26.setChecked(false);
        genre27.setChecked(false);
        genre28.setChecked(false);
        countrykr.setChecked(false);
        countryjp.setChecked(false);
        countryfr.setChecked(false);
        countrygb.setChecked(false);
        countryhk.setChecked(false);
        countryetc.setChecked(false);
        countryus.setChecked(false);
    }

    public void Set() {
        if (genre1.isChecked() == true) {
            FilterData.getInstance().genre = "1";
        } else if (genre2.isChecked() == true) {
            FilterData.getInstance().genre = "2";
        } else if (genre3.isChecked() == true) {
            FilterData.getInstance().genre = "3";
        } else if (genre4.isChecked() == true) {
            FilterData.getInstance().genre = "4";
        } else if (genre5.isChecked() == true) {
            FilterData.getInstance().genre = "5";
        } else if (genre6.isChecked() == true) {
            FilterData.getInstance().genre = "6";
        } else if (genre7.isChecked() == true) {
            FilterData.getInstance().genre = "7";
        } else if (genre8.isChecked() == true) {
            FilterData.getInstance().genre = "8";
        } else if (genre9.isChecked() == true) {
            FilterData.getInstance().genre = "9";
        } else if (genre10.isChecked() == true) {
            FilterData.getInstance().genre = "10";
        } else if (genre11.isChecked() == true) {
            FilterData.getInstance().genre = "11";
        } else if (genre12.isChecked() == true) {
            FilterData.getInstance().genre = "12";
        } else if (genre13.isChecked() == true) {
            FilterData.getInstance().genre = "13";
        } else if (genre14.isChecked() == true) {
            FilterData.getInstance().genre = "14";
        } else if (genre15.isChecked() == true) {
            FilterData.getInstance().genre = "15";
        } else if (genre16.isChecked() == true) {
            FilterData.getInstance().genre = "16";
        } else if (genre17.isChecked() == true) {
            FilterData.getInstance().genre = "17";
        } else if (genre18.isChecked() == true) {
            FilterData.getInstance().genre = "18";
        } else if (genre19.isChecked() == true) {
            FilterData.getInstance().genre = "19";
        } else if (genre20.isChecked() == true) {
            FilterData.getInstance().genre = "20";
        } else if (genre21.isChecked() == true) {
            FilterData.getInstance().genre = "21";
        } else if (genre22.isChecked() == true) {
            FilterData.getInstance().genre = "22";
        } else if (genre23.isChecked() == true) {
            FilterData.getInstance().genre = "23";
        } else if (genre24.isChecked() == true) {
            FilterData.getInstance().genre = "24";
        } else if (genre25.isChecked() == true) {
            FilterData.getInstance().genre = "25";
        } else if (genre26.isChecked() == true) {
            FilterData.getInstance().genre = "26";
        } else if (genre27.isChecked() == true) {
            FilterData.getInstance().genre = "27";
        } else if (genre28.isChecked() == true) {
            FilterData.getInstance().genre = "28";
        } else {
            FilterData.getInstance().genre = "null";
        }

        if (countrykr.isChecked() == true) {
            FilterData.getInstance().country = "KR";
        } else if (countryjp.isChecked() == true) {
            FilterData.getInstance().country = "JP";
        } else if (countryetc.isChecked() == true) {
            FilterData.getInstance().country = "ETC";
        } else if (countryus.isChecked() == true) {
            FilterData.getInstance().country = "US";
        } else if (countrygb.isChecked() == true) {
            FilterData.getInstance().country = "GB";
        } else if (countryhk.isChecked() == true) {
            FilterData.getInstance().country = "HK";
        } else if (countryfr.isChecked() == true) {
            FilterData.getInstance().country = "FR";
        } else {
            FilterData.getInstance().country = "null";
        }
    }

    public void Load() {
        if (FilterData.getInstance() != null) {
            if (!FilterData.getInstance().genre.equals("null")) {
                switch (FilterData.getInstance().genre) {
                    case "1":
                        genre1.setChecked(true);
                        break;
                    case "2":
                        genre2.setChecked(true);
                        break;
                    case "3":
                        genre3.setChecked(true);
                        break;
                    case "4":
                        genre4.setChecked(true);
                        break;
                    case "5":
                        genre5.setChecked(true);
                        break;
                    case "6":
                        genre6.setChecked(true);
                        break;
                    case "7":
                        genre7.setChecked(true);
                        break;
                    case "8":
                        genre8.setChecked(true);
                        break;
                    case "9":
                        genre9.setChecked(true);
                        break;
                    case "10":
                        genre10.setChecked(true);
                        break;
                    case "11":
                        genre11.setChecked(true);
                        break;
                    case "12":
                        genre12.setChecked(true);
                        break;
                    case "13":
                        genre13.setChecked(true);
                        break;
                    case "14":
                        genre14.setChecked(true);
                        break;
                    case "15":
                        genre15.setChecked(true);
                        break;
                    case "16":
                        genre16.setChecked(true);
                        break;
                    case "17":
                        genre17.setChecked(true);
                        break;
                    case "18":
                        genre18.setChecked(true);
                        break;
                    case "19":
                        genre19.setChecked(true);
                        break;
                    case "20":
                        genre20.setChecked(true);
                        break;
                    case "21":
                        genre21.setChecked(true);
                        break;
                    case "22":
                        genre22.setChecked(true);
                        break;
                    case "23":
                        genre23.setChecked(true);
                        break;
                    case "24":
                        genre24.setChecked(true);
                        break;
                    case "25":
                        genre25.setChecked(true);
                        break;
                    case "26":
                        genre26.setChecked(true);
                        break;
                    case "27":
                        genre27.setChecked(true);
                        break;
                    case "28":
                        genre28.setChecked(true);
                        break;
                }
            }
            if (!FilterData.getInstance().country.equals("null")) {
                switch (FilterData.getInstance().country) {
                    case "KR":
                        countrykr.setChecked(true);
                        break;
                    case "JP":
                        countryjp.setChecked(true);
                        break;
                    case "US":
                        countryus.setChecked(true);
                        break;
                    case "HK":
                        countryhk.setChecked(true);
                        break;
                    case "GB":
                        countrygb.setChecked(true);
                        break;
                    case "FR":
                        countryfr.setChecked(true);
                        break;
                    case "ETC":
                        countryetc.setChecked(true);
                        break;
                }
            }
        }
    }
}