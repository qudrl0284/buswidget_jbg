package com.planuri.allsampletest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class GpsGetInfo extends Service {

    private final Context locContext;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    protected LocationManager locationManager;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    Location location;
    double lat, lon; // lat == 경도, lon == 위도


    public GpsGetInfo(Context context) {
        this.locContext = context;
        getLocation();
    }

    public void getLocation() {

        locationManager = (LocationManager) locContext.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);


        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

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
        // GPS를 통하여 정보를 받아옴
        if(!isNetworkEnabled) {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
            location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            Log.d("[GPS]", "IsGPSEnabled = " + isGPSEnabled);
        }
        // GPS 정보를 받아오지못하면 연결된 네트워크의 주소를 받아온다.
        else {
            locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
            location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
            Log.d("[GPS]", "IsNetworkEnabled = " + isNetworkEnabled);
        }
    }

    public double getLatitude(){
        if(location != null){
            lat = location.getLatitude();
        }
        return lat;
    }

    public double getLongitude(){
        if(location != null){
            lon = location.getLongitude();
        }
        return lon;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
