package com.HaritMitraBack.mitra.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;


    //caching on => for some time we can limit it ,so that at a certain time real value can come
    //for limit kuch time tk fast response real api hit nhi hoi same chij k louye same token k liye ,uske refrtesh hojyega apneap
    @Cacheable(value ="weather", key = "#city.toLowerCase()")

    public Map<String, Object> getWeather(String city) {


//bugging for caching
        System.out.println("calling real weather api");


        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + city + "&appid=" + apiKey + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        // extract data
        Map<String, Object> main = (Map<String, Object>) response.get("main");
        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");

        Map<String, Object> weather = weatherList.get(0);

        // final clean response
        Map<String, Object> result = new HashMap<>();
        result.put("city", response.get("name"));
        result.put("temperature", main.get("temp"));
        result.put("humidity", main.get("humidity"));
        result.put("condition", weather.get("description"));

        return result;
    }
}