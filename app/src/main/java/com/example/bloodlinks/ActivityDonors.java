package com.example.bloodlinks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class ActivityDonors extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference dr = db.collection("Donors");
    private ExpandingList el;
    private ArrayList<String>names=new ArrayList<>();
    private ArrayList<String>gen=new ArrayList<>();
    private ArrayList<Float>dist=new ArrayList<>();
    private ArrayList<String>phone=new ArrayList<>();
    private ArrayList<String>loc=new ArrayList<>();
    private int count=0;
    private ArrayList<Donor> donorArrayList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donors);

        el=findViewById(R.id.elv);

        Intent intent=getIntent();
        Toast.makeText(this, intent.getStringExtra("bg"), Toast.LENGTH_SHORT).show();

        dr.whereEqualTo("bloodgroup",intent.getStringExtra("bg"))
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ////////////////
                        for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                            Donor d=documentSnapshot.toObject(Donor.class);
                            donorArrayList.add(d);
                            count++;
                        }

                        Collections.sort(donorArrayList,Donor.StuNameComparator);

                        Iterator it = donorArrayList.iterator();
                        while(it.hasNext()){

                            Donor dr=(Donor)it.next();
                            names.add(dr.getName());
                            gen.add(dr.getGender());
                            loc.add(dr.getLocation());

                            Location locationA = new Location("point A");

                            locationA.setLatitude(Donor.lati);
                            locationA.setLongitude(Donor.longi);

                            Location locationB = new Location("point B");

                            locationB.setLatitude(dr.getLatitude());
                            locationB.setLongitude(dr.getLongitude());

                            DecimalFormat numberFormat = new DecimalFormat("#.00");

                            Float num=Float.valueOf(numberFormat.format(locationA.distanceTo(locationB)/1000));
                            dist.add(num);
                            phone.add(dr.getMobile());

                        }
                        showView(count);
                        ////////////////
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActivityDonors.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void showView(final int count) {
        ExpandingItem ei[]=new ExpandingItem[count+1];
        View p[],d[];
        p=new View[count+1];
        d=new View[count+1];

        for(int i=0;i<count;i++) {
            ei[i]=el.createNewItem(R.layout.exp_item);
            ei[i].createSubItems(3);
            ((TextView)ei[i].findViewById(R.id.titem)).setText(names.get(i));
            p[i]=ei[i].getSubItemView(0);
            ((ImageView)p[i].findViewById(R.id.ivsubitem)).setImageResource(R.drawable.ic_phone_in_talk_black_24dp);
            final TextView ph=((TextView)p[i].findViewById(R.id.tsubitem));
            ph.setText(phone.get(i));
            ph.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in=new Intent(Intent.ACTION_DIAL);
                    in.setData(Uri.parse("tel:"+ph.getText()));
                    startActivity(in);
                }
            });

            d[i]=ei[i].getSubItemView(1);
            ((ImageView)d[i].findViewById(R.id.ivsubitem)).setImageResource(R.drawable.ic_person_pin_circle_black_24dp);
            ((TextView)d[i].findViewById(R.id.tsubitem)).setText(loc.get(i));

            d[i]=ei[i].getSubItemView(2);
            ((ImageView)d[i].findViewById(R.id.ivsubitem)).setImageResource(R.drawable.distance);
            ((TextView)d[i].findViewById(R.id.tsubitem)).setText(" "+dist.get(i)+" km");

            if(gen.get(i).equals("Male"))
                ei[i].setIndicatorIconRes(R.drawable.ic_man);
            else
                ei[i].setIndicatorIconRes(R.drawable.ic_woman);
            ei[i].setIndicatorColorRes(R.color.red_icon);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent x=new Intent(ActivityDonors.this,ActivityHome.class);
        startActivity(x);
    }
}
