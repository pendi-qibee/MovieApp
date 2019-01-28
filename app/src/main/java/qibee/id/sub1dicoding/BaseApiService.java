package qibee.id.sub1dicoding;

import qibee.id.sub1dicoding.model.Results;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BaseApiService {

    @GET("search/movie")
    Call<Results> search(
            @Query("query") String query,
            @Query("language") String language,
            @Query("api_key") String apiKey);


    @GET("movie/now_playing?language=en&api_key="+BuildConfig.MOVIEDB_API_KEY)
    Call<Results> getNowPlaying();

    @GET("movie/upcoming?language=en&api_key="+BuildConfig.MOVIEDB_API_KEY)
    Call<Results> getUpcoming();

//    @GET("movie/upcoming")
//    Call<Results> getUpcoming(@Query("language") String language,
//                                 @Query("api_key") String apiKey);
}
