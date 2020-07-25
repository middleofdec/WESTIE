package com.westie.westieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity
{   Button login_button;
    EditText user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);

        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (user.getText().toString().equals("Thanasiri") && pass.getText().toString().equals("1234"))
                {
                    SharePrefs.saveSharedSetting(login.this, "Thanasiri", "false");
                    SharePrefs.SharePrefsSAVE(getApplicationContext(), user.getText().toString());
                    Intent LogIn = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(LogIn);
                    finish();
                } else
                {
                    Toast.makeText(getApplication(), "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void onBackPressed()
    { finishAffinity();}
}
