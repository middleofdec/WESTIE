package com.westie.westieproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dht22 extends AppCompatActivity
{
    TextView TemptextView,HumitextView;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dht22);

        HumitextView = (TextView) findViewById(R.id.HumitextView);
        TemptextView = (TextView) findViewById(R.id.TemptextView);

        myRef = FirebaseDatabase.getInstance().getReference().child("DHT22");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {   String Hu = dataSnapshot.child("Humidity").getValue().toString();
                String Tem = dataSnapshot.child("Temperature").getValue().toString();
                HumitextView.setText(Hu);
                TemptextView.setText(Tem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void onBackPressed()
    {
        Intent B1 = new Intent(dht22.this, MainActivity.class);
        startActivity(B1);
    }
}
