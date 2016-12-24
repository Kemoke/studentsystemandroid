package kemoke.ius.studentsystemandroid.api;

import kemoke.ius.studentsystemandroid.models.User;
import kemoke.ius.studentsystemandroid.util.TokenJson;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("/auth/login")
    @FormUrlEncoded
    Call<TokenJson> login(@Field("email") String email, @Field("password") String password);

    @POST("/auth/register")
    @FormUrlEncoded
    Call<User> register(@Field("username") String username, @Field("email") String email, @Field("password") String password);

    @GET("/user/self")
    Call<User> self();
}
