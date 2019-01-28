package qibee.id.sub1dicoding;

public class Api implements Constants{

    public static BaseApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
