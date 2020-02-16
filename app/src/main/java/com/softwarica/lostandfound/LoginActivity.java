package com.softwarica.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softwarica.lostandfound.Bill.LoginBLL;
import com.softwarica.lostandfound.StrictModeClass.StrictModeClass;
import com.softwarica.lostandfound.notification.CreateChannel;

public class LoginActivity extends AppCompatActivity {

    NotificationManagerCompat notificationManagerCompat;
    private Button btnLogin,btnRegister;

    private EditText etLoginEmail, etLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etLoginEmail = findViewById(R.id.etusername);
        etLoginPassword = findViewById(R.id.etpassword);

        notificationManagerCompat = NotificationManagerCompat.from(this);
        CreateChannel channel = new CreateChannel(this);
        channel.createChannel();

        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


        btnLogin = findViewById(R.id.btnLogin);
       btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login();
           }
        });
    }
    private void login() {
        String email = etLoginEmail.getText().toString();
       String password = etLoginPassword.getText().toString();

        LoginBLL loginBLL = new LoginBLL();

        StrictModeClass.StrictMode();

       if (loginBLL.checkUser(email, password)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
           startActivity(intent);
           Toast.makeText(this, "Logged in successfully.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Either email or password is incorrect", Toast.LENGTH_SHORT).show();
            etLoginEmail.requestFocus();
        }
    }
}