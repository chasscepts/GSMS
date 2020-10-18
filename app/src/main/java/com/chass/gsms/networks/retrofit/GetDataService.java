package com.chass.gsms.networks.retrofit;

import com.chass.gsms.models.Attendance;
import com.chass.gsms.models.Class;
import com.chass.gsms.models.LoginResponse;
import com.chass.gsms.models.PlainResponse;
import com.chass.gsms.models.Student;

import java.util.List;

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
  Call<LoginResponse> login(
      @Field("schoolId") int schoolId,
      @Field("email") String email,
      @Field("password") String password
  );

  @Multipart
  @POST("school/register")
  Call<LoginResponse> register(
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
  Call<Class> addClass(
      @Field("schoolId") int schoolId,
      @Field("className") String className,
      @Field("teacherFirstname") String teacherFirstname,
      @Field("teacherLastname") String teacherLastname,
      @Field("teacherEmail") String teacherEmail,
      @Field("teacherPhoneNumber") String teacherPhoneNumber
  );

  @GET("school/class/{id}/{name}")
  Call<Class> getClass(@Path("id") int schoolId, @Path("name") String className);

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
   * @param className Name of class
   * @param date Date the attendance is taken
   * @param jsonString JSON representation of Attendance Array. Each object should be of form {id: studentId, status: [0 or 1]}
   * @return
   */
  @FormUrlEncoded
  @POST("school/attendance")
  Call<Attendance> postAttendance(
    @Field("schoolId") int schoolId,
    @Field("className") String className,
    @Field("date") String date,
    @Field("attendance") String jsonString
  );

  /**
   * Makes an api request to retrieve class attendance for a single date
   * @param schoolId The id of school
   * @param className Name of class
   * @param date Date to retrieve attendance [format: yyyy-MM-dd]
   * @return A retrofit Call wrapper network response
   */
  @GET("school/attendance/{schoolId}/{className}/{date}")
  Call<Attendance> getAttendance(
      @Path("schoolId") int schoolId,
      @Path("className") String className,
      @Path("date") String date
  );

  /**
   * Makes an api request to retrieve class attendances for a period
   * @param schoolId The id of school
   * @param className Name of class
   * @param startDate  inclusive start date to retrieve attendances [format: yyyy-MM-dd]
   * @param endDate  inclusive end date to retrieve attendances [format: yyyy-MM-dd]
   * @return A retrofit Call wrapper network response
   */
  @GET("school/attendance/{schoolId}/{className}/{startDate}/{endDate}")
  Call<List<Attendance>> getAttendances(
      @Path("schoolId") int schoolId,
      @Path("className") String className,
      @Path("startDate") String startDate,
      @Path("endDate") String endDate
  );
}
