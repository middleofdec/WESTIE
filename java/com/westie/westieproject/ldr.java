package com.westie.westieproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ldr extends AppCompatActivity
{
    TextView LDR_show,status;
    ImageView light_view;
    ImageButton power_light;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ldr);

        LDR_show    = findViewById(R.id.LDR_show);
        light_view  = findViewById(R.id.light_view);
        power_light = findViewById(R.id.power_light);
        status      = findViewById(R.id.status);

        myRef = FirebaseDatabase.getInstance().getReference().child("LDR");
        myRef.addValueEventListener(new ValueEventListener()
        {
            @SuppressLint({"ResourceAsColor", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String light_word = dataSnapshot.child("Light").getValue().toString();
                LDR_show.setText(light_word);

                String lamp = dataSnapshot.child("Lamp").getValue().toString();

                String datastring1 = "แสงสว่างมาก";
                String datastring2 = "แสงปกติ";
                String datastring3 = "แสงน้อย";
                String datastring4 = "ไม่มีแสง";

                String lamp_on  = "ON";
                String lamp_off = "OFF";

                if (light_word.equals(datastring1)) { light_view.setBackgroundResource(R.drawable.light_lv4); }
                if (light_word.equals(datastring2)) { light_view.setBackgroundResource(R.drawable.light_lv3); }
                if (light_word.equals(datastring3)) { light_view.setBackgroundResource(R.drawable.light_lv2); }
                if (light_word.equals(datastring4)) { light_view.setBackgroundResource(R.drawable.light_lv1); }

                if (lamp.equals(lamp_on)){power_light.setBackgroundResource(R.drawable.power_on);   status.setText("ON");}
                if (lamp.equals(lamp_off)){power_light.setBackgroundResource(R.drawable.power_off); status.setText("OFF");}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }

        });

        power_light.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (status.getText().toString().equals("ON")) {myRef.child("Lamp").setValue("OFF");}

                else if (status.getText().toString().equals("OFF")){myRef.child("Lamp").setValue("ON");}

            }
        });

    }

    public void onBackPressed()
    {
        Intent B3 = new Intent(ldr.this, MainActivity.class);
        startActivity(B3);
    }
}

