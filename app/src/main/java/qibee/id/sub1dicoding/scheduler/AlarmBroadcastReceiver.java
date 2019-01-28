package qibee.id.sub1dicoding.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

import qibee.id.sub1dicoding.Constants;
import qibee.id.sub1dicoding.R;

public class AlarmBroadcastReceiver extends BroadcastReceiver implements Constants {
    public static final String ONE_TIME_ALARM = "one_time_alarm";
    public static final String REPEATING_ALARM = "repeating_alarm";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";

    public AlarmBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        String notificationTitle = context.getResources().getString(R.string.app_name);
        int notificationId = type.equalsIgnoreCase(ONE_TIME_ALARM) ? RELEASE_NOTIFICATION_ID : DAILY_NOTIFICATION_ID;

        if (notificationId == DAILY_NOTIFICATION_ID) {
            NotificationHelper.showNotification(context, notificationTitle, message, notificationId);

        } else {
            NotificationHelper.showNotification(context, notificationTitle, message, notificationId);

            SchedulerManager schedulerManager = new SchedulerManager(context);
            schedulerManager.createPeriodicTask();

        }

    }


    public void setAlarm(Context context, String type, String time, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        PendingIntent pendingIntent = null;

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        if (type.equals(ONE_TIME_ALARM)) {
            pendingIntent = PendingIntent.getBroadcast(context, RELEASE_NOTIFICATION_ID, intent, 0);

        } else if (type.equals(REPEATING_ALARM)) {

            pendingIntent = PendingIntent.getBroadcast(context, DAILY_NOTIFICATION_ID, intent, 0);
        }

        if (calendar.before(Calendar.getInstance())) calendar.add(Calendar.DATE, 1);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        }
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


    }

}
