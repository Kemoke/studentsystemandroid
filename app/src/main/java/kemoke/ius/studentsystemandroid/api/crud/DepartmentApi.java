package kemoke.ius.studentsystemandroid.api.crud;

import java.util.List;

import kemoke.ius.studentsystemandroid.models.Department;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DepartmentApi {
    @GET("/department")
    Call<List<Department>> list();

    @GET("/department/with/{props}")
    Call<List<Department>> listWithProps(@Path("props") String props);

    @GET("/department/where/{prop}={value}")
    Call<List<Department>> listWhereProp(@Path("prop") String prop, @Path("value") String value);

    @GET("/department/where/{prop}={value}/with/{props}")
    Call<List<Department>> listWherePropWithProps(@Path("prop") String prop, @Path("value") String value, @Path("props") String props);

    @POST("/department")
    Call<Department> add(@Body Department body);

    @GET("/department/{id}")
    Call<Department> get(@Path("id") int id);

    @GET("/department/{id}/with/{props}")
    Call<Department> getWithProps(@Path("id") int id, @Path("props") String props);

    @GET("/department/{id}/where/{prop}={value}")
    Call<Department> getWhereProp(@Path("id") int id, @Path("prop") String prop, @Path("value") String value);

    @GET("/department/{id}/where/{prop}={value}/with/{props}")
    Call<Department> getWherePropWithProps(@Path("id") int id, @Path("prop") String prop, @Path("value") String value, @Path("props") String props);

    @PUT("/department/{id}")
    Call<Department> edit(@Path("id") int id, @Body Department body);

    @DELETE("/department/{id}")
    Call<String> delete(@Path("id") int id);
}
