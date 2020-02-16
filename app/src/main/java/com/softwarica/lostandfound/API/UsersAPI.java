package com.softwarica.lostandfound.API;

import com.softwarica.lostandfound.module.Feedback;
import com.softwarica.lostandfound.module.Human;
import com.softwarica.lostandfound.module.Lost;
import com.softwarica.lostandfound.module.Products;
import com.softwarica.lostandfound.module.User;
import com.softwarica.lostandfound.serverresponse.ImageResponse;
import com.softwarica.lostandfound.serverresponse.RegisterResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface UsersAPI {
    @POST("users/register")
    Call<RegisterResponse> registerUser(@Body User users);

    @FormUrlEncoded
    @POST("users/login")
    Call<RegisterResponse> checkUser(@Field("email") String email, @Field("password") String password);

    @Multipart
    @POST("upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

    @GET("users/me")
    Call<User> getUserDetails(@Header("Authorization") String token);

    @GET("users/user")
    Call<List<User>> getUser(@Header("Authorization") String token);

    @GET("feedback/")
    Call<List<Feedback>> getFeedback(@Header("Authorization") String token);

    @FormUrlEncoded
    @PUT("users/userupdate")
    Call<RegisterResponse> userUpdate(@Header("Authorization") String token, @Field("_id") String id, @Field("fullname") String fullname, @Field("address") String address, @Field("phone") String phone, @Field("email") String email);

    @FormUrlEncoded
    @POST("/feedback")
    Call<Void> feedback(@Header("Authorization") String token, @Field("fullname") String fullname, @Field("email") String email, @Field("feedback") String feedback);

    @GET("human/")
    Call<List<Human>> getHuman(@Header("Authorization") String token);

    //@GET("products/")
    //Call<List<Products>> getproduct (@Header("Authorization")String token);

    @GET("product")
    Call<List<Products>> getproduct();



    //(@Header("Authorization") String token)
    @POST ("lost/addlost")
    Call<RegisterResponse> addlost(@Body Lost lost);
    @GET("lost/listlost")
    Call<List<Lost>>getalllost();
}

