package com.chass.gsms.networks.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GetDataService {

  /**
   *
   */
  @FormUrlEncoded
  @POST("user")
  Call<String> login(
      @Field("school_id") String schoolId,
      @Field("email") String email,
      @Field("password") String password
  );

  @Multipart
  @POST("school")
  Call<String> register(
    @Field("school_name") String schoolName,
    @Field("school_address") String schoolAddress,
    @Field("school_email") String schoolEmail,
    @Field("school_phone_number") String schoolPhoneNumber,
    @Part("school_picture") String schoolProfilePicture,
    @Field("admin_firstname") String adminFirstname,
    @Field("admin_lastname") String adminLastname,
    @Field("admin_email") String adminEmail,
    @Field("admin_phone_number") String adminPhoneNumber,
    @Field("admin_password") String password
  );
}
