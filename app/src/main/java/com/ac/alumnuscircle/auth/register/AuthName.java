/**
 * @author Zhengfan
 * @date 16.09.03
 * @version 1
 * 功能：注册第二个界面，验证身份。
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.register.myspinner.MyAdapter;
import com.ac.alumnuscircle.cstt.ActivityName;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AuthName extends Activity {

    private LinearLayout back_llyt;
    private EditText name_et;
    private AppCompatSpinner sex_spinner;
    private Button next_btn;

    private List<String> sexData;
    private MyAdapter sexAdapter;
    private String sexInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_authnameact);
        init();
    }

    private void init(){
        initView();
        initData();
    }

    private void initView(){
        back_llyt = (LinearLayout)findViewById(R.id.register_authnameact_back_llyt);
        name_et = (EditText)findViewById(R.id.register_authnameact_name_et);
        sex_spinner = (AppCompatSpinner)findViewById(R.id.register_authnameact_sex_spinner);
        next_btn = (Button)findViewById(R.id.register_authnameact_next_btn);

        back_llyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sex_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexInfo = sexData.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sexInfo = sexData.get(0);
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**这个正则表达式用来判断是否为中文**/
                String chinese = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";
                Pattern pattern = Pattern.compile(chinese);
                if(name_et.getText().toString().equals("")){
                    Toast.makeText(AuthName.this,"请输入姓名~",
                            Toast.LENGTH_SHORT).show();
                }else if(!pattern.matcher(name_et.getText().toString()).matches()){
                    Toast.makeText(AuthName.this,"只能输入中文~",
                            Toast.LENGTH_SHORT).show();
                    name_et.setText("");
                }else {
                    gotoNext();
                }


            }
        });

    }

    private void initData(){
        initSexSpinner();
    }

    private void initSexSpinner(){
        sexData = new ArrayList<String>();
        sexData.add("男");
        sexData.add("女");
        sexAdapter = new MyAdapter(this, sexData);
        sex_spinner.setAdapter(sexAdapter);
        sex_spinner.setPopupBackgroundResource(R.color.transparent);
    }

    /**
     * 跳转到下一个界面
     * 2016年9月6日09:18:01
     * 曾博晖创建
     * */
    private void gotoNext(){
        Intent intent = new Intent(ActivityName.register_AuthMajor);
        RegisterUser.name=name_et.getText().toString();
        if(sexInfo.equals("男")){
            RegisterUser.gender="1";
        }else {
            RegisterUser.gender="0";
        }
        Log.i("Gender is",""+RegisterUser.gender);
        startActivity(intent);
    }

}
