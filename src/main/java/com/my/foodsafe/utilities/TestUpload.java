package com.my.foodsafe.utilities;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class TestUpload implements ITestUpload {

    private static final String API_KEY = "AIzaSyAi7cV_WuMVSgH71QPLZYh1DcNi4oOHT-c";
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/photo";
    public static String directoryName = System.getProperty("user.dir");
    public static String uPhotoPrefix = "/src/main/resources/images/";

    public void savePhotoLocally(String photoReference) {
        String url = BASE_URL + "?maxwidth=400&photoreference=" + photoReference + "&key=" + API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            byte[] photoData = response.getBody();
            String fileName = "photo_" + System.currentTimeMillis() + ".jpg"; // 可以根据需要修改文件名

            try {
                Files.createDirectories(Paths.get(directoryName + uPhotoPrefix));
                FileOutputStream fos = new FileOutputStream(directoryName + uPhotoPrefix + fileName);
                fos.write(photoData);
                fos.close();
                System.out.println("Photo saved successfully: " + directoryName + uPhotoPrefix + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to save photo.");
            }
        } else {
            System.out.println("Failed to fetch photo.");
        }
    }

}
