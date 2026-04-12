package com.HaritMitraBack.mitra.service;

import com.HaritMitraBack.mitra.dto.SchemeDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchemeService {

    private final List<SchemeDTO> schemes = List.of(



            // 👨‍🌾 FARMER
            new SchemeDTO("PM-KISAN Samman Nidhi", "FARMER", "Active", "https://pmkisan.gov.in"),
            new SchemeDTO("Kisan Credit Card", "FARMER", "Active", "https://www.myscheme.gov.in/schemes/kcc"),
            new SchemeDTO("Ayushman Bharat Yojana", "FARMER", "Active", "https://pmjay.gov.in"),
            new SchemeDTO("PM Kisan Maandhan Yojana", "FARMER", "Active", "https://maandhan.in"),
            new SchemeDTO("National Livestock Mission", "FARMER", "Active", "https://nlm.udyamimitra.in"),
            new SchemeDTO("Dairy Entrepreneurship Development Scheme", "FARMER", "Active", "https://nabard.org"),
            new SchemeDTO("PM Formalisation of Micro Food Processing Enterprises", "FARMER", "Active", "https://pmfme.mofpi.gov.in"),
            new SchemeDTO("National Mission on Edible Oils", "FARMER", "Active", "https://nmeo.dac.gov.in"),
            new SchemeDTO("Atmanirbhar Bharat Abhiyan Agriculture", "FARMER", "Active", "https://www.india.gov.in"),
            new SchemeDTO("Farmer Producer Organisation Scheme (FPO)", "FARMER", "Active", "https://sfacindia.com"),

            // 🌾 AGRICULTURE
            new SchemeDTO("Pradhan Mantri Fasal Bima Yojana", "AGRICULTURE", "Active", "https://pmfby.gov.in"),
            new SchemeDTO("Soil Health Card Scheme", "AGRICULTURE", "Active", "https://soilhealth.dac.gov.in"),
            new SchemeDTO("PM Krishi Sinchai Yojana", "AGRICULTURE", "Active", "https://pmksy.gov.in"),
            new SchemeDTO("e-NAM", "AGRICULTURE", "Active", "https://www.enam.gov.in"),
            new SchemeDTO("Paramparagat Krishi Vikas Yojana", "AGRICULTURE", "Active", "https://pgsindia-ncof.gov.in"),
            new SchemeDTO("National Mission on Sustainable Agriculture", "AGRICULTURE", "Active", "https://nmsa.dac.gov.in"),
            new SchemeDTO("Sub-Mission on Agricultural Mechanization", "AGRICULTURE", "Active", "https://agrimachinery.nic.in"),
            new SchemeDTO("Rashtriya Krishi Vikas Yojana", "AGRICULTURE", "Active", "https://rkvy.nic.in"),
            new SchemeDTO("National Food Security Mission", "AGRICULTURE", "Active", "https://nfsm.gov.in"),
            new SchemeDTO("National Beekeeping and Honey Mission", "AGRICULTURE", "Active", "https://nbhm.gov.in"),
            new SchemeDTO("Integrated Scheme for Agricultural Marketing", "AGRICULTURE", "Active", "https://agmarknet.gov.in"),
            new SchemeDTO("Mission for Integrated Development of Horticulture", "AGRICULTURE", "Active", "https://midh.gov.in"),
            new SchemeDTO("Blue Revolution Scheme", "AGRICULTURE", "Active", "https://dof.gov.in"),
            new SchemeDTO("Pradhan Mantri Matsya Sampada Yojana", "AGRICULTURE", "Active", "https://dof.gov.in"),
            new SchemeDTO("Agri Infrastructure Fund", "AGRICULTURE", "Active", "https://agriinfra.dac.gov.in"),
            new SchemeDTO("National Bamboo Mission", "AGRICULTURE", "Active", "https://nbm.nic.in"),
            new SchemeDTO("Rainfed Area Development Programme", "AGRICULTURE", "Active", "https://nmsa.dac.gov.in"),
            new SchemeDTO("Seed Village Programme", "AGRICULTURE", "Active", "https://seednet.gov.in"),
            new SchemeDTO("Organic Farming Scheme India", "AGRICULTURE", "Active", "https://pgsindia-ncof.gov.in"),
            new SchemeDTO("Crop Diversification Programme", "AGRICULTURE", "Active", "https://agricoop.nic.in")
    );


    //caching on
    @Cacheable(value ="schemes" , key="#category !=null ? #category :'all' ")
    public List<SchemeDTO> getSchemes(String category) {

        if(category ==null || category.isEmpty()){
            return schemes;
        }

        List<SchemeDTO> result = schemes.stream()
                .filter(s -> s.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new RuntimeException("No schemes found for category: " + category);
        }

        return result;
    }
}