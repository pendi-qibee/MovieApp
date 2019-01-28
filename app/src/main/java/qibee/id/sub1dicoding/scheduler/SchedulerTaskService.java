package qibee.id.sub1dicoding.scheduler;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import qibee.id.sub1dicoding.BaseApiService;
import qibee.id.sub1dicoding.R;
import qibee.id.sub1dicoding.model.Movie;
import qibee.id.sub1dicoding.model.Results;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static qibee.id.sub1dicoding.Api.getApiService;
import static qibee.id.sub1dicoding.Constants.RELEASE_NOTIFICATION_ID;

public class SchedulerTaskService extends GcmTaskService {

    public static String TAG = "today movies";
    BaseApiService apiService;
    private List<Movie> movieList;

    @Override
    public int onRunTask(TaskParams taskParams) {
        Timber.tag("qibeelog gcmtaskservice").d(" run");
        int result = 0;
        if (taskParams.getTag().equals(TAG)) {
            getMovieData();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }

        return result;
    }

    private void getMovieData() {
        apiService = getApiService();
        apiService.getUpcoming().enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        movieList = response.body().getMovies();
                        checkTodayRelease(movieList);
                    }
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {

            }
        });
    }

    private void checkTodayRelease(List<Movie> movieList) {

        Date date = Calendar.getInstance().getTime();
        String dateString;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        dateString = df.format(date); //result=2018-12-28
        Timber.d(dateString);


        StringBuilder sb = new StringBuilder();
        String todayMovies = "";
        String  dates;
        List<Movie> todayMovie = new ArrayList<>();
        for (Movie movie : movieList) {
            dates = movie.getReleaseDate();
            if (dates.equals(dateString)) {
                todayMovie.add(movie);
                sb.append(movie.getTitle()).append(", ");

                Timber.d("today release%s", movie.getTitle());
            }
        }

        if (sb.length() > 0) {
            todayMovies = sb.toString();
        }
        if (todayMovie.size()!=0) notifyUser(todayMovies);
    }


    private void notifyUser(String todayMovies) {
        Timber.d("today release : %s", todayMovies);

        NotificationHelper.showNotification(getApplicationContext(),
                getString(R.string.release_notification_title),
                getString(R.string.release_notification_text) +todayMovies,
                RELEASE_NOTIFICATION_ID
                );
    }
}
