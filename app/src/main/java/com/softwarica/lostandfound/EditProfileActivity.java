package com.softwarica.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EditProfileActivity extends AppCompatActivity {
    private EditText etName, etAddress, etPhone, etEmail;
    private CircleImageView imgUser;
    private Button btnupdate, btnhome ;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        imgUser = findViewById(R.id.imgUser);
        btnhome = findViewById(R.id.btnhome);
        btnupdate = findViewById(R.id.btnupdate);


        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this, MainActivity.class));
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUser();
            }
        });


        UsersAPI usersAPI = URL.getInstance().create(UsersAPI.class);
        Call<User> user = usersAPI.getUserDetails(URL.token);
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(EditProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                etName.setText(response.body().getFullname());
                etAddress.setText(response.body().getAddress());
                etPhone.setText(response.body().getPhone());
                etEmail.setText(response.body().getEmail());

                id = response.body().get_id();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Messag", "OnFailure" + t.getLocalizedMessage());
                Toast.makeText(EditProfileActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void UpdateUser() {
        UsersAPI usersAPI = URL.getInstance().create(UsersAPI.class);
        String fullname = etName.getText().toString();
        String address = etAddress.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();
//        User user = new User(email,fullname,phone,address);
        Call<RegisterResponse> updatecall = usersAPI.userUpdate(URL.token, id, fullname, address,phone,email);

        updatecall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Toast.makeText(EditProfileActivity.this, "Updated Successfully!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

}


