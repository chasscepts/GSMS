package com.chass.gsms.networks.retrofit;

import com.chass.gsms.models.Class;
import com.chass.gsms.models.LoginResponse;
import com.chass.gsms.models.PlainResponse;
import com.chass.gsms.models.Student;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiClient {

  /**
   *
   */
  @FormUrlEncoded
  @POST("school/user")
  Call<LoginResponse> login(
      @Field("schoolId") int schoolId,
      @Field("email") String email,
      @Field("password") String password
  );

  @Multipart
  @POST("school/register")
  Call<LoginResponse> register(
      @Part("schoolName") RequestBody schoolName,
      @Part("schoolAddress") RequestBody schoolAddress,
      @Part("schoolEmail") RequestBody schoolEmail,
      @Part("schoolPhoneNumber") RequestBody schoolPhoneNumber,
      @Part MultipartBody.Part schoolProfilePicture,
      @Part("adminFirstname") RequestBody adminFirstname,
      @Part("adminLastname") RequestBody adminLastname,
      @Part("adminEmail") RequestBody adminEmail,
      @Part("adminPhoneNumber") RequestBody adminPhoneNumber,
      @Part("adminPassword") RequestBody password
  );

  @FormUrlEncoded
  @POST("school/class")
  Call<Class> addClass(
      @Field("schoolId") int schoolId,
      @Field("className") String className,
      @Field("teacherFirstname") String teacherFirstname,
      @Field("teacherLastname") String teacherLastname,
      @Field("teacherEmail") String teacherEmail,
      @Field("teacherPhoneNumber") String teacherPhoneNumber
  );

  @GET("school/class/{schoolId}/{classId}")
  Call<Class> getClass(@Path("schoolId") int schoolId, @Path("classId") int classId);

  @FormUrlEncoded
  @POST("school/student")
  Call<Student> addStudent(
      @Field("schoolId") int schoolId,
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
   * If parent2 fields are not filled we use this overload to add student
   * @param schoolId
   * @param className
   * @param studentFirstname
   * @param studentLastname
   * @param parent1Firstname
   * @param parent1Lastname
   * @param parent1Email
   * @param parent1PhoneNumber
   * @return
   */
  @FormUrlEncoded
  @POST("school/student")
  Call<Student> addStudent(
      @Field("schoolId") int schoolId,
      @Field("className") String className,
      @Field("studentFirstname") String studentFirstname,
      @Field("studentLastname") String studentLastname,
      @Field("parent1Firstname") String parent1Firstname,
      @Field("parent1Lastname") String parent1Lastname,
      @Field("parent1Email") String parent1Email,
      @Field("parent1PhoneNumber") String parent1PhoneNumber
  );

  /**
   * Posts the attendance of a class to the endpoint
   * @param schoolId GSMS id of school
   * @param classId Name of class
   * @param date Date the attendance is taken
   * @param jsonString JSON representation of Attendance Array. Each object should be of form {id: studentId, status: [0 or 1]}
   * @return
   */
  @FormUrlEncoded
  @POST("school/attendance")
  Call<PlainResponse> postAttendance(
      @Field("schoolId") int schoolId,
      @Field("classId") int classId,
      @Field("date") String date,
      @Field("attendance") String jsonString
  );

  /**
   * Makes an api request to retrieve student attendance for a single date
   * @param schoolId The id of school
   * @param classId Name of class
   * @param studentId id of student
   * @return A retrofit Call wrapper network response
   */
  @GET("school/attendance/student/{schoolId}/{classId}/{studentId}/{year}/{month}/{start}/{count}")
  Call<List<String>> getStudentAttendance(
      @Path("schoolId") int schoolId,
      @Path("classId") int classId,
      @Path("studentId") int studentId,
      @Path("year") int year,
      @Path("month") int month,
      @Path("start") int startDate,
      @Path("count") int count
  );
  ///schoolId/classId/studentId/year/month/start/count
}
