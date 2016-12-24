package kemoke.ius.studentsystemandroid.api.crud;

import java.util.List;

import kemoke.ius.studentsystemandroid.models.Program;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProgramApi {
    @GET("/program")
    Call<List<Program>> list();

    @GET("/program/with/{props}")
    Call<List<Program>> listWithProps(@Path("props") String props);

    @GET("/program/where/{prop}={value}")
    Call<List<Program>> listWhereProp(@Path("prop") String prop, @Path("value") String value);

    @GET("/program/where/{prop}={value}/with/{props}")
    Call<List<Program>> listWherePropWithProps(@Path("prop") String prop, @Path("value") String value, @Path("props") String props);

    @POST("/program")
    Call<Program> add(@Body Program body);

    @GET("/program/{id}")
    Call<Program> get(@Path("id") int id);

    @GET("/program/{id}/with/{props}")
    Call<Program> getWithProps(@Path("id") int id, @Path("props") String props);

    @GET("/program/{id}/where/{prop}={value}")
    Call<Program> getWhereProp(@Path("id") int id, @Path("prop") String prop, @Path("value") String value);

    @GET("/program/{id}/where/{prop}={value}/with/{props}")
    Call<Program> getWherePropWithProps(@Path("id") int id, @Path("prop") String prop, @Path("value") String value, @Path("props") String props);

    @PUT("/program/{id}")
    Call<Program> edit(@Path("id") int id, @Body Program body);

    @DELETE("/program/{id}")
    Call<String> delete(@Path("id") int id);
}
