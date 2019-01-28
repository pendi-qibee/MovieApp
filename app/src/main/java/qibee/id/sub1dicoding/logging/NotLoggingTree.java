package qibee.id.sub1dicoding.logging;
import timber.log.Timber;

public class NotLoggingTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {

    }
}
