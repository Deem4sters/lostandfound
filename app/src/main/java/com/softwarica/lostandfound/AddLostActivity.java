package com.softwarica.lostandfound;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.loader.content.CursorLoader;

import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.softwarica.lostandfound.API.URL.URL;
import com.softwarica.lostandfound.API.UsersAPI;
import com.softwarica.lostandfound.StrictModeClass.StrictModeClass;
import com.softwarica.lostandfound.module.Lost;
import com.softwarica.lostandfound.notification.CreateChannel;
import com.softwarica.lostandfound.serverresponse.ImageResponse;
import com.softwarica.lostandfound.serverresponse.RegisterResponse;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddLostActivity extends AppCompatActivity {

    EditText lostname, losttype, lostlocation, lostphone, lostdetails, loststatus;
    Button btnaddlost;
    ImageView  lostimage;
    String imagePath;
    private String imageName = "";
    NotificationManagerCompat notificationManagerCompat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lost);

        notificationManagerCompat = notificationManagerCompat.from(this);
        CreateChannel channel = new CreateChannel(this);
        channel.createChannel();




        lostname = findViewById(R.id.lostname);
        losttype = findViewById(R.id.losttype);
        lostphone = findViewById(R.id.lostphone);
        lostdetails = findViewById(R.id.lostdetails);
        loststatus = findViewById(R.id.loststatus);
        btnaddlost = findViewById(R.id.btnaddlost);
        lostimage = findViewById(R.id.lostimage);

        lostimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();

            }
        });
        btnaddlost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    addlost();
                    saveImageOnly();
                } else {
                    Toast.makeText(AddLostActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    lostname.requestFocus();

                }
                return;
            }
        });
    }


    private boolean validate() {
        boolean status = true;
        if (lostname.getText().toString().length() < 6) {
            status = false;
        }
        return status;
    }

    private void BrowseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please select an image ", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri = data.getData();
        lostimage.setImageURI(uri);
        imagePath = getRealPathFromUri(uri);
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),
                uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    private void saveImageOnly() {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",
                file.getName(), requestBody);

        UsersAPI usersAPI = URL.getInstance().create(UsersAPI.class);
        retrofit2.Call<ImageResponse> responseBodyCall = usersAPI.uploadImage(body);

        StrictModeClass.StrictMode();
        //Synchronous method
        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(this, "Image inserted" + imageName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void addlost() {


        String name = lostname.getText().toString();
        String type = losttype.getText().toString();
        String location = lostlocation.getText().toString();
        String phone = lostphone.getText().toString();
        String details = lostdetails.getText().toString();
        String status = loststatus.getText().toString();

        Lost lost = new Lost(name, type, location, phone, details, status , imageName);

        UsersAPI usersAPI = URL.getInstance().create(UsersAPI.class);
        Call<RegisterResponse> registerCall =usersAPI.addlost (lost);

        registerCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(retrofit2.Call<RegisterResponse> call, retrofit2.Response<RegisterResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(AddLostActivity.this, "Code" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(AddLostActivity.this, "Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                RoomNotification();
                finish();
            }

            @Override
            public void onFailure(retrofit2.Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(AddLostActivity.this, "Error" +t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }




        });
    }
    private void RoomNotification() {

        Notification notification = new NotificationCompat.Builder(this, CreateChannel.CHANNEL_1)
                .setContentTitle(" Application")
                .setContentText(" Successfully Uploaded")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1, notification);

    }

}




















//package com.softwarica.lostandfound.LF;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.loader.content.CursorLoader;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.softwarica.lostandfound.API.URL.URL;
//import com.softwarica.lostandfound.API.UsersAPI;
//import com.softwarica.lostandfound.R;
//import com.softwarica.lostandfound.StrictModeClass.StrictModeClass;
//import com.softwarica.lostandfound.module.Lost;
//import com.softwarica.lostandfound.serverresponse.ImageResponse;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URI;
//
//import okhttp3.Call;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import retrofit2.http.Url;
//
//public class AddLostActivity extends AppCompatActivity {
//
//    EditText lostname, losttype, lostlocation,lostphone, lostdetails,loststatus;
//    Button btnaddlost;
//    ImageView lostimage;
//    String imagePath;
//    private String imageName= "";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_lost);
//
////        lostname = findViewById(R.id.lostname);
////        losttype = findViewById(R.id.losttype);
////        lostphone = findViewById(R.id.lostphone);
////        lostdetails = findViewById(R.id.lostdetails);
////        loststatus = findViewById(R.id.loststatus);
////        btnaddlost = findViewById(R.id.btnaddlost;
////        lostimage = findViewById(R.id.lostimage);
////
////        lostimage.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                BrowseImage();
////            }
////        });
////        btnaddlost.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (validate()) {
////                    addlost();
////                    saveImageOnly();
////                }else {
////                    Toast.makeText(AddLostActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
////                    lostname.requestFocus();
////                    return;
////                }
////            }
////        });
////
////
////    }
////    private boolean validate() {
////        boolean status = true;
////        if (lostname.getText().toString().length() < 6) {
////            status = false;
////        }
////        return status;
////    }
////
////    private void BrowseImage() {
////        Intent intent = new Intent(Intent.ACTION_PICK);
////        intent.setType("image/*");
////        startActivityForResult(intent, 0);
////    }
////
////    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////
////        if (resultCode == RESULT_OK) {
////            if (data == null) {
////                Toast.makeText(this, "Please select an image ", Toast.LENGTH_SHORT).show();
////            }
////        }
////        Uri uri= data.getData();
////        lostimage.setImageURI(uri);
////        imagePath = getRealPathFromUri(uri);
////    }
////
////    private String getRealPathFromUri(URI uri) {
////        String[] projection = {MediaStore.Images.Media.DATA};
////        CursorLoader loader = new CursorLoader(getApplicationContext());
////        Cursor cursor = loader.loadInBackground();
////        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
////        cursor.moveToFirst();
////        String result = cursor.getString(colIndex);
////        cursor.close();
////        return result;
////    }
////
////    private void saveImageOnly() {
////        File file = new File(imagePath);
////        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
////        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",
////                file.getName(), requestBody);
////
////        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
////        Call<ImageResponse> responseBodyCall = usersAPI.uploadImage(body);
////
////        StrictModeClass.StrictMode();
////        //Synchronous method
////        try {
////            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
////            imageName = imageResponseResponse.body().getFilename();
////            Toast.makeText(this, "Image inserted" + imageName, Toast.LENGTH_SHORT).show();
////        } catch (IOException e) {
////            Toast.makeText(this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
////            e.printStackTrace();
////        }
////    }
////
////    private void addlost() {
////
////        String name = lostname.getText().toString();
////        String type = losttype.getText().toString();
////        String location = lostlocation.getText().toString();
////        String phone = lostphone.getText().toString();
////        String details = lostdetails.getText().toString();
////        String status = loststatus.getText().toString();
////
////        Lost lost = new Lost(name, type, location, phone, details, status, imageName);
////
////        UsersAPI usersAPI = URL.getInstance().create(UsersAPI.class);
////        Call<RoomResponse> roomCall = usersAPI.registerRoom(room);
////
////        roomCall.enqueue(new Callback<RoomResponse>() {
////            @Override
////            public void onResponse(Call<RoomResponse> call, Response<RoomResponse> response) {
////                if (!response.isSuccessful()) {
////                    Toast.makeText(UploadRoomActivity.this, "Code" + response.code(), Toast.LENGTH_SHORT).show();
////                    return;
////                } else
////                    Toast.makeText(UploadRoomActivity.this, "Room Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(UploadRoomActivity.this, DashboardActivity.class);
////                startActivity(intent);
////                finish();
////            }
////
////            @Override
////            public void onFailure(Call<RoomResponse> call, Throwable t) {
////                Toast.makeText(UploadRoomActivity.this, "Error, Room uploaded failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
////            }
////        });
////    }
//
//}
//}
