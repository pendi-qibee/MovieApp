package qibee.id.sub1dicoding;

import android.app.Application;
import android.content.SharedPreferences;

import qibee.id.sub1dicoding.logging.NotLoggingTree;
import qibee.id.sub1dicoding.logging.TimberLogImplementation;
import timber.log.Timber;

public class App extends Application {
    private static App app;
    private static final String PREF = "movieapp_pref";
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        sharedPref = this.getSharedPreferences(PREF, MODE_PRIVATE);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            TimberLogImplementation.init();
        } else {
            Timber.plant(new NotLoggingTree());
        }
    }

    public static synchronized App getInstance() {
        return app;
    }

    public void setFirstRun(Boolean firstRunStatus) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(PREF, firstRunStatus);
        editor.apply();
    }

    public Boolean getFirstRun() {
        return sharedPref.getBoolean(PREF, true);
    }

}
