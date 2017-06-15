package com.project.oo.postatm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by BaLaBuBu on 2017/6/15.
 */

public class ATMInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private LayoutInflater inflater;
    public ATMInfoWindowAdapter(Context c)
    {
        inflater = LayoutInflater.from(c);
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View infoWindow = inflater.inflate(R.layout.atm_infowindow,null);
        TextView txtLine1 = (TextView) infoWindow.findViewById(R.id.txtLine1);
        TextView txtLine2 = (TextView) infoWindow.findViewById(R.id.txtLine2);
        txtLine1.setText(marker.getTitle());
        txtLine2.setText(marker.getSnippet());
        return infoWindow;
    }
}
