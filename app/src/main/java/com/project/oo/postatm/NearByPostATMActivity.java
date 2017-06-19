package com.project.oo.postatm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

public class NearByPostATMActivity extends FragmentActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMyLocationButtonClickListener {
    private PostATMManager postATMManager;
    private LocationManager locationManager;
    private HistoryManager historyManager;
    SupportMapFragment mapFragment;
    GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_post_atm);
        postATMManager = PostATMManager.getInstance();
        historyManager = HistoryManager.getInstance();
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
    }

    private GoogleMap.OnInfoWindowClickListener atmClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            PostATM selectedAtm = postATMManager.getPostATMByName(marker.getTitle());
            Intent intent = new Intent();
            intent.setClass(NearByPostATMActivity.this, ATMInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedATM", selectedAtm);
            intent.putExtras(bundle);
            startActivity(intent);
            historyManager.addRecord(selectedAtm);
            try {
                historyManager.saveRecordDate(NearByPostATMActivity.this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 17));
            locationManager.removeUpdates(locationListener);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(NearByPostATMActivity.this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1, locationListener);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();
        markATMs();
    }

    void markATMs() {
        int height = 104;
        int width = 80;

        BitmapDrawable bitmapdraw = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.mark, null);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        for (int i = 0; i < postATMManager.getPostATMCount(); i++) {
            PostATM atm = postATMManager.postATMs.get(i);
            LatLng atmPosition = new LatLng(atm.latitude, atm.longitude);
            MarkerOptions markOpt = new MarkerOptions().position(atmPosition).title(atm.name);
            markOpt.snippet(atm.getFullAddress() + "\n" + atm.getStatus());
            markOpt.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            //markOpt.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMap.addMarker(markOpt);
        }
        ATMInfoWindowAdapter adapter = new ATMInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(adapter);
        mMap.setOnInfoWindowClickListener(atmClickListener);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

}
