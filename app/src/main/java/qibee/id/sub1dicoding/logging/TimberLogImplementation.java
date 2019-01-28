package qibee.id.sub1dicoding.logging;

import timber.log.Timber;


public class TimberLogImplementation implements TimberLog{


    public static void init() {
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                return String.format("qibeelog:%s:%s",
                        super.createStackElementTag(element),
                        element.getLineNumber());
            }
        });
    }
}
