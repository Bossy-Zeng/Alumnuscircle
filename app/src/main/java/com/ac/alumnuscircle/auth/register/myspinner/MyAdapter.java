/**@author 吴正凡
 * */
package com.ac.alumnuscircle.auth.register.myspinner;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.ac.alumnuscircle.R;
import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<String> data;
    private Context context;

    public MyAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.register_spinner, null);
        if(convertView != null){
            TextView infoTv = (TextView)convertView.findViewById(R.id.register_spinner_info);
            infoTv.setText(data.get(position));
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.register_spinner_item, null);
        if(convertView != null){
            TextView infoTv = (TextView)convertView.findViewById(R.id.register_spinner_item_info);
            infoTv.setText(data.get(position));
        }
        return convertView;
    }

    public void setData(List<String> data){
        this.data = data;
    }
}
