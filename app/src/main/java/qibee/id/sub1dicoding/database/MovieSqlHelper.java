package qibee.id.sub1dicoding.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import qibee.id.sub1dicoding.provider.FavoritColumn;

public class MovieSqlHelper extends SQLiteOpenHelper {

    Context context;

    private static int VERSI_DB = 1;
    private static String NAMA_DB = "db_film";

    public MovieSqlHelper(Context context) {
        super(context, NAMA_DB, null, VERSI_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_CREATE_TABLE = "create table " + FavoritColumn.TABLE_NAME + " (" +
                FavoritColumn._ID + " integer primary key autoincrement, " +
                FavoritColumn.TITLE + " text not null, " +
                FavoritColumn.BACKDROP + " text not null, " +
                FavoritColumn.POSTER + " text not null, " +
                FavoritColumn.RELEASE_DATE + " text not null, " +
                FavoritColumn.VOTE_AVERAGE + " text not null, " +
                FavoritColumn.VOTE_COUNT + " text not null, " +
                FavoritColumn.OVERVIEW + " text not null " +
                ");";

        db.execSQL(QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE MOVIE IF EXISTS " + FavoritColumn.TABLE_NAME);
        onCreate(db);
    }
}
