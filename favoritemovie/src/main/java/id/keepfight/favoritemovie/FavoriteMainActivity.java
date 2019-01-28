package id.keepfight.favoritemovie;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import id.keepfight.favoritemovie.databinding.ActivityFavoriteMainBinding;

public class FavoriteMainActivity extends AppCompatActivity implements Constants, LoaderManager.LoaderCallbacks<Cursor> {

    MovieFavoriteAdapter movieFavoriteAdapter;
    ActivityFavoriteMainBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite_main);
        movieFavoriteAdapter = new MovieFavoriteAdapter(this);
        viewBinding.recyclerviewMovie.setLayoutManager(new LinearLayoutManager(this));
        getSupportLoaderManager().initLoader(2, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, DbContract.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        viewBinding.recyclerviewMovie.setAdapter(movieFavoriteAdapter);
        movieFavoriteAdapter.setMovies(cursor);
        setClickListener();
    }

    private void setClickListener() {
        movieFavoriteAdapter.setOnItemClickListener((v, position) -> {
            Movie movie = movieFavoriteAdapter.getMovieItem(position);
            Intent intent = new Intent(FavoriteMainActivity.this, FavoriteDetailActivity.class);
            intent.putExtra(MOVIE_EXTRA, movie);
            startActivity(intent);
        });
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(2, null, this);
    }
}
