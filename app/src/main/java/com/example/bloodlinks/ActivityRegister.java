package com.example.bloodlinks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.location.FusedLocationProviderClient;


public class ActivityRegister extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Double longitude, latitude;
    private Spinner spinner;
    private Button btnsignup;
    private CheckBox checkBox;
    private EditText etregistername,etregisteremail,etregisterpassword,etregistermobile;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference donorsRef = db.collection("Donors");
    private String bloodgroup,gender;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupViews();
        firebaseAuth = FirebaseAuth.getInstance();



        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


    }

    private void setupViews() {

        checkBox = findViewById(R.id.checkboxage);
        etregisteremail = findViewById(R.id.etregisteremail);
        etregistermobile = findViewById(R.id.etregistermobile);
        etregistername = findViewById(R.id.etregistername);
        etregisterpassword = findViewById(R.id.etregisterpassword);
        btnsignup = findViewById(R.id.btnsignup);
        radioGroup = findViewById(R.id.radioGroup);
        progressDialog = new ProgressDialog(this);

        spinner =findViewById(R.id.spinnerbgroup);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.bloodgroup,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }



    private void fetchLocation() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.


            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                latitude = location.getLatitude();
                longitude = location.getLongitude();

            }
        });

    }



    private void saveData() {


        String uname = etregisteremail.getText().toString().trim();
        String umail = etregisteremail.getText().toString().trim();
        String umob = etregistermobile.getText().toString().trim();


        Donor donor = new Donor(uname, umail, umob, bloodgroup, latitude, longitude, gender);

        donorsRef.add(donor)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ActivityRegister.this, "data saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActivityRegister.this, "data not saved", Toast.LENGTH_SHORT).show();

                    }
                });


    }




    private Boolean validate() {

        Boolean result = false;


        String uname = etregistername.getText().toString().trim();
        String uemail = etregisteremail.getText().toString().trim();
        String upassword = etregisterpassword.getText().toString().trim();
        String umobile = etregistermobile.getText().toString().trim();

        if (uname.isEmpty() || uemail.isEmpty() || upassword.isEmpty() || umobile.isEmpty()) {
            Toast.makeText(this, "fill all the above fields", Toast.LENGTH_SHORT).show();
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(uemail).matches()){
            Toast.makeText(this, "incorrect email", Toast.LENGTH_SHORT).show();
        }
        else if(!android.util.Patterns.PHONE.matcher(umobile).matches() || umobile.length()!=10){
            Toast.makeText(this, "incorrect mobile number", Toast.LENGTH_SHORT).show();

        }
        else if(!checkBox.isChecked()){
            Toast.makeText(this, "select the checkbox", Toast.LENGTH_SHORT).show();
        }
        else {
            result = true;
        }

        return result;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        bloodgroup = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void signUpClick(View view) {

        if(validate()){


            progressDialog.show();
            String uemail = etregisteremail.getText().toString().trim();
            String upassword = etregisterpassword.getText().toString().trim();


            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){



                fetchLocation();

                firebaseAuth.createUserWithEmailAndPassword(uemail,upassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    saveData();
                                    progressDialog.dismiss();
                                    Toast.makeText(ActivityRegister.this, "registered successfully", Toast.LENGTH_SHORT).show();

                                    finish();

                                    startActivity(new Intent(ActivityRegister.this,ActivityUser.class));

                                }
                                else{

                                    progressDialog.dismiss();
                                    Toast.makeText(ActivityRegister.this, "unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
            else{

                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }



        }



    }

    public void radioButtonClick(View view) {

        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);
        gender = radioButton.getText().toString();

    }
}
