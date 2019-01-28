package qibee.id.sub1dicoding;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import qibee.id.sub1dicoding.databinding.FragmentMovieBinding;
import qibee.id.sub1dicoding.model.Movie;
import qibee.id.sub1dicoding.model.Results;
import qibee.id.sub1dicoding.provider.Contract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static qibee.id.sub1dicoding.Constants.MOVIE_EXTRA;


public class MovieFragment extends Fragment {
    BaseApiService apiService;
//    private List<Movie> movieList;
    private MovieAdapter movieAdapter;
    private static final String ARG_CODE_MOVIE = "code_movie";
    FragmentMovieBinding binding;
    MovieFavoriteAdapter movieFavoriteAdapter;
    MovieUpcomingAdapter movieUpcomingAdapter;
    Context context;
    public MovieFragment() {
        // Required empty public constructor
    }

    public static MovieFragment newInstance(int sectionNumber) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CODE_MOVIE, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        apiService = Api.getApiService();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie, container, false);
        context = binding.getRoot().getContext();
        movieAdapter = new MovieAdapter(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvMovie.setLayoutManager(layoutManager);

        if (getArguments() != null) {
            setFragment();
        }
        return binding.getRoot();
    }

    private void setFragment() {
        assert getArguments() != null;
        int CODE_FRAGMENT = getArguments().getInt(ARG_CODE_MOVIE);
        switch (CODE_FRAGMENT) {
            case 0:
                getNowPlayingMovieList();
                break;
            case 1:
                getUpcomingMovieList();
                break;
            case 2:
                getFavoriteMovieList();
                break;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void getFavoriteMovieList() {
        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... voids) {
                return context.getContentResolver().query(
                        Contract.CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                super.onPostExecute(cursor);
                movieFavoriteAdapter = new MovieFavoriteAdapter(getContext());
                    binding.rvMovie.setAdapter(movieFavoriteAdapter);
                    movieFavoriteAdapter.setMovies(cursor);
                    setClickListenerFavorite();

            }
        }.execute();

    }

    private void setClickListenerFavorite() {
        movieFavoriteAdapter.setOnItemClickListener((v, position) -> {
            Movie movie = movieFavoriteAdapter.getMovieItem(position);
            getDetail(movie);

        });
    }

    private void getDetail(Movie movie) {
        Intent intent = new Intent(getContext(), DetailMovieActivity.class);
        intent.putExtra(MOVIE_EXTRA, movie);
        startActivity(intent);
    }

    private void getNowPlayingMovieList() {
        apiService.getNowPlaying().enqueue(new Callback<Results>() {
            @Override
            public void onResponse(@NonNull Call<Results> call, @NonNull Response<Results> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Movie> movieList = response.body().getMovies();
                        Log.d("movie title now", movieList.get(0).getOriginalTitle());
                        movieUpcomingAdapter = new MovieUpcomingAdapter(getContext());
                        binding.rvMovie.setAdapter(movieUpcomingAdapter);
                        movieUpcomingAdapter.setMovies(movieList);
                        setClickListener2();
                    }
                } else {
                    Log.d("movie title", "error");

                }
            }

            @Override
            public void onFailure(@NonNull Call<Results> call, @NonNull Throwable t) {

            }
        });


    }

    private void setClickListener2() {
        movieUpcomingAdapter.setOnItemClickListener((v, position) -> {
            Movie movie = movieUpcomingAdapter.getResultAtPosition(position);
            getDetail(movie);

        });
    }

    private void getUpcomingMovieList() {
        apiService.getUpcoming().enqueue(new Callback<Results>() {
            @Override
            public void onResponse(@NonNull Call<Results> call, @NonNull Response<Results> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Movie> movieList = response.body().getMovies();
                        movieAdapter = new MovieAdapter(getContext());
                        binding.rvMovie.setAdapter(movieAdapter);
                        movieAdapter.setMovies(movieList);
                        setClickListener();
                    }
                } else {
                    Log.d("result title", "error");

                }
            }

            @Override
            public void onFailure(@NonNull Call<Results> call, @NonNull Throwable t) {

            }
        });
    }

    private void setClickListener() {
        movieAdapter.setOnItemClickListener((v, position) -> {
            Movie movie = movieAdapter.getResultAtPosition(position);
            getDetail(movie);

        });
    }


}
