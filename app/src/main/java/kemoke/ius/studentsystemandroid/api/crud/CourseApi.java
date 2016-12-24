package kemoke.ius.studentsystemandroid.api.crud;

import java.util.List;

import kemoke.ius.studentsystemandroid.models.Course;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CourseApi {
    @GET("/course")
    Call<List<Course>> list();

    @GET("/course/with/{props}")
    Call<List<Course>> listWithProps(@Path("props") String props);

    @GET("/course/where/{prop}={value}")
    Call<List<Course>> listWhereProp(@Path("prop") String prop, @Path("value") String value);

    @GET("/course/where/{prop}={value}/with/{props}")
    Call<List<Course>> listWherePropWithProps(@Path("prop") String prop, @Path("value") String value, @Path("props") String props);

    @POST("/course")
    Call<Course> add(@Body Course body);

    @GET("/course/{id}")
    Call<Course> get(@Path("id") int id);

    @GET("/course/{id}/with/{props}")
    Call<Course> getWithProps(@Path("id") int id, @Path("props") String props);

    @GET("/course/{id}/where/{prop}={value}")
    Call<Course> getWhereProp(@Path("id") int id, @Path("prop") String prop, @Path("value") String value);

    @GET("/course/{id}/where/{prop}={value}/with/{props}")
    Call<Course> getWherePropWithProps(@Path("id") int id, @Path("prop") String prop, @Path("value") String value, @Path("props") String props);

    @PUT("/course/{id}")
    Call<Course> edit(@Path("id") int id, @Body Course body);

    @DELETE("/course/{id}")
    Call<String> delete(@Path("id") int id);
}
