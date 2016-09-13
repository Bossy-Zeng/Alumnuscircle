/**
 * Created by 曾博晖 on 2016/9/8.
 *
 * @author 曾博晖
 * @date 2016年9月8日10:29:12
 * @version 1
 * 功能：实现模糊搜索的界面以及逻辑
 */
package com.ac.alumnuscircle.main.ctc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ac.alumnuscircle.R;


public class FuzzySearchAct extends Activity implements View.OnClickListener {
    private EditText content_et;
    private RelativeLayout search_rly;
    private RecyclerView result_rv;
    private ImageView delete_img;
    private LinearLayout backlly;
    private ImageView back_img;
    private Intent resultIntent;
    public static final int FuzzySearchAct_REQUEST_CODE = 0x10096;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ctc_fuzzysearchact);
        initView();
    }

    private void initView() {
        content_et = (EditText) findViewById(R.id.ctc_fuzzysearchact_tlb_et);
        search_rly = (RelativeLayout) findViewById(R.id.ctc_fuzzysearchact_tlb_search_rly);
        result_rv = (RecyclerView) findViewById(R.id.ctc_fuzzysearchact_rv);
        delete_img = (ImageView) findViewById(R.id.ctc_fuzzysearchact_tlb_delete_img);
        backlly = (LinearLayout) findViewById(R.id.ctc_fuzzysearchact_tlb_back_llyt);
        back_img = (ImageView) findViewById(R.id.ctc_fuzzysearchact_tlb_back_btn);

        back_img.setOnClickListener(this);
        backlly.setOnClickListener(this);
        content_et.addTextChangedListener(textWatcher);
        search_rly.setOnClickListener(this);
        delete_img.setOnClickListener(this);
        resultIntent = new Intent();
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            System.out.println("-1-onTextChanged-->"
                    + content_et.getText().toString() + "<--");
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
//
            System.out.println("-2-beforeTextChanged-->"
                    + content_et.getText().toString() + "<--");

        }

        @Override
        public void afterTextChanged(Editable s) {
//
            System.out.println("-3-afterTextChanged-->"
                    + content_et.getText().toString() + "<--");
            if (s.length() == 0) {
                delete_img.setVisibility(View.GONE);
            } else {
                delete_img.setVisibility(View.VISIBLE);
            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ctc_fuzzysearchact_tlb_search_rly:
                if (!content_et.getText().toString().equals("")) {
                    resultIntent.putExtra("queryData", content_et.getText().toString());
                    setResult(FuzzySearchAct_REQUEST_CODE, resultIntent);
                    finish();
                } else {
                    Toast.makeText(FuzzySearchAct.this, "搜索的数据不可为空",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ctc_fuzzysearchact_tlb_delete_img:
                content_et.setText("");
                break;
            case R.id.ctc_fuzzysearchact_tlb_back_btn:
                finish();
                break;
            default:
                break;

        }

    }


}
