package kemoke.ius.studentsystemandroid.api.crud;

import java.util.List;

import kemoke.ius.studentsystemandroid.models.Section;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SectionApi {
    @GET("/section")
    Call<List<Section>> list();

    @GET("/section/with/{props}")
    Call<List<Section>> listWithProps(@Path("props") String props);

    @GET("/section/where/{prop}={value}")
    Call<List<Section>> listWhereProp(@Path("prop") String prop, @Path("value") String value);

    @GET("/section/where/{prop}={value}/with/{props}")
    Call<List<Section>> listWherePropWithProps(@Path("prop") String prop, @Path("value") String value, @Path("props") String props);

    @POST("/section")
    Call<Section> add(@Body Section body);

    @GET("/section/{id}")
    Call<Section> get(@Path("id") int id);

    @GET("/section/{id}/with/{props}")
    Call<Section> getWithProps(@Path("id") int id, @Path("props") String props);

    @GET("/section/{id}/where/{prop}={value}")
    Call<Section> getWhereProp(@Path("id") int id, @Path("prop") String prop, @Path("value") String value);

    @GET("/section/{id}/where/{prop}={value}/with/{props}")
    Call<Section> getWherePropWithProps(@Path("id") int id, @Path("prop") String prop, @Path("value") String value, @Path("props") String props);

    @PUT("/section/{id}")
    Call<Section> edit(@Path("id") int id, @Body Section body);

    @DELETE("/section/{id}")
    Call<String> delete(@Path("id") int id);
}
