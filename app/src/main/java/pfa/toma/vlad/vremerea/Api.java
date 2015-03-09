package pfa.toma.vlad.vremerea;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Vlad on 07-Jan-15.
 */
public interface Api {

    @GET("/weather")
    void getWeather(@Query("q") String cityName, Callback<WeatherResponse> callback);

}
