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
  @POST("school/user")
  Call<String> login(
      @Field("schoolId") String schoolId,
      @Field("email") String email,
      @Field("password") String password
  );

  @Multipart
  @POST("school/register")
  Call<String> register(
    @Field("schoolName") String schoolName,
    @Field("schoolAddress") String schoolAddress,
    @Field("schoolEmail") String schoolEmail,
    @Field("schoolPhoneNumber") String schoolPhoneNumber,
    @Part("schoolPicture") String schoolProfilePicture,
    @Field("adminFirstname") String adminFirstname,
    @Field("adminLastname") String adminLastname,
    @Field("adminEmail") String adminEmail,
    @Field("adminPhoneNumber") String adminPhoneNumber,
    @Field("adminPassword") String password
  );

  @FormUrlEncoded
  @POST("school/class")
  Call<String> addClass(
      @Field("schoolId") String schoolId,
      @Field("className") String className,
      @Field("teacherFirstname") String teacherFirstname,
      @Field("teacherLastname") String teacherLastname,
      @Field("teacherEmail") String teacherEmail,
      @Field("teacherPhoneNumber") String teacherPhoneNumber
  );

  @GET("school/class/{id}/{name}")
  Call<String> getClass(@Path("id") String schoolId, @Path("name") String className);

  @FormUrlEncoded
  @POST("school/student")
  Call<String> addStudent(
    @Field("schoolId") String schoolId,
    @Field("className") String className,
    @Field("studentFirstname") String studentFirstname,
    @Field("studentLastname") String studentLastname,
    @Field("parent1Firstname") String parent1Firstname,
    @Field("parent1Lastname") String parent1Lastname,
    @Field("parent1Email") String parent1Email,
    @Field("parent1PhoneNumber") String parent1PhoneNumber,
    @Field("parent2Firstname") String parent2Firstname,
    @Field("parent2Lastname") String parent2Lastname,
    @Field("parent2Email") String parent2Email,
    @Field("parent2PhoneNumber") String parent2PhoneNumber
  );

  /**
   * Posts the attendance of a class to the endpoint
   * @param schoolId GSMS id of school
   * @param className Name of class
   * @param date Date the attendance is taken
   * @param jsonString JSON representation of Attendance Array. Each object should be of form {id: studentId, status: [0 or 1]}
   * @return
   */
  @FormUrlEncoded
  @POST("school/attendance")
  Call<String> postAttendance(
    @Field("schoolId") String schoolId,
    @Field("className") String className,
    @Field("date") String date,
    @Field("attendance") String jsonString
  );
}
