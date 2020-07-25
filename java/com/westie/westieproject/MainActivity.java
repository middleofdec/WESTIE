package com.westie.westieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity
{   Button logout_button, dht22_temp_humi_button, mq2_button,ldr_button,camera_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {   super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CheckUser();

        SharedPreferences SP = getApplicationContext().getSharedPreferences("NAME",0);

        dht22_temp_humi_button = findViewById(R.id.dht22_temp_humi_button);
        dht22_temp_humi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent T2 = new Intent(MainActivity.this, dht22.class);
                startActivity(T2);
            }
        });

        mq2_button = findViewById(R.id.mq2_button);
        mq2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent T3 = new Intent(MainActivity.this, mq2.class);
                startActivity(T3);
            }
        });

        ldr_button = findViewById(R.id.ldr_button);
        ldr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent T4 = new Intent(MainActivity.this, ldr.class);
                startActivity(T4);
            }
        });


        camera_btn = findViewById(R.id.camera_btn);
        camera_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent T5 = new Intent(MainActivity.this,camera.class);
                startActivity(T5);
            }
        });

        logout_button = findViewById(R.id.logout_button);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SharePrefs.saveSharedSetting(MainActivity.this,"Thanasiri","true");
                SharePrefs.SharePrefsSAVE(getApplicationContext(),"");
                Intent LogOut = new Intent(getApplicationContext(),login.class);
                startActivity(LogOut);
                finish();
            }
        });

        CheckUser();
    }

    public void CheckUser()
    {
        Boolean Check = Boolean.valueOf(SharePrefs.readNameSharedSetting(MainActivity.this,"Thanasiri","true"));

        Intent introIntent = new Intent(MainActivity.this,login.class);
        introIntent.putExtra("Thanasiri",Check);

        if (Check)
        {
            startActivity(introIntent);
        }
    }

    public void onBackPressed()
    { finishAffinity();
    }
}
