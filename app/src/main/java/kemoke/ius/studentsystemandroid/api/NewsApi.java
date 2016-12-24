package kemoke.ius.studentsystemandroid.api;

import java.util.List;

import kemoke.ius.studentsystemandroid.models.Article;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NewsApi {

    @GET("/news")
    Call<List<Article>> getNews();

    @POST("/news")
    Call<Article> getArticle(@Body String url);
}
