package com.example.bloodlinks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityRegister extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    Button btnsignup;
    CheckBox checkBox;
    EditText etregistername,etregisteremail,etregisterpassword,etregistermobile;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupViews();
        firebaseAuth = FirebaseAuth.getInstance();




    }

    private void setupViews() {

        checkBox = findViewById(R.id.checkboxage);
        etregisteremail = findViewById(R.id.etregisteremail);
        etregistermobile = findViewById(R.id.etregistermobile);
        etregistername = findViewById(R.id.etregistername);
        etregisterpassword = findViewById(R.id.etregisterpassword);
        btnsignup = findViewById(R.id.btnsignup);


        spinner =findViewById(R.id.spinnerbgroup);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.bloodgroup,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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

        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void signUpClick(View view) {

        if(validate()){

            String uemail = etregisteremail.getText().toString().trim();
            String upassword = etregisterpassword.getText().toString().trim();

            firebaseAuth.createUserWithEmailAndPassword(uemail,upassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(ActivityRegister.this, "registered successfully", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ActivityRegister.this,ActivityUser.class));
                            }
                            else{

                                Toast.makeText(ActivityRegister.this, "unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }



    }
}
