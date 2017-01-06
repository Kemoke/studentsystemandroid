package kemoke.ius.studentsystemandroid.api;

import java.util.List;

import kemoke.ius.studentsystemandroid.models.Article;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsApi {

    @GET("/news")
    Call<List<Article>> getNews();
}
