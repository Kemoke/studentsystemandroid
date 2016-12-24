package kemoke.ius.studentsystemandroid.api.crud;

import java.util.List;

import kemoke.ius.studentsystemandroid.models.Instructor;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InstructorApi {
    @GET("/instructor")
    Call<List<Instructor>> list();

    @GET("/instructor/with/{props}")
    Call<List<Instructor>> listWithProps(@Path("props") String props);

    @GET("/instructor/where/{prop}={value}")
    Call<List<Instructor>> listWhereProp(@Path("prop") String prop, @Path("value") String value);

    @GET("/instructor/where/{prop}={value}/with/{props}")
    Call<List<Instructor>> listWherePropWithProps(@Path("prop") String prop, @Path("value") String value, @Path("props") String props);

    @POST("/instructor")
    Call<Instructor> add(@Body Instructor body);

    @GET("/instructor/{id}")
    Call<Instructor> get(@Path("id") int id);

    @GET("/instructor/{id}/with/{props}")
    Call<Instructor> getWithProps(@Path("id") int id, @Path("props") String props);

    @GET("/instructor/{id}/where/{prop}={value}")
    Call<Instructor> getWhereProp(@Path("id") int id, @Path("prop") String prop, @Path("value") String value);

    @GET("/instructor/{id}/where/{prop}={value}/with/{props}")
    Call<Instructor> getWherePropWithProps(@Path("id") int id, @Path("prop") String prop, @Path("value") String value, @Path("props") String props);

    @PUT("/instructor/{id}")
    Call<Instructor> edit(@Path("id") int id, @Body Instructor body);

    @DELETE("/instructor/{id}")
    Call<String> delete(@Path("id") int id);
}
