package com.project.oo.postatm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by BaLaBuBu on 2017/6/14.
 */

public class PostATMAdapter extends BaseAdapter {
    private List<PostATM> atms;
    private LayoutInflater inflater;
    public PostATMAdapter(Context c, List<PostATM> atmLst)
    {
        inflater = LayoutInflater.from(c);
        atms = atmLst;
    }
    @Override
    public int getCount() {
        return atms.size();
    }

    @Override
    public Object getItem(int position) {
        return atms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.atm_item,null);
        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        TextView txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
        TextView txtNote = (TextView)convertView.findViewById(R.id.txtNote);
        PostATM selectedAtm = atms.get(position);
        txtName.setText(selectedAtm.name);
        txtAddress.setText(selectedAtm.getFullAddress());
        txtNote.setText(selectedAtm.getStatus());
        return convertView;
    }
}
