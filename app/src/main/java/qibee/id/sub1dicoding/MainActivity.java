package qibee.id.sub1dicoding;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import qibee.id.sub1dicoding.databinding.ActivityMainBinding;
import qibee.id.sub1dicoding.model.Movie;
import qibee.id.sub1dicoding.model.Results;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements Constants {
    ActivityMainBinding viewBinding;
    BaseApiService baseApiService;
    String query;
    MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        baseApiService = Api.getApiService();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        viewBinding.recyclerviewMovie.setLayoutManager(layoutManager);
        movieAdapter = new MovieAdapter(this);
        viewBinding.recyclerviewMovie.setAdapter(movieAdapter);

        if(savedInstanceState!=null){
            movieList = savedInstanceState.getParcelableArrayList("key");
            movieAdapter.setMovies(movieList);
            Timber.d(movieList.get(0).getTitle());
        }

        setTitleBar();
        movieAdapter.setOnItemClickListener((v, position) -> {
            Movie movie = movieAdapter.getResultAtPosition(position);
            Intent intent = new Intent(MainActivity.this, DetailMovieActivity.class);
            intent.putExtra(MOVIE_EXTRA, movie);
            startActivity(intent);
        });
    }

    private void setTitleBar() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Searching");
        }
    }


    public void searchButtonClicked(View view) {
        query = viewBinding.inputSearch.getText().toString();
        baseApiService.search(query, LANGUAGE, BuildConfig.MOVIEDB_API_KEY).enqueue(new Callback<Results>() {
            @Override
            public void onResponse(@NonNull Call<Results> call, @NonNull Response<Results> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        movieList = response.body().getMovies();
                        movieAdapter.setMovies(movieList);
                    } else {
                        Log.d("response = ", "null");

                    }
                } else {
                    Log.d("response ", "try again.. never give up!!");

                }
            }

            @Override
            public void onFailure(@NonNull Call<Results> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, getString(R.string.text_tryagain), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("key", (ArrayList<? extends Parcelable>) movieList);
    }
}
