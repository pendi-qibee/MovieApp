package qibee.id.sub1dicoding;

import android.content.ContentValues;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import qibee.id.sub1dicoding.database.FavoriteSqlHelper;
import qibee.id.sub1dicoding.databinding.ActivityDetailMovieBinding;
import qibee.id.sub1dicoding.model.Movie;
import qibee.id.sub1dicoding.provider.Contract;
import qibee.id.sub1dicoding.provider.FavoritColumn;

public class DetailMovieActivity extends AppCompatActivity implements Constants {
    Movie movie;
    ActivityDetailMovieBinding viewBinding;
    private Boolean isFavorite = false;
    FavoriteSqlHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_movie);
        setTitleBar();
        movie = getIntent().getParcelableExtra(MOVIE_EXTRA);
        checkFavoriteStatus();
            //set movieItem pada layout
        if (movie != null) viewBinding.setMovieItem(movie);
    }

    private void checkFavoriteStatus() {
        sqlHelper = new FavoriteSqlHelper(this);
        sqlHelper.open();

        Cursor cursor = getContentResolver().query(Uri.parse(Contract.CONTENT_URI + "/" + movie.getId()),
                null,
                null,
                null,
                null
        );
        if (cursor != null) {
            if (cursor.moveToFirst()) isFavorite = true;
            cursor.close();
        }
        setIcon();
    }

    private void setTitleBar() {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.detail_title);
        }
    }

    public void setFavoriteIcon(View view) {
        if (isFavorite) removeFromFavorite();
        else addToFavorite();

        isFavorite = !isFavorite;
        setIcon();
    }

    private void setIcon() {
        if (isFavorite) viewBinding.imageFavorite.setImageResource(R.drawable.ic_lace);
        else viewBinding.imageFavorite.setImageResource(R.drawable.ic_star);
    }

    private void removeFromFavorite() {
        getContentResolver().delete(
                Uri.parse(Contract.CONTENT_URI + "/" + movie.getId()),
                null,
                null
        );
    }

    private void addToFavorite() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoritColumn._ID, movie.getId());
        contentValues.put(FavoritColumn.TITLE, movie.getTitle());
        contentValues.put(FavoritColumn.BACKDROP, movie.getBackdropPath());
        contentValues.put(FavoritColumn.POSTER, movie.getPosterPath());
        contentValues.put(FavoritColumn.RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(FavoritColumn.VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(FavoritColumn.VOTE_COUNT, movie.getVoteCount());
        contentValues.put(FavoritColumn.OVERVIEW, movie.getOverview());

        getContentResolver().insert(Contract.CONTENT_URI, contentValues);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( sqlHelper != null) sqlHelper.close();

    }
}
