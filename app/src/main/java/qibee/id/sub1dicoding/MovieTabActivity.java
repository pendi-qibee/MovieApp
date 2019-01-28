package qibee.id.sub1dicoding;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import qibee.id.sub1dicoding.databinding.ActivityMovieTabBinding;
import qibee.id.sub1dicoding.scheduler.AlarmBroadcastReceiver;
import timber.log.Timber;

import static qibee.id.sub1dicoding.scheduler.AlarmBroadcastReceiver.ONE_TIME_ALARM;
import static qibee.id.sub1dicoding.scheduler.AlarmBroadcastReceiver.REPEATING_ALARM;

public class MovieTabActivity extends AppCompatActivity {

    ActivityMovieTabBinding viewBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_tab);
        setSupportActionBar(viewBinding.toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        viewBinding.container.setAdapter(mSectionsPagerAdapter);
        viewBinding.container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(viewBinding.tabs));
        viewBinding.tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewBinding.container));

        if (App.getInstance().getFirstRun()) {
            setAlarm();
            App.getInstance().setFirstRun(false);
        }

    }

    private void setAlarm() {
        Timber.tag("alarm status: ").d("ready");

        AlarmBroadcastReceiver alarmBroadcastReceiver = new AlarmBroadcastReceiver();

        alarmBroadcastReceiver.setAlarm(this,
                ONE_TIME_ALARM,
                "08:00",
                getString(R.string.release_notificatian_ready));

        alarmBroadcastReceiver.setAlarm(this,
                REPEATING_ALARM,
                "07:00",
                getString(R.string.openapp_message));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_search) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //atur posisi fragment
            return MovieFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 3;
        }


    }
}
