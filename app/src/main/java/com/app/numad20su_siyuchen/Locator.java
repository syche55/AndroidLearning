package com.app.numad20su_siyuchen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Locator extends AppCompatActivity {

    private Button button;
    private TextView locationTextView;

    private LocationManager locationManager;
    private LocationListener locationListener;  //listen for location changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locator);

        button = findViewById(R.id.buttonLocation);
        locationTextView = (TextView) findViewById(R.id.textViewLocation);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String latitude = String.valueOf(location.getLatitude());
                String longitude = String.valueOf(location.getLongitude());


                locationTextView.setText("Latitude: "+latitude+"\nLongitude: "+longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 0);
            return;
        } else {
            configureButton();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode==0 && grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            configureButton();
        }
            else {
                Intent intent = new Intent(Locator.this, MainActivity.class);
                startActivity(intent);
            }
    }

    private void configureButton() {
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
                } catch (SecurityException se) {

                }
            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("location_rotate", locationTextView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        locationTextView.setText(savedInstanceState.getString("location_rotate"));
    }
}
