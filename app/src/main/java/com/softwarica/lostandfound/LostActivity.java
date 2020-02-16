package com.softwarica.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softwarica.lostandfound.API.URL.URL;
import com.softwarica.lostandfound.API.UsersAPI;
import com.softwarica.lostandfound.module.Lost;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LostActivity extends AppCompatActivity {
    private TextView lostname, losttype, lostlocation, lostphone, lostdetails,loststatus,tvlostresult;
    private ImageView lostimage;
    private Button btngoto;

    private RecyclerView recyclerView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        btngoto.findViewById(R.id.btngoto);

        btngoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LostActivity.this, AddLostActivity.class));
            }
        });

        UsersAPI usersAPI = URL.getInstance().create(UsersAPI.class);
        Call<List<Lost>> listCall = usersAPI.getalllost();
        listCall.enqueue(new Callback<List<Lost>>() {
            @Override
            public void onResponse(Call<List<Lost>> call, Response<List<Lost>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(LostActivity.this, "Error" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Lost> lostList = response.body();
                for (Lost lost : lostList) {
                    String data = "";
                    data += "Name is :" + lost.getName() + "\n";
                    data += "Email is :" + lost.getType() + "\n";
                    data += "Message is :" + lost.getLocation() + "\n";
                    data += "Message is :" + lost.getPhone() + "\n";
                    data += "Message is :" + lost.getDetails() + "\n";
                    data += lost.getImage() + "\n";
                    data += "Message is :" + lost.getStatus() + "\n";

                    tvlostresult.append(data);
                }
            }

            @Override
            public void onFailure(Call<List<Lost>> call, Throwable t) {
                Log.d("Msg", "onFailure" + t.getLocalizedMessage());
                Toast.makeText(LostActivity.this, "error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


//
//        recyclerView3 = findViewById(R.id.recyclerView3);
//        RecyclerLost();
//    }
//    private void RecyclerLost(){
//        UsersAPI usersAPI = URL.getInstance().create(UsersAPI.class);
//        final Call<List<Lost>> lost = usersAPI.getalllost();
//        lost.enqueue(new Callback<List<Lost>>() {
//
//            @Override
//            public void onResponse(Call<List<Lost>> call, Response<List<Lost>> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(LostActivity.this, "", Toast.LENGTH_SHORT).show();
//                    return;
//
//                }
//                List<Lost> listlost = response.body();
////                LostAdapter lostAdapter = new LostAdapter(LostActivity.this, listlost);
//                //LostAdapter lostAdapter = new LostAdapter(LostActivity.this,listlost);
//               // recyclerView3.setAdapter(lostAdapter);
//                recyclerView3.setLayoutManager(new LinearLayoutManager(LostActivity.this, LinearLayoutManager.VERTICAL,
//                false));
//            }
//
//            @Override
//            public void onFailure(Call<List<Lost>> call, Throwable t) {
//                Toast.makeText(LostActivity.this, "Error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

}

//
//
//
//
