package id.keepfight.favoritemovie;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import id.keepfight.favoritemovie.databinding.ActivityFavoriteDetailBinding;

public class FavoriteDetailActivity extends AppCompatActivity implements Constants {

    ActivityFavoriteDetailBinding viewBinding;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite_detail);
        movie = getIntent().getParcelableExtra(MOVIE_EXTRA);

        if (movie != null) viewBinding.setMovieItem(movie);
    }
}
