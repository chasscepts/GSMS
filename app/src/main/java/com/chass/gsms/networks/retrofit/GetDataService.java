package com.chass.gsms.networks.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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

  @FormUrlEncoded
  @POST("school/class")
  Call<String> addClass(
      @Field("school_id") String schoolId,
      @Field("class_name") String className,
      @Field("teacher_firstname") String teacherFirstname,
      @Field("teacher_lastname") String teacherLastname,
      @Field("teacher_email") String teacherEmail,
      @Field("teacher_phone_number") String teacherPhoneNumber
  );

  @GET("school/class/{id}/{name}")
  Call<String> getClass(@Path("id") String schoolId, @Path("name") String className);

  @FormUrlEncoded
  @POST("school/student")
  Call<String> addStudent(
      @Field("school_id") String schoolId,
      @Field("class_name") String className,
      @Field("parent1_firstname") String parent1Firstname,
      @Field("parent1_lastname") String parent1Lastname,
      @Field("parent1_email") String parent1Email,
      @Field("parent1_phone_number") String parent1PhoneNumber,
      @Field("parent2_firstname") String parent2Firstname,
      @Field("parent2_lastname") String parent2Lastname,
      @Field("parent2_email") String parent2Email,
      @Field("parent2_phone_number") String parent2PhoneNumber
  );
}
