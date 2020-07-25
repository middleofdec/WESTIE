package com.westie.westieproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mq2 extends AppCompatActivity
{
    TextView LPGtextView, COtextView, SmoketextView;
    CardView CardLPG,CardCO,CardSmoke;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mq2);

        LPGtextView   = findViewById(R.id.LPGtextView);
        COtextView    = findViewById(R.id.COtextView);
        SmoketextView = findViewById(R.id.SmoketextView);

        CardCO      = findViewById(R.id.CardCO);
        CardLPG     = findViewById(R.id.CardLPG);
        CardSmoke   = findViewById(R.id.CardSmoke);

        myRef = FirebaseDatabase.getInstance().getReference().child("MQ-2");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String LPG   = dataSnapshot.child("LPG").getValue().toString();
                String CO    = dataSnapshot.child("CO").getValue().toString();
                String Smoke = dataSnapshot.child("Smoke").getValue().toString();

                LPGtextView.setText(LPG);
                COtextView.setText(CO);
                SmoketextView.setText(Smoke);

                int iLPG   = Integer.parseInt(LPG);
                int iCO    = Integer.parseInt(CO);
                int iSmoke = Integer.parseInt(Smoke);
                ////////////////////////////////////////////////////////////////////////////////
                if (iLPG < 1000)
                {
                    CardLPG.setCardBackgroundColor(Color.parseColor("#35FFFFFF"));
                }
                if (iLPG >= 1000)
                {
                    CardLPG.setCardBackgroundColor(Color.parseColor("#FF2400"));
                }
                ////////////////////////////////////////////////////////////////////////////////
                if (iCO < 100)
                {
                    CardCO.setCardBackgroundColor(Color.parseColor("#35FFFFFF"));
                }
                if (iCO >= 100 && iCO <= 400)
                {
                    CardCO.setCardBackgroundColor(Color.parseColor("#FFBD2E"));
                }
                else if (iCO > 400 && iCO <= 1600)
                {
                    CardCO.setCardBackgroundColor(Color.parseColor("#FF5C00"));
                }
                else if (iCO > 1600)
                {
                    CardCO.setCardBackgroundColor(Color.parseColor("#FF2400"));
                }
                ////////////////////////////////////////////////////////////////////////////////
                if (iSmoke <= 50)
                {
                    CardSmoke.setCardBackgroundColor(Color.parseColor("#35FFFFFF"));
                }
                if (iSmoke > 50 && iSmoke <= 100)
                {
                    CardSmoke.setCardBackgroundColor(Color.parseColor("#A6D608"));
                }
                else if (iSmoke > 100 && iSmoke <= 200)
                {
                    CardSmoke.setCardBackgroundColor(Color.parseColor("#FFBD2E"));
                }
                else if (iSmoke > 200 && iSmoke <= 300)
                {
                    CardSmoke.setCardBackgroundColor(Color.parseColor("#FF5C00"));
                }
                else if (iSmoke > 300)
                {
                    CardSmoke.setCardBackgroundColor(Color.parseColor("#FF2400"));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onBackPressed()
    {
        Intent B2 = new Intent(mq2.this, MainActivity.class);
        startActivity(B2);
    }
}
