package qibee.id.sub1dicoding.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class MovieRemoteService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MovieRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
