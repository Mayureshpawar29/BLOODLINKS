package com.example.bloodlinks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.ramotion.circlemenu.CircleMenuView;

public class ActivityHome extends AppCompatActivity {
    private LocationManager locationManager;
    private Task<Void> fusedLocationProviderClient;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private CircleMenuView cm;
    private Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        cm=findViewById(R.id.cm);
        b=findViewById(R.id.bd);

        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();


        if(networkInfo != null && networkInfo.isConnected()==true )
        {
            Toast.makeText(this, "Network Available", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "Network Not Available", Toast.LENGTH_SHORT).show();

        }

        cm.setEventListener(new CircleMenuView.EventListener() {
            public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int index) {
                String bgs[]={"A+","A-","B+","B-","O+","O-","AB+","AB-"};
                Intent i=new Intent(ActivityHome.this,ActivityDonors.class);
                i.putExtra("bg",bgs[index]);
                startActivity(i);
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ActivityHome.this,ActivityLogin.class);
                startActivity(i);
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1) {
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ActivityHome.this, "Location services Denied! ", Toast.LENGTH_LONG).show();
                finish();
            }
            else {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                        .requestLocationUpdates(new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                                .setInterval(10 * 1000)
                                .setFastestInterval(2000), new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                onLocationChanged(locationResult.getLastLocation());
                            }
                        }, Looper.myLooper());
                if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)==false){
                    new AlertDialog.Builder(this)
                            .setTitle("Location needed!")
                            .setMessage("Location required for better user experience.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    finish();
                                }
                            }).create().show();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Permission needed!")
                        .setMessage("Location services required for better user experience.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(ActivityHome.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                finish();
                            }
                        }).create().show();
            }
            else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                    .requestLocationUpdates(new LocationRequest()
                            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                            .setInterval(10 * 1000)
                            .setFastestInterval(2000), new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                onLocationChanged(locationResult.getLastLocation());
                            }
                        }, Looper.myLooper());
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)==false){
                new AlertDialog.Builder(this)
                        .setTitle("Location needed!")
                        .setMessage("Location required for better user experience.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                finish();
                            }
                        }).create().show();
            }
        }
    }

    private void onLocationChanged(Location location) {
        Donor.lati=location.getLatitude();
        Donor.longi=location.getLongitude();
    }
}
