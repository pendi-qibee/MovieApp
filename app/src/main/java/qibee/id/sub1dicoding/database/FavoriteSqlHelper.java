package qibee.id.sub1dicoding.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import qibee.id.sub1dicoding.provider.FavoritColumn;

import static android.provider.BaseColumns._ID;

public class FavoriteSqlHelper {

    private static String TABLE_NAME = FavoritColumn.TABLE_NAME;

    private Context context;
    private MovieSqlHelper movieSqlHelper;

    private SQLiteDatabase database;

    public FavoriteSqlHelper(Context context) {
        this.context = context;
    }

    public FavoriteSqlHelper open() throws SQLException {
        movieSqlHelper = new MovieSqlHelper(context);
        database = movieSqlHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        movieSqlHelper.close();
    }

    public Cursor queryProvider() {
        return database.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC"
        );
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(
                TABLE_NAME,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null
        );
    }

    public long insertProvider(ContentValues values) {
        return database.insert(
                TABLE_NAME,
                null,
                values
        );
    }

    public int deleteProvider(String id) {
        return database.delete(
                TABLE_NAME,
                _ID + " = ?",
                new String[]{id}
        );
    }
}
