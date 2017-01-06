package kemoke.ius.studentsystemandroid.activities.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kemoke.ius.studentsystemandroid.R;
import kemoke.ius.studentsystemandroid.adapters.NewsListAdapter;
import kemoke.ius.studentsystemandroid.api.HttpApi;
import kemoke.ius.studentsystemandroid.models.Article;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kemoke.ius.studentsystemandroid.util.ThisApplication.getThisApplication;

public class NewsFragment extends Fragment implements Callback<List<Article>> {

    @BindView(R.id.newsList)
    RecyclerView newsList;

    List<Article> news;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int cols = Math.round((displayMetrics.widthPixels / displayMetrics.density) / 320);
        newsList.setLayoutManager(new StaggeredGridLayoutManager(cols, StaggeredGridLayoutManager.VERTICAL));
        news = getThisApplication().getPreferences().getList(Article.class, "news");
        newsList.setAdapter(new NewsListAdapter(getContext(), news));
        HttpApi.newsApi().getNews().enqueue(this);
        return view;
    }

    @Override
    public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
        if(response.isSuccessful()){
            news.clear();
            news.addAll(response.body());
            newsList.getAdapter().notifyDataSetChanged();
            getThisApplication().getPreferences().saveList(response.body(), "news");
        } else {
            try {
                Log.e("srv err", response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<List<Article>> call, Throwable t) {
        Log.e("news api", t.getMessage());
    }
}
