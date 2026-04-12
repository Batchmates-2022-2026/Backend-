package com.HaritMitraBack.mitra.service;

import com.HaritMitraBack.mitra.dto.MarketPriceDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class MarketPriceService {
    @Value("${agmarknet.api.key}")
    private String API_KEY;


    //caching on
    @Cacheable(value="market", key ="#crop + '-' + (#state != null ? #state : 'all' )")
    public List<MarketPriceDTO> getPrices(String crop, String state) {

        String url = "https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070"
                + "?api-key=" + API_KEY
                + "&format=json"
                + "&limit=1000";

        RestTemplate restTemplate = new RestTemplate();
        Map response = restTemplate.getForObject(url, Map.class);

        if (response == null || response.get("records") == null) {
            throw new RuntimeException("No mandi data found for crop: " + crop + " in state: " + state);
        }

        List<Map<String, Object>> records =
                (List<Map<String, Object>>) response.get("records");

        List<MarketPriceDTO> result = new ArrayList<>();
        List<MarketPriceDTO> allData = new ArrayList<>();

        for (Map<String, Object> record : records) {
            String apiState = record.get("state").toString().trim();
            String apiCommodity = record.get("commodity").toString().trim();

            MarketPriceDTO dto = new MarketPriceDTO(
                    record.get("market").toString(),
                    record.get("district").toString(),apiState,

                    record.get("modal_price").toString()
            );

            allData.add(dto);

            // ✅ FILTER
            if (apiCommodity.equalsIgnoreCase(crop.trim())) {

                if (state == null || state.isEmpty() ||
                        apiState.equalsIgnoreCase(state.trim())) {

                    result.add(dto);
                }
            }
            }


        // ✅ NO DATA CASE
        if (result.isEmpty()) {
            throw new RuntimeException("No mandi data found for crop: " + crop +
                    (state != null ? " in state: " + state : ""));
        }

        // ✅ FINAL RETURN (INSIDE METHOD)
        return state == null ? allData : result;
    }
}