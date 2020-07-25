package com.westie.westieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Member;

public class camera extends AppCompatActivity
{
    Button button_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //switch_camera = findViewById(R.id.switch_camera);

        button_go     = findViewById(R.id.button_go);



        button_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String url = "http://192.168.43.120/";
                Intent url_cam = new Intent(Intent.ACTION_VIEW);
                url_cam.setData(Uri.parse(url));
                startActivity(Intent.createChooser(url_cam, "Open with"));
                //web_camera.setWebViewClient(new WebViewClient());
                //web_camera.loadUrl("http://192.168.43.120/");
            }
        });

        /*myRef = FirebaseDatabase.getInstance().getReference().child("Tool");
        switch_camera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    String ON = "1";
                    myRef.setValue(ON);

                    web_camera.setWebViewClient(new WebViewClient());
                    web_camera.loadUrl("http://192.168.43.120/");

                }
                else
                {
                    String OFF = "0";
                    myRef.setValue(OFF);

                    <Switch
                        android:id="@+id/switch_camera"
                        android:layout_width="107dp"
                        android:layout_height="71dp"
                        android:checked="true"
                        android:text="@string/camera"
                        app:layout_constraintBottom_toBottomOf="parent"
                         app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintHorizontal_bias="0.131"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toTopOf="parent"
                        app:layout_constraintVertical_bias="0.08" />
                }
            }
        });*/


    }

    public void onBackPressed()
    {
        Intent B4 = new Intent(camera.this, MainActivity.class);
        startActivity(B4);
    }
}