package com.my.foodsafe.services;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OCRService implements IOCRService {

    @Value("${google.vision.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public OCRService() {
        this.restTemplate = new RestTemplate();
    }

    public String processOCR(MultipartFile file) throws IOException {
        // 加载凭证
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\danie\\Downloads\\citric-expanse-425013-h9-d495b37938ab.json"));
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();
        ImageAnnotatorClient client = ImageAnnotatorClient.create(settings);

        // 使用Google Cloud Vision API进行OCR文字识别
        ByteString imgBytes = ByteString.copyFrom(file.getBytes());
        Image image = Image.newBuilder().setContent(imgBytes).build();
        Feature feature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(image).build();

        List<AnnotateImageRequest> requests = new ArrayList<>();
        requests.add(request);

        BatchAnnotateImagesResponse responses = client.batchAnnotateImages(requests);
        AnnotateImageResponse response = responses.getResponsesList().get(0);

        String ocrText = response.getTextAnnotationsList().isEmpty() ? "" : response.getTextAnnotationsList().get(0).getDescription();

        // 使用正则表达式提取品名
        Pattern pattern = Pattern.compile("品名/用途:\\s*([^/]+)");
        Matcher matcher = pattern.matcher(ocrText);
        String productName = "";
        while (matcher.find()) {
            productName = matcher.group(1);
        }

        return "dd";
    }
}
