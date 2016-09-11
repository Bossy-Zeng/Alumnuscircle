/**
 * @author Zhengfan
 * @date 16.09.03
 * @version 1
 * 功能：注册第四个界面，验证工作。
 */

package com.ac.alumnuscircle.auth.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.AppCompatSpinner;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ac.alumnuscircle.R;
import com.ac.alumnuscircle.auth.Login;
import com.ac.alumnuscircle.auth.httpreq.HttpGet;

import com.ac.alumnuscircle.auth.loginJson.MapToJson;
import com.ac.alumnuscircle.auth.register.myspinner.MyAdapter;
import com.ac.alumnuscircle.auth.register.parsedata.ParseLocation;
import com.ac.alumnuscircle.cstt.ActivityName;
import com.ac.alumnuscircle.toolbox.json.ParseComplexJson;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthWork extends Activity {


    private LinearLayout back_llyt;
    private EditText company_et;
    private EditText post_et;
    private AppCompatSpinner country_spinner;
    private AppCompatSpinner state_spinner;
    private AppCompatSpinner city_spinner;
    private Button next_btn;

    private ParseLocation parseLocation;

    private List<String> countryData;
    private MyAdapter countryAdapter;
    private String countryInfo;

    private List<String> stateData;
    private MyAdapter stateAdapter;
    private String stateInfo;

    private List<String> cityData;
    private MyAdapter cityAdapter;
    private String cityInfo;
    private Gson gson;
    private String resCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_authworkact);

        init();

    }

    private void init() {
        initView();
        initData();
    }

    private void initView() {
        gson = new Gson();
        resCode = "";

        back_llyt = (LinearLayout) findViewById(R.id.register_authworkact_back_llyt);
        company_et = (EditText) findViewById(R.id.register_authworkact_company_et);
        post_et = (EditText) findViewById(R.id.register_authworkact_post_et);
        country_spinner = (AppCompatSpinner) findViewById(R.id.register_authworkact_country_spinner);
        state_spinner = (AppCompatSpinner) findViewById(R.id.register_authworkact_state_spinner);
        city_spinner = (AppCompatSpinner) findViewById(R.id.register_authworkact_city_spinner);
        next_btn = (Button) findViewById(R.id.register_authworkact_next_btn);

        back_llyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryInfo = countryData.get(position);
                setStateSpinnerData(countryInfo);
                if (stateData.size() != 0) {
                    stateInfo = stateData.get(0);
                    setCitySpinnerData(countryInfo, stateInfo);
                } else {
                    stateInfo = null;
                    setCitySpinnerData(countryInfo, null);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (stateData.size() != 0) {
                    stateInfo = stateData.get(position);
                    setCitySpinnerData(countryInfo, stateInfo);
                } else {
                    stateInfo = null;
                    setCitySpinnerData(countryInfo, null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityInfo = cityData.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent("");
//                startActivity(intent);
                RegisterUser.company = company_et.getText().toString();
                RegisterUser.job = post_et.getText().toString();
                RegisterUser.country = countryInfo;
                RegisterUser.state = stateInfo;
                RegisterUser.city = cityInfo;
                Intent intent=new Intent(ActivityName.register_AuthHeadImg);
                startActivity(intent);
//
            }
        });
    }



    private void initData() {
        parseLocation = new ParseLocation(getApplicationContext());
        initCountrySpinner();
        initStateSpinner();
        initCitySpinner();
    }

    private void initCountrySpinner() {
        countryData = new ArrayList<String>();
        countryData = parseLocation.parseLocationLeftData();
        countryAdapter = new MyAdapter(this, countryData);
        country_spinner.setAdapter(countryAdapter);
        country_spinner.setPopupBackgroundResource(R.color.transparent);
        countryInfo = countryData.get(0);
    }

    private void initStateSpinner() {
        stateData = new ArrayList<String>();
        if (countryInfo == null) {
            initCitySpinner();
        }
        stateData = parseLocation.parseLocationMiddleData(countryInfo);
        stateAdapter = new MyAdapter(this, stateData);
        state_spinner.setAdapter(stateAdapter);
        state_spinner.setPopupBackgroundResource(R.color.transparent);
        if (stateData.size() == 0) {
            return;
        }
        stateInfo = stateData.get(0);
    }

    private void initCitySpinner() {
        cityData = new ArrayList<String>();
        if (countryInfo == null) {
            initCitySpinner();
        }
        if (stateInfo == null) {
            cityData = parseLocation.parseLocationRightData(countryInfo, null);
        } else {
            cityData = parseLocation.parseLocationRightData(countryInfo, stateInfo);
        }
        cityAdapter = new MyAdapter(this, cityData);
        city_spinner.setAdapter(cityAdapter);
        city_spinner.setPopupBackgroundResource(R.color.transparent);
        if (cityData.size() == 0) {
            return;
        }
        cityInfo = cityData.get(0);
    }

    private void setStateSpinnerData(String countryInfo) {
        if (stateData == null) {
            stateData = new ArrayList<String>();
        }
        if (countryInfo == null) {
            initCitySpinner();
        }
        stateData = parseLocation.parseLocationMiddleData(countryInfo);
        stateAdapter.setData(stateData);
        stateAdapter.notifyDataSetChanged();
        if (stateData.size() == 0) {
            return;
        }
        stateInfo = stateData.get(0);
    }

    private void setCitySpinnerData(String countryInfo, String stateInfo) {
        if (cityData == null) {
            cityData = new ArrayList<String>();
        }
        if (countryInfo == null) {
            initCitySpinner();
        }
        if (stateInfo == null) {
            cityData = parseLocation.parseLocationRightData(countryInfo, null);
        } else {
            cityData = parseLocation.parseLocationRightData(countryInfo, stateInfo);
        }
        cityAdapter.setData(cityData);
        cityAdapter.notifyDataSetChanged();
        if (cityData.size() == 0) {
            return;
        }
        cityInfo = cityData.get(0);
    }
}
