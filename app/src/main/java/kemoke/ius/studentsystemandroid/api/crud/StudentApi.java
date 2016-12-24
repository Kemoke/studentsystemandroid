package kemoke.ius.studentsystemandroid.api.crud;

import java.util.List;

import kemoke.ius.studentsystemandroid.models.Student;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudentApi{
    @GET("/student")
    Call<List<Student>> list();

    @GET("/student/with/{props}")
    Call<List<Student>> listWithProps(@Path("props") String props);

    @GET("/student/where/{prop}={value}")
    Call<List<Student>> listWhereProp(@Path("prop") String prop, @Path("value") String value);

    @GET("/student/where/{prop}={value}/with/{props}")
    Call<List<Student>> listWherePropWithProps(@Path("prop") String prop, @Path("value") String value, @Path("props") String props);

    @POST("/student")
    Call<Student> add(@Body Student body);

    @GET("/student/{id}")
    Call<Student> get(@Path("id") int id);

    @GET("/student/{id}/with/{props}")
    Call<Student> getWithProps(@Path("id") int id, @Path("props") String props);

    @GET("/student/{id}/where/{prop}={value}")
    Call<Student> getWhereProp(@Path("id") int id, @Path("prop") String prop, @Path("value") String value);

    @GET("/student/{id}/where/{prop}={value}/with/{props}")
    Call<Student> getWherePropWithProps(@Path("id") int id, @Path("prop") String prop, @Path("value") String value, @Path("props") String props);

    @PUT("/student/{id}")
    Call<Student> edit(@Path("id") int id, @Body Student body);

    @DELETE("/student/{id}")
    Call<String> delete(@Path("id") int id);
}
