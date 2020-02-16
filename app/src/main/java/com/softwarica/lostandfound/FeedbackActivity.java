package com.softwarica.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.softwarica.lostandfound.API.URL.URL;
import com.softwarica.lostandfound.API.UsersAPI;
import com.softwarica.lostandfound.module.Feedback;
import com.softwarica.lostandfound.module.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity {

    EditText etName, etEmail, etfeedback;
    Button btnsubmit;
    private TextView tvresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //Blinding
        etfeedback = findViewById(R.id.etfeedback);
        btnsubmit = findViewById(R.id.btnsubmit);
        tvresult=findViewById(R.id.tvresult);

        UsersAPI usersAPI=URL.getInstance().create(UsersAPI.class);
        Call<List<Feedback>> listCall=usersAPI.getFeedback(URL.token);
        listCall.enqueue(new Callback<List<Feedback>>() {
            @Override
            public void onResponse(Call<List<Feedback>> call, Response<List<Feedback>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(FeedbackActivity.this,"Error"+ response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Feedback> feedbackList = response.body();
                for (Feedback feedback :feedbackList) {
                    String data = "";
                    data += "Name is :" + feedback.getFullname() + "\n";
                    data += "Email is :" +feedback.getEmail() + "\n";
                    data += "Message is :" +feedback.getFeedback() + "\n";
                    data += "--------------------------------------------"+ "\n";

                    tvresult.append(data);
                }
            }


            @Override
            public void onFailure(Call<List<Feedback>> call, Throwable t) {
                Log.d("Msg", "onFailure" + t.getLocalizedMessage());
                Toast.makeText(FeedbackActivity.this, "error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback();
            }
        });
    }

    private void feedback() {
        UsersAPI usersAPI = URL.getInstance().create(UsersAPI.class);
        retrofit2.Call<User> userCall = usersAPI.getUserDetails(URL.token);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(retrofit2.Call<User> call, Response<User> response) {
                String fullname = response.body().getFullname();
                String email = response.body().getEmail();
                String feedback = etfeedback.getText().toString();

                Feedback feedbacks = new Feedback(fullname, email, feedback);

                UsersAPI usersAPI = URL.getInstance().create(UsersAPI.class);
                Call<Void> registerCall = usersAPI.feedback(URL.token, fullname, email, feedback);

                registerCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(FeedbackActivity.this, "error" + response.code(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FeedbackActivity.this, "Feedback added sucessfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(FeedbackActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }

                });
            }

            @Override
            public void onFailure(retrofit2.Call<User> call, Throwable t) {
                Toast.makeText(FeedbackActivity.this, "error", Toast.LENGTH_SHORT).show();
            }

        });
    }
}


