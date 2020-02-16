package com.softwarica.lostandfound;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.softwarica.lostandfound.API.URL.URL;
import com.softwarica.lostandfound.API.UsersAPI;
import com.softwarica.lostandfound.module.User;
import com.softwarica.lostandfound.serverresponse.RegisterResponse;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvAddress, tvPhone, tvEmail;
    private CircleImageView imgUser;
    private Button btnEditUser, btnhome , btnlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvAddress);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        imgUser = findViewById(R.id.imgUser);
        btnEditUser = findViewById(R.id.btnEditUser);
        btnhome = findViewById(R.id.btnhome);
        btnlogout = findViewById(R.id.btnlogout);

        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }
        });
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });




        UsersAPI usersAPI = URL.getInstance().create(UsersAPI.class);
        retrofit2.Call<User> user = usersAPI.getUserDetails(URL.token);

        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Error" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                tvName.setText(response.body().getFullname());
                tvAddress.setText(response.body().getAddress());
                tvPhone.setText(response.body().getPhone());
                tvEmail.setText(response.body().getEmail());
                //image path
                String imgPath = URL.imageUserPath + response.body().getImage();

                Picasso.get().load(imgPath).into(imgUser);
//
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                Log.d("Msg", "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(ProfileActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();

    }
}
