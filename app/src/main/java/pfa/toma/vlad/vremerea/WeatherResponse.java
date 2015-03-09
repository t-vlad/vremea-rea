package pfa.toma.vlad.vremerea;

import java.util.List;

/**
 * Created by Vlad on 07-Jan-15.
 */
public class WeatherResponse {

    private int cod;
    private List<Weather> weather;
    private String name;
    private WeatherSys sys;

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weatherItems) {
        this.weather = weather;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeatherSys getSys() {
        return sys;
    }

    public void setSys(WeatherSys weatherSys) {
        this.sys = weatherSys;
    }

}
