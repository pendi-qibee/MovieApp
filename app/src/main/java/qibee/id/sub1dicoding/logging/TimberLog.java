package qibee.id.sub1dicoding.logging;

import java.util.Map;

public interface TimberLog {
     static void init(){};
     static void init(final Map<String, String> userInformation){};
}
