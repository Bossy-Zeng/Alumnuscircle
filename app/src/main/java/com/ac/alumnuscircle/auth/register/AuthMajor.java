/**
 * @author Zhengfan
 * @date 16.09.03
 * @version 1
 * 功能：注册第三个界面，验证专业。
 */

package com.ac.alumnuscircle.auth.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.register.myspinner.MyAdapter;
import com.ac.alumnuscircle.auth.register.parsedata.ParseMajor;
import com.ac.alumnuscircle.auth.register.parsedata.ParseYear;
import com.ac.alumnuscircle.cstt.ActivityName;

import java.util.ArrayList;
import java.util.List;

public class AuthMajor extends Activity {
    private LinearLayout back_llyt;
    private AppCompatSpinner college_spinner;
    private AppCompatSpinner major_spinner;
    private AppCompatSpinner year_spinner;
    private Button isStudent_btn;
    private Button isNotStudent_btn;

    private ParseMajor parseMajor;
    private ParseYear parseYear;

    private List<String> collegeData;
    private MyAdapter collegeAdapter;
    private String collegeInfo;

    private List<String> majorData;
    private MyAdapter majorAdapter;
    private String majorInfo;

    private List<String> yearData;
    private MyAdapter yearAdapter;
    private String yearInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_authmajoract);
        init();
    }

    private void init(){
        initView();
        initData();
    }

    private void initView(){
        back_llyt = (LinearLayout)findViewById(R.id.register_authmajoract_back_llyt);
        college_spinner = (AppCompatSpinner)findViewById(R.id.register_authmajoract_college_spinner);
        major_spinner = (AppCompatSpinner)findViewById(R.id.register_authmajoract_major_spinner);
        year_spinner = (AppCompatSpinner)findViewById(R.id.register_authmajoract_year_spinner);
        isStudent_btn = (Button)findViewById(R.id.register_authmajoract_isstudent_btn);
        isNotStudent_btn = (Button)findViewById(R.id.register_authmajoract_isnotstudent_btn);

        back_llyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        college_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                collegeInfo = collegeData.get(position);
                setMajorSpinnerData(collegeInfo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        major_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                majorInfo = majorData.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearInfo = yearData.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        isStudent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 点击我是在校生按钮。
                 */
                RegisterUser.faculty=collegeInfo;
                RegisterUser.major=majorInfo;
                RegisterUser.admission_year=yearInfo;
                RegisterUser.country="中国";
                RegisterUser.state="江苏";
                RegisterUser.city="南京";
                RegisterUser.company="the SEU";
                RegisterUser.job="student";


                Intent intent = new Intent(ActivityName.register_AuthHeadImg);
                startActivity(intent);
            }
        });

        isNotStudent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 点击我已工作按钮。
                 */
                RegisterUser.faculty=collegeInfo;
                RegisterUser.major=majorInfo;
                RegisterUser.admission_year=yearInfo;
                Log.i("THE INFO IS",RegisterUser.faculty+RegisterUser.major
                +RegisterUser.admission_year);
                Intent intent = new Intent(ActivityName.register_AuthWork);
                startActivity(intent);
            }
        });
    }

    private void initData(){
        parseMajor = new ParseMajor(getApplicationContext());
        parseYear = new ParseYear();
        initCollegeSpinner();
        initMajorSpinner();
        initYearSpinner();
    }


    private void initCollegeSpinner(){
        collegeData = new ArrayList<String>();
        collegeData = parseMajor.parseMajorLeftData();
        collegeAdapter = new MyAdapter(this, collegeData);
        college_spinner.setAdapter(collegeAdapter);
        college_spinner.setPopupBackgroundResource(R.color.transparent);
        collegeInfo = collegeData.get(0);
    }

    private void initMajorSpinner(){
        majorData = new ArrayList<String>();
        majorData = parseMajor.parseMajorRightData(collegeInfo);
        majorAdapter = new MyAdapter(this, majorData);
        major_spinner.setAdapter(majorAdapter);
        major_spinner.setPopupBackgroundResource(R.color.transparent);
        majorInfo = majorData.get(0);
    }

    private void initYearSpinner(){
        yearData = new ArrayList<String>();
        yearData = parseYear.parseYearLeftData();
        yearAdapter = new MyAdapter(this, yearData);
        year_spinner.setAdapter(yearAdapter);
        year_spinner.setPopupBackgroundResource(R.color.transparent);
        yearInfo = yearData.get(0);
    }

    private void setMajorSpinnerData(String collegeInfo){
        if(majorData == null){
            majorData = new ArrayList<String>();
        }
        majorData = parseMajor.parseMajorRightData(collegeInfo);
        majorAdapter.setData(majorData);
        majorAdapter.notifyDataSetChanged();
        majorInfo = majorData.get(0);
    }

}
