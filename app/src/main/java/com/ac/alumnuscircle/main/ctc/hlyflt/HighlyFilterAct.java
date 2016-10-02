/**
 * @author Zhengfan
 * @date 16.08.31
 * @version 1
 * 功能：实现高级筛选的界面。
 */

package com.ac.alumnuscircle.main.ctc.hlyflt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.main.ctc.hlyflt.parsedata.ParseLocation;
import com.ac.alumnuscircle.main.ctc.hlyflt.parsedata.ParseMajor;
import com.ac.alumnuscircle.main.ctc.hlyflt.parsedata.ParseYear;
import com.ac.alumnuscircle.module.PickerView;


import java.util.ArrayList;
import java.util.List;

public class HighlyFilterAct extends Activity {

    private Intent resultIntent;
    public static final int HighlyFilterAct_RESULT_CODE = 0x10086;

    private LayoutInflater layoutInflater;

    private LinearLayout back;
    private LinearLayout submit;
    private Button ensure;

    /**
     * 上传的最终筛选数据流。
     */
    private ArrayList<String> majorFilterInfoList;
    private String minYear;
    private String maxYear;
    private ArrayList<String> locationFilterInfoList;

    /**
     * 专业筛选的按钮和布局。
     */
    private LinearLayout majorHeadLlyt;
    private TextView majorLeftTv;
    private TextView majorRightTv;
    private LinearLayout majorJar;
    private ArrayList<LinearLayout> majorList;
    private ArrayList<TextView> majorInfoTvList;
    private ArrayList<ImageButton> majorRemoveImgbtnList;

    /**
     * 年份筛选的按钮和布局。
     */
    private LinearLayout yearHeadLlyt;
    private TextView yearLeftTv;
    private TextView yearRightTv;

    /**
     * 地域筛选的按钮和布局。
     */
    private LinearLayout locationHeadLlyt;
    private TextView locationLeftTv;
    private TextView locationMiddleTv;
    private TextView locationRightTv;
    private LinearLayout locationJar;
    private ArrayList<LinearLayout> locationList;
    private ArrayList<TextView> locationInfoTvList;
    private ArrayList<ImageButton> locationRemoveImgbtnList;

    /**
     * 底部承载PickerView的罐头布局。
     */
    private FrameLayout pvJar;

    /**
     * 底部PickerView的三个线性布局，动态添加。
     */
    private LinearLayout majorPvLlyt;
    private LinearLayout yearPvLlyt;
    private LinearLayout locationPvLlyt;

    /**
     * 状态标识：
     * 1表示专业筛选。
     * 2表示年份筛选。
     * 3表示地域筛选。
     */
    private static final int MAJOR_STATE = 0;
    private static final int YEAR_STATE = 1;
    private static final int LOCATION_STATE = 2;
    private int currentState = MAJOR_STATE;

    /**
     * 三个布局内的各类PickerView。
     */
    private PickerView pvlayout_majorpv_pvleft;
    private PickerView pvlayout_majorpv_pvright;

    private PickerView pvlayout_yearpv_pvleft;
    private PickerView pvlayout_yearpv_pvright;

    private PickerView pvlayout_locationpv_pvleft;
    private PickerView pvlayout_locationpv_pvmiddle;
    private PickerView pvlayout_locationpv_pvright;

    /**
     * 数据生成器。
     */
    private ParseMajor parseMajor;
    private ParseYear parseYear;
    private ParseLocation parseLocation;

    /**
     * 三个布局内的各类PickerView的数据。
     */
    private List<String> pvlayout_majorpv_pvleft_data;
    private List<String> pvlayout_majorpv_pvright_data;

    private List<String> pvlayout_yearpv_pvleft_data;
    private List<String> pvlayout_yearpv_pvright_data;

    private List<String> pvlayout_locationpv_pvleft_data;
    private List<String> pvlayout_locationpv_pvmiddle_data;
    private List<String> pvlayout_locationpv_pvright_data;

    private int removeMajorItemId = 0;
    private int removeLocationItemId = 0;

    private static final int REMOVE_MAJOR_ITEM = 0x74138;
    private static final int REMOVE_LOCATION_ITEM = 0x8259;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REMOVE_MAJOR_ITEM:
                    removeMajorItem(removeMajorItemId);
                    break;
                case REMOVE_LOCATION_ITEM:
                    removeLocationItem(removeLocationItemId);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hlyflt_highlyfilteract);
        init();
    }

    private void init() {
        resultIntent = new Intent();
        layoutInflater = getLayoutInflater();
        parseMajor = new ParseMajor(getApplicationContext());
        parseYear = new ParseYear();
        parseLocation = new ParseLocation(getApplicationContext());

        initView();
        initData();
    }

    private void initView() {
        back = (LinearLayout) findViewById(R.id.hlyflt_highlyfilteract_tlb_back_llyt);
        submit = (LinearLayout) findViewById(R.id.hlyflt_highlyfilteract_tlb_submit_llyt);
        ensure = (Button) findViewById(R.id.hlyflt_highlyfilteract_ensure_btn);

        initMajor();
        initYear();
        initLocation();

        initPickerViewLayout();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitToServer();
            }
        });

        ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentState) {
                    case MAJOR_STATE:
                        addMajorItem();
                        break;
                    case YEAR_STATE:
                        addYearItem();
                        break;
                    case LOCATION_STATE:
                        addLocationItem();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initData() {
        initPickerViewData();
        initSetDefaultSelectedItem();
    }

    private void initMajor() {
        majorLeftTv = (TextView) findViewById(R.id.hlyflt_highlyfilteract_major_left_tv);
        majorRightTv = (TextView) findViewById(R.id.hlyflt_highlyfilteract_major_right_tv);
        majorHeadLlyt = (LinearLayout) findViewById(R.id.hlyflt_highlyfilteract_majorHead_llyt);
        majorJar = (LinearLayout) findViewById(R.id.hlyflt_highlyfilteract_majorjar_llyt);
        majorList = new ArrayList<LinearLayout>();
        majorInfoTvList = new ArrayList<TextView>();
        majorRemoveImgbtnList = new ArrayList<ImageButton>();
        majorFilterInfoList = new ArrayList<String>();

        majorHeadLlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchState(MAJOR_STATE);
            }
        });
    }

    private void initYear() {
        yearLeftTv = (TextView) findViewById(R.id.hlyflt_highlyfilteract_year_left_tv);
        yearRightTv = (TextView) findViewById(R.id.hlyflt_highlyfilteract_year_right_tv);
        yearHeadLlyt = (LinearLayout) findViewById(R.id.hlyflt_highlyfilteract_YearHead_llyt);

        yearHeadLlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchState(YEAR_STATE);
            }
        });
    }

    private void initLocation() {
        locationLeftTv = (TextView) findViewById(R.id.hlyflt_highlyfilteract_location_left_tv);
        locationMiddleTv = (TextView) findViewById(R.id.hlyflt_highlyfilteract_location_middle_tv);
        locationRightTv = (TextView) findViewById(R.id.hlyflt_highlyfilteract_location_right_tv);
        locationHeadLlyt = (LinearLayout) findViewById(R.id.hlyflt_highlyfilteract_locationHead_llyt);
        locationJar = (LinearLayout) findViewById(R.id.hlyflt_highlyfilteract_locationjar_llyt);
        locationList = new ArrayList<LinearLayout>();
        locationInfoTvList = new ArrayList<TextView>();
        locationRemoveImgbtnList = new ArrayList<ImageButton>();
        locationFilterInfoList = new ArrayList<String>();

        locationHeadLlyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchState(LOCATION_STATE);
            }
        });
    }

    private void initPickerViewLayout() {
        majorPvLlyt = (LinearLayout) layoutInflater.inflate(R.layout.pvlayout_majorpv, pvJar, false);
        yearPvLlyt = (LinearLayout) layoutInflater.inflate(R.layout.pvlayout_yearpv, pvJar, false);
        locationPvLlyt = (LinearLayout) layoutInflater.inflate(R.layout.pvlayout_locationpv, pvJar, false);
        pvJar = (FrameLayout) findViewById(R.id.hlyflt_highlyfilteract_pvjar_flyt);
        switchState(MAJOR_STATE);

        pvlayout_majorpv_pvleft = (PickerView) majorPvLlyt.findViewById(R.id.pvlayout_majorpv_pvleft);
        pvlayout_majorpv_pvright = (PickerView) majorPvLlyt.findViewById(R.id.pvlayout_majorpv_pvright);

        pvlayout_yearpv_pvleft = (PickerView) yearPvLlyt.findViewById(R.id.pvlayout_yearpv_pvleft);
        pvlayout_yearpv_pvright = (PickerView) yearPvLlyt.findViewById(R.id.pvlayout_yearpv_pvright);

        pvlayout_locationpv_pvleft = (PickerView) locationPvLlyt.findViewById(R.id.pvlayout_locationpv_pvleft);
        pvlayout_locationpv_pvmiddle = (PickerView) locationPvLlyt.findViewById(R.id.pvlayout_locationpv_pvmiddle);
        pvlayout_locationpv_pvright = (PickerView) locationPvLlyt.findViewById(R.id.pvlayout_locationpv_pvright);

        pvlayout_majorpv_pvleft.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                majorLeftPvSelected(text);
            }
        });

        pvlayout_majorpv_pvright.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                majorRightPvSelected(text);
            }
        });

        pvlayout_yearpv_pvleft.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                yearLeftPvSelected(text);
            }
        });

        pvlayout_yearpv_pvright.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                yearRightPvSelected(text);
            }
        });

        pvlayout_locationpv_pvleft.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                locationLeftPvSelected(text);
            }
        });

        pvlayout_locationpv_pvmiddle.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                locationMiddlePvSelected(text);
            }
        });

        pvlayout_locationpv_pvright.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                locationRightPvSelected(text);
            }
        });
    }

    private void initPickerViewData() {
        pvlayout_majorpv_pvleft_data = new ArrayList<String>();
        pvlayout_majorpv_pvright_data = new ArrayList<String>();
        pvlayout_yearpv_pvleft_data = new ArrayList<String>();
        pvlayout_yearpv_pvright_data = new ArrayList<String>();
        pvlayout_locationpv_pvleft_data = new ArrayList<String>();
        pvlayout_locationpv_pvmiddle_data = new ArrayList<String>();
        pvlayout_locationpv_pvright_data = new ArrayList<String>();

        pvlayout_majorpv_pvleft_data = parseMajor.parseMajorLeftData();
        pvlayout_majorpv_pvleft.setData(pvlayout_majorpv_pvleft_data);
        majorLeftPvSelected(pvlayout_majorpv_pvleft_data.get(pvlayout_majorpv_pvleft_data.size() / 2));

        pvlayout_yearpv_pvleft_data = parseYear.parseYearLeftData();
        pvlayout_yearpv_pvleft.setData(pvlayout_yearpv_pvleft_data);
        yearLeftPvSelected(pvlayout_yearpv_pvleft_data.get(pvlayout_yearpv_pvleft_data.size() / 2));

        pvlayout_locationpv_pvleft_data = parseLocation.parseLocationLeftData();
        pvlayout_locationpv_pvleft.setData(pvlayout_locationpv_pvleft_data);
        locationLeftPvSelected(pvlayout_locationpv_pvleft_data.get(pvlayout_locationpv_pvleft_data.size() / 2));
    }

    private void initSetDefaultSelectedItem() {
        majorLeftTv.setText(pvlayout_majorpv_pvleft_data.get(pvlayout_majorpv_pvleft_data.size() / 2));
        majorRightTv.setText(pvlayout_majorpv_pvright_data.get(pvlayout_majorpv_pvright_data.size() / 2));

        yearLeftTv.setText(pvlayout_yearpv_pvleft_data.get(pvlayout_yearpv_pvleft_data.size() / 2));
        yearRightTv.setText(pvlayout_yearpv_pvright_data.get(pvlayout_yearpv_pvright_data.size() / 2));

        locationLeftTv.setText(pvlayout_locationpv_pvleft_data.get(pvlayout_locationpv_pvleft_data.size() / 2));
        locationMiddleTv.setText(pvlayout_locationpv_pvmiddle_data.get(pvlayout_locationpv_pvmiddle_data.size() / 2));
        locationRightTv.setText(pvlayout_locationpv_pvright_data.get(pvlayout_locationpv_pvright_data.size() / 2));
    }

    private void switchState(int state) {
        switch (state) {
            case MAJOR_STATE:
                currentState = MAJOR_STATE;
                pvJar.removeAllViews();
                pvJar.addView(majorPvLlyt);
                break;
            case YEAR_STATE:
                currentState = YEAR_STATE;
                pvJar.removeAllViews();
                pvJar.addView(yearPvLlyt);
                yearLeftTv.setTextColor(Color.rgb(154, 154, 154));
                yearRightTv.setTextColor(Color.rgb(154, 154, 154));
                yearLeftTv.setTextSize(16);
                yearRightTv.setTextSize(16);
                break;
            case LOCATION_STATE:
                currentState = LOCATION_STATE;
                pvJar.removeAllViews();
                pvJar.addView(locationPvLlyt);
                break;
            default:
                break;
        }
    }

    private void addMajorItem() {
        String left = majorLeftTv.getText().toString();
        String right = majorRightTv.getText().toString();
        String info = left + "  ·  " + right;
        if (left.equals("所有学院")) {
            left = "";
        }
        if (right.equals("所有专业")) {
            right = "";
        }
        String temp = "\"" + "_" + left + "_" + right + "\"";
        if (majorFilterInfoList.contains(temp)) {
            Toast.makeText(getApplicationContext(), "您已经选中了该选项。", Toast.LENGTH_SHORT).show();
            return;
        }
        final LinearLayout majorItem = (LinearLayout) layoutInflater.inflate(R.layout.hlyflt_highlyfilteract_major_item, majorJar, false);
        final TextView infoTv = (TextView) majorItem.findViewById(R.id.hlyflt_highlyfilteract_major_item_info_tv);
        final ImageButton removeImgbtn = (ImageButton) majorItem.findViewById(R.id.hlyflt_highlyfilteract_major_item_remove_imgbtn);
        infoTv.setText(info);
        removeImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeMajorItemId = majorRemoveImgbtnList.indexOf(v);
                handler.sendEmptyMessage(REMOVE_MAJOR_ITEM);
            }
        });
        majorInfoTvList.add(infoTv);
        majorRemoveImgbtnList.add(removeImgbtn);
        majorList.add(majorItem);
        majorJar.addView(majorItem);
        majorFilterInfoList.add(temp);

    }

    private void addYearItem() {
        minYear = yearLeftTv.getText().toString();
        maxYear = yearRightTv.getText().toString();
        yearLeftTv.setTextColor(Color.rgb(51, 51, 51));
        yearRightTv.setTextColor(Color.rgb(51, 51, 51));
        yearLeftTv.setTextSize(18);
        yearRightTv.setTextSize(18);
    }

    private void addLocationItem() {
        String left = locationLeftTv.getText().toString();
        String middle = locationMiddleTv.getText().toString();
        String right = locationRightTv.getText().toString();
        String info = left + "  ·  " + middle + "  ·  " + right;
        if (left.equals("所有国家")) {
            left = "";
        }
        if (middle.equals("所有省份")) {
            middle = "";
        }
        if (right.equals("所有城市")) {
            right = "";
        }
        String temp = "\"" + "_" + left + "_" + middle + "_" + right + "\"";
        if (locationFilterInfoList.contains(temp)) {
            Toast.makeText(getApplicationContext(), "您已经选中了该选项。", Toast.LENGTH_SHORT).show();
            return;
        }
        final LinearLayout locationItem = (LinearLayout) layoutInflater.inflate(R.layout.hlyflt_highlyfilteract_location_item, locationJar, false);
        final TextView infoTv = (TextView) locationItem.findViewById(R.id.hlyflt_highlyfilteract_location_item_info_tv);
        final ImageButton removeImgbtn = (ImageButton) locationItem.findViewById(R.id.hlyflt_highlyfilteract_location_item_remove_imgbtn);
        infoTv.setText(info);
        removeImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLocationItemId = locationRemoveImgbtnList.indexOf(v);
                handler.sendEmptyMessage(REMOVE_LOCATION_ITEM);
            }
        });
        locationInfoTvList.add(infoTv);
        locationRemoveImgbtnList.add(removeImgbtn);
        locationList.add(locationItem);
        locationJar.addView(locationItem);
        locationFilterInfoList.add(temp);
    }

    private void removeMajorItem(int id) {
        majorFilterInfoList.remove(id);
        majorInfoTvList.remove(id);
        majorRemoveImgbtnList.remove(id);
        majorJar.removeView(majorList.get(id));
        majorList.remove(id);
    }

    private void removeLocationItem(int id) {
        locationFilterInfoList.remove(id);
        locationInfoTvList.remove(id);
        locationRemoveImgbtnList.remove(id);
        locationJar.removeView(locationList.get(id));
        locationList.remove(id);
    }

    private void majorLeftPvSelected(String text) {
        String left = text;
        majorLeftTv.setText(left);
        pvlayout_majorpv_pvright_data = parseMajor.parseMajorRightData(left);
        pvlayout_majorpv_pvright.setData(pvlayout_majorpv_pvright_data);
        String right = pvlayout_majorpv_pvright_data.get(pvlayout_majorpv_pvright_data.size() / 2);
        majorRightTv.setText(right);
    }

    private void majorRightPvSelected(String text) {
        String right = text;
        majorRightTv.setText(right);
    }

    private void yearLeftPvSelected(String text) {
        String left = text;
        yearLeftTv.setText(left);
        pvlayout_yearpv_pvright_data = parseYear.parseYearRightData(left);
        pvlayout_yearpv_pvright.setData(pvlayout_yearpv_pvright_data);
        String right = pvlayout_yearpv_pvright_data.get(pvlayout_yearpv_pvright_data.size() / 2);
        yearRightTv.setText(right);
    }

    private void yearRightPvSelected(String text) {
        String right = text;
        yearRightTv.setText(right);
    }

    private void locationLeftPvSelected(String text) {
        String left = text;
        locationLeftTv.setText(left);
        pvlayout_locationpv_pvmiddle_data = parseLocation.parseLocationMiddleData(left);
        pvlayout_locationpv_pvmiddle.setData(pvlayout_locationpv_pvmiddle_data);
        String middle = pvlayout_locationpv_pvmiddle_data.get(pvlayout_locationpv_pvmiddle_data.size() / 2);
        locationMiddleTv.setText(middle);
        if (pvlayout_locationpv_pvmiddle_data.size() == 1) {
            pvlayout_locationpv_pvright_data = parseLocation.parseLocationRightData(left, null);
        } else {
            pvlayout_locationpv_pvright_data = parseLocation.parseLocationRightData(left, middle);
        }
        pvlayout_locationpv_pvright.setData(pvlayout_locationpv_pvright_data);
        String right = pvlayout_locationpv_pvright_data.get(pvlayout_locationpv_pvright_data.size() / 2);
        locationRightTv.setText(right);
    }

    private void locationMiddlePvSelected(String text) {
        String left = locationLeftTv.getText().toString();
        String middle = text;
        locationMiddleTv.setText(middle);
        if (pvlayout_locationpv_pvmiddle_data.size() == 1) {
            pvlayout_locationpv_pvright_data = parseLocation.parseLocationRightData(left, null);
        } else {
            pvlayout_locationpv_pvright_data = parseLocation.parseLocationRightData(left, middle);
        }
        pvlayout_locationpv_pvright.setData(pvlayout_locationpv_pvright_data);
        String right = pvlayout_locationpv_pvright_data.get(pvlayout_locationpv_pvright_data.size() / 2);
        locationRightTv.setText(right);
    }

    private void locationRightPvSelected(String text) {
        String right = text;
        locationRightTv.setText(right);
    }


    private void submitToServer() {
        Log.e("??????//", ">>>>>>>>>>>>>>");
        String majorFilter = "";
        if (majorFilterInfoList != null && majorFilterInfoList.size() != 0) {
            majorFilter = "[";
            for (int i = 0; i < majorFilterInfoList.size(); i++) {
                majorFilter = majorFilter + majorFilterInfoList.get(i) + ",";
            }
            majorFilter = majorFilter.substring(0, majorFilter.length() - 1);
            majorFilter = majorFilter + "]";
        }

        String minYear = "1952";
        if (this.minYear != null && !this.minYear.equals("不筛选")) {
            minYear = this.minYear;
        }
        String maxYear = "2016";
        if (this.maxYear != null && !this.maxYear.equals("不筛选")) {
            maxYear = this.maxYear;
        }

        String locationFilter = "";
        if (locationFilterInfoList != null && locationFilterInfoList.size() != 0) {
            locationFilter = "[";
            for (int j = 0; j < locationFilterInfoList.size(); j++) {
                locationFilter = locationFilter + locationFilterInfoList.get(j) + ",";
            }
            locationFilter = locationFilter.substring(0, locationFilter.length() - 1);
            locationFilter = locationFilter + "]";
        }
        if (majorFilter != null && majorFilter.length() != 0) {
            resultIntent.putExtra("majorFilter", majorFilter);
        } else {
            resultIntent.putExtra("majorFilter", "[]");
        }
        if (minYear != null) {
            resultIntent.putExtra("minYear", minYear);
        } else {
            resultIntent.putExtra("minYear", "0");
        }
        if (maxYear != null) {
            resultIntent.putExtra("maxYear", maxYear);
        } else {
            resultIntent.putExtra("maxYear", "9999");
        }
        if (locationFilter != null && locationFilter.length() != 0) {
            resultIntent.putExtra("locationFilter", locationFilter);
        } else {
            resultIntent.putExtra("locationFilter", "[]");
        }
        Log.e("HIGHLY LOCATION is", "HHHHHHH+  "+locationFilter);
        setResult(HighlyFilterAct_RESULT_CODE, resultIntent);
        finish();

//        /**
//         * Todo: 上传 majorFilter、minYear、maxYear、locationFilter 到服务器进行筛选。
//         */
//
//        Log.i("test", "majorFilter: " + majorFilter);
//        Log.i("test", "minYear:  " + minYear + "maxYear: " + maxYear);
//        Log.i("test", "locationFilter: " + locationFilter);
    }

}
