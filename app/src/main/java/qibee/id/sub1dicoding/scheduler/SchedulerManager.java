package qibee.id.sub1dicoding.scheduler;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class SchedulerManager {

    private GcmNetworkManager gcmNetworkManager;

    SchedulerManager(Context context) {
        gcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask() {
        Task periodicTask = new PeriodicTask.Builder()
                .setService(SchedulerTaskService.class)
                .setPeriod(24 * 60 * 60L)
                .setFlex(10)
                .setTag(SchedulerTaskService.TAG)
                .setPersisted(true)
                .build();
        gcmNetworkManager.schedule(periodicTask);
    }

}
