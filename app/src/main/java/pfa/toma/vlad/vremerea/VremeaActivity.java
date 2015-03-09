package pfa.toma.vlad.vremerea;

import pfa.toma.vlad.vremerea.util.SystemUiHider;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;


public class VremeaActivity extends Activity {

    protected TextView cityView, mainView, descriptionView;

    protected ImageView backgroundImage;

    protected String location = "Iasi, RO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_vremea);

        backgroundImage         = (ImageView)findViewById(R.id.background_image);

        this.setDayImage();

        cityView           = (TextView)findViewById(R.id.city);
        mainView           = (TextView)findViewById(R.id.main);
        descriptionView    = (TextView)findViewById(R.id.description);



        LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        Criteria locationCriteria       = new Criteria();

        locationCriteria.setPowerRequirement(Criteria.POWER_LOW);
        locationCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
        locationCriteria.setCostAllowed(false);



        String provider                 = locationManager.getBestProvider(locationCriteria, true);
        Log.i("App", "povider - " + provider);
        final Location loc    = locationManager.getLastKnownLocation(provider);
        Log.i("App", "LOC00 - " + loc);
        if(loc == null) {
            Log.i("App", "Requesting update - ");
            locationManager.requestSingleUpdate(provider, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.i("App", "LOC01 - " + location);
                    loc.set(location);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.i("App", "LL - sta cha");
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.i("App", "LL - prov en");
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.i("App", "LL - prov dis");
                }
            }, null);
        }

        Log.i("App", "LOC1 - " + loc);

        fetchAndDisplayWeather(this.location);

        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }


    protected void fetchAndDisplayWeather(String location){

        RestClient.get().getWeather(location, new Callback<WeatherResponse>() {
            @Override
            public void success(WeatherResponse weatherResponse, Response response) {

                displayWeather(weatherResponse);

                Log.i("App", "Main - " + weatherResponse.getWeather().get(0).getMain());
                Log.i("App", "Name - " + weatherResponse.getName());

                Calendar c  = Calendar.getInstance();
                long currentTime = c.getTime().getTime() / 1000;

                if((currentTime > weatherResponse.getSys().getSunset()) || (currentTime < weatherResponse.getSys().getSunrise())) {
                    // it is night
                    setNightImage();
                }

            }

            @Override
            public void failure(RetrofitError error) {

                Log.i("App", "Something went wrung! - " + error.getMessage());

            }
        });
    }

    protected void displayWeather(WeatherResponse weatherResponse){
        cityView.setText(weatherResponse.getName());
        mainView.setText(weatherResponse.getWeather().get(0).getMain());
        descriptionView.setText(weatherResponse.getWeather().get(0).getDescription());
    }

    protected void setDayImage() {
        Picasso.with(this.getBaseContext()).load(R.drawable.sunny_750_1000).into(backgroundImage);
    }

    protected void setNightImage(){
        Picasso.with(this.getBaseContext()).load(R.drawable.night_750_1000).into(backgroundImage);
    }



}
