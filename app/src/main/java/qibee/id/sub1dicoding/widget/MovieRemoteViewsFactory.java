package qibee.id.sub1dicoding.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import qibee.id.sub1dicoding.Constants;
import qibee.id.sub1dicoding.R;
import qibee.id.sub1dicoding.model.Movie;
import qibee.id.sub1dicoding.provider.Contract;

public class MovieRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private Cursor cursor;



    public MovieRemoteViewsFactory(Context applicationContext, Intent intent) {
        context = applicationContext;
    }

    @Override
    public void onCreate() {
        cursor = context.getContentResolver().query(
                Contract.CONTENT_URI,
                null,
                null,
                null,
                null

        );
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Movie movie = getMovieItem(position);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.movie_app_widgetlist);

        Bitmap bitmap = null;
        try {
            bitmap = Picasso.get().load(Constants.URL_IMAGE_W185 + movie.getPosterPath()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        remoteViews.setImageViewBitmap(R.id.image_widget_favorite, bitmap);
        Bundle bundle = new Bundle();
        bundle.putInt(MovieAppWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(bundle);

        remoteViews.setOnClickFillInIntent(R.id.image_widget_favorite, fillInIntent);
        return remoteViews;
    }

    private Movie getMovieItem(int position) {
        if (!cursor.moveToPosition(position)) throw new IllegalStateException("cursor Invalid");
        return new Movie(cursor);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
