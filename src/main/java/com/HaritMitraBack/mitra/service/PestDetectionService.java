package com.HaritMitraBack.mitra.service;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.HaritMitraBack.mitra.dto.PestResponseDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.CompletableFuture;

@Service
public class PestDetectionService {

    private static final String API_KEY = "2N5FACqxAglTfc8UqegX";
    private static final String MODEL = "pest-detection-v9gfo/4";

    @Autowired
    private AIService aiService;

    public PestResponseDTO detect(MultipartFile file) {

        PestResponseDTO result = new PestResponseDTO();

        try {
            // 🔹 file convert
            File tempFile = File.createTempFile("upload", ".jpg");
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(file.getBytes());
            }

            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                            "file",
                            tempFile.getName(),
                            RequestBody.create(tempFile, MediaType.parse("image/jpeg"))
                    )
                    .build();

            String url = "https://detect.roboflow.com/" + MODEL + "?api_key=" + API_KEY;

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();

            if (response.body() == null) {
                result.setMessage("No response from detection API");
                return result;
            }

            String responseBody = response.body().string();

            JSONObject json = new JSONObject(responseBody);
            JSONArray predictions = json.getJSONArray("predictions");

            // ❌ No pest
            if (predictions.length() == 0) {
                result.setMessage("No pest detected. Try clearer image.");
                return result;
            }

            // ✅ Pest found
            JSONObject first = predictions.getJSONObject(0);

            String pest = first.getString("class");
            double confidence = first.getDouble("confidence");

            result.setPest(pest);
            result.setConfidence(confidence);



            return result;

        } catch (Exception e) {
            result.setMessage("Error: " + e.getMessage());
            return result;
        }
    }



    // 🔥 AI method
    private String getTreatmentFromAI(String pest) {

        String prompt = "Pest detected: " + pest +
                ". Give treatment, pesticide, and prevention in simple farmer-friendly language.";

        return  aiService.askAI(prompt);

    }
}