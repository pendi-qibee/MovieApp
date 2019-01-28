package qibee.id.sub1dicoding.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import qibee.id.sub1dicoding.database.FavoriteSqlHelper;

public class FavoriteContentProvider extends ContentProvider {
    private static final int FAVORITE_CODE = 1;
    private static final int FAVORITE_ID_CODE = 3;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(Contract.AUTHORITY, FavoritColumn.TABLE_NAME, FAVORITE_CODE);
        sUriMatcher.addURI(Contract.AUTHORITY, FavoritColumn.TABLE_NAME + "/#", FAVORITE_ID_CODE);
    }

    private FavoriteSqlHelper favoriteMovieSqlHelper;

    @Override
    public boolean onCreate() {
        favoriteMovieSqlHelper = new FavoriteSqlHelper(getContext());
        favoriteMovieSqlHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE_CODE:
                cursor = favoriteMovieSqlHelper.queryProvider();
                break;

            case FAVORITE_ID_CODE:
                cursor = favoriteMovieSqlHelper.queryByIdProvider(uri.getLastPathSegment());
                break;

            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE_CODE:
                added = favoriteMovieSqlHelper.insertProvider(values);
                break;

            default:
                added = 0;
                break;
        }


        if (added > 0) {
            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);

            }
        }

        return Uri.parse(Contract.CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int idDeleted;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE_ID_CODE:
                idDeleted = favoriteMovieSqlHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                idDeleted = 0;
                break;
        }
        if (idDeleted > 0) {
            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);

            }
        }
        return idDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
